package pt.ubi;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemHistoricAdapter extends BaseAdapter {

    private List<ItemHistoric> itemHistorics;
    private Activity activity;

    public ItemHistoricAdapter(Activity activity, List<ItemHistoric> itemh){
        this.activity = activity;
        this.itemHistorics = itemh;
    }

    @Override
    public int getCount() {
        return itemHistorics.size();
    }

    @Override
    public Object getItem(int position) {
        return itemHistorics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemHistorics.get(position).getIdh();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_historic, parent, false);
        TextView datetime = view.findViewById(R.id.txtDatetime);
        TextView ctn = view.findViewById(R.id.txtCntHistoric);

        ItemHistoric ih = itemHistorics.get(position);
        datetime.setText(ih.getDatetime());
        ctn.setText(String.valueOf(ih.getCnt()));

        return view;
    }
}
