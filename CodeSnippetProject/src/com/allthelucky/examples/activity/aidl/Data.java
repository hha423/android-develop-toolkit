package com.allthelucky.examples.activity.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 参数为自定义类型时，必须实现Parcelable接口
 * 
 * @author pxw
 * 
 */
public class Data implements Parcelable {
    public String vars;

    @Override
    public String toString() {
        return "vars:=" + vars;
    }

    public static final Parcelable.Creator<Data> CREATOR = new Creator<Data>() {

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }

        @Override
        public Data createFromParcel(Parcel source) {
            Data d = new Data();
            d.vars = source.readString();
            return d;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        vars = dest.readString();
    }

}
