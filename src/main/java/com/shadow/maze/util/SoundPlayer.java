package com.shadow.maze.util;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {
    public void playSound(String resourcePath) {
        new Thread(() -> {
            try {
                InputStream audioSrc = SoundPlayer.class.getResourceAsStream(resourcePath);
                if (audioSrc == null) {
                    System.err.println("Audio file not found: " + resourcePath);
                    return;
                }

                InputStream bufferedInput = new BufferedInputStream(audioSrc);
                AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedInput);

                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                clip.addLineListener( event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
