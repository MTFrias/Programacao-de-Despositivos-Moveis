package pt.ubi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class idividualItem extends AppCompatActivity {

    private TextView nomeItem, descItem, cntItem;
    private Button btnplus, btnminus, btn_StopCnt;
    public ListView lstHistoric;

    private DBHelp db;
    public  Item item = null;
    public  ItemHistoric itemh = new ItemHistoric();

    public List<ItemHistoric> lstItemH ;
    public List<ItemHistoric> itemshistFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idividual_item);

        //Button
        btnplus = findViewById(R.id.btn_plus);
        btnminus = findViewById(R.id.btnminus);
        btn_StopCnt = findViewById(R.id.btn_StopCnt);
        //TextView
        nomeItem = findViewById(R.id.identificaItem);
        descItem = findViewById(R.id.descricaoItem);
        cntItem = findViewById(R.id.txtCont);
        lstHistoric = findViewById(R.id.LstHistorico);


        Intent it = getIntent();
        if(it.hasExtra("itemAbrir")){
            item = (Item) it.getSerializableExtra("itemAbrir");
            nomeItem.setText(item.getNomeItem());
            cntItem.setText(String.valueOf(item.getCont()));
            descItem.setText( item.getDescription());

            itemh.setCnt(Integer.parseInt(cntItem.getText().toString()));
        }

         db = new DBHelp(this);
         lstItemH = db.getAllhistoric(item);
         itemshistFiltrados.addAll(lstItemH);

         ItemHistoricAdapter adapter = new ItemHistoricAdapter(idividualItem.this, itemshistFiltrados);
         lstHistoric.setAdapter(adapter);

         registerForContextMenu(lstHistoric);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_contexto_historico, menu);
    }

   public void incremento(View view){

         int value = Integer.parseInt(cntItem.getText().toString());
         value = value + 1;
         cntItem.setText(String.valueOf(value));
         itemh.setCnt(Integer.parseInt(cntItem.getText().toString()));
    }

    public void decremento(View view){
        int value = Integer.parseInt(cntItem.getText().toString());
        if(value > 0){
             cntItem.setText(String.valueOf(value - 1));
             itemh.setCnt(value - 1);
        }else{
            Toast.makeText(this, "Este valor não pode ser menor que 0", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopContagem(View view){

        db.inserirHistorico(item, itemh);

        lstItemH = db.getAllhistoric(item);
        itemshistFiltrados.clear();
        itemshistFiltrados.addAll(lstItemH);

        ItemHistoricAdapter adapter = new ItemHistoricAdapter(idividualItem.this, itemshistFiltrados);
        lstHistoric.setAdapter(adapter);
        lstHistoric.invalidateViews();

        cntItem.setText(String.valueOf(0));
        db.PlusValue(item, Integer.parseInt(cntItem.getText().toString()));


    }

    @Override
    public void onResume(){
        super.onResume();

        lstHistoric.invalidateViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.PlusValue(item, Integer.parseInt(cntItem.getText().toString()));
    }

    public void eliminarHistorico(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ItemHistoric itemhEliminar = itemshistFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja mesmo eliminar a contagem" + itemhEliminar.getDatetime() + "?")
                .setNegativeButton("Não", null)
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemshistFiltrados.remove(itemhEliminar);
                        lstItemH.remove(itemhEliminar);
                        db.eliminarHistorico(itemhEliminar);
                        lstHistoric.invalidateViews();
                    }
                }).create();

            dialog.show();
    }

}

