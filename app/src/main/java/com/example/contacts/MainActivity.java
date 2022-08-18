package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Contact> contactList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=(ListView) findViewById(R.id.listView);
        FloatingActionButton addButton=(FloatingActionButton) findViewById(R.id.addImage);

        ListAdapter listAdapter;
        DbHelper DB;
        DB =new DbHelper(this);
        contactList=DB.GetAllContacts();

        listAdapter = (new ListAdapter(MainActivity.this, contactList));
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (contactList.size() >= 1) {
                    Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
                    intent.putExtra("name", contactList.get(i).name);
                    intent.putExtra("number", contactList.get(i).phone);
                    intent.putExtra("city", contactList.get(i).city);
                    intent.putExtra("image", contactList.get(i).imageResource);
                    intent.putExtra("id",contactList.get(i).id);

                    startActivity(intent);
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(intent);
            }
        });
    }
}