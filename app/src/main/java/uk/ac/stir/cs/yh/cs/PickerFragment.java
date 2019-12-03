package uk.ac.stir.cs.yh.cs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Database;
import uk.ac.stir.cs.yh.cs.database.Unit;

/**
 * This fragment allows the user to select a category and units to convert between.
 * @author Connor Stewart
 */
public class PickerFragment extends Fragment {

    /** Allows the user to select a category. */
    private Spinner categorySpinner;

    /** Allows the user to select the unit to convert from. */
    private Spinner fromSpinner;

    /** Allows the user to select the unit to convert to. */
    private Spinner toSpinner;

    /** Stores categories being shown in the categorySpinner. */
    private ArrayAdapter<Category> categoryArrayAdapter;

    /** Stores units being shown in the toSpinner. */
    private ArrayAdapter<Unit> toUnitArrayAdapter;

    /** Stores units being shown in the fromSpinner. */
    private ArrayAdapter<Unit> fromUnitArrayAdapter;

    /** The last category selected by the user. */
    private Category lastSelectedCategory = null;

    /** Whether the initial data has been inserted into the spinners. */
    private boolean initialised = false;

    /** The saved instance state. */
    private Bundle savedInstanceState;

    //keys for storing data

    /** The key for storing the selected category. */
    private static final String CATEGORY_KEY = "selectedCategory";

    /** The key for storing the selected from unit. */
    private static final String FROM_UNIT_KEY = "fromUnit";

    /** The key for storing the selected to unit. */
    private static final String TO_UNIT_KEY = "toUnit";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.initDB(getContext());

        //initialise array adapters for storing data while in another fragment

        categoryArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnitArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        fromUnitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toUnitArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        toUnitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        //initialise UI
        View view = inflater.inflate(R.layout.activity_picker, container, false);

        initialiseSpinners(view);
        initialiseEvents(view);

        //set all data in spinners to default if they haven't been initialised yet
        List<Category> categories = Database.getDB().categoryDao().getAll();

        categoryArrayAdapter.clear();
        categoryArrayAdapter.addAll(categories);


        List<Unit> units = Database.getDB().unitDao().getUnitsByCategory(categories.get(0).id);
        fromUnitArrayAdapter.clear();
        fromUnitArrayAdapter.addAll(units);

        units.remove(0);
        toUnitArrayAdapter.clear();
        toUnitArrayAdapter.addAll(units);

        if (!initialised) {
            categorySpinner.setSelection(0);
            fromSpinner.setSelection(0);
            toSpinner.setSelection(0);
            initialised = true;
        }

        //if returning from a saved state, set category selected to the saved category
        if (savedInstanceState != null) {
            Category selectedCategory = (Category) savedInstanceState.getSerializable(CATEGORY_KEY);

            int position = categoryArrayAdapter.getPosition(selectedCategory);
            categorySpinner.setSelection(position);
            updateFromSpinner(); //set other events off
        }

        return view;
    }

    /**
     * Initialises the spinners references and sets their adapters.
     * @param view the view containing the spinners
     */
    private void initialiseSpinners(View view) {
        categorySpinner = view.findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(categoryArrayAdapter);

        fromSpinner = view.findViewById(R.id.fromSpinner);
        fromSpinner.setAdapter(fromUnitArrayAdapter);

        toSpinner = view.findViewById(R.id.toSpinner);
        toSpinner.setAdapter(toUnitArrayAdapter);
    }

    /**
     * Initialises the events that trigger when the user selects a category or from unit.
     * @param view the view containing the spinner
     */
    private void initialiseEvents(View view) {
        final Button btnConvert = view.findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener((v) -> goToConversionFragment());

        final Button btnInsert = view.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener((v) -> goToInsertFragment());

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateToSpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category current = (Category) categorySpinner.getSelectedItem();

                if (!current.equals(lastSelectedCategory)) {
                    updateFromSpinner();
                    lastSelectedCategory = current;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //save all items selected in the spinners
        outState.putSerializable(CATEGORY_KEY, (Category) categorySpinner.getSelectedItem());
        outState.putSerializable(FROM_UNIT_KEY, (Unit) fromSpinner.getSelectedItem());
        outState.putSerializable(TO_UNIT_KEY, (Unit) toSpinner.getSelectedItem());

        super.onSaveInstanceState(outState);
    }

    /**
     * Replaces this fragment with a conversion fragment, sending the two select units in a bundle.
     */
    private void goToConversionFragment() {
        Unit fromUnit = (Unit) fromSpinner.getSelectedItem();
        Unit toUnit = (Unit) toSpinner.getSelectedItem();

        if (fromUnit == null && toUnit == null) {
            Toast.makeText(getContext(), "No units to convert!", Toast.LENGTH_LONG).show();
            return;
        }

        ConversionFragment newFragment = new ConversionFragment();

        Bundle argBundle = new Bundle();
        argBundle.putSerializable(ConversionFragment.FROM_KEY, fromUnit);
        argBundle.putSerializable(ConversionFragment.TO_KEY, toUnit);

        newFragment.setArguments(argBundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
    }

    /**
     * Replaces this fragment with the a fragment depending on the user choice.
     */
    private void goToInsertFragment() {
        String[] options = {"Category", "Unit", "Conversion"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("What kind of data would you like to insert?");

        builder.setItems(options, (dialog, selection) -> {
            Fragment fragment = null;
            switch (selection) {
                case 0:
                    fragment = new InsertCategoryFragment();
                    break;
                case 1:
                    fragment = new InsertUnitFragment();
                    break;
                case 2:
                    fragment = new InsertConversionFragment();
                    break;
            }

            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        });

        builder.show();
    }

    /**
     * Updates the items in the from spinner based on the selected category.
     * If there is a saved instance it will select the unit that was selected in the last instance.
     */
    private void updateFromSpinner() {
        fromUnitArrayAdapter.clear();
        fromUnitArrayAdapter.addAll(getUnitsFromSelectedCategory());
        fromSpinner.setSelection(0);

        if (savedInstanceState != null) {
            Unit fromUnit = (Unit) savedInstanceState.getSerializable(FROM_UNIT_KEY);
            int position = fromUnitArrayAdapter.getPosition(fromUnit);
            fromSpinner.setSelection(position);
        }

        updateToSpinner();
    }

    /**
     * Updates the units in the to spinner based on what is selected in the from spinner.
     * If there is a saved instance it will select the unit that was selected in the last instance.
     */
    private void updateToSpinner() {
        Unit fromSelected = (Unit) fromSpinner.getSelectedItem();
        List<Unit> units = getUnitsFromSelectedCategory();
        units.remove(fromSelected);

        toUnitArrayAdapter.clear();
        toUnitArrayAdapter.addAll(units);

        if (savedInstanceState != null) {
            Unit toUnit = (Unit) savedInstanceState.getSerializable(TO_UNIT_KEY);
            int position = toUnitArrayAdapter.getPosition(toUnit);
            toSpinner.setSelection(position);
        }
    }

    /**
     * Gets a list of units given the category selected in the category spinner.
     * @return a list of relevant units
     */
    private List<Unit> getUnitsFromSelectedCategory() {
        Category selectedCategory = (Category) categorySpinner.getSelectedItem();
        return Database.getDB().unitDao().getUnitsByCategory(selectedCategory.id);
    }
}
