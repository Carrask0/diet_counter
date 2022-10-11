package es.upsa.programacion.com.example.dietcounter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class InfoViewModel extends AndroidViewModel {

    Repository repository;
    String id;
    Food food;

    public InfoViewModel(@NonNull Application application) {
        super(application);
        DietCounterApplication dietCounterApplication = (DietCounterApplication) application;
        this.repository = dietCounterApplication.getRepository();
    }

    public Food findFoodByID(String id)
    {
        this.id = id;
        this.food = repository.getFoodById(id);
        return food;
    }

    // Makes sure input is not "", since "" cannot be converted to Double
    // Checking intake > 0 is actually redundant since user will only be able to input numbers, not "-", however that funcionality is implemented
    public int validateIntake(@NonNull String valueAsString) {
        if(valueAsString.equals("")) return Constants.EMPTY_STRING;
        double intake = Double.parseDouble(valueAsString);
        if (intake <= 0) return Constants.NON_POSITIVE_NUMBER;
        else return Constants.NO_ERROR;
    }

    // Returns value of intake in grams in case user decides to input intake in portions
    public Double valueOnPortions(Double intake) {
        return intake * food.getAvgIntake();
    }

    public Food.FoodEntry inputFoodEntry(Food food, Double intake) {
        return repository.inputNewEntry(food, intake);
    }
}
