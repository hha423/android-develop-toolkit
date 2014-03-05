package cn.winfirm.test;


import android.app.Activity;
import android.os.Bundle;
import cn.winfirm.test.entity.Addr;
import cn.winfirm.test.entity.Person;

public class OtherActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Person p = this.getIntent().getParcelableExtra("PAR");
        System.out.println(p.getName());
        System.out.println(p.getSex());
        System.out.println(p.getAge());
        
        for(Addr a: p.getList()) {
            System.out.println(a.toString());
        }
    }
}
