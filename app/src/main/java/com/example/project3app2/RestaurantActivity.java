package com.example.project3app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class RestaurantActivity extends AppCompatActivity {

    public static String[] titlesArray;
    public static String[] pagesArray;
    FragmentContainerView itemListFragmentContainerView;
    FragmentContainerView webViewFragmentContainerView;
    private ListViewModel model;

    private WebFragment webViewFragment;
    private ItemsListFragment itemsListFragment;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant);

        // Restaurant Data
        titlesArray = getResources().getStringArray(R.array.restaurantTitles);
        pagesArray = getResources().getStringArray(R.array.restaurantPages);

        // Getting reference to the fragment container views
        itemListFragmentContainerView = (FragmentContainerView) findViewById(R.id.items_list_fragment_container_restaurants);
        webViewFragmentContainerView = (FragmentContainerView) findViewById(R.id.web_view_fragment_container_restaurants);

        // Reference to the ViewModel
        model = new ViewModelProvider(this).get(ListViewModel.class);

        if(webViewFragment == null) {
            webViewFragment = new WebFragment();
        }
        webViewFragment.setLinksArray(pagesArray);
        if(itemsListFragment == null) {
            itemsListFragment = new ItemsListFragment();
        }
        itemsListFragment.setItemsArray(titlesArray);

        // Fragment Manager
        fragmentManager = getSupportFragmentManager();

        // Observe the model for listening to changes
        model.getSelectedItem().observe(this, itemPos -> {
            if(!webViewFragment.isAdded()) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.web_view_fragment_container_restaurants, webViewFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.items_list_fragment_container_restaurants, itemsListFragment);
        fragmentTransaction.commit();
        // Adding to backstack needs layout update
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setLayout();
            }
        });

    }

    // This method set the layout for fragments
    private void setLayout() {
        int orientation = this.getResources().getConfiguration().orientation;
        updateFragmentLayout(orientation);
    }

    // On configuration change, update the orientation
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        updateFragmentLayout(orientation);

    }

    // This method actually updates the fragment layout
    private void updateFragmentLayout(int orientation) {
        if(!webViewFragment.isAdded()) {
            itemListFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            webViewFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
        } else {
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                itemListFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 0f));
                webViewFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
            } else {
                itemListFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                webViewFragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }
        }
    }


    // Create items in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle onClick of action bar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.switchToAttractions){
            Intent intent = new Intent(this, AttractionActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}