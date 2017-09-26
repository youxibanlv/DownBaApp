package com.strike.downba_app.activity;

import android.support.v7.widget.RecyclerView;

import com.strike.downba_app.base.BaseActivity;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by strike on 2017/6/5.
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewInject(R.id.content)
    private RecyclerView content;

}
