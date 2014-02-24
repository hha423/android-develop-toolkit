package com.example.aidlserverproject;

import com.example.aidl.ISecurityService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class SecurityService extends Service {
	final ISecurityService.Stub binder = new SecurityServiceBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	class SecurityServiceBinder extends ISecurityService.Stub {

		@Override
		public String decode(String input) throws RemoteException {
			return "hello world";
		}

		@Override
		public String encode(String input) throws RemoteException {
			return "avadsfasfsdafasfsd";
		}
		
	}
}
