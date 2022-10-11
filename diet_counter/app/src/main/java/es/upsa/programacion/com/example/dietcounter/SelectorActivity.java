package es.upsa.programacion.com.example.dietcounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.upsa.programacion.com.example.dietcounter.databinding.FoodSelectorBinding;

public class SelectorActivity extends AppCompatActivity {

    private FoodSelectorBinding viewBinding;
    SelectorViewModel viewModel;
    SelectorAdapter adapter;

    // LAUNCH INFO ACTIVITY
    ActivityResultLauncher<Food> infoActivityLauncher = registerForActivityResult(new ActivityResultContract<Food, Food.FoodEntry>() { //Argumentos: 1.- Necesario para crear la actividad,,,, 2.- Devuelto por la actividad
                                                                                @NonNull
                                                                                @Override
                                                                                public Intent createIntent(@NonNull Context context, Food food) {
                                                                                    Intent intent = new Intent(Intent.ACTION_INSERT,Uri.parse(food.getKey()),context, InfoActivity.class);
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
                                                                                    Intent intent = new Intent();
                                                                                    intent.setAction(getIntent().getAction());
                                                                                    intent.setData(Uri.parse(foodEntry.getEntryId()));
                                                                                    setResult(RESULT_OK, intent);
                                                                                    finish();
                                                                                }
                                                                            }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding = FoodSelectorBinding.inflate(this.getLayoutInflater());
        setContentView(this.viewBinding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(SelectorViewModel.class);

        // INIT RecyclerView
        this.adapter = new SelectorAdapter();
        this.viewBinding.foodSelectorRV.setAdapter(this.adapter);
        this.adapter.setFoodsDB(viewModel.getFoodsDB());

        // SELECT FOOD
        // Launches InfoActivity upon clicking on any food of RecyclerView
        this.adapter.setOnItemClick( food -> infoActivityLauncher.launch(food));

        //CANCEL BUTTON
        this.viewBinding.foodSelectorCancelBT.setOnClickListener(v -> finish());
    }
}
