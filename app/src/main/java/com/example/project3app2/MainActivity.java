package com.example.project3app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String APP_PERMISSION = "edu.uic.cs478.fall2021.project3";
    private static final int APP_PERMISSION_REQ_CODE = 20;
    private static final String INTENT_FILTER = "edu.uic.cs478.fall2021.project3.broadcastIntent";

    PlaceReceiver pReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkAppPermission()) {
            setContentView(R.layout.activity_main);
            // Registering the broadcast receiver with an intent filter and permission
            pReceiver = new PlaceReceiver();
            registerReceiver(pReceiver, new IntentFilter(INTENT_FILTER), APP_PERMISSION, null);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{APP_PERMISSION}, APP_PERMISSION_REQ_CODE);
        }
    }


    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);
        if (code == APP_PERMISSION_REQ_CODE) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                // Restart the activity if permission is granted
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.not_granted_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // this method checks if permission is granted or not
    private boolean checkAppPermission() {
        return ActivityCompat.checkSelfPermission(this, APP_PERMISSION) == PackageManager.PERMISSION_GRANTED;
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
            // start attraction activity with explicit intent
            Intent intent = new Intent(this, AttractionActivity.class);
            startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.switchToRestaurants) {
            // start restaurant activity with explicit intent
            Intent intent = new Intent(this, RestaurantActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}