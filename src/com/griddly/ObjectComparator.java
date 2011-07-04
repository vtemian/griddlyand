package com.griddly;

import java.util.Comparator;
import fi.foyt.foursquare.api.entities.Location;

public class ObjectComparator implements Comparator{
    public int compare(Object obj1, Object obj2) {
    	Location myObj1 = (Location)obj1;
    	Location myObj2 = (Location)obj2;
        int stringResult = myObj1.getDistance().compareTo(myObj2.getDistance());
        return stringResult;
    }
}
