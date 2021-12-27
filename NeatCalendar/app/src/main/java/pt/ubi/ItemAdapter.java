package pt.ubi;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private List<Item> items;
    private Activity activity;

    public ItemAdapter(Activity activity, List<Item> items ) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.listitem, parent, false);
        TextView nome = view.findViewById(R.id.txtNome);
        TextView contagem = view.findViewById(R.id.txtContagem);
        TextView datetime = view.findViewById(R.id.txtdatetime);

        Item i = items.get(position);
        nome.setText(i.getNomeItem());
        contagem.setText(String.valueOf(i.getCont()));
        datetime.setText(i.getDatetime());
        return view;
    }
}
