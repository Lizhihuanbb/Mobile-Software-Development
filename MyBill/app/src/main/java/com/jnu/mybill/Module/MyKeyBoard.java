package com.jnu.mybill.Module;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.jnu.mybill.R;

public class MyKeyBoard implements KeyboardView.OnKeyboardActionListener{
    private KeyboardView keyboardView;
    private EditText editText;
    private final Keyboard keyBoard;
    private Boolean STATES=false;

    public Boolean getSTATES() {
        return STATES;
    }

    public void setSTATES(Boolean STATES) {
        this.STATES = STATES;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
            Editable editable = editText.getText();
            if (editText.getText()!=null&&STATES==true){
                editable.clear();
                setSTATES(false);
            }
            editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (i) {
                case Keyboard.KEYCODE_DELETE:   //点击了删除键
                    if (editable!=null &&editable.length()>0) {
                        if (start>0) {
                            editable.delete(start-1,start);
                        }
                    }
                    break;
                case -100:   //点击了清零
                    editable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:    //点击了完成
                    onEnsureListener.onEnsure();   //通过接口回调的方法，当点击确定时，可以调用这个方法
                    break;
                default:  //其他数字直接插入
                    editable.insert(start,Character.toString((char)i));
                    break;
            }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public MyKeyBoard(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);
        keyBoard = new Keyboard(this.editText.getContext(), R.xml.key);

        this.keyboardView.setKeyboard(keyBoard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(this);
    }

    public void showKeyboard(){
        int visibility=keyboardView.getVisibility();
        if(visibility ==View.INVISIBLE||visibility==View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    public void hideKeyboard(){
        int visibility=keyboardView.getVisibility();
        if(visibility ==View.VISIBLE||visibility==View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }
}
