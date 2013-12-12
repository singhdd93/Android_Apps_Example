package com.pixel.fbcoolcovers;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
public class ConnectionDetector {
     
    private Context c;
     
    public ConnectionDetector(Context context){
        this.c = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
}