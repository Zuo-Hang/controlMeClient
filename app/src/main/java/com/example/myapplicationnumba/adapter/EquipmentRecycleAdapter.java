package com.example.myapplicationnumba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.entity.EquipmentBean;

import java.util.ArrayList;

/**
 * 设备管理循环视图的适配器
 */
public class EquipmentRecycleAdapter extends RecyclerView.Adapter<EquipmentRecycleAdapter.myViewHodler> {
    private Context context;
    //private List<EquipmentModel> goodsEntityList;
    private ArrayList<EquipmentBean> equipmentBeanArrayList;

    //创建构造函数
    public EquipmentRecycleAdapter(Context context, ArrayList<EquipmentBean> equipmentBeanArrayList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.equipmentBeanArrayList=equipmentBeanArrayList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.equipment_item, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根据点击位置绑定数据
        EquipmentBean data = equipmentBeanArrayList.get(position);
        holder.mItemGoodsName.setText(data.getEquName());//获取实体类中的设备名并设置
        holder.mItemGoodsPrice.setText(data.getEquManufacturer());//获取实体类中的生产厂家并设置

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return equipmentBeanArrayList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mItemGoodsImg;
        private TextView mItemGoodsName;
        private TextView mItemGoodsPrice;

        public myViewHodler(View itemView) {
            super(itemView);
            mItemGoodsImg = (ImageView) itemView.findViewById(R.id.item_goods_img);
            mItemGoodsName = (TextView) itemView.findViewById(R.id.item_goods_name);
            mItemGoodsPrice = (TextView) itemView.findViewById(R.id.item_goods_price);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, equipmentBeanArrayList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, EquipmentBean data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 更改数据源，并显示
     * @param dataSource
     */
    public void setDataSource(ArrayList<EquipmentBean> dataSource) {
        this.equipmentBeanArrayList = dataSource;
        notifyDataSetChanged();
    }

}
