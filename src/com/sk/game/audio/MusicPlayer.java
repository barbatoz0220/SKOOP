package com.sk.game.audio;

import javax.sound.sampled.*;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayer implements Runnable {

    private ArrayList<AudioFile> musicFiles; // Inputting a playlist
    private int currentSongIndex;
    private volatile boolean running;

    public MusicPlayer(String... files) {
        musicFiles = new ArrayList<>();
        for (String file : files) {
            System.out.println("Loading Song: " + file + " ...");
            musicFiles.add(new AudioFile("res/audio/" + file + ".wav"));
        }
    }

    @Override
    public void run() {
        running = true;
        AudioFile song = musicFiles.get(currentSongIndex);
        song.play();

        while (running) {
            if (!song.isPlaying()) {
                currentSongIndex++;
                if (currentSongIndex >= musicFiles.size()) {
                    currentSongIndex = 0;
                }
                song = musicFiles.get(currentSongIndex);
                song.play();
            }
        }

        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
