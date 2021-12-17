package com.jnu.mybill;

import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.mybill.data.BillType;
import com.jnu.mybill.data.DBControler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutcomeFragment extends Fragment {

    private OutcomeAdapter outcomeAdapter;
    private ArrayList<BillType> billTypes;

    private TextView catagory,remarks,time;
    private EditText money;
    private KeyboardView keyboard;



    public OutcomeFragment() {
        // Required empty public constructor
    }

    public static OutcomeFragment newInstance() {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_outcome,container,false);

        initData();


//        初始化
        keyboard=rootView.findViewById(R.id.option_keybored);
        money=rootView.findViewById(R.id.option_billMoney);
        catagory=rootView.findViewById(R.id.option_catagory);
        remarks=rootView.findViewById(R.id.option_remarks);
        time=rootView.findViewById(R.id.option_time);


//        show KeyBoard
        MyKeyBorad myKeyBorad=new MyKeyBorad(keyboard, money);
        myKeyBorad.showKeyboard();
        myKeyBorad.setOnEnsureListener(new MyKeyBorad.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //点击了键盘的确定按钮
            }
        });


        outcomeAdapter = new OutcomeAdapter(billTypes);
        RecyclerView recyclerView=rootView.findViewById(R.id.option_recycle_view);
        GridLayoutManager girdlayoutManager = new GridLayoutManager(this.getContext(), 5);
        recyclerView.setLayoutManager(girdlayoutManager);
        recyclerView.setAdapter(outcomeAdapter);

        return rootView;
    }


    public void initData(){
        billTypes= DBControler.getBillTypeList(0);
    }

    class OutcomeAdapter extends RecyclerView.Adapter<OutcomeAdapter.ViewHolder>{


        private ArrayList<BillType> billtypes;

        public OutcomeAdapter(ArrayList<BillType> billtypes){
            this.billtypes = billtypes;
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
                MenuItem edit=contextMenu.add(contextMenu.NONE, MENU_UPDATE, MENU_UPDATE, R.string.string_update);
                MenuItem delete=contextMenu.add(contextMenu.NONE,MENU_DELETE,MENU_DELETE, R.string.string_delete);
                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position=getAdapterPosition();
                Intent intent= new Intent(OutcomeFragment.this.getContext(),MainActivity.class);
                intent.putExtra("position",position);
                return true;
            }

            public ImageView getOption_item_photo() { return option_item_photo; }

            public TextView getOption_item_text() { return option_item_text; }

        }

        @NonNull
        @Override
        public OutcomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.option_item_holder, parent, false);
            return new OutcomeAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OutcomeAdapter.ViewHolder holder, int position) {
            holder.getOption_item_photo().setImageResource(billtypes.get(position).getCoverResourceId());
            holder.getOption_item_text().setText(billtypes.get(position).getTypeName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int icon_position=holder.getAdapterPosition();
                    String a=billtypes.get(icon_position).getTypeName();
                    catagory.setText(billtypes.get(icon_position).getTypeName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return billtypes.size();
        }

    }
}