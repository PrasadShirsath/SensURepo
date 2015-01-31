package com.appFactory.SensU;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * 
 * 
 * This class starts the service after phone is rebooted
 * 
 * 
 */
public class OnBoot extends BroadcastReceiver
{
	 @Override
	 public void onReceive(Context aContext, Intent aIntent) 
	 {
		 //start our service
		 try
		 {
			 aContext.startService(new Intent(aContext,SensorService.class));
		 }
		 catch(Exception e)
		{
		
		}
	}

}
