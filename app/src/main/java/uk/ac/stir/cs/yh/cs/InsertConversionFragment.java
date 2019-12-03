package uk.ac.stir.cs.yh.cs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Conversion;
import uk.ac.stir.cs.yh.cs.database.Database;
import uk.ac.stir.cs.yh.cs.database.Unit;

public class InsertConversionFragment extends Fragment {

    private Spinner unitOneSpinner;
    private Spinner unitTwoSpinner;
    private EditText conversionFactorEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_insert_conversion, container, false);

        List<Category> categories = Database.getDB().categoryDao().getAll();
        String[] categoryNames = new String[categories.size()];

        unitOneSpinner = view.findViewById(R.id.conversionInsertUnitOneSpinner);
        unitTwoSpinner = view.findViewById(R.id.conversionInsertUnitTwoSpinner);

        conversionFactorEditText = view.findViewById(R.id.conversionFactorEditText);

        Button btnInsertConversion = view.findViewById(R.id.btnInsertConversion);

        for (int i = 0; i < categories.size(); i++)
            categoryNames[i] = categories.get(i).categoryName;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a category to insert a conversion to.");

        builder.setItems(categoryNames, (dialog, selection) -> {
            Category selectedCategory = Database.getDB().categoryDao().getCategoryByName(categoryNames[selection]);
            populateSpinner(unitOneSpinner, Database.getDB().unitDao().getUnitsByCategory(selectedCategory.id));

            unitOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Unit selectUnitOne = (Unit) unitOneSpinner.getSelectedItem();
                    List<Unit> units = Database.getDB().unitDao().getUnitsByCategory(selectedCategory.id);
                    units.remove(selectUnitOne);

                    populateSpinner(unitTwoSpinner, units);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        });

        btnInsertConversion.setOnClickListener((v) -> insertConversion());

        builder.show();

        return view;
    }

    private void insertConversion() {
        Unit unitOne = (Unit) unitOneSpinner.getSelectedItem();
        Unit unitTwo = (Unit) unitTwoSpinner.getSelectedItem();

        String conversionFactorString = conversionFactorEditText.getText().toString();
        double conversionFactor = Double.parseDouble(conversionFactorString);

        Conversion existingConversion = Database.getDB().conversionDao().getConversion(unitOne.id, unitTwo.id);
        if (existingConversion == null) {
            Database.getDB().conversionDao().insertAll(new Conversion(unitOne.id, unitTwo.id, conversionFactor));
        } else {
            Toast.makeText(getContext(), "Conversion between these units already exists.", Toast.LENGTH_LONG).show();
        }

        getActivity().onBackPressed();
    }

    private void populateSpinner(Spinner spinner, List<Unit> units) {
        ArrayAdapter<Unit> categoryArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArrayAdapter.addAll(units);
        spinner.setAdapter(categoryArrayAdapter);
    }
}
