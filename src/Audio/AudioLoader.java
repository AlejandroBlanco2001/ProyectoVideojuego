package Audio;

import tinysound.Music;
import tinysound.Sound;
import tinysound.TinySound;
/**
 * Carga el audio que se va a usar en el videojuego.
 * @version 1.1
 */
public class AudioLoader implements Runnable{

    public static Music damageEnemyShip, bgMusic, bgTalkMomentSpaceInvaders, bgMusicSpaceInvaders;
    public static Sound shot, upMenu, damageAsteroid,openGate,closedGate,backInTime,jumpSound;
    public static Music[] musicPlayListMainLevel = new Music[5];
    /**
     * Metodo que se hace al inicializar la clase.
     */
    public static void init() {
        bgMusic = TinySound.loadMusic("/Music/menuMusic.wav");
        bgTalkMomentSpaceInvaders = TinySound.loadMusic("/Music/spaceMusicIntro.wav");
        shot = TinySound.loadSound("/SFX/spaceShot.wav");
        upMenu = TinySound.loadSound("/SFX/menuSelection.wav");
        bgMusicSpaceInvaders = TinySound.loadMusic("/Music/spaceInvadersRace.wav");
        damageAsteroid = TinySound.loadSound("/SFX/explosionAsteroid.wav");
        damageEnemyShip = TinySound.loadMusic("/SFX/explosionEnemy.wav");
        openGate = TinySound.loadSound("/SFX/gateOpenSound.wav");
        closedGate = TinySound.loadSound("/SFX/gateClosed.wav");
        backInTime = TinySound.loadSound("SFX/backInTime.wav");
        jumpSound = TinySound.loadSound("SFX/jumpSound.wav");
        fillPlayList();
    }

    private static void fillPlayList() {
        musicPlayListMainLevel[0] = TinySound.loadMusic("/Music/M0.wav");
        musicPlayListMainLevel[1] = TinySound.loadMusic("/Music/M1.wav");
        musicPlayListMainLevel[2] = TinySound.loadMusic("/Music/M2.wav");
        musicPlayListMainLevel[3] = TinySound.loadMusic("/Music/M3.wav"); 
        musicPlayListMainLevel[4] = TinySound.loadMusic("/Music/M4.wav");
    }

    @Override
    public void run() {
        init();
    }

}
