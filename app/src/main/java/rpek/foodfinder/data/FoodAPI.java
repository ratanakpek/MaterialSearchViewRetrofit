package rpek.foodfinder.data;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rpek.foodfinder.model.Food;

import static android.R.attr.key;

/**
 * Created by r.pek on 09.10.2017.
 */

public interface FoodAPI {
    @GET("/api/search")
    Observable<Food> searchFood(@Query("key") String key, @Query("q") String search);
}
