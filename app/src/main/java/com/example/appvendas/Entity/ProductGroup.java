package com.example.appvendas.Entity;

import android.widget.ImageView;

public class ProductGroup {

    private String groupName;
    private int groupImg;
    private boolean selected;

    public ProductGroup(String groupName, int groupImg, boolean selected) {
        this.groupName = groupName;
        this.groupImg = groupImg;
        this.selected = selected;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(int groupImg) {
        this.groupImg = groupImg;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
