package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    Clip clip;
    Clip looping_clip;
    File[] soundFiles = new File[10]; // Adjust size based on number of sounds
    int backgroundMusicFiles; // Number of background music files (to loop)
    boolean audioOn = true;

    public enum Sounds {
        BGM_MENU, BGM_MAP1, BGM_MAP2, MENU_MOVE, MENU_SELECT
    }

    public SoundManager() {
        // Load sound files
        soundFiles[0] = new File("res/audio/bgm_menu.wav"); // Menu background music
        soundFiles[1] = new File("res/audio/bgm_map1.wav"); // Background music for level 1 (lava map)
        soundFiles[2] = new File("res/audio/bgm_map2.wav"); // Background music for level 2 (sky map)
        soundFiles[3] = new File("res/audio/menu_move.wav"); // Menu move sound
        soundFiles[4] = new File("res/audio/menu_select.wav"); // Menu select sound

        backgroundMusicFiles = 3;
    }

    public void playMusic(int i) {
        if (!audioOn)
            return;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFiles[i]);
            // Convert to 16-bit PCM
            AudioFormat targetFormat = new AudioFormat(
                    44100, // Sample rate
                    16, // Sample size in bits (changed from 24 to 16)
                    2, // Channels (stereo)
                    true, // Signed
                    false // Little endian
            );

            AudioInputStream convertedAis = AudioSystem.getAudioInputStream(targetFormat, ais);
            if (i < backgroundMusicFiles) {
                looping_clip = AudioSystem.getClip();
                looping_clip.open(convertedAis);
                looping_clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
            } 
            else {
                clip = AudioSystem.getClip();
                clip.open(convertedAis);
                clip.loop(0); // Play the sound once
            }
        } catch (LineUnavailableException e) {
            System.err.println("Audio system unavailable: " + e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio format: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading sound file " + soundFiles[i].getName() + ": " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Sound file not found at index " + i);
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (looping_clip != null) {
            looping_clip.stop();
        }
    }

    public void toggleAudio() {
        audioOn = !audioOn;

        if (audioOn)
            playMusic(Sounds.BGM_MENU.ordinal());
        else
            stopMusic();
    }
}
