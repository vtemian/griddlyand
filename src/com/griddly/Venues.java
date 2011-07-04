package com.griddly;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.Location;

public class Venues {
	private List<CompactVenue> venues = new LinkedList<CompactVenue>();
	
	public void add(CompactVenue Venue){
		venues.add(Venue);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompactVenue> getVenues(){
		Collections.sort(venues, new ObjectComparator());
		return venues;
	}

 
}

