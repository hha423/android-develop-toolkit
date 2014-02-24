package com.allthelucky.examples.activity.paraceable;


import android.app.Activity;
import android.os.Bundle;

public class ParceableActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Person p = this.getIntent().getParcelableExtra("PAR");
        System.out.println(p.name);
        System.out.println(p.sex);
        System.out.println(p.age);
        
        for(Addr a: p.list) {
            System.out.println(a.toString());
        }
    }
}
