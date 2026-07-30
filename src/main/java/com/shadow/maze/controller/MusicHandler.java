package com.shadow.maze.controller;

import com.shadow.maze.util.SoundPlayer;

public class MusicHandler {
    private static SoundPlayer sp = new SoundPlayer();

    public static void playShieldSound() {
        sp.playSound("/sound/shield_acquire.wav");
    }

    public static void playKeySound() {
        sp.playSound("/sound/coin.wav");
    }

    public static void playHurtSound() {
        sp.playSound("/sound/receivedamage.wav");
    }

    public static void playSuccessSound() {
        sp.playSound("/sound/tadaaa.wav");
    }

    public static void playClickSound() {
        sp.playSound("/sound/cursor.wav");
    }
}
