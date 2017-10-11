package rpek.foodfinder;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rpek.foodfinder.adapter.FoodAdapter;
import rpek.foodfinder.data.FoodAPI;
import rpek.foodfinder.model.Food;
import rpek.foodfinder.service.ServiceGenerator;

public class MainActivity extends AppCompatActivity {


    private MaterialSearchView searchView;
    String[] keywordSearch;
    boolean firstTimeSearch=true;
    String keySearchHistory;


    //item layout
    RecyclerView rcvFoodItem;
    FoodAdapter foodAdapter;
    List<Food.Recipes> mFoodListDefault;
    List<Food.Recipes> mFoodAfterSearch=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.shape);
        searchView.setEllipsize(true);
        searchView.setSubmitOnClick(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showSearchResult();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if(firstTimeSearch) {
                    letSearch(newText);
                    firstTimeSearch=false;
                }
                keySearchHistory=newText;
                return false;
            }
        });

       /* searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "On Item Click", Toast.LENGTH_SHORT).show();
                if(mFoodListDefault!=null)
                    showSearchResult(mFoodListDefault);
            }
        });*/
        /*searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "On Click", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    public void letSearch(String search) {
        FoodAPI service = ServiceGenerator.createService(FoodAPI.class);
        Observable<Food> call = service.searchFood("2c56ab81d3416842019252d81557667b", search);

        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodApi -> {
                    if(foodApi.getRecipes()!=null){
                        if (foodApi.getRecipes().size() > 0) {
                            if (foodApi != null) {
                                mFoodListDefault =foodApi.getRecipes();
                                keywordSearch = new String[foodApi.getRecipes().size()];
                                for (int i = 0; i < foodApi.getRecipes().size(); i++) {
                                    keywordSearch[i] = foodApi.getRecipes().get(i).getTitle();
                                }
                                searchView.setSuggestions(keywordSearch);
                            }
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        rcvFoodItem = (RecyclerView) findViewById(R.id.rcvFoodItem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvFoodItem.setLayoutManager(mLayoutManager);
        rcvFoodItem.setItemAnimator(new DefaultItemAnimator());
        foodAdapter = new FoodAdapter(this, mFoodAfterSearch);
    }

    private void showSearchResult() {
        List<Food.Recipes> footList=searchKeyWordBy(keySearchHistory);
        if(footList!=null){
            rcvFoodItem.setAdapter(foodAdapter);
            foodAdapter.notifyDataSetChanged();
        }
    }

    private List<Food.Recipes> searchKeyWordBy(String searchKey){
        // clear history search
        mFoodAfterSearch.clear();
        if(mFoodListDefault !=null){
            for(Food.Recipes item:mFoodListDefault){
                //string.toLowerCase().startsWith(constraint.toString().toLowerCase())
                if(item.getTitle().toString().toLowerCase().startsWith(searchKey.toLowerCase())){
                    mFoodAfterSearch.add(item);
                }
            }
        }
        return mFoodAfterSearch;
    }
}
