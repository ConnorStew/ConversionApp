package uk.ac.stir.cs.yh.cs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import uk.ac.stir.cs.yh.cs.database.Database;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = findViewById(R.id.pager);
        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(pagerAdapter);

        new Thread(() -> {
            Database.initDB(this);
            pager.setCurrentItem(0);
        }).start();
    }
}
