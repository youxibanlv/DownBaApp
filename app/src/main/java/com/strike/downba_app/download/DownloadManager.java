package com.strike.downba_app.download;


import com.strike.downba_app.base.AppConfig;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.view.DownloadBtn;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.LogUtil;
import org.xutils.db.converter.ColumnConverterFactory;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Author: wyouflf
 * Date: 13-11-10
 * Time: 下午8:10
 */
public final class DownloadManager {

    static {
        // 注册DownloadState在数据库中的值类型映射
        ColumnConverterFactory.registerColumnConverter(DownloadState.class, new DownloadStateConverter());
    }

    private static volatile DownloadManager instance;

    private final static int MAX_DOWNLOAD_THREAD = 2; // 有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.

    private final DbManager db;
    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD, true);
    private final List<DownloadInfo> downloadInfoList = new ArrayList<DownloadInfo>();
    private final ConcurrentHashMap<DownloadInfo, DownloadCallback>
            callbackMap = new ConcurrentHashMap<DownloadInfo, DownloadCallback>(5);

    private DownloadManager() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("download")
                .setDbVersion(1);
        db = x.getDb(daoConfig);
        try {
            List<DownloadInfo> infoList = db.selector(DownloadInfo.class).findAll();
            if (infoList != null) {
                for (DownloadInfo info : infoList) {
                    if (info.getState().value() < DownloadState.FINISHED.value()) {
                        info.setState(DownloadState.STOPPED);
                    }
                    downloadInfoList.add(info);
                }
            }
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
    }

    /*package*/
    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void updateDownloadInfo(DownloadInfo info) {
        try {
            db.update(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public int getDownloadListCount() {
        return downloadInfoList.size();
    }

    public DownloadInfo getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }
    public DownloadInfo getDownloadInfo(App app) {
        DownloadInfo info = null;
        try {
            info = db.selector(DownloadInfo.class).where("objId","=",app.getApp_id()).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }
    public synchronized void startDownload(String url, App app, DownloadBtn viewHolder) {
        String objId = app.getApp_id();
        String path = AppConfig.getBasePath()+"apk/";
        String savePath = path+app.getApp_title()+".apk";
        boolean autoResume = true;
        boolean autoRename = true;

        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        String fileSavePath = new File(savePath).getAbsolutePath();
        DownloadInfo downloadInfo = null;
        try {
            downloadInfo = db.selector(DownloadInfo.class)
                    .where("objId", "=", objId)
                    .and("fileSavePath", "=", fileSavePath)
                    .findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (downloadInfo != null) {
            DownloadCallback callback = callbackMap.get(downloadInfo);
            if (callback != null) {
                if (viewHolder == null) {
                    return;
                }
                if (callback.switchViewHolder(viewHolder)) {
                    return;
                } else {
                    callback.cancel();
                }
            }
        }

        // create download info
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo();
            downloadInfo.setUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setObjId(objId);
            downloadInfo.setFileSavePath(fileSavePath);
            try {
                db.saveBindingId(downloadInfo);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

        // start downloading
        if (viewHolder == null) {
//            viewHolder = new DefaultDownloadViewHolder(null, downloadInfo);
        } else {
            viewHolder.update(downloadInfo);
        }
        DownloadCallback callback = new DownloadCallback(viewHolder);
        callback.setDownloadManager(this);
        callback.switchViewHolder(viewHolder);
        RequestParams params = new RequestParams(url);
        params.setAutoResume(downloadInfo.isAutoResume());
        params.setAutoRename(downloadInfo.isAutoRename());
        params.setSaveFilePath(downloadInfo.getFileSavePath());
        params.setExecutor(executor);
        params.setCancelFast(true);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        callback.setCancelable(cancelable);
        callbackMap.put(downloadInfo, callback);

        if (downloadInfoList.contains(downloadInfo)) {
            int index = downloadInfoList.indexOf(downloadInfo);
            downloadInfoList.remove(downloadInfo);
            downloadInfoList.add(index, downloadInfo);
        } else {
            downloadInfoList.add(downloadInfo);
        }
    }

    public void stopDownload(int index) {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        stopDownload(downloadInfo,null);
    }

    public void stopDownload(DownloadInfo downloadInfo,DownloadBtn viewHolder) {
        Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
        if (cancelable != null) {
            cancelable.cancel();
            downloadInfo.setState(DownloadState.STOPPED);
                updateDownloadInfo(downloadInfo);
        }else {
            DownloadCallback callback = new DownloadCallback(viewHolder);
            callback.setDownloadManager(this);
            viewHolder.update(downloadInfo);
            callback.switchViewHolder(viewHolder);
            RequestParams params = new RequestParams(downloadInfo.getUrl());
            params.setAutoResume(downloadInfo.isAutoResume());
            params.setAutoRename(downloadInfo.isAutoRename());
            params.setSaveFilePath(downloadInfo.getFileSavePath());
            params.setExecutor(executor);
            params.setCancelFast(true);
            cancelable = x.http().get(params, callback);
            callback.setCancelable(cancelable);
            callbackMap.put(downloadInfo, callback);
        }
    }

    public void stopAllDownload() {
        for (DownloadInfo downloadInfo : downloadInfoList) {
            Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
            if (cancelable != null) {
                cancelable.cancel();
            }
        }
    }

    public void removeDownload(int index) throws DbException {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        db.delete(downloadInfo);
        stopDownload(downloadInfo,null);
        downloadInfoList.remove(index);
    }

    public void removeDownload(DownloadInfo downloadInfo) {
        try {
            db.delete(downloadInfo);
            stopDownload(downloadInfo,null);
            downloadInfoList.remove(downloadInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
