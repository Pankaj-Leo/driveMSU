package com.example.drivemsu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNavigation();
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    // Permission has already been granted, start navigation
                    startNavigation();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, start navigation
                startNavigation();
            }
        }
    }

    private void startNavigation() {
        // Montclair State University Coordinates: 40.862256, -74.197801
        Uri gmmIntentUri = Uri.parse("google.navigation:q=40.862256,-74.197801&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else {
            Uri webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=40.862256,-74.197801&travelmode=driving");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            }
        }
    }
}
