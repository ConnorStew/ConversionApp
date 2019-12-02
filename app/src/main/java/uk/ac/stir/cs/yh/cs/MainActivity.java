package uk.ac.stir.cs.yh.cs;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import uk.ac.stir.cs.yh.cs.database.Database;

public class MainActivity extends AppCompatActivity {

    private PickerFragment pickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Connor", "ON CREATE FOR ACTIVITY!");

        Database.initDB(this);

        setContentView(R.layout.activity_main);

        pickerFragment = new PickerFragment();
        if (savedInstanceState != null) {
            Fragment savedFragment = getSupportFragmentManager().getFragment(savedInstanceState, "picker");
            if (savedFragment != null)
                pickerFragment = (PickerFragment) savedFragment;
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, pickerFragment).addToBackStack(null).commit();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "picker", pickerFragment);
        Log.d("Connor", "Saved picker fragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
