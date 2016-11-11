package com.strike.downba_app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.strike.downbaapp.R;


/**
 * Created by jacky on 16/3/8.
 */
public class YesNoDialog extends Dialog {

    private TextView leftBtn = null;
    private TextView rightBtn = null;
    private TextView dialogContent = null;
    private TextView dialog_title = null;
    private String title = null;
    private String content = null;
    private String leftBtnMsg = null;
    private String rightBtnMsg = null;
    private boolean canCancel = true;

    private CustomDialogClickListener leftClickListener = null;
    private CustomDialogClickListener rightClickListener = null;

    public YesNoDialog(Context context) {
        super(context);
    }

    public YesNoDialog(Context context, boolean canCancel, CustomDialogClickListener leftClickListener, CustomDialogClickListener rightClickListener) {
        super(context);
        this.leftClickListener = leftClickListener;
        this.rightClickListener = rightClickListener;
        this.canCancel = canCancel;
    }

    public YesNoDialog(Context context, String title, String content, String leftBtnMsg, String rightBtnMsg, boolean canCancel, CustomDialogClickListener leftClickListener, CustomDialogClickListener rightClickListener) {
        super(context);
        this.title = title;
        this.content = content;
        this.leftBtnMsg = leftBtnMsg;
        this.rightBtnMsg = rightBtnMsg;
        this.canCancel = canCancel;
        this.leftClickListener = leftClickListener;
        this.rightClickListener = rightClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gw_dialog_yesno);
        setCancelable(canCancel);
        leftBtn = (TextView) findViewById(R.id.left_btn);
        rightBtn = (TextView) findViewById(R.id.right_btn);
        dialogContent = (TextView) findViewById(R.id.dialog_content);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.click(YesNoDialog.this);
                } else {
                    dismiss();
                }
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightClickListener != null) {
                    rightClickListener.click(YesNoDialog.this);
                } else {
                    dismiss();
                }
            }
        });
        if (!TextUtils.isEmpty(leftBtnMsg)) {
            leftBtn.setText(leftBtnMsg);
        }

        if (!TextUtils.isEmpty(rightBtnMsg)) {
            rightBtn.setText(rightBtnMsg);
        }
        if (!TextUtils.isEmpty(content)) {
            dialogContent.setText(content);
        }
        if (!TextUtils.isEmpty(title)){
            dialog_title.setText(title);
        }
    }

    public void setContentLayout(int gravity) {
        dialogContent.setGravity(gravity);
    }

    public void setMessage(String msg) {
        dialogContent.setText(msg);
    }

    public TextView getDialogContent() {
        return dialogContent;
    }
}
