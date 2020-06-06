package com.example.myapplicationnumba.activitys.find;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.adapter.FindResultRecycleAdapter;
import com.example.myapplicationnumba.entity.EquipmentBean;

import java.io.Serializable;
import java.util.List;

/**
 * 利用广播发现周边设备结果页面（一个列表页面）
 */
public class FindResultActivity extends AppCompatActivity {

    //定义RecyclerView
    public RecyclerView equipmentRecyclerView;
    //定义RecyclerView的数据源
    private List<EquipmentBean> equipmentEntities;
    //定义RecyclerView的视图适配器
    private FindResultRecycleAdapter findResultRecycleAdapter;

    /**
     * 生命周期之onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        /**
         * 从跳转过来的上一个视图获取所传递的信息
         */
        Intent intent = getIntent ();
        equipmentEntities = (List<EquipmentBean>) getIntent().getSerializableExtra("arrayList");
        initRecyclerView();
    }

    /**
     * 对recycleview进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        equipmentRecyclerView=(RecyclerView)this.findViewById(R.id.find_equipment_recycler);
        //创建adapter
        findResultRecycleAdapter = new FindResultRecycleAdapter(this, equipmentEntities);
        //为RecyclerView的Item绑定点击事件
        findResultRecycleAdapter.setOnItemClickListener(new FindResultRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //将此条数据传递到下一个页面
                Intent intent = new Intent(FindResultActivity.this, SettingDeviceActivity.class);
                intent.putExtra("arrayList", (Serializable) equipmentEntities);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
            }
        });
        //给RecyclerView设置adapter
        equipmentRecyclerView.setAdapter(findResultRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        equipmentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        equipmentRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
