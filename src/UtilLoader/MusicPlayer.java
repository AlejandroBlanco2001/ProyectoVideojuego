package UtilLoader;

import java.util.ArrayList;
import tinysound.Music;

/**
 *
 * @author Omen
 */
public class MusicPlayer implements Runnable {

    private volatile boolean running = true;
    private ArrayList<Music> musicState;
    private Music currentMusic;
    private int currentIndexMusic;

    public MusicPlayer(Music music) {
        musicState = new ArrayList();
        musicState.add(music);
    }

    public MusicPlayer(Music... musics) {
        musicState = new ArrayList();
        for (Music m : musics) {
            if (m != null) {
                musicState.add(m);
            }
        }
    }

    @Override
    public void run() {
        if (musicState.size() == 1) {
            currentMusic = musicState.get(0);
            currentIndexMusic = 0;
        } else {
            int random = (int) (Math.random() * 4);
            currentMusic = musicState.get(random);
        }
        while (running) {
            if (!currentMusic.playing()) {
                currentMusic.play(false, 0.5f);
            }
            if (!currentMusic.playing()) {
                if (musicState.size() == 1) {
                    currentMusic = musicState.get(0);
                } else {
                    int random = (int) (Math.random() * 4);
                    currentMusic = musicState.get(random);
                }
            }
        }
        currentMusic.stop();
    }

    public void kill() {
        running = false;
    }

    public void resume(){
        running = true;
    }
    
    public void switchSong(){
        if(musicState.size() > 1){
            currentMusic = musicState.get(currentIndexMusic + 1);
        }
    }
    
    public void lowerMusicVolume() {
        if (currentMusic.playing()) {
            if (currentMusic.getVolume() > 0f) {
                currentMusic.setVolume(currentMusic.getVolume() - 0.1f);
            }
        }
    }

    public void raiseMusicVolume() {
        if (currentMusic.playing()) {
            if (currentMusic.getVolume() < 1.f) {
                currentMusic.setVolume(currentMusic.getVolume() + 0.1f);
            }
        }
    }
    
    public void mute(){
        if(currentMusic.playing()){
            currentMusic.setVolume(0f);
        }
    }
}
