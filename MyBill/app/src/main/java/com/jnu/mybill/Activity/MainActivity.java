package com.jnu.mybill.Activity;

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
import com.jnu.mybill.Fragment.BaseRecordFragment;
import com.jnu.mybill.R;
import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.DBControler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView totalmoney,outcomemoney,incomemoney;

    private MainAdapter mainAdapter;
    private ArrayList<BillList> billLists=new ArrayList<BillList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billLists=DBControler.getBillList();

        totalmoney=findViewById(R.id.header_totalmoney);
        outcomemoney=findViewById(R.id.header_outcomemoney);
        incomemoney=findViewById(R.id.header_incomemoney);
        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
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
        refresh();
        mainAdapter.notifyDataSetChanged();
    }
    private void refresh(){
        Double sum,income=0.0,outcome=0.0;
        for (BillList temp:billLists){
            if (temp.getKind()==1)
                income=income+temp.getMoney();
            else outcome=outcome+temp.getMoney();
        }
        sum=income-outcome;
        totalmoney.setText(String.valueOf(sum));
        incomemoney.setText(String.valueOf(income));
        outcomemoney.setText(String.valueOf(outcome));
    }


    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
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
            private final TextView list_item_time;



            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.list_item_cover = (ImageView) itemView.findViewById(R.id.list_item_cover);
                this.list_item_category = (TextView) itemView.findViewById(R.id.list_item_category);
                this.list_item_remarks =(TextView)itemView.findViewById(R.id.list_item_remarks);
                this.list_item_money=(TextView)itemView.findViewById(R.id.list_item_money);
                this.list_item_time=(TextView)itemView.findViewById(R.id.list_item_time);

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
                Intent intent;
                switch (menuItem.getItemId()){
                    case MENU_UPDATE:
                        intent = new Intent(MainActivity.this, OptionActivity.class);
                        BillList data=billLists.get(position);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("bill",data);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case MENU_DELETE:
                        AlertDialog.Builder alertDB = new AlertDialog.Builder(MainActivity.this);
                        alertDB.setPositiveButton(MainActivity.this.getResources().getString(R.string.string_make_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBControler.deleteItemFrombillListById(billLists.get(position).getId());
                                billlists.remove(position);
                                MainActivity.MainAdapter.this.notifyItemRemoved(position);
                                refresh();
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

            public TextView getList_item_time() {
                return list_item_time;
            }

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
            holder.getList_item_time().setText(billlists.get(position).getTime().substring(0,11));
//            holder.getList_item_time().setText(billlists.get(position).getTime());

        }

        @Override
        public int getItemCount() {
            return billlists.size();
        }

    }

}