package cn.winfirm.test;

import java.util.ArrayList;
import java.util.List;

import cn.winfirm.test.entity.Addr;
import cn.winfirm.test.entity.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Person p = new Person();
        p.setName("hello");
        p.setAge(20);
        p.setSex("male");

        List<Addr> l = new ArrayList<Addr>();
        Addr a = new Addr();
        a.setAddr("wu han, hb");
        a.setPost("445000");
        l.add(new Addr(a));
        
        a = new Addr();
        a.setAddr("jz, hb");
        a.setPost("434023");
        l.add(new Addr(a));
        p.setList(l);

        Intent data = new Intent(this, OtherActivity.class);
        data.putExtra("PAR", p);
        startActivity(data);
    }
}
