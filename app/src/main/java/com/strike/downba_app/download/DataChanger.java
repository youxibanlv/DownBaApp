package com.strike.downba_app.download;

import java.util.Observable;

/**
 * Created by CharmingLee on 2015/5/28.
 */
public class DataChanger extends Observable {
    private static DataChanger dataChanger;

    public static DataChanger getInstance(){
        if(dataChanger == null){
            dataChanger = new DataChanger();
        }
        return dataChanger;
    }

    public synchronized void notifyDownloadDataChange(DownloadInfo info){
        setChanged();
        notifyObservers(info);
    }
}
