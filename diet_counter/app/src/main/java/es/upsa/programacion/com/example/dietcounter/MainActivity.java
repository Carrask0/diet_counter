package es.upsa.programacion.com.example.dietcounter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.upsa.programacion.com.example.dietcounter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    MainViewModel viewModel;
    MainAdapter adapter;

    // LAUNCH SELECTOR ACTIVITY
    ActivityResultLauncher<Void> selectorActivityLauncher = registerForActivityResult(new ActivityResultContract<Void, Food.FoodEntry>() {

                                                                                      @NonNull
                                                                                      @Override
                                                                                      public Intent createIntent(@NonNull Context context, Void v) {
                                                                                          Intent intent = new Intent(context, SelectorActivity.class);
                                                                                          intent.setAction( Intent.ACTION_SEARCH );
                                                                                          return intent;
                                                                                      }

                                                                                      @Override
                                                                                      public Food.FoodEntry parseResult(int resultCode, @Nullable Intent intent) {
                                                                                          if (resultCode == Activity.RESULT_OK) {
                                                                                              return viewModel.getEntryById(intent.getDataString());
                                                                                          }
                                                                                          return null;
                                                                                      }
                                                                                        },
                                                                                        foodEntry -> {
                                                                                            if(foodEntry != null) {
                                                                                                showData();
                                                                                            }
                                                                                        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(this.viewBinding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        this.adapter = new MainAdapter();
        this.viewBinding.mainFoodListRV.setAdapter(this.adapter);
        this.adapter.setFoodList(viewModel.getFoodList());
        showData();

        // ADD BUTTON
        // Launches SelectorActivity upon clicking on '+' button
        this.viewBinding.mainAddBT.setOnClickListener( v -> selectorActivityLauncher.launch(null));

        // DELETE BUTTON
        // Deletes all data on foodlist and calls showData() so that changes are noticed by user
        this.viewBinding.mainDeleteBT.setOnClickListener(v -> {
            viewModel.deleteAll();
            showData();
        });
    }

    // Shows data inputted by user
    // Shows total number of kcals, proteins, carbs and fats
    // Shows all food entries on RecyclerView
    public void showData(){

        // Show macronutrients vales
        viewBinding.mainNumKcal.setText(String.valueOf(round(viewModel.getTotalKcals(), 2)));
        viewBinding.mainNumProt.setText(String.valueOf(round(viewModel.getTotalProt(), 2)));
        viewBinding.mainNumCarbs.setText(String.valueOf(round(viewModel.getTotalCarbs(), 2)));
        viewBinding.mainNumFats.setText(String.valueOf(round(viewModel.getTotalFats(), 2)));

        // Shows all entries on RecyclerView
        this.adapter.setFoodList(viewModel.getFoodList());
    }

    //Utility function - Rounds double to n places
    // This function is used twice along this program and should have been implemented as an static method on a Utility class, however I dediced not to in order to reduce the number of classes
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}