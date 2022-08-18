package com.example.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Contact> {
    public ListAdapter(Context context, ArrayList<Contact> contactArrayList){
        super(context,R.layout.list_item,contactArrayList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Contact contact=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        ImageView imageView=convertView.findViewById(R.id.profile_picture);
        TextView contactName=convertView.findViewById(R.id.TextViewContactName);
        TextView contactNumber=convertView.findViewById(R.id.TextViewContactNumber);
        TextView contactCreated=convertView.findViewById(R.id.TextViewContactCreated);

        imageView.setImageResource(contact.imageResource);
        contactName.setText(contact.name);
        contactNumber.setText(contact.phone);
        contactCreated.setText(contact.contactCreated);
        return convertView;
    }
}
