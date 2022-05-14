package vttp.project.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.project.model.Music;

public class SearchService {


    final String URL = "https://itunes.apple.com/search";

    // setting default
    public List<Music> searchGiphy(String artist) {
        return searchMusic(artist, 5, "US");

    }

    public List<Music> searchGiphy(String artist, String country) {
        return searchMusic(artist, 5, country);

    }

    public List<Music> searchGiphy(String artist, Integer limit) {
        return searchMusic(artist, limit, "US");

    }


    public List<Music> searchMusic(String artist, Integer limit, String country) {

        List<Music> list = new ArrayList<Music>();
        String finalURL = UriComponentsBuilder
                    .fromUriString(URL)
                    .queryParam("term", artist)
                    .queryParam("country", country)
                    .queryParam("limit", limit)
                    .toUriString();
        

        RequestEntity<Void> req = RequestEntity
                                .get(finalURL)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        // possbile to send error so try and catch 

        try {
            resp = template.exchange(req, String.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        } 

        // resp.getStatus(); //status code
        // resp.getBody; // get payload
        

        // {} is json object
        // [] is json array
        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject(); // {} is json object
            
            Integer count = o.getInt("resultsCount");
            JsonArray results = o.getJsonArray("results"); // [] is json array this is for data

            for (int i=0; i <count; i++) {

                Music music = new Music();
                JsonObject temp = results.getJsonObject(i);

                
                String explicit =  temp.getString("trackExplicitness");
                
                String artistName = temp.getString("artistName");

                String trackName = temp.getString("trackName");
                String trackCensorName = temp.getString("trackCensoredName");

                String collectionName = temp.getString("collectionName");
                String collectionCensorName = temp.getString("collectionCensoredName");

                String countryName = temp.getString("country");
                String genre = temp.getString("primaryGenreName");

                // images and music
                String artwork = temp.getString("artworkUrl100");
                String mp4 = temp.getString("previewUrl");

                list.add(music);
            }


            

        } catch (Exception ex) {
            System.err.printf(">>>> service Error: %s\n", ex.getMessage());
            ex.printStackTrace();
        }
        
        //System.out.println(">>>>> service list: " + list);
        return list;

    }
    
}
