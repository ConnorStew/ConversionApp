package uk.ac.stir.cs.yh.cs;


import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.stir.cs.yh.cs.database.Database;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database.initDB(this);

        setContentView(R.layout.activity_main);

        PickerFragment newFragment = new PickerFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
