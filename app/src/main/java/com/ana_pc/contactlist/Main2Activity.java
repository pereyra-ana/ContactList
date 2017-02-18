package com.ana_pc.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        TextView iconMailView = (TextView)findViewById(R.id.icon_mail);
        iconMailView.setTypeface(font);

        TextView iconPhoneView = (TextView)findViewById(R.id.icon_phone);
        iconPhoneView.setTypeface(font);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView edit = (TextView)findViewById(R.id.nombre_de_persona);

                String nombre = edit.getText().toString();

                Intent intent2 = new Intent();

                intent2.putExtra("nombre", nombre);


                Main2Activity.this.setResult(RESULT_OK, intent2);

                Main2Activity.this.finish();

            }
        });

    }
}
