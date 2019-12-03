package uk.ac.stir.cs.yh.cs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Database;
import uk.ac.stir.cs.yh.cs.database.Unit;

public class InsertUnitFragment extends Fragment {

    private EditText unitEditText;
    private Spinner categorySpinner;
    private EditText unitSuffixEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_insert_unit, container, false);

        List<Category> categories = Database.getDB().categoryDao().getAll();
        categorySpinner = view.findViewById(R.id.activityInsertCategorySpinner);
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArrayAdapter.addAll(categories);
        categorySpinner.setAdapter(categoryArrayAdapter);


        unitEditText = view.findViewById(R.id.unitNameEditText);
        unitSuffixEditText = view.findViewById(R.id.unitSuffixEditText);

        Button btnInsertCategory = view.findViewById(R.id.btnInsertCategory);
        btnInsertCategory.setOnClickListener(v -> insertUnit());

        return view;
    }

    private void insertUnit() {
        String unitName = unitEditText.getText().toString();
        String unitSuffix = unitSuffixEditText.getText().toString();

        Category selectedCategory = (Category) categorySpinner.getSelectedItem();

        if (Database.getDB().unitDao().getUnitByName(unitName) != null) {
            Toast.makeText(getContext(), "Unit already exists.", Toast.LENGTH_LONG).show();
        } else {
            Database.getDB().unitDao().insertAll(new Unit(unitName, unitSuffix, selectedCategory.id));
        }

        getActivity().onBackPressed();
    }
}
