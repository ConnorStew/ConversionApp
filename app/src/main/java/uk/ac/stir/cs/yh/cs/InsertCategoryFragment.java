package uk.ac.stir.cs.yh.cs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.stir.cs.yh.cs.database.Category;
import uk.ac.stir.cs.yh.cs.database.Database;

public class InsertCategoryFragment extends Fragment {

    /** The EditText element that will contain the category name. */
    private EditText categoryEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_insert_category, container, false);

        categoryEditText = view.findViewById(R.id.categoryNameEditText);

        Button btnInsertCategory = view.findViewById(R.id.btnInsertCategory);
        btnInsertCategory.setOnClickListener(v -> insertCategory());

        return view;
    }

    /**
     * Inserts the given category into the database.
     */
    private void insertCategory() {
        String categoryName = categoryEditText.getText().toString();

        if (Database.getDB().categoryDao().getCategoryByName(categoryName) != null) {
            Toast.makeText(getContext(), "Category already exists.", Toast.LENGTH_LONG).show();
        } else {
            Database.getDB().categoryDao().insertAll(new Category(categoryName));
        }

        getActivity().onBackPressed();
    }
}
