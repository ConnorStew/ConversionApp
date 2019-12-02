package uk.ac.stir.cs.yh.cs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.stir.cs.yh.cs.database.Conversion;
import uk.ac.stir.cs.yh.cs.database.Database;
import uk.ac.stir.cs.yh.cs.database.Unit;

/**
 * This fragment handles converting between two different Units.
 * @author Connor Stewart
 */
public class ConversionFragment extends Fragment {



    /** The unit to convert from. */
    private Unit fromUnit;

    /** The unit to convert to. */
    private Unit toUnit;

    private TextView resultText;

    private EditText fromInput;

    private Context context;

    static final String FROM_KEY = "fromUnit";
    static final String TO_KEY = "toUnit";

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            this.fromUnit = (Unit) args.get(FROM_KEY);
            this.toUnit = (Unit) args.get(TO_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_conversion, container, false);
        this.context = getContext();

        TextView fromText = view.findViewById(R.id.fromUnitText);
        TextView toText = view.findViewById(R.id.toUnitText);
        resultText = view.findViewById(R.id.txtResult);
        fromInput = view.findViewById(R.id.fromInput);

        fromText.setText(fromUnit.unitName);
        toText.setText(toUnit.unitName);

        view.findViewById(R.id.btnCalc).setOnClickListener(this::calculate);
        view.findViewById(R.id.btnClear).setOnClickListener(this::clear);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void calculate(View view) {
        Conversion conversion = Database.getDB().conversionDao().getConversion(fromUnit.id, toUnit.id);
        if (conversion == null)
            Toast.makeText(context, "Can't find conversion!", Toast.LENGTH_LONG).show();
        else {
            if (fromInput.getText() == null || fromInput.getText().toString().equals("")) {
                Toast.makeText(context, "Please input a value to convert.", Toast.LENGTH_LONG).show();
                return;
            }

            double userInput = Double.parseDouble(fromInput.getText().toString());
            double calculatedAmount = userInput * conversion.conversionFactor;
            resultText.setText(Double.toString(calculatedAmount));
        }
    }

    private void clear(View view) {
        resultText.setText("");
        resultText.clearFocus();

        fromInput.setText("");
        fromInput.clearFocus();
    }
}
