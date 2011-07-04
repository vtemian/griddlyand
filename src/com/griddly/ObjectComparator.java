package com.griddly;

import java.util.Comparator;

import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.Location;

public class ObjectComparator implements Comparator{
    public int compare(Object obj1, Object obj2) {
    	
    	if (obj1 instanceof CompactVenue && obj2 instanceof CompactVenue){
    		CompactVenue v1 = (CompactVenue) obj1;
    		CompactVenue v2 = (CompactVenue) obj2;
    		
    		double r = v1.getLocation().getDistance() - v2.getLocation().getDistance();
        	
        	if (r > 0){
        		return 1;
        	}
        	else if (r < 0){
        		return -1;
        	}
    	}
        return 0;
    }
}
