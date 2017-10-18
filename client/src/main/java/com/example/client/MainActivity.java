package com.example.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.machenike.aidldemo.PlusInterface;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    private TextView mTextView;
    private PlusInterface mPlusInterface;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mPlusInterface = PlusInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mPlusInterface = null;
        }
    };
    private String num1;
    private String num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        bindMyService();
    }

    private void bindMyService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.machenike.aidldemo","com.example.machenike.aidldemo.IRemoteService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
//        intent.setAction("aaa");
//        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    private void initView() {
        mEditText1 = (EditText) findViewById(R.id.num1);
        mEditText2 = (EditText) findViewById(R.id.num2);
        mTextView = (TextView) findViewById(R.id.result);
    }

    public void plus(View view) {
        num1 = mEditText1.getText().toString().trim();
        num2 = mEditText2.getText().toString().trim();
        try {
            int plus = mPlusInterface.plus(Integer.parseInt(num1), Integer.parseInt(num2));
            mTextView.setText(plus+"");
        } catch (RemoteException e) {
            mTextView.setText("计算错误");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
