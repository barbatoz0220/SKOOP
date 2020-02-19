package com.sk.game.audio;

import javax.sound.sampled.*;
import java.io.File;

public class AudioFile implements LineListener {

    private File soundFile;
    private AudioInputStream AIS; // Put that object in an audio input stream
    private AudioFormat format; // Get the format of the input stream
    private DataLine.Info info;
    private Clip clip;
    private FloatControl gainControl;
    private boolean playing;

    public AudioFile(String fileName) {
        soundFile = new File(fileName); // Create an object of File Class
        try {
            AIS = AudioSystem.getAudioInputStream(soundFile); // Put that object in an audio input stream
            format = AIS.getFormat(); // Get the format of the input stream
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(AIS);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play() {
        gainControl.setValue(-20.0f); // Turn down default volume
        clip.start();
        playing = true;
    }

    public void play(float volume) {
        gainControl.setValue(volume);
        clip.start();
        playing = true;
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.START) {
            playing = true;
        } else if (event.getType() == LineEvent.Type.STOP) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0); // Reset back to the 1st frame of the sound
            playing = false;
        }
    }
}
