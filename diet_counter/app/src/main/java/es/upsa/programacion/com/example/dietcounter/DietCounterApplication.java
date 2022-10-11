package es.upsa.programacion.com.example.dietcounter;

import android.app.Application;

public class DietCounterApplication extends Application {
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        this.repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
