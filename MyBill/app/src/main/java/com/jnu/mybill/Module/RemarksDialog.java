package com.jnu.mybill.Module;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.jnu.mybill.R;

public class RemarksDialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancelBtn,ensureBtn;
    MyKeyBorad.OnEnsureListener onEnsureListener;


    public interface OnEnsureListener{
        public void onEnsure();
    }

    public void setOnEnsureListener(MyKeyBorad.OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public RemarksDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remarks);//设置对话框显示布局
        et = findViewById(R.id.dialog_remarks_et);
        cancelBtn = findViewById(R.id.dialog_remarks_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_remarks_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_remarks_btn_cancel:
                cancel();
                break;
            case R.id.dialog_remarks_btn_ensure:
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }
    //    获取输入数据的方法
    public String getEditText(){
        return et.getText().toString().trim();
    }
    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            //自动弹出软键盘的方法
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
