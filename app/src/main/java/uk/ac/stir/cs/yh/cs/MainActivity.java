package uk.ac.stir.cs.yh.cs;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.stir.cs.yh.cs.database.Database;

/**
 * This activity hosts fragments used by the Application.
 * @author Connor Stewart
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new PickerFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments().get(0) instanceof PickerFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
