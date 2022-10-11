package es.upsa.programacion.com.example.dietcounter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    Repository repository;
    DietCounterApplication dietCounterApplication;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dietCounterApplication = (DietCounterApplication) application;
        this.repository = dietCounterApplication.getRepository();
    }

    public List<Food.FoodEntry> getFoodList()
    {
        return repository.getFoodList();
    }

    //Empties foodList in case user clicks 'DELETE ALL' button
    public void deleteAll() {
        this.repository.emptyFoodList();
    }

    public double getTotalKcals() {
        double total = 0.0;
        for (Food.FoodEntry f : getFoodList()) {
            total += f.getFood().getKcal() * f.getIntake() / 100;
        }
        return total;
    }

    public double getTotalProt() {
        double total = 0.0;
        for (Food.FoodEntry f : getFoodList()) {
            total += f.getFood().getProteins() * f.getIntake() /100;
        }
        return total;
    }

    public double getTotalCarbs() {
        double total = 0.0;
        for (Food.FoodEntry f : getFoodList()) {
            total += f.getFood().getCarbohidrates() * f.getIntake() / 100;
        }
        return total;
    }

    public double getTotalFats() {
        double total = 0.0;
        for (Food.FoodEntry f : getFoodList()) {
            total += f.getFood().getFats() * f.getIntake() / 100;
        }
        return total;
    }

    public Food.FoodEntry getEntryById(String id) {
        return repository.getEntryById(id);
    }
}
