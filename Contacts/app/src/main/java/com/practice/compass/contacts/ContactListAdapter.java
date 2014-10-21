package com.practice.compass.contacts;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {

    private Context context;
    private String contactNames[],contactImageURIs[],contactNumbers[];

    public ContactListAdapter(Context context, String[] contctNames, String[] contactNumbers, String[] contactImageURIs) {

        this.context = context;
        this.contactNames = contctNames;
        this.contactNumbers = contactNumbers;
        this.contactImageURIs = contactImageURIs;
    }

    @Override
    public int getCount() {
        return contactNames.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContatBadgeHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contactbadge,parent,false);
            holder = new ContatBadgeHolder(convertView);
            convertView.setTag(holder);
        }
        else {
                holder = (ContatBadgeHolder) convertView.getTag();
        }

        holder.contactName.setText(contactNames[position]);
        holder.contactnumber.setText(contactNumbers[position]);
        try {
            if(!contactImageURIs[position].equals("")) {
                holder.contactImage.setImageURI(Uri.parse(contactImageURIs[position]));
            }
        }catch (NullPointerException e) {  holder.contactImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.contact)); }

        return convertView;
    }

    class ContatBadgeHolder{

        TextView contactName;
        TextView contactnumber;
        ImageView contactImage;

        ContatBadgeHolder(View v){

            contactName = (TextView) v.findViewById(R.id.contact_name);
            contactnumber = (TextView) v.findViewById(R.id.contact_number);
            contactImage = (ImageView) v.findViewById(R.id.contact_image);
        }
    }
}
