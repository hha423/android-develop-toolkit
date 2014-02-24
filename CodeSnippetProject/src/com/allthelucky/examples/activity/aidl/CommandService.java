package com.allthelucky.examples.activity.aidl;

import com.allthelucky.examples.activity.aidl.ICommand;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CommandService extends Service {
    
    private ICommand.Stub stub =  new CommandServiceBinder();
    
   
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
    
    class CommandServiceBinder extends ICommand.Stub {
        
        @Override
        public void request(String result) throws RemoteException {
            System.out.println("excute:"+result);
        }

        @Override
        public void init(Data data) throws RemoteException {
            System.out.println(data);
        }
    };
    
}

