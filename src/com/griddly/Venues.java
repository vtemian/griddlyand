package com.griddly;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import fi.foyt.foursquare.api.entities.Location;

public class Venues {
	private List<fi.foyt.foursquare.api.entities.Location> venues;
	
	public void add(fi.foyt.foursquare.api.entities.Location Venue){
		venues.add(Venue);
	}
	
	@SuppressWarnings("unchecked")
	public List<Location> getVenues(){
		Collections.sort(venues, new ObjectComparator());
		return venues;
	}

 
}

