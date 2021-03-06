package com.jnu.mybill.Fragment;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.mybill.Module.MyKeyBoard;
import com.jnu.mybill.Module.RemarksDialog;
import com.jnu.mybill.Module.SelectCalendarDialog;
import com.jnu.mybill.R;
import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.BillType;
import com.jnu.mybill.data.DBControler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener{
    private BaseRecordAdapter baseRecordAdapter;
    private KeyboardView keyboard;

    public int kind;

    public void setKind(int kind) {
        this.kind = kind;
    }

    public BillList billList,test;
    public TextView catagory,remarks,time;
    public EditText money;
    public static String PARAM1="bill";
    public ArrayList<BillType> billTypes=new ArrayList<BillType>();


    public BaseRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billList = new BillList();   //创建对象

//        开始时默认选定"其他"
        billList.setCatagory("其他");
        billList.setCoverreSourceid(R.drawable.other);
        if (getArguments() != null) {
            test = (BillList) getArguments().getSerializable(PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_baserecord,container,false);

        initData();


//        初始化
        keyboard=rootView.findViewById(R.id.option_keybored);
        money=rootView.findViewById(R.id.option_billMoney);
        catagory=rootView.findViewById(R.id.option_catagory);
        remarks=rootView.findViewById(R.id.option_remarks);
        time=rootView.findViewById(R.id.option_time);



        time.setOnClickListener(this);
        remarks.setOnClickListener(this);

        catagory.setText("其他");



//        show KeyBoard
        MyKeyBoard myKeyBorad=new MyKeyBoard(keyboard, money);
        myKeyBorad.showKeyboard();

//        设置键盘确定按钮的点击事件
        myKeyBorad.setOnEnsureListener(new MyKeyBoard.OnEnsureListener() {
            @Override
            public void onEnsure() {
//                获取输入的金额
                String getmoney = money.getText().toString();
                if (TextUtils.isEmpty(getmoney)||getmoney.equals("0")) {
                    getActivity().finish();
                    return;
                }
                double moneyvalue = Double.parseDouble(getmoney);
                billList.setMoney(moneyvalue);

//                将数据添加到数据库
                saveListToDB();
                if (test!=null){
                    DBControler.deleteItemFrombillListById(test.getId());
                }

                getActivity().finish();

            }
        });


//      初始化时间类，并将其显示出来
        setInitTime();

        if (test!=null&&test.getKind()==kind){
            catagory.setText(test.getCatagory());
            money.setText(String.valueOf(test.getMoney()));
//            money.setSelection(String.valueOf(money.getText()).length());

            if (test.getRemarks()!=null){
                remarks.setText(test.getRemarks());
            }
            else    remarks.setText("备注");
            time.setText(test.getTime());
            myKeyBorad.setSTATES(true);
        }

        baseRecordAdapter = new BaseRecordAdapter(billTypes);
        RecyclerView recyclerView=rootView.findViewById(R.id.option_recycle_view);
        GridLayoutManager girdlayoutManager = new GridLayoutManager(this.getContext(), 5);
        recyclerView.setLayoutManager(girdlayoutManager);
        recyclerView.setAdapter(baseRecordAdapter);




        return rootView;
    }

    //    IncomeFragment和OutcomeFragment需要修改的地方  Income为1，Outcome为0
//////////////////////////////
    public abstract void initData();
    public abstract void saveListToDB();
////////////////////////////////////

    //初始化时间类并暂时保存在billlist中
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String timestring = sdf.format(date);
        time.setText(timestring);
        billList.setTime(timestring);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        billList.setYear(year);
        billList.setMonth(month);
        billList.setDay(day);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_remarks:
                showRemarksDialog();
                break;
            case R.id.option_time:
                showCalendarDialog();
                break;
        }
    }

    
    //设置日历并保存中
    private void showCalendarDialog() {
        SelectCalendarDialog dialog = new SelectCalendarDialog(getContext());
        dialog.show();
        //设定确定按钮被点击了的监听器
        dialog.setOnEnsureListener(new SelectCalendarDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {

                BaseRecordFragment.this.time.setText(time);
                billList.setTime(time);
                billList.setYear(year);
                billList.setMonth(month);
                billList.setDay(day);
            }
        });
    }

//    设置备注并保存
    private void showRemarksDialog() {
        final RemarksDialog dialog = new RemarksDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new MyKeyBoard.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    remarks.setText(msg);
                    billList.setRemarks(msg);
                }
                dialog.cancel();
            }
        });
    }




    class BaseRecordAdapter extends RecyclerView.Adapter<BaseRecordAdapter.ViewHolder> {
        private ArrayList<BillType> billtypes;

        public BaseRecordAdapter(ArrayList<BillType> billtypes) {
            this.billtypes = billtypes;
        }


        public class ViewHolder extends RecyclerView.ViewHolder{
            private final ImageView option_item_photo;
            private final TextView option_item_text;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.option_item_photo = (ImageView) itemView.findViewById(R.id.option_item_photo);
                this.option_item_text = (TextView) itemView.findViewById(R.id.option_item_text);

            }

            public ImageView getOption_item_photo() {
                return option_item_photo;
            }
            public TextView getOption_item_text() {
                return option_item_text;
            }
        }

        @NonNull
        @Override
        public BaseRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.option_item_holder, parent, false);
            return new BaseRecordAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseRecordAdapter.ViewHolder holder, int position) {
            holder.getOption_item_photo().setImageResource(billtypes.get(position).getCoverResourceId());
            holder.getOption_item_text().setText(billtypes.get(position).getTypeName());

    //            设置菜单项的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int icon_position = holder.getAdapterPosition();
    //                    点击其他种类后修改其选定的类型，并添加到Billlist中
                    String a = billtypes.get(icon_position).getTypeName();
                    catagory.setText(billtypes.get(icon_position).getTypeName());

    //                    点击图片后修改选定的封面和类型
                    billList.setCatagory(a);
                    billList.setCoverreSourceid(billtypes.get(icon_position).getCoverResourceId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return billtypes.size();
        }

    }
}