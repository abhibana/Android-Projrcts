package com.practice.compass.myproject;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity{

    private String listChoices[] = {"Count","TextPlay","Email","Call","Photo","Get","GFX","Browser","Tabs","SpeakOut","Notification",
                                    "ListSongs","VideoList"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, listChoices));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try{
            Class calledClass = Class.forName("com.practice.compass.myproject."+listChoices[position]);
            Intent calledIntent = new Intent(Menu.this,calledClass);
            startActivity(calledIntent);
        } catch (ClassNotFoundException e) {}
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

            super.onCreateOptionsMenu(menu);
            MenuInflater blowUp = getMenuInflater() ;
            blowUp.inflate(R.menu.cool_menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.about_us: Intent about = new Intent(Menu.this,About.class);
                                startActivity(about);
                                break;

            case R.id.prefences: Intent prefs = new Intent(Menu.this,Prefs.class);
                                 startActivity(prefs);
                                 break;

            case R.id.exit:      finish();
                                 break;
        }

        return true;
    }
}
