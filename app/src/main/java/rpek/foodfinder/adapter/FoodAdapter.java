package rpek.foodfinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rpek.foodfinder.R;
import rpek.foodfinder.model.Food;

/**
 * Created by r.pek on 10.10.2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{

    private Context mContext;
    private List<Food.Recipes> foodItem;

    public FoodAdapter(Context mContext, List<Food.Recipes> foodItem) {
        this.mContext = mContext;
        this.foodItem = foodItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvFoodName.setText(foodItem.get(position).getTitle());
        holder.tvsocialRank.setText(foodItem.get(position).getSocial_rank()+"");
        holder.tvWebsite.setText(foodItem.get(position).getPublisher_url());

        Glide.with(mContext).load(foodItem.get(position).getImage_url())
                .centerCrop()
                .into(holder.ivImageUrl);

    }

    @Override
    public int getItemCount() {
        return foodItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFoodName, tvsocialRank,tvWebsite;
        public ImageView ivImageUrl;

        public MyViewHolder(View view) {
            super(view);
            tvFoodName = (TextView) view.findViewById(R.id.tvFoodName);
            tvsocialRank = (TextView) view.findViewById(R.id.tvSocialRank);
            ivImageUrl = (ImageView) view.findViewById(R.id.ivImageUrl);
            tvWebsite = (TextView) view.findViewById(R.id.tvWebsite);
        }
    }
}
