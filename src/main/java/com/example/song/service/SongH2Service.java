package com.example.song.service;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.stereotype.Service;
 import org.springframework.web.server.ResponseStatusException;
 import java.util.ArrayList;
 import com.example.song.repository.SongRepository;
 import com.example.song.model.SongRowMapper;
 import  com.example.song.model.Song;
 import java.util.*;
@Service
 public class SongH2Service implements SongRepository {
    
    @Autowired
    public JdbcTemplate db;
    
    @Override
    public ArrayList<Song> getSongs(){
        List<Song> list= db.query("select * from playlist", new SongRowMapper());
        ArrayList<Song> songs=new ArrayList<>(list);
        return songs;
    }

    @Override
    public Song postSong(Song song){
       db.update("insert into playlist(songName,lyricist,singer,musicDirector) values(?,?,?,?)",
       song.getSongName(),song.getLyricist(), song.getSinger(), song.getMusicDirector());

    try{
       Song existingSong= db.queryForObject("select * from playlist where songName=?",
       new SongRowMapper(),song.getSongName());

       return existingSong;
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

        
    }

    @Override
    public Song getSong(int id){
       try{
       Song existingSong= db.queryForObject("select * from playlist where songId=?",
       new SongRowMapper(),id);
       
       return existingSong;
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    }

    @Override
    public Song putSong(int id, Song song){
        
        Song existingSong= getSong(id);
        if(song.getSongName()!=null)
        existingSong.setSongName(song.getSongName());

        if(song.getLyricist()!=null)
       existingSong.setLyricist(song.getLyricist());

        if(song.getSinger()!=null)
    existingSong.setSinger(song.getSinger());

        if(song.getMusicDirector()!=null)
     existingSong.setMusicDirector(song.getMusicDirector());

    db.update("update playlist set songName=?, lyricist=?, singer=?, musicDirector=? where songId=?",
    existingSong.getSongName(),existingSong.getLyricist(),existingSong.getSinger(),existingSong.getMusicDirector(),id);
       
       
    return existingSong;

    }

    @Override
    public void deleteSong(int id){
       
       db.update("delete from playlist where songId=?",id);

    
    }

}