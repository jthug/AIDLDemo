package com.example.machenike.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by MACHENIKE on 2017/9/15.
 */

public class IRemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private IBinder mIBinder = new PlusInterface.Stub(){

        @Override
        public int plus(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    };
}
