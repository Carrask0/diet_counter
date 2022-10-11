package es.upsa.programacion.com.example.dietcounter;

public class Food {

    private final String key;
    private final String name;
    private final int kcal;
    private final Double proteins;
    private final Double carbohidrates;
    private final Double fats;
    private final Double avgIntake;

    public Food(String key, String name, int kcal, Double proteins, Double carbohidrates, Double fats, Double avgIntake) {
        this.key = key;
        this.name = name;
        this.kcal = kcal;
        this.proteins = proteins;
        this.carbohidrates = carbohidrates;
        this.fats = fats;
        this.avgIntake = avgIntake;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public int getKcal() {
        return kcal;
    }

    public Double getProteins() {
        return proteins;
    }

    public Double getCarbohidrates() {
        return carbohidrates;
    }

    public Double getFats() {
        return fats;
    }

    public Double getAvgIntake() {
        return avgIntake;
    }


    // FOOD ENTRY
    // Almacena las entradas de usuario, guardando la comida y la cantidad
    public static class FoodEntry{
        private final String entryId;
        private final Food food;
        private final Double intake;

        public FoodEntry(String entryId, Food food, Double intake) {
            this.entryId = entryId;
            this.food = food;
            this.intake = intake;
        }

        public Food getFood() {
            return food;
        }

        public Double getIntake() {
            return intake;
        }

        public String getEntryId() {
            return this.entryId;
        }
    }
}
