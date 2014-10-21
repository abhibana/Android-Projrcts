package com.practice.compass.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ContactList extends Activity implements ListView.OnItemClickListener{
    String contactNames[];
    String contactNumbers[];
    String contactImageURIs[];
    ListView listView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactlist);
        setUpcontacts();
        listView = (ListView) findViewById(R.id.contact_list);
        listView.setAdapter(new ContactListAdapter(this,contactNames,contactNumbers,contactImageURIs));
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactNumbers[position]));
        startActivity(intent);
        finish();
    }

    private void setUpcontacts(){

        String projection[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        cursor = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        int nameId = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numberId = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int imageId = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

        String names[] = new String[cursor.getCount()];
        String numbers[] = new String[cursor.getCount()];
        String imageURIs[] = new String[cursor.getCount()];

        while(cursor.moveToNext()){
            names[cursor.getPosition()] = cursor.getString(nameId);
            numbers[cursor.getPosition()] = cursor.getString(numberId);
            imageURIs[cursor.getPosition()] = cursor.getString(imageId);
        }
        contactNames = names;
        contactNumbers = numbers;
        contactImageURIs = imageURIs;
    }
}
