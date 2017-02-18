package com.ana_pc.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private String[] nombres = new String[]{
            "Diego", "Nahuel", "Marcelo", "Agustin", "Diego 2", "Juan", "Manuel", "Ezequiel", "Jonathan",
            "Juan 2", "Ana Sofia", "Marcos", "Andrea", "Nicolas"
    };

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView)findViewById(R.id.lista);
        lista.setAdapter( miAdapter);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(intent, 85);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 85){
            if(resultCode == Activity.RESULT_OK){
                /*String nombre = data.getStringExtra("nombre");

                miAdapter.notifyDataSetChanged();*/
            }
        }
    }

    private BaseAdapter miAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return nombres.length;
        }

        @Override
        public String getItem(int position) {
            return nombres[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String nombre = getItem(position);

            if(convertView == null){
                Log.d("Lista De Ejemplo", "(" + position + ") Estoy creando un nuevo objeto");
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.row_persona, parent, false);
            }else{
                Log.d("Lista De Ejemplo", "(" + position + ") Estoy reciclando un objeto");
            }

            TextView nombreView = (TextView) convertView.findViewById(R.id.nombre_de_persona);

            nombreView.setText(nombre);

            return convertView;
        }
    };

}
