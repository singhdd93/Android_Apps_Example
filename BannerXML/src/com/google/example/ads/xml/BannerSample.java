package com.google.example.ads.xml;

import android.app.Activity;
import android.os.Bundle;

/**
 * A simple {@link Activity} which embeds an AdView in its layout XML.
 */
public class BannerSample extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // This example requires no additional code since the optional
    // "loadAdOnCreate=true" XML attribute was used. If "loadAdOnCreate" were
    // not specified, the ad would have to be loaded by creating an AdRequest
    // and using Activity.findViewById() to get the AdView.
    //
    // The "loadAdOnCreate" XML attribute makes it simpler to get ads since no
    // code is required, but it also limits the developer's control over the ad
    // request since a generic AdRequest is used.
  }
}
