package com.jnu.booklistmainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private List<Book> books;
    private CustomAdapter customAdapter;

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
}
class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private List<Book> books;

    public CustomAdapter(List<Book> books){
        this.books=books;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
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
            MenuItem add=contextMenu.add(contextMenu.NONE,1,1,"Add");
            MenuItem delete=contextMenu.add(contextMenu.NONE,2,2,"Delete");
//            MenuItem delete=contextMenu.add(contextMenu.NONE,3,3,"Delete");
            add.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
//            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position=getAdapterPosition();
            switch (menuItem.getItemId()){
                case 1:

                    books.add(position,new Book("信息安全数学基础（第2版）", R.drawable.book_1));
//                    notifyDataSetChanged();
                    CustomAdapter.this.notifyItemInserted(position);
                    break;
                case 2:
                    books.remove(position);
                    CustomAdapter.this.notifyItemRemoved(position);
                    break;
//                case 3:
//                    View dialagueView=LayoutInflater.from().inflate(R.layout.input_item,null);
//                    books.remove(position);
//                    CustomAdapter.this.notifyItemRemoved(position);
//                    break;
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