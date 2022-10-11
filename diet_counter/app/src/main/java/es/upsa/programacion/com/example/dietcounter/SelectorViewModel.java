package es.upsa.programacion.com.example.dietcounter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class SelectorViewModel extends AndroidViewModel {
    Repository repository;
    DietCounterApplication dietCounterApplication;

    public SelectorViewModel(@NonNull Application application) {
        super(application);
        dietCounterApplication = (DietCounterApplication) application;
        this.repository = dietCounterApplication.getRepository();
    }

    public List<Food> getFoodsDB()
    {
        return repository.getFoodsDB();
    }

    public Food.FoodEntry getEntryById(String id) {
        return repository.getEntryById(id);
    }
}
