package com.emransac.promotorventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.emransac.promotorventas.Adapters.PfAdapter;
import com.emransac.promotorventas.Entities.Frescos;

import java.util.ArrayList;

public class ProVMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView2;
    private PfAdapter adapter, adapter2;
    Frescos frecos;

    Button btnAdd, btnAdd2;
    public static ArrayList<Frescos> pro_frecos = new ArrayList<>();
    public static ArrayList<Frescos> pro_polvos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_vmain);

        btnAdd = findViewById(R.id.addpolvos);
        btnAdd2 = findViewById(R.id.addfrescos);

        btnAdd.setOnClickListener(v -> {
            frecos = new Frescos(" - ","producto fresco");
            pro_polvos.add(frecos);
            adapter.notifyDataSetChanged();
        });

        btnAdd2.setOnClickListener(v -> {
            frecos = new Frescos(" - ","producto polvo");
            pro_frecos.add(frecos);
            adapter2.notifyDataSetChanged();
        });

        /*
        for(int i=0;i<8;i++){
            String id = String.valueOf(i);
            String nombre = "producto "+i;
            frecos = new Frescos(id,nombre);
            pro_frecos.add(frecos);
        }*/


        recyclerView = findViewById(R.id.mypolvos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PfAdapter(this, pro_polvos);
        recyclerView.setAdapter(adapter);

        recyclerView2 = findViewById(R.id.myfrecos);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new PfAdapter(this, pro_frecos);
        recyclerView2.setAdapter(adapter2);


    }
}