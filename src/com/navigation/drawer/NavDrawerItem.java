package com.navigation.drawer;

public class NavDrawerItem {
    
    private String title;
    private String subtitle;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;
     
    public NavDrawerItem(){}
 
    public NavDrawerItem(String title, String subtitle, int icon){
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }
     
    public NavDrawerItem(String title, String subtitle, int icon, boolean isCounterVisible, String count){
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }
     
    public String getTitle(){
        return this.title;
    }
     
    public String getSubtitle() {
    	return this.subtitle;
    }
    
    public int getIcon(){
        return this.icon;
    }
     
    public String getCount(){
        return this.count;
    }
     
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setSubtitle(String subtitle) {
    	this.subtitle = subtitle;
    }
    
    public void setIcon(int icon){
        this.icon = icon;
    }
     
    public void setCount(String count){
        this.count = count;
    }
     
    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
}
