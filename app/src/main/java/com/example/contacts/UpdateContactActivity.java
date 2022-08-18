package com.example.contacts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateContactActivity extends AppCompatActivity {
    ImageView imageView;
    private static final int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);


        EditText contactName=(EditText) findViewById(R.id.contactName);
        EditText contactNumber=(EditText) findViewById(R.id.contactNumber);
        EditText city=(EditText) findViewById(R.id.city);
        imageView=(ImageView) findViewById(R.id.profile_picture);
        Button updateButton=(Button) findViewById(R.id.updateButton);
        FloatingActionButton addImageButton=(FloatingActionButton) findViewById(R.id.addImage);

        DbHelper DB;
        DB =new DbHelper(this);

        Intent intent=getIntent();
        Bundle bundle = getIntent().getExtras();
        Integer id = bundle.getInt("id", -1);

        Contact contact=DB.getContact(id);

        imageView.setImageResource(contact.imageResource);
        contactName.setText(contact.name);
        contactNumber.setText(contact.phone);
        city.setText(contact.city);

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
        updateButton.setOnClickListener(new View.OnClickListener() {
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

                    Intent intent = new Intent(UpdateContactActivity.this, MainActivity.class);

                    String date="Updated: "+ LocalDate.now().toString() +" " + LocalTime.now().getHour()+":" + LocalTime.now().getMinute();

                    Boolean checkUpdateData=DB.UpdateContact(id,contactName.getText().toString(),contactNumber.getText().toString(),city.getText().toString(),date,R.drawable.blankimage);
                    if(checkUpdateData==true)
                        Toast.makeText(UpdateContactActivity.this,"Contact Updated!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(UpdateContactActivity.this,"Contact Updation Failed!",Toast.LENGTH_SHORT).show();

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