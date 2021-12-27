package pt.ubi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class newItem extends AppCompatActivity {

   private TextInputEditText nomeitem, cnt, description;

   private DBHelp db;
   private Item item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        nomeitem = (TextInputEditText) findViewById(R.id.editNameItem);
        cnt = (TextInputEditText) findViewById(R.id.editContagem);
        description = (TextInputEditText) findViewById(R.id.editDescription);

        db = new DBHelp(this);

        //Apanhar o obejcto para editar enviado pelo Intent
        Intent it = getIntent();
        if(it.hasExtra("item")){
            item = (Item) it.getSerializableExtra("item");
            nomeitem.setText(item.getNomeItem());
            cnt.setText(String.valueOf(item.getCont()));
            description.setText(item.getDescription());
        }
    }

    public  void AddItemDB(View view){
        if(item == null) {
            Item obj = new Item();

            if(nomeitem.getText().toString().matches("")) {
                Toast.makeText(this, "É necessário inserir um nome.", Toast.LENGTH_SHORT).show();
            }else {
                if (cnt.getText().toString().matches("")) {
                    cnt.setText("0");
                }
                    obj.setNomeItem(nomeitem.getText().toString());
                    obj.setCont(Integer.parseInt(cnt.getText().toString()));


                    obj.setDescription(description.getText().toString());

                    db.inserir(obj);
                    Toast.makeText(this, "Objeto inserido com sucesso.", Toast.LENGTH_SHORT).show();
            }
        }else
            {
                item.setNomeItem(nomeitem.getText().toString());
                if(cnt.getText().toString().matches("")){
                    Toast.makeText(this, "Este valor não pode ser vazio.", Toast.LENGTH_SHORT).show();
                }else {

                    item.setCont(Integer.parseInt(cnt.getText().toString()));
                    item.setDescription(description.getText().toString());
                    db.actualizar(item);
                    Toast.makeText(this, "Objeto actualizado.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

