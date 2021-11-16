package com.jnu.booklistmainactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.booklistmainactivity.data.Book;
import com.jnu.booklistmainactivity.data.DataBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static final int RESULT_CODE_ADD = 996;
    private List<Book> books;
    private CustomAdapter customAdapter;

//    The old way get activity reslult:
//    public static final int REQUEST_CODE_ADD = 1;
//    public static final int REQUEST_CODE_UPDATE =REQUEST_CODE_ADD+1;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode== REQUEST_CODE_ADD){
//            if(resultCode== RESULT_CODE_ADD){
//                String bookname=data.getStringExtra("name");
//                int position=data.getIntExtra("position",books.size());
//                books.add(position,new Book(bookname,R.drawable.book_no_name));
//                customAdapter.notifyItemInserted(position);
//            }
//        }
//        else if (requestCode== REQUEST_CODE_UPDATE){
//            if (resultCode==RESULT_CODE_ADD){
//                String bookname=data.getStringExtra("name");
//                int position=data.getIntExtra("position",books.size());
//                books.get(position).setName(bookname);
//                customAdapter.notifyItemChanged(position);
//            }
//        }
//    }
    private ActivityResultLauncher<Intent> launcher_add=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if(resultCode== RESULT_CODE_ADD){
                String bookname=data.getStringExtra("name");
                int position=data.getIntExtra("position",books.size());
                books.add(position,new Book(bookname,R.drawable.book_no_name));
                dataBank.saveData();
                customAdapter.notifyItemInserted(position);
            }
        }
    });
    private ActivityResultLauncher<Intent> launcher_edit=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if (resultCode==RESULT_CODE_ADD){
                String bookname=data.getStringExtra("name");
                int position=data.getIntExtra("position",books.size());
                books.get(position).setName(bookname);
                dataBank.saveData();
                customAdapter.notifyItemChanged(position);
            }
        }
    });
    private DataBank dataBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EditBookActivity.class);
                intent.putExtra("position",books.size());
                launcher_add.launch(intent);
            }
        });

        customAdapter =new CustomAdapter(books);
        RecyclerView recyclerView=findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);
    }
    public void initData(){
        dataBank = new DataBank(MainActivity.this);
        books= dataBank.loadData();
    }
    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
        private List<Book> books;

        public CustomAdapter(List<Book> books){
            this.books=books;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int MENU_ADD = 1;
            public static final int MENU_UPDATE = MENU_ADD+1;
            public static final int MENU_DELETE = MENU_ADD+2;
            private final ImageView imageView;
            private final TextView textView;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.imageView = (ImageView) itemView.findViewById(R.id.image_view_book_cover);
                this.textView = (TextView) itemView.findViewById(R.id.text_view_book_title);

                itemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextView() {
                return textView;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuItem add=contextMenu.add(contextMenu.NONE,MENU_ADD,MENU_ADD,"Add");
                MenuItem edit=contextMenu.add(contextMenu.NONE, MENU_UPDATE, MENU_UPDATE,"Update");
                MenuItem delete=contextMenu.add(contextMenu.NONE,MENU_DELETE,MENU_DELETE,"Delete");
                add.setOnMenuItemClickListener(this);
                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position=getAdapterPosition();
                Intent intent= new Intent(MainActivity.this,EditBookActivity.class);
                switch (menuItem.getItemId()){
                    case MENU_ADD:
                        intent.putExtra("position",position);
//                        old way:
//                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD);
//                        new  way:
                        launcher_add.launch(intent);
                        break;
                    case MENU_UPDATE:
                        intent.putExtra("position",position);
                        intent.putExtra("name",books.get(position).getName());
//                        old way:
//                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_UPDATE);
//                        new  way:
                        launcher_edit.launch(intent);
                        break;
                    case MENU_DELETE:
//                        books.remove(position);
//                        dataBank.saveData();
//                        CustomAdapter.this.notifyItemRemoved(position);
                        AlertDialog.Builder alertDB = new AlertDialog.Builder(MainActivity.this);
                        alertDB.setPositiveButton(MainActivity.this.getResources().getString(R.string.string_make_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(position);
                                dataBank.saveData();
                                CustomAdapter.this.notifyItemRemoved(position);
                            }
                        });
                        alertDB.setNegativeButton(MainActivity.this.getResources().getString(R.string.string_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDB.setMessage(MainActivity.this.getResources().getString(R.string.string_make_sure) +books.get(position).getName()+"？");
                        alertDB.setTitle(MainActivity.this.getResources().getString(R.string.string_tips)).show();
                        break;
                }
                return true;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item_holder, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getImageView().setImageResource(books.get(position).getCoverresourceid());
            holder.getTextView().setText(books.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }


    }
}
