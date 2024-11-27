package com.threeChickens.homeService.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.MediaType;
import com.threeChickens.homeService.dto.googleMap.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class GoogleMapUtil {
    @Value("${google.map.apiKey}")
    private String apiKey;

    @Value("${google.map.baseUrl}")
    private String baseUrl;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PlaceDto> placeAutocomplete(String input) throws IOException {
        String url = baseUrl + "maps/api/place/autocomplete/json?input=" + input + "&key=" + apiKey + "&components=country:vn&language=vi" ;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_PLACE);
        }

        PlaceResponseDto placeResponseDto = objectMapper.readValue(response.body().string(), PlaceResponseDto.class);

        if(!Objects.equals(placeResponseDto.getStatus(), "OK")){
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_PLACE);
        }

        return placeResponseDto.getPredictions();
    }

    public GeocodeDto geocode(String placeId, String latitude, String longitude) throws IOException {
        String url = baseUrl + "maps/api/geocode/json?key=" + apiKey + "&language=vi";
        if(placeId != null) {
            url += "&place_id=" + placeId;
        }

        if(latitude != null && longitude != null) {
            url += "&latlng=" + latitude + "," + longitude;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_GEOCODE);
        }

        GeocodeResponseDto geocodeResponse = objectMapper.readValue(response.body().string(), GeocodeResponseDto.class);

        if(!Objects.equals(geocodeResponse.getStatus(), "OK") || geocodeResponse.getResults().isEmpty()){
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_GEOCODE);
        }

        return geocodeResponse.getResults().getFirst();
    }

    public DistanceMatrixDto distanceMatrix(String originPlaceId, String destinationPlaceId) throws IOException {
        String url = baseUrl + "maps/api/distancematrix/json?key=" + apiKey + "&language=vi&origins=place_id:" + originPlaceId + "&destinations=place_id:" + destinationPlaceId;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_GEOCODE);
        }

        DistanceMatrixResponseDto distanceMatrixResponseDto = objectMapper.readValue(response.body().string(), DistanceMatrixResponseDto.class);

        if(!Objects.equals(distanceMatrixResponseDto.getStatus(), "OK") || distanceMatrixResponseDto.getElements().isEmpty()){
            throw new AppException(StatusCode.FAIL_GOOGLE_MAP_DISTANCE_MATRIX);
        }

        return distanceMatrixResponseDto.getElements().getFirst();
    }
}
