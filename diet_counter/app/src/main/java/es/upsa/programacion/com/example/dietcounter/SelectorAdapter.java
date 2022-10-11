package es.upsa.programacion.com.example.dietcounter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upsa.programacion.com.example.dietcounter.databinding.ItemFoodSelectBinding;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorViewHolder>{

    public interface OnItemClick {
        void onItemClick(Food food);
    }

    private List<Food> foodsDB;
    private OnItemClick onItemClick;

    public void setFoodsDB(List<Food> foodsDB) {
        this.foodsDB = foodsDB;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public SelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodSelectBinding itemViewBinding = ItemFoodSelectBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new SelectorViewHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectorViewHolder holder, int position) {
        Food foodSelect = foodsDB.get(position);
        holder.bind(foodSelect);
    }

    @Override
    public int getItemCount() {
        return (foodsDB == null)? 0: foodsDB.size();
    }

    public class SelectorViewHolder extends RecyclerView.ViewHolder{

        private final ItemFoodSelectBinding itemViewBinding;
        private Food food;

        public SelectorViewHolder(ItemFoodSelectBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;

            this.itemViewBinding.getRoot().setOnClickListener(v -> {
                if(onItemClick != null) {
                    onItemClick.onItemClick(food);
                }
            });
        }

        public void bind(Food foodSelect) {
            itemViewBinding.itemFoodEntryName.setText(foodSelect.getName());
            this.food = foodSelect;
        }
    }
}
