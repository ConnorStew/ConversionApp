package uk.ac.stir.cs.yh.cs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Database;

public class PickerFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_picker, container, false);

        final Context context = getContext();

        new Thread(() -> {
            List<Category> categories = Database.getDB().categoryDao().getAll();
            String[] categoryNames = new String[categories.size()];
            for (int i = 0; i < categories.size(); i++)
                categoryNames[i] = categories.get(i).categoryName;

            Spinner categorySpinner = view.findViewById(R.id.categorySpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            categorySpinner.setAdapter(adapter);
        }).start();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
