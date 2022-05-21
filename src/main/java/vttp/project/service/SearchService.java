package vttp.project.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.project.model.Music;
import vttp.project.model.Search;
import vttp.project.model.User;
import vttp.project.repository.SearchRepo;

@Service
public class SearchService {

    @Autowired
    private SearchRepo searchRepo;

    final String URL = "https://itunes.apple.com/search";


    public List<Music> searchMusic(Search search, User user) {

        List<Music> list = new ArrayList<Music>();
        String finalURL = UriComponentsBuilder
                    .fromUriString(URL)
                    .queryParam("term", search.getArtist())
                    .queryParam("country", search.getCountry())
                    .queryParam("limit", search.getLimit())
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

                
                music.setExplicit(temp.getString("trackExplicitness"));
                
                music.setArtistName(temp.getString("artistName"));

                music.setTrackName(temp.getString("trackName"));
                music.setTrackCensorName(temp.getString("trackCensoredName"));

                music.setCollectionName(temp.getString("collectionName"));
                music.setCollectionCensorName(temp.getString("collectionCensoredName"));

                music.setCountryName(temp.getString("country"));
                music.setGenre(temp.getString("primaryGenreName"));

                // images and music
                music.setArtwork(temp.getString("artworkUrl100"));
                music.setMp4(temp.getString("previewUrl"));

                if(searchRepo.checkMusic(music, user) == 1) {
                    searchRepo.save(music, user);
                }

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
