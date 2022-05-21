package vttp.project.repository;

public class Queries {

    public static final String SQL_INSERT_INTO_USER = "insert into user(username, password) values (?, md5(?))";
    public static final String SQL_INSERT_INTO_ITUNES = "insert into itunes(explicit, artistName, trackName, trackCensorName, collectionName, collectionCensorName, countryName, genre, artwork, mp4, username) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String SQL_SELECT_USER_PW = "select count(*) count from user where username = ? and password = md5(?)";
    public static final String SQL_SELECT_USER = "select count(*) count from user where username = ?";

    public static final String SQL_SELECT_ITUNES = "select count(*) count from itunes where artistName=? and trackName =? and collectionName=? and countryName=? and genre=? and username = ?";


}
