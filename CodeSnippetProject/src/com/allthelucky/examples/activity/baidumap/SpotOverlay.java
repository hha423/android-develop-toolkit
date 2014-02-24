package com.allthelucky.examples.activity.baidumap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.Projection;

/**
 * 添加图标图层,不可点击
 * 
 * @author pxw
 * 
 */
public class SpotOverlay extends Overlay {

    private Context mContext;
    private GeoPoint gepPoint;
    private int resId;

    public SpotOverlay(Context context, int resId, GeoPoint gepPoint) {
        this.mContext = context;
        this.gepPoint = gepPoint;
        this.resId = resId;
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        super.draw(canvas, mapView, shadow);

        Projection projection = mapView.getProjection();
        Point point = new Point();
        projection.toPixels(gepPoint, point);// 将GeoPoint 转换成point.
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);

        int x = point.x + 5;
        int y = point.y - bitmap.getHeight();

        canvas.drawBitmap(bitmap, x, y, new Paint());
    }
}
