package com.emransac.promotorventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.emransac.promotorventas.Adapters.PfAdapter;
import com.emransac.promotorventas.Entities.Frescos;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProVMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView2;
    private PfAdapter adapter, adapter2;

    String[] distribuidores = {"Distribuidor1","Distribuidor2","Distribuidor3","Distribuidor4"};
    String[] categorias = {"Bodega","Minimarket","Supermercado","Especiería","Puesto de Mercado",
            "Tienda de Abarrotes","Food Truck","Puesto de Comida","Snack","Carniceria","Pizzeria","Otros"};
    String Distribuidor;
    String categoriasfinal;
    AutoCompleteTextView distribuidor;
    AutoCompleteTextView categoria;
    ArrayAdapter<String> adapterdistribuidores;
    ArrayAdapter<String> adaptercategoria;



    EditText txtDistribuido,txtCliente, txtTelefono, txtDireccion,txtNombreComercial,txtCategoria,txtPolvos,txtFrescos,txtVentas,txtObservaciones;
    private CheckBox checkBoxExhibidor;
    private CheckBox checkBoxPop;

    Button btn_insert;
    Frescos frecos;

    int contador = 0;
    int contador2 = 0;
    Button btnAdd, btnAdd2;
    public static ArrayList<Frescos> pro_frecos = new ArrayList<>();
    public static ArrayList<Frescos> pro_polvos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_vmain);



        pro_frecos.clear();
        pro_polvos.clear();

        btnAdd = findViewById(R.id.addpolvos);
        btnAdd2 = findViewById(R.id.addfrescos);




        recyclerView = findViewById(R.id.mypolvos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PfAdapter(this, pro_polvos);
        recyclerView.setAdapter(adapter);

        recyclerView2 = findViewById(R.id.myfrecos);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new PfAdapter(this, pro_frecos);
        recyclerView2.setAdapter(adapter2);



        // start get data from layout

        distribuidor = findViewById(R.id.distribuidor_txt);
        adapterdistribuidores = new ArrayAdapter<String>(this,R.layout.list_item,distribuidores);
        distribuidor.setAdapter(adapterdistribuidores);
        distribuidor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //accion que se debe mandar al los campos editables
                String item = parent.getItemAtPosition(position).toString();
                Distribuidor = item;
                //txtTienda = findViewById(R.id.tienda);
                //txtTienda.setText(item);
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });






        txtCliente = findViewById(R.id.cliente_txt);
        txtTelefono = findViewById(R.id.telefono_txt);
        txtDireccion = findViewById(R.id.direccion_txt);
        txtNombreComercial = findViewById(R.id.nombreco_txt);




        categoria = findViewById(R.id.categoria_txt);
        adaptercategoria = new ArrayAdapter<String>(this,R.layout.list_item,categorias);
        categoria.setAdapter(adaptercategoria);
        categoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //accion que se debe mandar al los campos editables
                String item = parent.getItemAtPosition(position).toString();
                categoriasfinal = item;
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        txtPolvos = findViewById(R.id.polvos_txt);
        txtPolvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPolvos.setText(""); // Establecer el texto en blanco
            }
        });



        txtFrescos = findViewById(R.id.frescos_txt);

        txtFrescos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFrescos.setText(""); // Establecer el texto en blanco
            }
        });


        btnAdd.setOnClickListener(v -> {
            contador++;
            final String polvos = txtPolvos.getText().toString().trim();
            frecos = new Frescos(Integer.toHexString(contador),polvos);
            pro_polvos.add(frecos);
            adapter.notifyDataSetChanged();
        });

        btnAdd2.setOnClickListener(v -> {
            contador2++;
            final String frescos = txtFrescos.getText().toString().trim();
            frecos = new Frescos(Integer.toString(contador2),frescos);
            pro_frecos.add(frecos);
            adapter2.notifyDataSetChanged();
        });


        checkBoxExhibidor = findViewById(R.id.exhibidor);
        checkBoxPop = findViewById(R.id.pop);


        txtVentas = findViewById(R.id.Ventas_txt);
        txtObservaciones = findViewById(R.id.observaciones_txt);

        btn_insert = findViewById(R.id.btn_guardar);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData() {



        final String distribuidor = Distribuidor;
        final String cliente = txtCliente.getText().toString().trim();//obligatorio
        final String telefono = txtTelefono.getText().toString().trim();//opcional
        final String direccion = txtDireccion.getText().toString().trim();//obligatorio
        final String nombrecomercial = txtNombreComercial.getText().toString().trim();//opcional
        final String categoria = categoriasfinal;


        final String polvos = txtPolvos.getText().toString().trim();
        final String frescos = txtFrescos.getText().toString().trim();

        boolean isCheckedEx = checkBoxExhibidor.isChecked();
        boolean isCheckedPop = checkBoxPop.isChecked();


        final String ventas = txtVentas.getText().toString().trim();
        final String observaciones = txtObservaciones.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(cliente.isEmpty()){
            Toast.makeText(this, "Ingrese Cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(direccion.isEmpty()){
            Toast.makeText(this, "Ingrese Teléfono", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(categoria.isEmpty()){
            Toast.makeText(this, "Ingrese Categoria", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(ventas.isEmpty()){
            Toast.makeText(this, "Ingrese Ventas", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            List<Frescos> lista = adapter.getPfList();
            String[] arraypolvos = new String[lista.size()];
            int i = 0;
            for (Frescos f : lista) {
                arraypolvos[i] = f.getName();
                i++;
            }


            List<Frescos> lista2 = adapter2.getPfList();
            String[] arraypolvos2 = new String[lista2.size()];
            int i2 = 0;
            for (Frescos f2 : lista2) {
                arraypolvos2[i2] = f2.getName();
                i2++;
            }

            JSONArray jsonArray = new JSONArray(Arrays.asList(arraypolvos));
            JSONArray jsonArray2 = new JSONArray(Arrays.asList(arraypolvos2));


            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/android/insertar_reporte_promotor.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("Se guardo correctamente.")){
                                Toast.makeText(ProVMainActivity.this, "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(ProVMainActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProVMainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();

                    String valorCadena1 = String.valueOf(isCheckedEx);
                    String valorCadena2 = String.valueOf(isCheckedPop);


                    params.put("distribuidor",distribuidor);
                    params.put("cliente",cliente);
                    params.put("telefono",telefono);
                    params.put("direccion",direccion);
                    params.put("nombrecomercial",nombrecomercial);
                    params.put("categoriasfinal",categoriasfinal);
                    //params.put("polvos",polvos);
                    //params.put("frescos",frescos);
                    params.put("polvosarr", jsonArray.toString());
                    params.put("frescosarr", jsonArray2.toString());
                    params.put("isCheckedEx",valorCadena1);
                    params.put("isCheckedPop",valorCadena2);
                    params.put("ventas",ventas);
                    params.put("observaciones",observaciones);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(ProVMainActivity.this);
            requestQueue.add(request);


        }

        Log.d("TAG", "distrinuidor: " + distribuidor +
                "  cliente: " + cliente +
                "  Telefono: " + telefono +
                "  Direccion: " + direccion +
                "  NombreCom: " + nombrecomercial +
                "  Categoria: " + categoriasfinal +
                "  Polvos: " + polvos +
                "  Frescos: " + frescos +
                "  Exhibidor: " + isCheckedEx +
                "  Pop: " + isCheckedPop +
                "  Ventas" + ventas +
                "  Observaciones: " + observaciones);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
