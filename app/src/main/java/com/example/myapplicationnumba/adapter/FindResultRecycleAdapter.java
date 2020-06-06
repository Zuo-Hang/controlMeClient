package com.example.myapplicationnumba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.entity.EquipmentBean;

import java.util.List;

public class FindResultRecycleAdapter extends RecyclerView.Adapter<FindResultRecycleAdapter.myViewHodler> {

    private Context context;
    private List<EquipmentBean> EquipmentList;

    /**
     * 构造函数
     * @param context
     * @param equipmentList
     */
    public FindResultRecycleAdapter(Context context, List<EquipmentBean> equipmentList) {
        this.context = context;
        EquipmentList = equipmentList;
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     */
    @NonNull
    @Override
    public myViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.equipment_item2, null);
        return new myViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHodler holder, int position) {
        EquipmentBean data=EquipmentList.get(position);
        holder.item_equipmentLists_name.setText(data.getEquName());
        holder.item_equipmentLists_manufacturers.setText(data.getEquManufacturer());
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return EquipmentList.size();
    }

    public interface OnItemClickListener {
        void onClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView item_equipmentLists_name;
        private TextView item_equipmentLists_manufacturers;

        public myViewHodler(View itemView) {
            super(itemView);
            item_equipmentLists_name = (TextView) itemView.findViewById(R.id.item_equipmentLists_name);
            item_equipmentLists_manufacturers = (TextView) itemView.findViewById(R.id.item_equipmentLists_manufacturers);
//            itemView.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //可以选择直接在本位置直接写业务处理
//                    Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, SettingDeviceActivity.class);
//                    intent.putExtra("arrayList", (Serializable) EquipmentList);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
