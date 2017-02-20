package com.ana_pc.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends Activity {

    private Integer position = null;
    private static final int REQUEST_CAMERA = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        TextView iconMailView = (TextView)findViewById(R.id.icon_mail);
        iconMailView.setTypeface(font);

        TextView iconPhoneView = (TextView)findViewById(R.id.icon_phone);
        iconPhoneView.setTypeface(font);

        TextView editUserImage = (TextView)findViewById(R.id.icon_edit);
        editUserImage.setTypeface(font);

        TextView editImage = (TextView)findViewById(R.id.edit_image);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        Intent intent = this.getIntent();
        if (intent.getStringExtra("name") != null)
        {
            Log.d("CONTACT LIST", "Update action - Nombre: " + intent.getStringExtra("name"));
            TextView nameText = (TextView)findViewById(R.id.nombre_de_persona);
            nameText.setText(intent.getStringExtra("name"));

            if (intent.getStringExtra("lastName") != null) {
                TextView lastNameText = (TextView) findViewById(R.id.lastName);
                lastNameText.setText(intent.getStringExtra("lastName"));
            }
            if (intent.getStringExtra("email") != null) {
                TextView emailText = (TextView) findViewById(R.id.emailAddress);
                emailText.setText(intent.getStringExtra("email"));
            }
            if (intent.getStringExtra("phone") != null) {
                TextView phoneText = (TextView) findViewById(R.id.phoneNumber);
                phoneText.setText(intent.getStringExtra("phone"));
            }
            if (intent.getExtras().get("photo") != null) {
                ImageView image = (ImageView)findViewById(R.id.image_contact);
                image.setImageBitmap((Bitmap)intent.getExtras().get("photo"));
            }
            position = intent.getExtras().getInt("position");
        }
        else{
            Log.d("CONTACT LIST", "Create action");
        }

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView nameEdit = (TextView)findViewById(R.id.nombre_de_persona);
                TextView lastNameEdit = (TextView)findViewById(R.id.lastName);
                TextView emailEdit = (TextView)findViewById(R.id.emailAddress);
                TextView phoneEdit = (TextView)findViewById(R.id.phoneNumber);
                ImageView photo = (ImageView)findViewById(R.id.image_contact);

                String name = nameEdit.getText().toString();

                if (name == null || name.isEmpty())
                {
                    Toast.makeText(Main2Activity.this, "Name can not be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                String lastName = lastNameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)photo.getDrawable()) != null ? ((BitmapDrawable)photo.getDrawable()).getBitmap() : null;

                Intent intent2 = new Intent();
                intent2.putExtra("name", name);
                intent2.putExtra("lastName", lastName);
                intent2.putExtra("email", email);
                intent2.putExtra("phone", phone);
                intent2.putExtra("photo", bitmap);

                if (position != null)
                {
                    intent2.putExtra("action", "update");
                    intent2.putExtra("position", position);
                }
                else
                {
                    intent2.putExtra("action", "create");
                }
                Main2Activity.this.setResult(RESULT_OK, intent2);
                Main2Activity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA){
            if(resultCode == Activity.RESULT_OK)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageView image = (ImageView)findViewById(R.id.image_contact);
                image.setImageBitmap(photo);
            }
        }
    }
}
