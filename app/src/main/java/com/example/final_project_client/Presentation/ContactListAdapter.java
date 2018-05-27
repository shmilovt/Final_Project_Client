package com.example.final_project_client.Presentation;

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
            viewHolder.contactPhoneTxt = (TextView) convertView.findViewById(R.id.contactPhone);
            viewHolder.callBtn = (ImageButton) convertView.findViewById(R.id.callBtn);
            viewHolder.colonTxt = (TextView) convertView.findViewById(R.id.ContactListItemColon);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ContactsListItemHolder) convertView.getTag();
        }

        Contact contact = contacts[position];
        String contactName =  contact.getName() ;
        final String contactPhone = contact.getPhone() ;

        if (contactName.compareTo("") == 0){
            viewHolder.colonTxt.setText("");
        }

        viewHolder.contactNameTxt.setText(contactName);
        viewHolder.contactPhoneTxt.setText(contactPhone);
        viewHolder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0522204747" /*+ contactPhone*/));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });


        return convertView;
    }

    static class ContactsListItemHolder{
        TextView contactNameTxt;
        TextView contactPhoneTxt;
        TextView colonTxt;
        ImageButton   callBtn;
    }
}
