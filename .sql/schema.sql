DROP DATABASE IF EXISTS search;

CREATE SCHEMA search;

USE search;

CREATE TABLE user (
    username VARCHAR(256) not null,
    password VARCHAR(256) not null,
    
    primary key(username)
);


CREATE TABLE itunes (

    id INTEGER AUTO_INCREMENT,
    explicit VARCHAR(256),
    artistName VARCHAR(256),
    trackName VARCHAR(256),
    trackCensorName VARCHAR(256),
    collectionName VARCHAR(256),
    collectionCensorName VARCHAR(256),
    countryName VARCHAR(256),
    genre VARCHAR(256), 

	-- image
    artwork VARCHAR(256), 
    mp4 VARCHAR(256),

    
    -- foreign keys
    username VARCHAR(256),


    PRIMARY KEY(id),
    
    CONSTRAINT fk_username
        FOREIGN KEY(username)
        REFERENCES user(username)
);