package com.example.androidhive;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabAndListView extends TabActivity {
	// TabSpec Names
	private static final String INBOX_SPEC = "Inbox";
	private static final String OUTBOX_SPEC = "Outbox";
	private static final String PROFILE_SPEC = "Profile";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        // Inbox Tab
        TabSpec inboxSpec = tabHost.newTabSpec(INBOX_SPEC);
        // Tab Icon
        inboxSpec.setIndicator(INBOX_SPEC, getResources().getDrawable(R.drawable.icon_inbox));
        Intent inboxIntent = new Intent(this, InboxActivity.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);
        
        // Outbox Tab
        TabSpec outboxSpec = tabHost.newTabSpec(OUTBOX_SPEC);
        outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.icon_outbox));
        Intent outboxIntent = new Intent(this, OutboxActivity.class);
        outboxSpec.setContent(outboxIntent);
        
        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profileSpec.setContent(profileIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
    }
}