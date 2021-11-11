package com.jnu.booklistmainactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
                customAdapter.notifyItemChanged(position);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        customAdapter =new CustomAdapter(books);
        RecyclerView recyclerView=findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);
    }
    public void initData(){
        books=new ArrayList<Book>();
        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
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
                        books.remove(position);
                        CustomAdapter.this.notifyItemRemoved(position);
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
