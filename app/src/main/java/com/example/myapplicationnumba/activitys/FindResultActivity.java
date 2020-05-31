package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.adapter.EquipmentRecycleAdapter;
import com.example.myapplicationnumba.adapter.FindResultRecycleAdapter;
import com.example.myapplicationnumba.entity_model.EquipmentBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindResultActivity extends AppCompatActivity {

    public RecyclerView equipmentRecyclerView;//定义RecyclerView
    private List<EquipmentBean> equipmentEntities;
    private FindResultRecycleAdapter findResultRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        Intent intent = getIntent ();
        equipmentEntities = (List<EquipmentBean>) getIntent().getSerializableExtra("arrayList");
        //对recycleview进行配置
        initRecyclerView();
    }
    private void initRecyclerView() {
        //获取RecyclerView
        equipmentRecyclerView=(RecyclerView)this.findViewById(R.id.find_equipment_recycler);
        //创建adapter
        findResultRecycleAdapter = new FindResultRecycleAdapter(this, equipmentEntities);
        findResultRecycleAdapter.setOnItemClickListener(new FindResultRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
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
