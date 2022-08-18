package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        ImageView imageView=(ImageView) findViewById(R.id.profile_picture);
        TextView textViewName=(TextView) findViewById(R.id.contactName);
        TextView textViewNumber=(TextView) findViewById(R.id.contactNumber);
        TextView textViewCity=(TextView) findViewById(R.id.city);

        ImageButton buttonCall=(ImageButton) findViewById(R.id.buttonCall);
        ImageButton buttonMessage=(ImageButton) findViewById(R.id.buttonMessage);

        Button buttonUpdate=(Button) findViewById(R.id.buttonUpdate);
        Button buttonDelete=(Button) findViewById(R.id.buttonDelete);

        DbHelper DB;
        DB =new DbHelper(this);

        Intent intent=getIntent();

        if(intent!=null)
        {
            imageView.setImageResource(intent.getIntExtra("image",R.drawable.blankimage));
            textViewName.setText(intent.getStringExtra("name"));
            textViewNumber.setText(intent.getStringExtra("number"));
            textViewCity.setText(intent.getStringExtra("city"));

            buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("tel:" + intent.getStringExtra("number"));
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(uri);
                    startActivity(callIntent);
                }
            });
            buttonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("smsto:" +  textViewNumber.getText().toString());
                    Intent msgIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    startActivity(msgIntent);
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ContactDetailsActivity.this, MainActivity.class);

                    Bundle bundle = getIntent().getExtras();
                    Integer id = bundle.getInt("id", -1);

                   Boolean checkDeleteData= DB.DeleteContact(id);
                    if(checkDeleteData==true)
                        Toast.makeText(ContactDetailsActivity.this,"Contact Deleted!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ContactDetailsActivity.this,"Contact Deletion Failed!",Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            });
        }
    }
}