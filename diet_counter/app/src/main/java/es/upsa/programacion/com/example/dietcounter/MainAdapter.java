package es.upsa.programacion.com.example.dietcounter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upsa.programacion.com.example.dietcounter.databinding.ItemFoodEntryBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<Food.FoodEntry> foodList;

    public void setFoodList(List<Food.FoodEntry> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodEntryBinding itemViewBinding = ItemFoodEntryBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MainViewHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Food.FoodEntry foodEntry = foodList.get(position);
        holder.bind(foodEntry);
    }

    @Override
    public int getItemCount() {
        return (foodList == null)? 0 : foodList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{

        private final ItemFoodEntryBinding itemViewBinding;

        public MainViewHolder(ItemFoodEntryBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;
        }

        public void bind(Food.FoodEntry foodEntry) {
            itemViewBinding.itemFoodEntryName.setText(foodEntry.getFood().getName());
            itemViewBinding.itemFoodEntryQuantity.setText(String.valueOf(foodEntry.getIntake()));
        }
    }
}
