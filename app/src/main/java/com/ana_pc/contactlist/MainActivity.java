package com.ana_pc.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<Person> nombres = new ArrayList<>();
    private ListView lista;

    private void loadData()
    {
        nombres.add(new Person("Juan 2", null, null, null, null));
        nombres.add(new Person("Ana Sofia", null, null, null, null));
        nombres.add(new Person("Marcos", null, null, null, null));
        nombres.add(new Person("Andrea", null, null, null, null));
        nombres.add(new Person("Nicolas", null, null, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView)findViewById(R.id.lista);
        lista.setAdapter(miAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object person = lista.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", ((Person)person).getName());
                if (((Person)person).getLastName() != null && !((Person)person).getLastName().isEmpty())
                {
                    intent.putExtra("lastName", ((Person)person).getLastName());
                }
                if (((Person)person).getEmailAddress() != null && !((Person)person).getEmailAddress().isEmpty())
                {
                    intent.putExtra("email", ((Person)person).getEmailAddress());
                }
                if (((Person)person).getPhoneNumber() != null && !((Person)person).getPhoneNumber().isEmpty())
                {
                    intent.putExtra("phone", ((Person)person).getPhoneNumber());
                }
                if (((Person)person).getPhoto() != null)
                {
                    intent.putExtra("photo", ((Person)person).getPhoto());
                }
                intent.putExtra("position", position);
                startActivityForResult(intent, 85);
            }
        });

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
                String name = data.getStringExtra("name");
                String lastName = data.getStringExtra("lastName");
                String email = data.getStringExtra("email");
                String phone = data.getStringExtra("phone");
                Bitmap bitmap = (Bitmap)data.getExtras().get("photo");

                String action = data.getStringExtra("action");
                if ("update".equals(action))
                {
                    Integer position = data.getExtras().getInt("position");
                    nombres.get(position).updatePerson(name, lastName, email, phone, bitmap);
                }
                else if ("create".equals(action))
                {
                    nombres.add(new Person(name, lastName, email, phone, bitmap));
                }
                miAdapter.notifyDataSetChanged();
            }
        }
    }

    private BaseAdapter miAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return nombres.size();
        }

        @Override
        public Person getItem(int position) {
            return nombres.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String nombre = getItem(position).getName();
            String lastName = getItem(position).getLastName() != null ? getItem(position).getLastName() : "";

            if(convertView == null){
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.row_persona, parent, false);
            }

            Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

            TextView userIconView = (TextView) convertView.findViewById(R.id.person_icon);
            userIconView.setTypeface(font);

            TextView nombreView = (TextView) convertView.findViewById(R.id.nombre_de_persona);
            nombreView.setText(nombre + " " + lastName);

            return convertView;
        }
    };

}
