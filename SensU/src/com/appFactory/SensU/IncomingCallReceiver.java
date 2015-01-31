package com.appFactory.SensU;

/**
 * detects incoming call
 * sets values of
 * gdata.pitchAfterCall=gdata.pitch;
 *gdata.proxyAfterCall=gdata.proxySensor;
 *sgdata.isIncoming=true;
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class IncomingCallReceiver extends BroadcastReceiver 
{
	
	AudioManager am=null;
	static int prevRingermode=3;
	GlobalDataApplication gdata=null;
	boolean flag=true;
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		try{
			
			gdata=(GlobalDataApplication)GlobalDataApplication.getInstance();

			am=(AudioManager)gdata.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

		Bundle bundle=null;
		//Log.d("incomingcall", "when call detected pitch was :"+gdata.pitch);
		
		if(intent != null)
			bundle=intent.getExtras();
		
		if(bundle==null)
		{
			return ;
		}
		
		String extrastate=bundle.getString(TelephonyManager.EXTRA_STATE);
	//	String headsetState=bundle.getString();
		
		
		
		
		if(extrastate.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
		{
			synchronized(this)
			{

				if(!gdata.isIncoming)
				{
					prevRingermode = am.getRingerMode();
				}
			gdata.isIncoming=true;	

			gdata.pitchAfterCall=gdata.pitch;
			gdata.proxyAfterCall=gdata.proxySensor;

			}
			
		}
		
		if(extrastate.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK) || extrastate.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE))
		{
			synchronized(this)
			{
			gdata.isIncoming=false;
			//am.getRingerMode();
			am.setRingerMode(prevRingermode);

			//SensorService.setInitialVolume();
			}
			
			
		}
	}	
		catch (Exception e) 
		{
			
	}
	}
}
