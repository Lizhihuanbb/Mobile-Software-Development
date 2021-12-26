package com.jnu.mybill.Module;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.jnu.mybill.R;

public class MyKeyBorad {
    private KeyboardView keyboardView;
    private EditText editText;
    private final Keyboard keyBoard;

    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public MyKeyBorad(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);
        keyBoard = new Keyboard(this.editText.getContext(), R.xml.key);

        this.keyboardView.setKeyboard(keyBoard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }
    KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            Editable editable=editText.getText();
            int start=editText.getSelectionStart();
            switch (i){
                case Keyboard.KEYCODE_DELETE:
                    if (editable!=null && editable.length()>0){
                        if (start>0){
                            editable.delete(start-1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
                    onEnsureListener.onEnsure();
                    break;
                default:
                    editable.insert(start,Character.toString((char)i));
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
    };

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
