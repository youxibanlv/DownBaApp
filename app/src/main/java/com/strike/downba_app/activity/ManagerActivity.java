package com.strike.downba_app.activity;

import android.view.View;

import com.strike.downba_app.base.BaseActivity;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by strike on 2016/12/4.
 */
@ContentView(R.layout.activity_manager)
public class ManagerActivity extends BaseActivity {

    @Event(value = {R.id.ll_update,R.id.ll_down,R.id.ll_help})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.ll_update:

                break;
            case R.id.ll_down:

                break;
            case R.id.ll_help:

                break;
        }
    }

}
