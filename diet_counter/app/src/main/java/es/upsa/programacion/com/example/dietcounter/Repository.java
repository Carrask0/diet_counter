package es.upsa.programacion.com.example.dietcounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Repository {
    private final HashMap<String, Food> foodsDB;
    private HashMap<String, Food.FoodEntry> foodlist;

    public Repository() {
        this.foodsDB = new HashMap<>();
        foodsDB.put("0000", new Food("0000", "Huevo", 155, 13.0, 1.1, 11.0,60.0));
        foodsDB.put("0001", new Food("0001", "Arroz", 130, 2.7, 28.0, 0.3,100.0));
        foodsDB.put("0002", new Food("0002", "Spaghetti", 158, 6.0, 31.0, 0.9,100.0));
        foodsDB.put("0003", new Food("0003", "Pechuga", 165, 31.0, 0.0, 3.6,98.0));
        foodsDB.put("0004", new Food("0004", "Leche", 42, 3.4, 5.0, 1.0,250.0));
        foodsDB.put("0005", new Food("0005", "Queso", 320, 24.6, 6.18, 24.5,50.0));
        foodsDB.put("0006", new Food("0006", "Chocolate", 546, 4.9, 61.0, 31.0,75.0));
        foodsDB.put("0007", new Food("0007", "Pizza", 266, 11.0, 33.0, 10.0,172.0));
        foodsDB.put("0008", new Food("0008", "Macarrones", 371, 13.0, 75.0, 1.5,100.0));
        foodsDB.put("0009", new Food("0009", "Cerveza", 43, 0.5, 3.6, 0.0,200.0));
        foodsDB.put("0010", new Food("0010", "Manzana", 52, 0.3, 14.0, 0.2,150.0));
        foodsDB.put("0011", new Food("0011", "Platano", 89, 1.1, 23.0, 0.3,150.0));
        foodsDB.put("0012", new Food("0012", "Naranja", 47, 0.9, 12.0, 0.1,150.0));
        foodsDB.put("0013", new Food("0013", "Zanahoria", 54, 0.74, 7.99, 2.58,100.0));
        foodsDB.put("0014", new Food("0014", "Ensalada Cesar", 400, 1.2, 3.1, 57.0,100.0));

        this.foodlist = new HashMap<>();
    }

    // Returns foodsDB as an ArrayList of its values
    public List<Food> getFoodsDB() {
        return new ArrayList<>( foodsDB.values() );
    }

    // Returns food from DB given its ID
    public Food getFoodById(String id) {
        return foodsDB.get(id);
    }

    // Returns foodlist as an ArrayList of its values
    public List<Food.FoodEntry> getFoodList() {
        return new ArrayList<>( foodlist.values() );
    }

    // Empties foodlist
    public void emptyFoodList() {
        foodlist.clear();
    }

    // Inputs new entry to foodlist given a food and an intake
    public Food.FoodEntry inputNewEntry(Food food, Double intake){
        String entryId = UUID.randomUUID().toString();
        Food.FoodEntry foodEntry = new Food.FoodEntry(entryId, food, intake);
        foodlist.put(entryId, foodEntry);
        return foodEntry;
    }

    // Returns foodEntry from foodlist given its ID
    public Food.FoodEntry getEntryById(String entryId) {
        return foodlist.get(entryId);
    }

}
