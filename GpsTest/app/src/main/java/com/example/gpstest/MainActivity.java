package com.example.gpstest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private class MyLocationListener implements LocationListener {

        TextView editLocation = findViewById(R.id.editLocation);
        Button pb = findViewById(R.id.button);


        @Override public void onLocationChanged(Location loc) {
            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText( getBaseContext(), "Location changed: Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Szerokość Geograficzna: " + loc.getLongitude();
            String latitude = "Długość Geograficzna: " + loc.getLatitude();
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses; try { addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality(); }

            } catch (IOException e) { e.printStackTrace(); }
            String s = longitude + "\n" + latitude + "\n\nAktualne miasto: " + cityName;
            editLocation.setText(s); }
        @Override public void onProviderDisabled(String provider) {}
        @Override public void onProviderEnabled(String provider) {}
        @Override public void onStatusChanged(String provider, int status, Bundle extras) {} }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}