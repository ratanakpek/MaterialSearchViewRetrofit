package rpek.foodfinder.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by r.pek on 09.10.2017.
 */

public class Food {


    @SerializedName("count")
    public int count;
    @SerializedName("recipes")
    public List<Recipes> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipes> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipes> recipes) {
        this.recipes = recipes;
    }

    public static class Recipes {
        @SerializedName("publisher")
        public String publisher;
        @SerializedName("f2f_url")
        public String f2f_url;
        @SerializedName("title")
        public String title;
        @SerializedName("source_url")
        public String source_url;
        @SerializedName("recipe_id")
        public String recipe_id;
        @SerializedName("image_url")
        public String image_url;
        @SerializedName("social_rank")
        public double social_rank;
        @SerializedName("publisher_url")
        public String publisher_url;

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getF2f_url() {
            return f2f_url;
        }

        public void setF2f_url(String f2f_url) {
            this.f2f_url = f2f_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getRecipe_id() {
            return recipe_id;
        }

        public void setRecipe_id(String recipe_id) {
            this.recipe_id = recipe_id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public double getSocial_rank() {
            return social_rank;
        }

        public void setSocial_rank(double social_rank) {
            this.social_rank = social_rank;
        }

        public String getPublisher_url() {
            return publisher_url;
        }

        public void setPublisher_url(String publisher_url) {
            this.publisher_url = publisher_url;
        }
    }
}
