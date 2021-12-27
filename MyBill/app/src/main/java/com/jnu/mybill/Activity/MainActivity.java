package com.jnu.mybill.Activity;

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
import com.jnu.mybill.R;
import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.DBControler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_CODE_ADD = 996;


    private MainAdapter mainAdapter;
    private ArrayList<BillList> billLists=new ArrayList<BillList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);


        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, OptionActivity.class);
                intent.putExtra("position",billLists.size());
                launcher_add.launch(intent);
            }
        });

        mainAdapter =new MainAdapter(billLists);
        RecyclerView recyclerView1=findViewById(R.id.billlist_recycle_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(mainAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<BillList> billLists1= DBControler.getBillList();
        billLists.clear();
        billLists.addAll(billLists1);
        mainAdapter.notifyDataSetChanged();
    }

    private ActivityResultLauncher<Intent> launcher_add=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if(resultCode== RESULT_CODE_ADD){
//                String bookname=data.getStringExtra("name");
//                int position=data.getIntExtra("position",books.size());
//                books.add(position,new Book(bookname,R.drawable.book_no_name));
//                dataBank.saveData();
//                customAdapter.notifyItemInserted(position);
            }
        }
    });
    private ActivityResultLauncher<Intent> launcher_edit=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data=result.getData();
            int resultCode=result.getResultCode();
            if (resultCode==RESULT_CODE_ADD){
//                String bookname=data.getStringExtra("name");
//                int position=data.getIntExtra("position",books.size());
//                books.get(position).setName(bookname);
//                dataBank.saveData();
//                customAdapter.notifyItemChanged(position);
            }
        }
    });

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
        private List<BillList> billlists;

        public MainAdapter(List<BillList> billLists){
            this.billlists =billLists;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int MENU_UPDATE = 1;
            public static final int MENU_DELETE = MENU_UPDATE+2;
            private final ImageView list_item_cover;
            private final TextView list_item_category;
            private final TextView list_item_remarks;
            private final TextView list_item_money;



            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.list_item_cover = (ImageView) itemView.findViewById(R.id.list_item_cover);
                this.list_item_category = (TextView) itemView.findViewById(R.id.list_item_category);
                this.list_item_remarks =(TextView)itemView.findViewById(R.id.list_item_remarks);
                this.list_item_money=(TextView)itemView.findViewById(R.id.list_item_money);

                itemView.setOnCreateContextMenuListener(this);
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuItem edit=contextMenu.add(contextMenu.NONE, MENU_UPDATE, MENU_UPDATE, R.string.string_update);
                MenuItem delete=contextMenu.add(contextMenu.NONE,MENU_DELETE,MENU_DELETE, R.string.string_delete);
                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position=getAdapterPosition();
                Intent intent= new Intent(MainActivity.this,OptionActivity.class);
                switch (menuItem.getItemId()){
                    case MENU_UPDATE:
                        billlists.remove(position);
                        intent.putExtra("position",position);
                        intent.putExtra("name", billlists.get(position).getCatagory());
//                        old way:
//                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_UPDATE);
//                        new  way:
                        launcher_edit.launch(intent);
                        break;
                    case MENU_DELETE:
                        AlertDialog.Builder alertDB = new AlertDialog.Builder(MainActivity.this);
                        alertDB.setPositiveButton(MainActivity.this.getResources().getString(R.string.string_make_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                billlists.remove(position);
                                DBControler.deleteItemFrombillListById(billLists.get(position).getId());
//                                dataControler.saveBill();
                                MainActivity.MainAdapter.this.notifyItemRemoved(position);
                            }
                        });
                        alertDB.setNegativeButton(MainActivity.this.getResources().getString(R.string.string_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDB.setMessage("确定删除" + billlists.get(position).getCatagory()+"？");
                        alertDB.setTitle(MainActivity.this.getResources().getString(R.string.string_tips)).show();
                        break;
                }
                return true;
            }

            public ImageView getList_item_cover() { return list_item_cover; }

            public TextView getList_item_category() { return list_item_category; }

            public TextView getList_item_remarks() {
                return list_item_remarks;
            }

            public TextView getList_item_money() {
                return list_item_money;
            }
        }

        @NonNull
        @Override
        public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_holder, parent, false);

            return new MainAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
            holder.getList_item_cover().setImageResource(billlists.get(position).getCoverreSourceid());
            holder.getList_item_category().setText(billlists.get(position).getCatagory());
            holder.getList_item_remarks().setText(billlists.get(position).getRemarks());
            holder.getList_item_money().setText("$ "+String.valueOf(billlists.get(position).getMoney()));
        }

        @Override
        public int getItemCount() {
            return billlists.size();
        }

    }

}