package pt.ubi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView lstItem;
    private DBHelp dbh;

    private List<Item> items;
    private List<Item> itemsFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstItem = (ListView) findViewById(R.id.listViewItem);

        dbh = new DBHelp(this);
        items = dbh.getAllItems();
        itemsFiltrados.addAll(items);

        ItemAdapter adapter = new ItemAdapter(MainActivity.this, itemsFiltrados);
        lstItem.setAdapter(adapter);

        if (itemsFiltrados.size() > 0){

            lstItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item i;
                    i = itemsFiltrados.get(position);

                    dbh.plus(i);
                    lstItem.invalidateViews();
                    items = dbh.getAllItems();
                    itemsFiltrados.clear();
                    itemsFiltrados.addAll(items);
                    lstItem.invalidateViews();

                }
            });

        }else{

            Toast.makeText(this, "Insira um novo objecto em \"+\" ", Toast.LENGTH_LONG).show();
        }

        //chamar o menu opcional para a listview
        registerForContextMenu(lstItem);
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_search, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraItem(newText);
                return false;
            }
        });

        return  true;

    }

    public void procuraItem(String nome){
        itemsFiltrados.clear();
        for (Item a : items){
            if(a.getNomeItem().toLowerCase().contains(nome.toLowerCase())){
                itemsFiltrados.add(a);
            }
        }

        lstItem.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        items = dbh.getAllItems();
        itemsFiltrados.clear();
        itemsFiltrados.addAll(items);
        lstItem.invalidateViews();

       lstItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item i;
                i = itemsFiltrados.get(position);
                dbh.plus(i);
                lstItem.invalidateViews();

                items = dbh.getAllItems();
                itemsFiltrados.clear();
                itemsFiltrados.addAll(items);
                lstItem.invalidateViews();

            }
        });

    }

    public void abrir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Item itemAbrir = itemsFiltrados.get(menuInfo.position);
       // Toast.makeText(this, itemAbrir.getDescription(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, idividualItem.class );
        intent.putExtra("itemAbrir", itemAbrir);
        startActivity(intent);

    }

    public void actualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Item itemActualizar = itemsFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this, newItem.class);
        intent.putExtra("item", itemActualizar);
        startActivity(intent);
    }

    //Activity para criar novo item;;
    public void goToNewItem(View view) {
        Intent intent = new Intent(this, newItem.class);
        startActivity(intent);
    }

    public void eliminar(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Item itemEliminar = itemsFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja mesmo eliminar a contagem de  " + itemEliminar.getNomeItem() + "?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsFiltrados.remove(itemEliminar);
                        items.remove(itemEliminar);
                        dbh.eliminar(itemEliminar);
                        lstItem.invalidateViews();
                    }
                }).create();
        dialog.show();
    }






}