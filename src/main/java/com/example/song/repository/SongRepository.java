package com.example.song.repository;

import com.example.song.model.Song;
import java.util.*;

public interface SongRepository{
    ArrayList<Song> getSongs();
    Song postSong(Song song);

    Song getSong(int id);

    Song putSong(int id, Song song);

    void deleteSong(int id);
}
