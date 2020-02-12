package com.app.firestore_crud;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.app.firestore_crud.fragments.AddEventFragment;
import com.app.firestore_crud.fragments.ViewEventsFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        addEventFrgmt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_m:
                addEventFrgmt();
                return true;
            case R.id.view_events_m:
                viewEventsFrgmt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addEventFrgmt() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new AddEventFragment());
        ft.commit();
    }

    public void viewEventsFrgmt() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new ViewEventsFragment());
        ft.commit();
    }
}
