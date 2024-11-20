package com.threeChickens.homeService.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleMapUtil {

    @Value("${google.map.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String reverseGeocode(double latitude, double longitude) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s",
                latitude, longitude, apiKey
        );

        return restTemplate.getForObject(url, String.class);
    }

    public String getDistance(double originLat, double originLng, double destLat, double destLng) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%f,%f&destinations=%f,%f&key=%s",
                originLat, originLng, destLat, destLng, apiKey
        );

        return restTemplate.getForObject(url, String.class);
    }

    public String getDirections(double originLat, double originLng, double destLat, double destLng) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&key=%s",
                originLat, originLng, destLat, destLng, apiKey
        );
        return restTemplate.getForObject(url, String.class);
    }


}
