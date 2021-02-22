package com.salioubah.proximity_parking.service;

import com.salioubah.proximity_parking.model.Parking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to manage service methods
 */
@Service
public class ParkingService {

    private final String urlJson = "https://data.bordeaux-metropole.fr/geojson?key=9Y2RU3FTE8&typename=st_park_p";
    private final String urlXml = "http://data.lacub.fr/wfs?key=9Y2RU3FTE8&SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&TYPENAME=ST_PARK_P&SRSNAME=EPSG:4326";

    /**
     * Load all parking data
     * @return List of parking
     */
    public List<Parking> loadData() {
        List<Parking> p = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(urlJson, Object.class);
        HashMap<String, Object> hashMap = (HashMap<String, Object>) responseEntity.getBody();
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) hashMap.get("features");
        for (HashMap<String, Object> v: list) {
            HashMap<String, Object> geometry = (HashMap<String, Object>) v.get("geometry");
            HashMap<String, Object> properties = (HashMap<String, Object>) v.get("properties");
            String name = (String) properties.get("nom");
            List<Double> coordinates = (List<Double>) geometry.get("coordinates");
            p.add(new Parking(name, coordinates.get(1), coordinates.get(0)));
        }
        return p;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base
     * @see  "https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude"
     * @param lat1 Latitude Start point
     * @param lat2 Latitude End point
     * @param lon1 Longitude Start point
     * @param lon2 Longitude End point
     * @param el1 Start altitude in meters
     * @param el2 End altitude in meters
     * @return distance in kilometres
     */
    private static double getDistance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

        // Radius of the earth
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c ;

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     *
     * @param p parking to check
     * @param latUser Latitude of Client (Web or Mobile)
     * @param lonUser Longitude of Client (Web or Mobile)
     * @param distance distance parameters set to 3 by default
     * @return boolean, true if distance between parking and Client is lower than distance done
     */
    public static boolean isInRange(Parking p, double latUser, double lonUser, double distance) {
        return getDistance(p.getLat(),latUser,p.getLon(),lonUser,0,0) <= distance;
    }
}
