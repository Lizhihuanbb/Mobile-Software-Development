package com.jnu.booklistmainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        EditText editText=findViewById(R.id.edit_name);

        Intent intent1=getIntent();
        int position=intent1.getIntExtra("position",0);
        String book_name =intent1.getStringExtra("name");


        if(book_name !=null){
            editText.setHint("Old:"+ book_name);
        }


        Button enter =findViewById(R.id.enter_button);
        Button cancel =findViewById(R.id.cancel_button);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("name",editText.getText().toString());
                intent.putExtra("position",position);
                setResult(BookFragment.RESULT_CODE_ADD,intent);
                EditBookActivity.this.finish();
            }
        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditBookActivity.this.finish();
//            }
//        });
    }
}