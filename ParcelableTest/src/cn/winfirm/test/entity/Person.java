package cn.winfirm.test.entity;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息类
 * 
 * @author pxw
 * 
 */
public class Person implements Parcelable {
    String name;
    public String sex;
    int age;
    List<Addr> list; // 列表的Addr信息类必须实现Seriable接口

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Addr> getList() {
        return list;
    }

    public void setList(List<Addr> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeInt(age);
        dest.writeList(list);
    }

    public final static Parcelable.Creator<Person> CREATOR = new Creator<Person>() {

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }

        @SuppressWarnings("unchecked")
        @Override
        public Person createFromParcel(Parcel source) {
            Person p = new Person();
            p.name = source.readString();
            p.sex = source.readString();
            p.age = source.readInt();
            p.list = source.readArrayList(ArrayList.class.getClassLoader());
            return p;
        }
    };

}
