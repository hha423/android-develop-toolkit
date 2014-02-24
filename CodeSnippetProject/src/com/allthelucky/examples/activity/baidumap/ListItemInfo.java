package com.allthelucky.examples.activity.baidumap;

import java.io.Serializable;

import com.baidu.mapapi.GeoPoint;

public class ListItemInfo implements Serializable {
    private static final long serialVersionUID = 2145221756623443656L;
    public GeoPoint geoPoint;
    public String title;
    public String description;

    public ListItemInfo(GeoPoint geoPoint, String title, String description) {
        this.geoPoint = geoPoint;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ListItemInfo [geoPoint=" + geoPoint + ", title=" + title + ", description=" + description + "]";
    }
}
