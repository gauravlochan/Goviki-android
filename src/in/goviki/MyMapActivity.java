package in.goviki;

import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;


public class MyMapActivity extends MapActivity {
    private static final String TAG = "Goviki";

    protected MapView mapView;

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(15);
        
        
        GeoPoint geoPoint = getLastKnownLocation();
        mapView.getController().setCenter(geoPoint);
        
        // Add a 'MyLocationOverlay' to track the current location
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableMyLocation();
    }
    
    
    protected GeoPoint getLastKnownLocation() {
        GeoPoint geoPoint;
        
        // try to call into location manager directly
        Location location = LocationHelper.getBestLocation(this);
        if (location != null) {
            return LocationHelper.locationToGeoPoint(location);
        }

        // HACK: In some phones (e.g. HTC Wildfire) our code to get the location fails
        // Center to Ashok Nagar police station :-)
        Logger.warn(TAG, "Unable to get a location from the phone.  Use default");
        SimpleGeoPoint sgPoint = new SimpleGeoPoint(12.971669, 77.610314);
        return sgPoint.getGeoPoint();
    }

}
