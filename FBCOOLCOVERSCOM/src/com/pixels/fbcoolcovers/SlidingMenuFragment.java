package com.pixels.fbcoolcovers;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;



public class SlidingMenuFragment extends SherlockFragment implements ExpandableListView.OnChildClickListener {
	
	private void getCovers(String category)
	{
		iga.temp =0 ;
    	iga.db.open();
    	if(category.equals("All"))
    	{
    		iga.c = iga.db.getAllDistinct();	
    	}
    	else
    	{
    		iga.c = iga.db.getCoversOfACategory(category);
    	}
		countTotal = iga.c.getCount();
		iga.imageTitles = new String [countTotal];
		iga.imageUrls = new String [countTotal];
		if (iga.c.moveToFirst())
		{
		do {
			String tit = iga.c.getString(iga.c.getColumnIndex(Util.COLUMN_CTITLE));
			String urL = iga.c.getString(iga.c.getColumnIndex(Util.COLUMN_CLINK));
			String cate = Util.COMPELTE_URL1+urL;
		    
		    iga.imageTitles[iga.temp] = tit;
		    iga.imageUrls[iga.temp] = cate;
		   
		    iga.temp++;
		} while (iga.c.moveToNext());
		}
		iga.db.close();
		((GridView) iga.listView).setAdapter(iga.new ImageAdapter());
		if ( iga.slidingMenu.isMenuShowing()) {
            iga.slidingMenu.toggle();
        }
	}
	   
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
        catSection.addSectionItem(101,"Abstract", "abstra");
        catSection.addSectionItem(102, "Animal", "animal");
        catSection.addSectionItem(103, "Anime", "anime");
        catSection.addSectionItem(104, "Attitude", "attitude");
        catSection.addSectionItem(105, "Bikes", "bikes");
        catSection.addSectionItem(106, "Boys", "boy");
        catSection.addSectionItem(107, "Cars", "car");
        catSection.addSectionItem(108, "Couples", "couples");
        catSection.addSectionItem(109, "Festivals", "festivas");
        catSection.addSectionItem(110, "Friends", "friends");
        catSection.addSectionItem(111, "Funny", "funny");
        catSection.addSectionItem(112, "Gadgets", "gadgets");
        catSection.addSectionItem(113, "Games", "games");
        catSection.addSectionItem(114, "Girls", "girls");
        catSection.addSectionItem(115, "Hearts", "heart");
        catSection.addSectionItem(116, "Hollywood", "hollywood");
        catSection.addSectionItem(117, "Horror", "horrow");
        catSection.addSectionItem(118, "Inspirational", "inspirtiona");
        catSection.addSectionItem(119, "Islam/Muslim", "islam");
        catSection.addSectionItem(120, "Kids", "kids");
        catSection.addSectionItem(121, "Lonely", "lonely");
        catSection.addSectionItem(122, "Love", "love");
        catSection.addSectionItem(123, "Music", "music");
        catSection.addSectionItem(124, "Others", "othes");
        catSection.addSectionItem(125, "Punjabi", "punjabi");
        catSection.addSectionItem(126, "Quotes", "quotes");
        catSection.addSectionItem(127, "Religious", "religion");
        catSection.addSectionItem(128, "Romantic", "heart");
        catSection.addSectionItem(129, "Sad", "sad");
        catSection.addSectionItem(130, "Shoes", "show");
        catSection.addSectionItem(131, "Sports", "sports");
        catSection.addSectionItem(132, "Troll", "troll");
        catSection.addSectionItem(133, "TV Shows", "tv");
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
        	getCovers("All");
            break;
        case 101:	
    		getCovers("Abstract");
            break;
        case 102:
        	
    		getCovers("Animal");
    		
            
            break;
        case 103:
        	
    		getCovers("Anime");
    		
        	
        	break;
        case 104:
        	
    		getCovers("Attitude");
    		
        	
        	break;
        case 105:
        	
    		getCovers("Bikes");
    		
        	
        	break;
        case 106:
        	
    		getCovers("Boys");
    		
        	
        	break;
        case 107:
        	
    		getCovers("Cars");
    		
        	
        	break;
        case 108:
        	
    		getCovers("Couples");
    		
        	
        	break;
        case 109:
        	
    		getCovers("Festivals");
    		
        	
        	break;
        case 110:
        	
    		getCovers("Friends");
    		
        	
        	break;
        case 111:
        	
    		getCovers("Funny");
    		
        	
        	break;
        case 112:
        	
    		getCovers("Gadgets");
    		
        	
        	break;
        case 113:
        	
    		getCovers("Games");
    		
        	
        	break;
        case 114:
        	
    		getCovers("Girls");
    		
        	
        	break;
        case 115:
        	
    		getCovers("Hearts");
    		
        	
        	break;
        case 116:
        	
    		getCovers("Hollywood");
    		
        	
        	break;
        case 117:
        	
    		getCovers("Horror");
    		
        	
        	break;
        case 118:
        	
    		getCovers("Inspirational");
    		
        	
        	break;
        case 119:
        	
    		getCovers("Islam / Muslim");
    		
        	
        	break;
        case 120:
        	
    		getCovers("Kids");
    		
        	
        	break;
        case 121:
        	
    		getCovers("Lonely");
    		
        	
        	break;
        case 122:
        	
    		getCovers("Love");
    		
        	
        	break;
        case 123:
        	
    		getCovers("Music");
    		
        	
        	break;
        case 124:
        	
    		getCovers("Others");
    		
        	
        	break;
        case 125:
        	
    		getCovers("Punjabi");
    		
        	
        	break;
        case 126:
        	
    		getCovers("Quotes");
    		
        	
        	break;
        case 127:
        	
    		getCovers("Religious");
    		
        	
        	break;
        case 128:
        	
    		getCovers("Romantic");
    		
        	
        	break;
        case 129:
        	
    		getCovers("Sad");
    		
        	
        	break;
        case 130:
        	
    		getCovers("Shoes");
    		
        	
        	break;
        case 131:
        	
    		getCovers("Sports");
    		
        	
        	break;
        case 132:
        	
    		getCovers("Troll");
    		
        	
        	break;
        case 133:
        	
    		getCovers("TV Shows");
    		
        	
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