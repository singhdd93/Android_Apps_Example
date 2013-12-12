package com.pixels.timelinepics;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.pixels.timelinepics.R;
import com.pixels.timelinepics.ImageGridActivity.ImageAdapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;



public class SlidingMenuFragment extends SherlockFragment implements ExpandableListView.OnChildClickListener {
	   
    private ExpandableListView sectionListView;
    ImageGridActivity iga;
    int countTotal;
   
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
      iga = (ImageGridActivity)getActivity();
        List<Section> sectionList = createMenu();
               
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
        this.sectionListView = (ExpandableListView) view.findViewById(R.id.slidingmenu_view);
        this.sectionListView.setGroupIndicator(null);
       
        SectionListAdapter sectionListAdapter = new SectionListAdapter(this.getActivity(), sectionList);
        this.sectionListView.setAdapter(sectionListAdapter);
       
        this.sectionListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
              @Override
              public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
              }
            });
       
        this.sectionListView.setOnChildClickListener(this);
       
        int count = sectionListAdapter.getGroupCount();
        for (int position = 0; position < count; position++) {
            this.sectionListView.expandGroup(position);
        }
       
        return view;
    }

    private List<Section> createMenu() {
        List<Section> sectionList = new ArrayList<Section>();

        
        
        Section catSection = new Section("Categories");
        catSection.addSectionItem(100, "All", "all");
        catSection.addSectionItem(23,"Attitude", "abstra");
        catSection.addSectionItem(25, "Boys", "animal");
        
        Section linksSection = new Section("Links");
        linksSection.addSectionItem(201, "Like Us !", "like");
        linksSection.addSectionItem(202, "Facebook Status App", "fbstatus");
        linksSection.addSectionItem(203, "About", "about");
       
        
        sectionList.add(catSection);
        sectionList.add(linksSection);
        
        return sectionList;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {

        switch ((int)id) {
        case 100:
        	iga.temp =0 ;
        	iga.db.open();
    		iga.c = iga.db.getAllDistinct();
    		countTotal = iga.c.getCount();
    		iga.imageTitles = new String [countTotal];
    		iga.imageUrls = new String [countTotal];
    		if (iga.c.moveToFirst())
    		{
    		do {
    			String tit = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_TITLE));
    			String urL = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_PIC));
    			String cate = "http://fbtimelinepics.com/admin/Uploads/"+urL;
    		    //Toast.makeText(getBaseContext(), mes, Toast.LENGTH_SHORT).show();
    		    iga.imageTitles[iga.temp] = tit;
    		    iga.imageUrls[iga.temp] = cate;
    		   // Toast.makeText(getBaseContext(), arrayMessages[temp], Toast.LENGTH_SHORT).show();
    		    iga.temp++;
    		} while (iga.c.moveToNext());
    		}
    		iga.db.close();
    		((GridView) iga.listView).setAdapter(iga.new ImageAdapter());
    		if ( iga.slidingMenu.isMenuShowing()) {
                iga.slidingMenu.toggle();
            }
            break;
        case 23:
        	iga.temp =0 ;
        	iga.db.open();
    		iga.c = iga.db.getTpicsOfACategory("23");
    		countTotal = iga.c.getCount();
    		iga.imageTitles = new String [countTotal];
    		iga.imageUrls = new String [countTotal];
    		if (iga.c.moveToFirst())
    		{
    		do {
    			String tit = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_TITLE));
    			String urL = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_PIC));
    			String cate = "http://fbtimelinepics.com/admin/Uploads/"+urL;
    		    //Toast.makeText(getBaseContext(), mes, Toast.LENGTH_SHORT).show();
    		    iga.imageTitles[iga.temp] = tit;
    		    iga.imageUrls[iga.temp] = cate;
    		   // Toast.makeText(getBaseContext(), arrayMessages[temp], Toast.LENGTH_SHORT).show();
    		    iga.temp++;
    		} while (iga.c.moveToNext());
    		}
    		iga.db.close();
    		((GridView) iga.listView).setAdapter(iga.new ImageAdapter());
    		if ( iga.slidingMenu.isMenuShowing()) {
                iga.slidingMenu.toggle();
            }
            break;
        case 25:
        	iga.temp =0 ;
        	iga.db.open();
    		iga.c = iga.db.getTpicsOfACategory("25");
    		countTotal = iga.c.getCount();
    		iga.imageTitles = new String [countTotal];
    		iga.imageUrls = new String [countTotal];
    		if (iga.c.moveToFirst())
    		{
    		do {
    			String tit = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_TITLE));
    			String urL = iga.c.getString(iga.c.getColumnIndex(iga.COLUMN_PIC));
    			String cate = "http://fbtimelinepics.com/admin/Uploads/"+urL;
    		    //Toast.makeText(getBaseContext(), mes, Toast.LENGTH_SHORT).show();
    		    iga.imageTitles[iga.temp] = tit;
    		    iga.imageUrls[iga.temp] = cate;
    		   // Toast.makeText(getBaseContext(), arrayMessages[temp], Toast.LENGTH_SHORT).show();
    		    iga.temp++;
    		} while (iga.c.moveToNext());
    		}
    		iga.db.close();
    		((GridView) iga.listView).setAdapter(iga.new ImageAdapter());
    		if ( iga.slidingMenu.isMenuShowing()) {
                iga.slidingMenu.toggle();
            }
            
            break;
        
        case 201:
        	try {
			    iga.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/155042251267920")));
			} catch (android.content.ActivityNotFoundException anfe) {
			    iga.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.facebook.com/coolstylishcoverpics")));
			}
			break;
        case 202:
        	try {
			    iga.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dds.fbstatus")));
			} catch (android.content.ActivityNotFoundException anfe) {
			    iga.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.dds.fbstatus")));
			}
			break;
        case 203:
        	Intent i = new Intent(iga.getBaseContext(), About.class);
			iga.startActivity(i);
			break;
        	

        }
       
        return false;
    }
}