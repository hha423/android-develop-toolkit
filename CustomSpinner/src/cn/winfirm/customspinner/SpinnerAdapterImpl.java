package cn.winfirm.customspinner;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Spinner自定义适配器
 * 
 * @author pxw
 */
public class SpinnerAdapterImpl implements SpinnerAdapter {
    Context context;
    List<Itemable> cardList;
    LayoutInflater inflater;

    public SpinnerAdapterImpl(Context context, List<Itemable> list) {
        this.context = context;
        this.cardList = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }
        TextView cardname = (TextView) convertView.findViewById(R.id.cardname);

        Itemable cardItem = cardList.get(position);
        cardname.setText(cardItem.getItemTitle());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item_dropdown, null);
        }
        TextView cardname = (TextView) convertView.findViewById(R.id.cardname);

        Itemable cardItem = cardList.get(position);
        cardname.setText(cardItem.getItemTitle());
        return convertView;
    }

}
