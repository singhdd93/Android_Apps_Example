package com.netpixel.brainykey;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;



public class SlidingMenuFragment extends SherlockFragment implements ExpandableListView.OnChildClickListener {
	   
    private ExpandableListView sectionListView;
    MainActivity ma;
    int countTotal;
   
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
      //iga = (ImageGridActivity)getActivity();
		ma = (MainActivity)getActivity();
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

        
        
        Section catSection = new Section("Courses");
        catSection.addSectionItem(100, "IAS", "ic_launcher");
        catSection.addSectionItem(101, "Public Administration", "ic_launcher");
        catSection.addSectionItem(102, "Geography", "ic_launcher");
        catSection.addSectionItem(103, "Culture", "ic_launcher");
        catSection.addSectionItem(104, "World History", "ic_launcher");
        catSection.addSectionItem(105, "Ethics & Integrity", "ic_launcher");
        catSection.addSectionItem(106, "World Geography", "ic_launcher");
        catSection.addSectionItem(107, "Case Studies of Ethics Papers", "ic_launcher");
        
       
        
        sectionList.add(catSection);
       
        
        return sectionList;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {

        switch ((int)id) {
        case 100:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/ias/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 101:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/public-administration/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 102:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/geography/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 103:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/culture/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 104:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/world-history/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 105:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/ethics-and-integrity/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 106:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/world-geography/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
        case 107:
        	
        	ma.wb.loadUrl("http://www.brainykey.com/category/case-studies-of-ethics-paper/");
        	ma.wb.setVisibility(View.GONE);
        	ma.pBar.setVisibility(View.VISIBLE);
        	ma.loadingView.setVisibility(View.VISIBLE);
        	if ( ma.slidingMenu.isMenuShowing()) {
                ma.slidingMenu.toggle();
            }
        	
			break;
			
        	

        }
       
        return false;
    }
}