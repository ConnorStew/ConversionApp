package uk.ac.stir.cs.yh.cs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Database;
import uk.ac.stir.cs.yh.cs.database.Unit;

public class PickerFragment extends Fragment {

    private Spinner categorySpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<Category> categories = new ArrayList<>(Database.getDB().categoryDao().getAll());

        final Context context = getContext();

        View view = inflater.inflate(R.layout.activity_picker, container, false);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        fromSpinner = view.findViewById(R.id.fromSpinner);
        toSpinner = view.findViewById(R.id.toSpinner);
        final Button btnConvert = view.findViewById(R.id.btnConvert);

        setSpinnerItems(categorySpinner, categories, context);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromUnitSelected(context);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected(context);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnConvert.setOnClickListener(this::btnConvertClicked);

        return view;
    }

    private void btnConvertClicked(View view) {
        ConversionFragment newFragment = new ConversionFragment();

        Bundle argBundle = new Bundle();
        argBundle.putSerializable("fromUnit", (Unit) fromSpinner.getSelectedItem());
        argBundle.putSerializable("toUnit", (Unit) toSpinner.getSelectedItem());

        newFragment.setArguments(argBundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void categorySelected(Context context) {
        Log.d("Click", "category selected");
        setSpinnerItems(fromSpinner, getUnitsFromSelectedCategory(), context);
    }

    private void fromUnitSelected(Context context) {
        Log.d("Click", "from spinner selected");
        Unit fromSelected = (Unit) fromSpinner.getSelectedItem();
        List<Unit> units = getUnitsFromSelectedCategory();
        units.remove(fromSelected);
        setSpinnerItems(toSpinner, units, context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<Unit> getUnitsFromSelectedCategory() {
        Category selectedCategory = (Category) categorySpinner.getSelectedItem();
        return Database.getDB().unitDao().getUnitsByCategory(selectedCategory.id);
    }

    private void setSpinnerItems(Spinner spinner, List<?> values, Context context) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}
