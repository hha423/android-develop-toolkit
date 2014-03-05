package cn.winfirm.customspinner;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * 自定义Spinner Adapter 应用实例
 * 
 * @author pxw
 */
public class TestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        //generate data, set adapter
        List<Itemable> cardList = new ArrayList<Itemable>();
        for (int i = 0; i < 10; i++) {
            SpinnerItem item = new SpinnerItem();
            item.setItemName("the " + i + " one");
            cardList.add(item);
        }

        SpinnerAdapter adapter = new SpinnerAdapterImpl(this, cardList);
        spinner.setAdapter(adapter);
        
        //set listener
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Itemable item = (SpinnerItem) parent.getAdapter().getItem(position);
                System.out.println(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });
    }
}
