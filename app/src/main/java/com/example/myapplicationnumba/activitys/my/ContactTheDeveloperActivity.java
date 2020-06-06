package com.example.myapplicationnumba.activitys.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnumba.R;

/**
 * 反馈信息页面
 */
public class ContactTheDeveloperActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_the_developer_layout);
        editText = findViewById(R.id.edt_feed_back);
        button = findViewById(R.id.feed_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s.equals("") || s == null) {
                    Toast.makeText(ContactTheDeveloperActivity.this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ContactTheDeveloperActivity.this, "反馈成功！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

