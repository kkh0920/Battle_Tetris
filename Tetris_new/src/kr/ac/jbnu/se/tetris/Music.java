package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Music{
    static File file;
    static Clip clip;

    Music() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setMusic();
    }

    public void setMusic() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        // file = new File("TetrisCode/bgm/bgm.wav");
        file = new File("bgm\\bgm.wav");
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(file));
    }

    public void stopMusic() {
        clip.stop();
    }

    public void startMusic() {
        clip.start();
        clip.loop(10);
    }

}


