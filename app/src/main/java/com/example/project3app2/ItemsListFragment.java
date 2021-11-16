package com.example.project3app2;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

public class ItemsListFragment extends ListFragment {

    private String[] itemsArray;
    Context containingActivity;
    private static final String TAG = "ItemsListFragment";

    ListViewModel model;

    // Sets the list array and binds it to the fragment
    public void setItemsArray(String[] array) {
        itemsArray = array;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        containingActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the data on orientation change
        if(savedInstanceState != null) {
            itemsArray = savedInstanceState.getStringArray("itemsArray");
        }

        // Reference to ViewModel
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Render list of items with Array Adapter
        setListAdapter(new ArrayAdapter<String>(
                containingActivity,
                R.layout.list_item,
                itemsArray
        ));

        // Listen to changes in model's Live Data for setting checked item
        model.getSelectedItem().observe(getViewLifecycleOwner(), itemPos -> {
            getListView().setItemChecked(itemPos, true);
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        // Update the model on click
        model.selectItem(position);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Storing the data in bundle
        outState.putStringArray("itemsArray", itemsArray);

    }
}