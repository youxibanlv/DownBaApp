package com.strike.downba_app.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.strike.downbaapp.R;


public class TipDialog extends Dialog {

    private Spanned content;
    private boolean canCancel = true;
    private TextView dialogContent = null;


    public TipDialog(Context context) {
        super(context);
    }

    public TipDialog(Context context, boolean canCancel) {
        super(context);
        this.canCancel = canCancel;
    }

    public TipDialog(Context context, Spanned content, boolean canCancel) {
        super(context);
        this.content = content;
        this.canCancel = canCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gw_dialog_tip);
        setCancelable(canCancel);
        dialogContent = (TextView) findViewById(R.id.dialog_content);

        if (!TextUtils.isEmpty(content)) {
            dialogContent.setText(content);
        }

    }

    public void refreshContent(Spanned content) {
        dialogContent.setText(content);
    }

}
