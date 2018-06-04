package com.example.final_project_client.Presentation.FullDescription;

import android.Manifest;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.Contact;


/**
 * Created by TAMIR on 5/26/2018.
 */

public class ContactListAdapter extends ArrayAdapter<String> {
    private Context context;
    private Contact [] contacts;
    public ContactListAdapter(Context context, Contact [] contacts) {
        super(context, R.layout.contacts_list_item);
        this.context = context;
        this.contacts = contacts;
    }



    @Override
    public int getCount() {
        return contacts.length;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactsListItemHolder viewHolder = new ContactsListItemHolder();
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.contacts_list_item, parent, false);
            viewHolder.contactNameTxt = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.callBtn = (Button) convertView.findViewById(R.id.callBtn);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ContactsListItemHolder) convertView.getTag();
        }

        Contact contact = contacts[position];
        String contactName =  contact.getName() ;
        final String contactPhone = contact.getPhone() ;



        viewHolder.contactNameTxt.setText(contactName);
        viewHolder.callBtn.setBackgroundResource(android.R.drawable.btn_default);
        viewHolder.callBtn.setText(contactPhone  +" \u260E");
        viewHolder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ contactPhone));
                context.startActivity(callIntent);
            }
        });


        return convertView;
    }

    static class ContactsListItemHolder{
        TextView contactNameTxt;
        Button   callBtn;
    }
}
