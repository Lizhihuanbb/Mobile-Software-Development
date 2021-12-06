package com.jnu.mybill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.mybill.data.DataControler;
import com.jnu.mybill.data.OptionList;

import java.util.List;

public class OptionActivity extends AppCompatActivity {
    private DataControler dataControler;

    private AddAdapter addAdapter;
    private List<OptionList> optionLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        initData();

        addAdapter = new AddAdapter(optionLists);
        RecyclerView recyclerView2=findViewById(R.id.option_recycle_view);
        GridLayoutManager girdlayoutManager = new GridLayoutManager(this, 5);
        recyclerView2.setLayoutManager(girdlayoutManager);
        recyclerView2.setAdapter(addAdapter);
    }
    public void initData(){
        dataControler=new DataControler(this);

        optionLists=dataControler.loadOption();
    }

    class AddAdapter extends RecyclerView.Adapter<OptionActivity.AddAdapter.ViewHolder>{
        private List<OptionList> optionLists;

        public AddAdapter(List<OptionList> optionLists){
            this.optionLists =optionLists;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int MENU_ADD = 1;
            public static final int MENU_UPDATE = MENU_ADD+1;
            public static final int MENU_DELETE = MENU_ADD+2;
            private final ImageView option_item_photo;
            private final TextView option_item_text;



            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.option_item_photo = (ImageView) itemView.findViewById(R.id.option_item_photo);
                this.option_item_text = (TextView) itemView.findViewById(R.id.option_item_text);

                itemView.setOnCreateContextMenuListener(this);
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuItem add=contextMenu.add(contextMenu.NONE,MENU_ADD,MENU_ADD, R.string.string_add);
                MenuItem edit=contextMenu.add(contextMenu.NONE, MENU_UPDATE, MENU_UPDATE, R.string.string_update);
                MenuItem delete=contextMenu.add(contextMenu.NONE,MENU_DELETE,MENU_DELETE, R.string.string_delete);
                add.setOnMenuItemClickListener(this);
                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                int position=getAdapterPosition();
//                Intent intent= new Intent(MainActivity.this,EditBookActivity.class);
//                switch (menuItem.getItemId()){
//                    case MENU_ADD:
//                        intent.putExtra("position",position);
////                        old way:
////                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD);
////                        new  way:
//                        launcher_add.launch(intent);
//                        break;
//                    case MENU_UPDATE:
//                        intent.putExtra("position",position);
//                        intent.putExtra("name", billlists.get(position).getCatagory());
////                        old way:
////                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_UPDATE);
////                        new  way:
//                        launcher_edit.launch(intent);
//                        break;
//                    case MENU_DELETE:
////                        books.remove(position);
////                        dataBank.saveData();
////                        CustomAdapter.this.notifyItemRemoved(position);
//                        AlertDialog.Builder alertDB = new AlertDialog.Builder(MainActivity.this);
//                        alertDB.setPositiveButton(MainActivity.this.getResources().getString(R.string.string_make_sure), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                billlists.remove(position);
//                                dataBank.saveData();
//                                MainActivity.CustomAdapter.this.notifyItemRemoved(position);
//                            }
//                        });
//                        alertDB.setNegativeButton(MainActivity.this.getResources().getString(R.string.string_cancel), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        alertDB.setMessage(MainActivity.this.getResources().getString(R.string.string_make_sure) + billlists.get(position).getCatagory()+"？");
//                        alertDB.setTitle(MainActivity.this.getResources().getString(R.string.string_tips)).show();
//                        break;
//                }
                return true;
            }

            public ImageView getOption_item_photo() { return option_item_photo; }

            public TextView getOption_item_text() { return option_item_text; }

        }

        @NonNull
        @Override
        public OptionActivity.AddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.option_item_holder, parent, false);

            return new OptionActivity.AddAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OptionActivity.AddAdapter.ViewHolder holder, int position) {
            holder.getOption_item_photo().setImageResource(optionLists.get(position).getOptionPhoto());
            holder.getOption_item_text().setText(optionLists.get(position).getOptionText());
        }

        @Override
        public int getItemCount() {
            return optionLists.size();
        }

    }
}