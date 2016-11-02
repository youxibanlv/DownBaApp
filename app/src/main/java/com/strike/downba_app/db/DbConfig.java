package com.strike.downba_app.db;


import com.strike.downba_app.base.AppConfig;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by strike on 16/5/31.
 */
public class DbConfig {

    public static final DbManager.DaoConfig APP_DB_CONFIG = new DbManager.DaoConfig()
            .setDbName(AppConfig.DEFAULT_APP_DB)
            .setDbVersion(AppConfig.DEFAULT_DB_VERSION)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
            try {
                db.dropDb();
            } catch (DbException e) {
                e.printStackTrace();

            }
        }
    });
}
