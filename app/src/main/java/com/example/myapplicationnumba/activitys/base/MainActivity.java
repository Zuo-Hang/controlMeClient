package com.example.myapplicationnumba.activitys.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.Server.EquipmentService;
import com.example.myapplicationnumba.Server.UserService;
import com.example.myapplicationnumba.Server.impl.EquipmentServiceImpl;
import com.example.myapplicationnumba.Server.impl.UserServiceImpl;
import com.example.myapplicationnumba.activitys.find.SettingDeviceActivity;
import com.example.myapplicationnumba.activitys.fragment.FindFragment;
import com.example.myapplicationnumba.activitys.fragment.ManagementFragment;
import com.example.myapplicationnumba.activitys.fragment.MeFragment;
import com.example.myapplicationnumba.activitys.my.LoginActivity;
import com.example.myapplicationnumba.base.MyApplication;
import com.example.myapplicationnumba.entity.EquipmentBean;
import com.example.myapplicationnumba.entity.SysUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * 主界面：
 * 包含了三个按钮，点击按钮可以切换到不同的fragment
 */

public  class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity-xx";
    protected LinearLayout mMenuMain;
    protected LinearLayout mMenuFind;
    protected LinearLayout mMenuMe;
    protected ManagementFragment collectFragment = new ManagementFragment();//管理
    protected FindFragment mFindFragmenr = new FindFragment();//发现
    protected MeFragment mMeFragment = new MeFragment();//我的
    private UserService userService;
    private SysUser sysUser;
    private EquipmentService equipmentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化用户信息
        userService = new UserServiceImpl();
        userService.getUser(this);
        //初始化设备列表信息
        equipmentService = new EquipmentServiceImpl();
        equipmentService.loadMyEquipment(this);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        //销毁闪屏页
        MyApplication.destroyActivity("SplashActivity");
        initView();
        //获取管理类
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content, collectFragment)
                .hide(collectFragment)
                .add(R.id.container_content, mFindFragmenr)
                .hide(mFindFragmenr)
                .add(R.id.container_content, mMeFragment)
                //事物添加  默认：显示首页  其他页面：隐藏
                //提交
                .commit();
    }

    /**
     * 初始化视图
     */
    public void initView() {
        mMenuMain = (LinearLayout) this.findViewById(R.id.menu_main);
        mMenuFind = (LinearLayout) this.findViewById(R.id.menu_find);
        mMenuMe = (LinearLayout) this.findViewById(R.id.menu_me);
        mMenuMain.setOnClickListener(this);
        mMenuFind.setOnClickListener(this);
        mMenuMe.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_main://首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(collectFragment)
                        .hide(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_find://发现
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(collectFragment)
                        .show(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case R.id.menu_me://我的
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(collectFragment)
                        .hide(mFindFragmenr)
                        .show(mMeFragment)
                        .commit();
                break;
        }
    }

    /**
     * 在其他视图跳转到当前视图的时候调用
     */
    @Override
    protected void onResume() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.getUser(this);
        sysUser = MyApplication.sysUser;
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk----------" + sysUser);
        equipmentService.loadMyEquipment(this);
        super.onResume();
    }

    /**
     * 双击退出程序
     */
    //--------------使用onKeyDown()干掉他--------------

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出控我", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 从二维码页面扫描返回的处理流程
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(TAG, "Cancelled");
                //Log.d(getClass().getName(), "Cancelled");
                Toast.makeText(this, "扫描结果为空", Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "Scanned: " + result.getContents());
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Intent setDevice = new Intent(this, SettingDeviceActivity.class);
                startActivity(setDevice);
            }
        }
    }

    public Handler handler=new Handler() {
        public void handleMessage (Message msg){
            switch (msg.what) {
                case 1:
                    //当为1时，代表查询成功。更改信息
                    mMeFragment.myNickNames.setText(MyApplication.sysUser.getUserName());
                    break;
                case 2:
                    collectFragment.equipmentBeanArrayList=(ArrayList<EquipmentBean>)msg.obj;
                    collectFragment.notifyAdapter(collectFragment.equipmentBeanArrayList);
            }

        }
    };
}
