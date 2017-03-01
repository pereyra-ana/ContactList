package com.ana_pc.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.ana_pc.contactlist.DBHelper.COL_PHOTO;
import static com.ana_pc.contactlist.DBHelper.TABLE_CONTACTS;

public class Main3Activity extends Activity {
    private TextView nameView;
    private TextView lastnameView;
    private TextView emailView;
    private TextView phoneView;
    private ImageView photoView;

    protected void initializeUserView()
    {
        nameView = (TextView) findViewById(R.id.nombre_de_persona);
        lastnameView = (TextView) findViewById(R.id.lastName);
        emailView = (TextView) findViewById(R.id.emailAddress);
        phoneView = (TextView) findViewById(R.id.phoneNumber);
        photoView = (ImageView) findViewById(R.id.image_contact);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        TextView iconMailView = (TextView)findViewById(R.id.icon_mail);
        iconMailView.setTypeface(font);

        TextView iconPhoneView = (TextView)findViewById(R.id.icon_phone);
        iconPhoneView.setTypeface(font);

        TextView userImage = (TextView)findViewById(R.id.icon_image);
        userImage.setTypeface(font);

        Intent intent = getIntent();
        final long id = intent.getLongExtra("personID", 0);
        final DBHelper bh = new DBHelper(this);
        final String idString = Long.toString(id);

        SQLiteDatabase dbRead = bh.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + TABLE_CONTACTS + " WHERE _id = ?", new String[]{ idString });
        cursor.moveToFirst();
        nameView.setText("Name: " + cursor.getString(cursor.getColumnIndex("name")));
        lastnameView.setText("Last name: " + cursor.getString(cursor.getColumnIndex("lastname")));
        emailView.setText("Email Address: " + cursor.getString(cursor.getColumnIndex("email")));
        phoneView.setText("Phone number: " + cursor.getString(cursor.getColumnIndex("phone")));
        String photo = cursor.getString(cursor.getColumnIndex(COL_PHOTO));
        if(photo != null)
        {
            photoView.setImageBitmap(BitmapFactory.decodeFile(photo));
        }
        else
        {
            photoView.setImageResource(R.drawable.user_image);
        }

        cursor.close();
        dbRead.close();

        findViewById(R.id.dial_number_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneView.getText() != null && !phoneView.getText().equals("")) {
                    if (ContextCompat.checkSelfPermission(Main3Activity.this,
                        android.Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(Main3Activity.this,
                                new String[]{android.Manifest.permission.CALL_PHONE},
                                1234);
                    } else {
                        try
                        {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            // TODO Fix show attributes on dashboard
                            String phone = phoneView.getText().toString().split(":")[1].trim();
                            callIntent.setData(Uri.parse("tel:" + phone));
                            startActivity(callIntent);
                        } catch (Exception e) {
                            Toast.makeText(Main3Activity.this, e.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
                }
            }
        });

        findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                intent.putExtra("personID", id);

                startActivityForResult(intent,85);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.initializeUserView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        this.initializeUserView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 85){
            if(resultCode == Activity.RESULT_OK)
            {
                Toast.makeText(Main3Activity.this, "User " + data.getStringExtra("fullname") + " updated", Toast.LENGTH_LONG).show();
            }
        }
    }
}
