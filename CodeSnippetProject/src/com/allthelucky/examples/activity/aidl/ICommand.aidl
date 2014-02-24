package com.allthelucky.examples.activity.aidl;
import com.allthelucky.examples.activity.aidl.Data;

interface ICommand {
    void request(String result);
    void init(in Data data);
}
