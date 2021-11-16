package com.example.project3app2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

@SuppressLint("ValidFragment")
public class WebFragment extends Fragment {

    ListViewModel model;
    private WebView webView;
    private LinearLayout webViewContainer;
    private String[] webLinksArray;

    // This method binds the web pages data to its local array
    public void setLinksArray(String[] array) {
        webLinksArray = array;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        // Web View Container
        webViewContainer = (LinearLayout) view.findViewById(R.id.webViewContainer);
        // Add Web View to the Web View Container Dynamically
        if(webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            parent.removeView(webView);
            webViewContainer.addView(webView);
        } else {
            createWebView();
        }

        return view;
    }

    // This method creates a new Web View
    private void createWebView() {
        if(webView != null) {
            ViewGroup webViewParent = (ViewGroup) webView.getParent();
            webViewParent.removeView(webView);
        }
        webView = new WebView(getContext());
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        webViewContainer.addView(webView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Reference to View Model
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        // Restore the data
        if(savedInstanceState != null) {
            webLinksArray = savedInstanceState.getStringArray("webLinksArray");
        }
        // Listen for changes in model's Live Data
        model.getSelectedItem().observe(getViewLifecycleOwner(), itemPos -> {
            String url = webLinksArray[itemPos];
            createWebView();
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            // Load the Web View with appropriate web page
            webView.loadUrl(url);
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the data
        outState.putStringArray("webLinksArray", webLinksArray);
    }
}