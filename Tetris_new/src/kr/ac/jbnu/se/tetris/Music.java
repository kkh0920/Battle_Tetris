package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Music {

    private File file;
    private Clip clip;

    Music() {
        setMusic();
    }

    public void setMusic() {
        file = new File("bgm/bgm.wav");
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        clip.stop();
    }

    public void startMusic() {
        clip.start();
        clip.loop(10);
    }

}


