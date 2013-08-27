package com.anmark.kebapp;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlacesMapActivityV2 extends Activity {

	private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//GoogleMapOptions options = new GoogleMapOptions();
		
		
		setContentView(R.layout.activity_places_map_v2);

		setUpMapIfNeeded();
	}
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapV2)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				//mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				//mMap.setMyLocationEnabled(true);
				
				//Location location = mMap.getMyLocation();
				
				//LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			    //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
			    
			    //mMap.animateCamera(cameraUpdate);
				
				// Enable MyLocation Layer of Google Map
				
				//mMap.setMyLocationEnabled(true);
				
				// Get LocationManager object from System Service LOCATION_SERVICE
			    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			    // Create a criteria object to retrieve provider
			    Criteria criteria = new Criteria();
			    
			    // Get the name of the best provider
			    String provider = locationManager.getBestProvider(criteria, true);

			    // Get Current Location
			    Location myLocation = locationManager.getLastKnownLocation(provider);

			    //set map type
			    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			    // Get latitude of the current location
			    double latitude = myLocation.getLatitude();

			    // Get longitude of the current location
			    double longitude = myLocation.getLongitude();

			    // Create a LatLng object for the current location
			    LatLng latLng = new LatLng(latitude, longitude);      

			    // Show the current location in Google Map        
			    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

			    // Zoom in the Google Map
			    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
			    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("That's you!"));
			}
		}
	}
}

/*
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PlacesMapActivityV2 extends Activity {
	// Nearest places
	PlacesList nearPlaces;

	// Map view
	//MapView mapView;
	GoogleMap gmap;

	// Map overlay items
	List<Overlay> mapOverlays;

	AddItemizedOverlay itemizedOverlay;

	GeoPoint geoPoint;
	// Map controllers
	MapController mc;
	
	double latitude;
	double longitude;
	OverlayItem overlayitem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_map_v2);

		// Getting intent data
		Intent i = getIntent();
		
		// Users current geo location
		String user_latitude = i.getStringExtra("user_latitude");
		String user_longitude = i.getStringExtra("user_longitude");
		
		// Nearplaces list
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

		//mapView = (GoogleMap) findViewById(R.id.mapView);
		
		gmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
		//mapView.setBuiltInZoomControls(true);

		//mapOverlays = mapView.getOverlays();
		
		// Geopoint to place on map
		//TODO: remove 1E6
		//geoPoint = new GeoPoint((int) (Double.parseDouble(user_latitude) * 1E6),
		//		(int) (Double.parseDouble(user_longitude) * 1E6));
		
		// Drawable marker icon
		Drawable drawable_user = this.getResources()
				.getDrawable(R.drawable.mark_red);
		
		/**itemizedOverlay = new AddItemizedOverlay(drawable_user, this);
		
		// Map overlay item
		overlayitem = new OverlayItem(geoPoint, "Your Location",
				"That is you!");

		itemizedOverlay.addOverlay(overlayitem);
		
		//mapOverlays.add(itemizedOverlay);
		itemizedOverlay.populateNow();
		
		// Drawable marker icon
		Drawable drawable = this.getResources()
				.getDrawable(R.drawable.mark_blue);
		
		itemizedOverlay = new AddItemizedOverlay(drawable, this);

		//mc = mapView.getController();

		// These values are used to get map boundary area
		// The area where you can see all the markers on screen
		int minLat = Integer.MAX_VALUE;
		int minLong = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int maxLong = Integer.MIN_VALUE;

		// check for null in case it is null
		if (nearPlaces.results != null) {
			// loop through all the places
			for (Place place : nearPlaces.results) {
				latitude = place.geometry.location.lat; // latitude
				longitude = place.geometry.location.lng; // longitude
				
				// Geopoint to place on map
				geoPoint = new GeoPoint((int) (latitude * 1E6),
						(int) (longitude * 1E6));
				
				// Map overlay item
				overlayitem = new OverlayItem(geoPoint, place.name,
						place.vicinity);

				itemizedOverlay.addOverlay(overlayitem);
				
				
				// calculating map boundary area
				minLat  = (int) Math.min( geoPoint.getLatitudeE6(), minLat );
			    minLong = (int) Math.min( geoPoint.getLongitudeE6(), minLong);
			    maxLat  = (int) Math.max( geoPoint.getLatitudeE6(), maxLat );
			    maxLong = (int) Math.max( geoPoint.getLongitudeE6(), maxLong );
			}
			//mapOverlays.add(itemizedOverlay);
			
			// showing all overlay items
			itemizedOverlay.populateNow();
		}
		
		/*
		// Adjusting the zoom level so that you can see all the markers on map
		mapView.getController().zoomToSpan(Math.abs( minLat - maxLat ), Math.abs( minLong - maxLong ));
		
		// Showing the center of the map
		mapView.animateCamera(new GeoPoint((maxLat + minLat)/2, (maxLong + minLong)/2 ));
		mapView.postInvalidate();
		 
		//gmap.setMyLocationEnabled(true);

	    // Get LocationManager object from System Service LOCATION_SERVICE
	    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	    // Create a criteria object to retrieve provider
	    Criteria criteria = new Criteria();

	    // Get the name of the best provider
	    String provider = locationManager.getBestProvider(criteria, true);

	    // Get Current Location
	    Location myLocation = locationManager.getLastKnownLocation(provider);

	    //set map type
	    gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

	    // Get latitude of the current location
	    double latitude = myLocation.getLatitude();

	    // Get longitude of the current location
	    double longitude = myLocation.getLongitude();

	    // Create a LatLng object for the current location
	    LatLng latLng = new LatLng(latitude, longitude);      

	    // Show the current location in Google Map        
	    gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	    gmap.animateCamera(CameraUpdateFactory.zoomTo(15));
	    gmap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("That's you!"));
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	   
	}

}
*/