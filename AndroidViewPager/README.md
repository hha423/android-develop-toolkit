Android ViewPagerIndicatorView
===========================

An android tab indicator with viewpager easy to use!

Usage:
--------------------------

1.in android layout file:

        <com.allthelucky.common.view.ViewPagerIndicatorView
        android:id="@+id/viewpager_indicator_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />


2.set ViewPagerIndicatorView in java code:

        ViewPagerIndicatorView viewPagerIndicatorView; = (ViewPagerIndicatorView) findViewById(R.id.viewpager_indicator_view);
        final Map<String, View> map = new HashMap<String, View>();
        map.put("TAB1", LayoutInflater.from(this).inflate(R.layout.activity_sample_pager_0, null));
        map.put("TAB2", LayoutInflater.from(this).inflate(R.layout.activity_sample_pager_1, null));
        viewPagerIndicatorView.setupLayout(map);

Screenshot:
--------------------------

![ScreenShot](https://raw.github.com/allthelucky/android-viewpager-indicator/master/AndroidViewpagerIndicator/screenshot/shortcuta.jpg)

![ScreenShot](https://raw.github.com/allthelucky/android-viewpager-indicator/master/AndroidViewpagerIndicator/screenshot/shortcutb.jpg)

![ScreenShot](https://raw.github.com/allthelucky/android-viewpager-indicator/master/AndroidViewpagerIndicator/screenshot/shortcutc.jpg)
