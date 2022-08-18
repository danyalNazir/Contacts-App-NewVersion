package com.example.contacts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    ImageView imageView;
    private static final int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        EditText contactName=(EditText) findViewById(R.id.contactName);
        EditText contactNumber=(EditText) findViewById(R.id.contactNumber);
        EditText city=(EditText) findViewById(R.id.city);
        imageView=(ImageView) findViewById(R.id.profile_picture);
        Button addButton=(Button) findViewById(R.id.addButton);
        FloatingActionButton addImageButton=(FloatingActionButton) findViewById(R.id.addImage);

        DbHelper DB;
        DB =new DbHelper(this);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(contactName.getText().toString().length()==0)
                    contactName.setError("Name is required");
                if(contactNumber.getText().toString().length()==0)
                    contactNumber.setError("Number is required");
                if(city.getText().toString().length()==0)
                    city.setError("city is required");


                if(contactName.getText().toString().length()!=0 && contactNumber.getText().toString().length()!=0 && city.getText().toString().length()!=0) {

                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);

                    String date="Created: "+ LocalDate.now().toString() +" " + LocalTime.now().getHour()+":" + LocalTime.now().getMinute();

                    Boolean checkInsertData=DB.CreateContact(contactName.getText().toString(),contactNumber.getText().toString(),city.getText().toString(),date,R.drawable.blankimage);
                    if(checkInsertData==true)
                        Toast.makeText(AddContactActivity.this,"Contact Created!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddContactActivity.this,"Contact Creation Failed!",Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        });
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
    }
}