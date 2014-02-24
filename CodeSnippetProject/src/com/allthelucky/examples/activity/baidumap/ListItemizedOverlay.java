package com.allthelucky.examples.activity.baidumap;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.OverlayItem;

public class ListItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    public List<OverlayItem> overlayList = new ArrayList<OverlayItem>();

    public ListItemizedOverlay(Drawable drawable, List<ListItemInfo> list) {
        super(boundCenterBottom(drawable));
        if (list != null && list.size() > 0) {
            for (ListItemInfo item : list) {
                overlayList.add(new OverlayItem(item.geoPoint, item.title, item.description));
            }
        }
        populate();
    }

    @Override
    protected OverlayItem createItem(int location) {
        return overlayList.get(location);
    }

    /**
     * 点击响应回调
     */
    @Override
    protected boolean onTap(int location) {
        return super.onTap(location);
    }

    @Override
    public int size() {
        return 0;
    }

}
