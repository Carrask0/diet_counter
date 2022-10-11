package es.upsa.programacion.com.example.dietcounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.upsa.programacion.com.example.dietcounter.databinding.FoodInfoBinding;

public class InfoActivity extends AppCompatActivity {
    private FoodInfoBinding viewBinding;
    InfoViewModel viewModel;
    Double intake;
    Food food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding = FoodInfoBinding.inflate(this.getLayoutInflater());
        setContentView(this.viewBinding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(InfoViewModel.class);

        //INIT VIEW
        this.food = viewModel.findFoodByID(getIntent().getDataString());
        showFoodInfo();
        intake = 0.0;
        showValues();
        viewBinding.foodInfoSelectRG.check(R.id.food_info_selectRBgrams);

        //CANCEL BUTTON
        viewBinding.foodInfoCancelBT.setOnClickListener(v -> finish());

        //EDIT TEXT
        viewBinding.foodInfoQuantityET.setOnEditorActionListener( (textView, actionId, keyEvent) -> onEditorAction(textView, actionId));

        //RADIO GROUP - CHANGE UPDATE
        viewBinding.foodInfoSelectRG.setOnCheckedChangeListener((v, isChecked) -> tryChangingIntake(viewBinding.foodInfoQuantityET.getText().toString()));

        //ADD BUTTON
        viewBinding.foodInfoAddBT.setOnClickListener( v -> {
            int errorET = viewModel.validateIntake(viewBinding.foodInfoQuantityET.getText().toString());

            //ERROR MESSAGE SETTING
            if(errorET != Constants.NO_ERROR) viewBinding.foodInfoQuantityET.setError(getString(errorET));
            else viewBinding.foodInfoQuantityET.setError(null);

            //DATA UPDATE
            if(errorET == Constants.NO_ERROR) {
                Food.FoodEntry foodEntry = viewModel.inputFoodEntry(food, intake);

                Intent intent = new Intent();
                intent.setAction(getIntent().getAction());
                intent.setData(Uri.parse(foodEntry.getEntryId()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    // Upon EditText Update
    // Checks ID and Action, then calls tryChangingIntake
    private boolean onEditorAction(TextView textView, int actionId) {
        if (textView.getId() == R.id.food_info_quantityET) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tryChangingIntake(textView.getText().toString());
            }
        }
        return false;
    }

    // Upon update on input (EditText changed or RadioGroup changed)
    // Will validate input of EditText and will update intake var according to which RB is checked, then it will showValues()
    private void tryChangingIntake(String valueAsString) {
        int errorId = viewModel.validateIntake(valueAsString);
        if(errorId == Constants.NO_ERROR) {
            viewBinding.foodInfoQuantityET.setError(null);

            if(viewBinding.foodInfoSelectRBgrams.isChecked()) {
                intake = Double.valueOf(valueAsString);
            }
            else if(viewBinding.foodInfoSelectRBportion.isChecked()) {
                intake = viewModel.valueOnPortions(Double.valueOf(valueAsString));
            }
            showValues();
        }
        else {
            if(errorId == Constants.EMPTY_STRING) {
                viewBinding.foodInfoQuantityET.setError(null);
            }
            else viewBinding.foodInfoQuantityET.setError(getString(errorId));
        }
    }

    // Shows default nutritional values of food
    public void showFoodInfo() {
        viewBinding.foodInfoName.setText(food.getName());
        viewBinding.foodInfoKcalValue.setText(String.valueOf(food.getKcal()));
        viewBinding.foodInfoProtValue.setText(String.valueOf(food.getProteins()));
        viewBinding.foodInfoCarbsValue.setText(String.valueOf(food.getCarbohidrates()));
        viewBinding.foodInfoFatsValue.setText(String.valueOf(food.getFats()));
        viewBinding.portionValue.setText(String.valueOf(food.getAvgIntake()));
    }

    // Shows nutritional values according to inputted intake
    public void showValues() {
        viewBinding.foodInfoNumKcal.setText(String.valueOf(round(food.getKcal() * intake/100, 2)));
        viewBinding.foodInfoNumProt.setText(String.valueOf(round(food.getProteins() * intake/100, 2)));
        viewBinding.foodInfoNumCarbs.setText(String.valueOf(round(food.getCarbohidrates() * intake/100, 2)));
        viewBinding.foodInfoNumFats.setText(String.valueOf(round(food.getFats() * intake/100, 2)));
    }

    //Utility function - Rounds double to n places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
