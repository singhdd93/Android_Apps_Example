package com.example.lestenphonecalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ListenPhoneCallState_Broadcast extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {
		
		Log.d("Tag","Inside BroadCast");
		
		try {
			TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			mTelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);
		} catch (Throwable e) {
			Log.d("Tag", e+"");
		}
    }
	
	class TeleListener extends PhoneStateListener
	{
		public void onCallStateChanged(int state, String incomingNumber)
		{
			super.onCallStateChanged(state, incomingNumber);
			switch (state)
			{
			case TelephonyManager.CALL_STATE_IDLE:
				//CALL_STATE_IDLE;
				Log.d("Tag","CALL_STATE_IDLE");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				//CALL_STATE_OFFHOOK;
				Log.d("Tag","CALL_STATE_OFFHOOK");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				//CALL_STATE_RINGING
				Log.d("Tag","CALL_STATE_RINGING");
				break;
			default:
				break;
			}
		}

	}

}
