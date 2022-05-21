package vttp.project.repository;
import static vttp.project.repository.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.project.model.Music;
import vttp.project.model.User;

@Repository
public class SearchRepo {


    @Autowired
    private JdbcTemplate template;
    
    public Integer checkMusic(Music music, User user) {
        SqlRowSet rs = template.queryForRowSet( SQL_SELECT_ITUNES, 
        music.getArtistName(),music.getTrackName(),  music.getCollectionName(),
        music.getCountryName(), music.getGenre(), user.getUsername());
        if (!rs.next()) {
            return 0;
            }
        return rs.getInt("count");
    }

    public boolean save(Music music, User user){
        int added = template.update(SQL_INSERT_INTO_ITUNES, music.getExplicit(), music.getArtistName(), 
        music.getTrackName(), music.getTrackCensorName(), music.getCollectionName(), 
        music.getCollectionCensorName(), music.getCountryName(), music.getGenre(),  
        music.getArtwork(), music.getMp4() ,user.getUsername());

        return added >0;
        
    }
    
}
