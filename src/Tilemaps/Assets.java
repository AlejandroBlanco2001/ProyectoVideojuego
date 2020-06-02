package Tilemaps;

import java.awt.image.BufferedImage;

public class Assets implements Runnable {

    public static BufferedImage astronautTalker, pursoid, fondoMenu, fondoSpaceInvaders,
            spriteNina, naveOff, naveOn, naveSemiOff, asteroids, bullet, laser, LaserAlien, enemy,
            vida, floor, library, BookPile, AutoMissil, charge, pursoidBullet, halfLife, playerStand,
            retroFloor, rowTile, platTile, empty, chest, elevatorTile, spikes, enigmaMachineTeleporter,
            floorDecorator1, platTiles, rightSing, wallTile, levelerTile, pyramidFill_1, pyramidFill_2, pyramidFill_3,
            spaceFloor, spaceWall, spaceWall2, spaceChest, spaceCrate, spaceBlocker, spaceTeleporter,
            CursorSpace, lastBackground, fondo8bits;

    public static BufferedImage playerDown[] = new BufferedImage[2];
    public static BufferedImage playerUp[] = new BufferedImage[3];
    public static BufferedImage playerRight[] = new BufferedImage[2];
    public static BufferedImage playerLeft[] = new BufferedImage[2];
    public static BufferedImage teleporterAnimation[] = new BufferedImage[3];

    public static BufferedImage spaceSpikes[] = new BufferedImage[3];
    public static BufferedImage mainPlayerUp[] = new BufferedImage[1];
    public static BufferedImage mainPlayerRunning[] = new BufferedImage[4];
    public static BufferedImage mainPlayerLeft[] = new BufferedImage[4];
    public static BufferedImage mainPlayerRight[] = new BufferedImage[4];
    public static BufferedImage mainPlayerFalling[] = new BufferedImage[1];
    public static BufferedImage mainPlayerStandStill[] = new BufferedImage[4];

    public static BufferedImage aerialEnemy[] = new BufferedImage[4];
    public static BufferedImage downEnemy[] = new BufferedImage[4];

    public static BufferedImage teleporterTile[] = new BufferedImage[9];
    public static BufferedImage Boss[] = new BufferedImage[2];

    public static BufferedImage backgroundMenu[] = new BufferedImage[60];

    public static BufferedImage backgroundLevel2[] = new BufferedImage[18];

    public static BufferedImage minimize[] = new BufferedImage[2];
    public static BufferedImage UILvl1[] = new BufferedImage[9];
    public static BufferedImage UILvl2[] = new BufferedImage[9];
    public static BufferedImage UIMenu[] = new BufferedImage[9];
    public static BufferedImage UIMainLvl[] = new BufferedImage[9];

    private static final int WIDHT = 131;
    private static final int HEIGHT = 110;

    /**
     * El metodo init() de la clase assets carga todas las imagenes que necesitaremos para los minijuegos
     */
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/Sheet.png"));
        SpriteSheet DownEnemy = new SpriteSheet(ImageLoader.loadImage("/Tilesets/pursoidSprite.png"));
        SpriteSheet AerialEnemy = new SpriteSheet(ImageLoader.loadImage("/Tilesets/LaserAlienSprite.png"));
        SpriteSheet boss = new SpriteSheet(ImageLoader.loadImage("/Tilesets/BossSpriteH.png"));
        SpriteSheet UIbuttons = new SpriteSheet(ImageLoader.loadImage("/UI/UIsprite.png"));
        spriteNina = sheet.crop(0, 0, WIDHT, HEIGHT);
        fondoSpaceInvaders = ImageLoader.loadImage("/Backgrounds/spaceInvaders.png");
        naveOff = ImageLoader.loadImage("/Player/naveOff.png");
        naveOn = ImageLoader.loadImage("/Player/naveOn.png");
        astronautTalker = ImageLoader.loadImage("/HUD/HUD_DIALOGUE.png");
        enemy = ImageLoader.loadImage(("/Tilesets/Pursoid.png"));
        pursoid = ImageLoader.loadImage("/Tilesets/Pursoid.png");
        LaserAlien = ImageLoader.loadImage("/Tilesets/LaserAlien.png");
        laser = ImageLoader.loadImage("/Tilesets/laser.png");
        SpriteSheet sheetAsteroids = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/Sheetasteroids.png"));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                asteroids = sheetAsteroids.crop(126 * j, 91 * i, 126, 91);
            }
        }
        SpriteSheet sheetBullets = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/Sheetbullets.png"));

        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    AutoMissil = sheetBullets.crop(0, 30 * i, 46, 30);
                    break;
                case 1:
                    bullet = sheetBullets.crop(0, 30 * i, 46, 30);
                    break;
                case 2:
                    pursoidBullet = sheetBullets.crop(0, 30 * i, 46, 30);
                    break;
            }
        }

        for (int i = 0; i < 23; i++) {
            backgroundMenu[i] = ImageLoader.loadImage("/Backgrounds/MainBackground/frame-" + (i + 1) + ".gif");
            if (i == 22) {
                lastBackground = ImageLoader.loadImage("/Backgrounds/MainBackground/frame-" + (i + 1) + ".gif");
            }
        }

        for (int i = 0; i < 18; i++) {
            backgroundLevel2[i] = ImageLoader.loadImage("/Background1/frame-" + (i + 1) + ".gif");
        }

        for (int i = 0; i < 4; i++) {
            aerialEnemy[i] = AerialEnemy.crop(i * 110, 0, 110, 99);
        }

        for (int i = 0; i < 4; i++) {
            downEnemy[i] = DownEnemy.crop(i * 104, 0, 104, 110);
        }

        for (int i = 0; i < 2; i++) {
            Boss[i] = boss.crop(i * 125, 0, 125, 120);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                switch (i) {
                    case 0:
                        UIMenu[j] = UIbuttons.crop(256 * j, 57 * i, 256, 57);
                        break;
                    case 1:
                        UILvl2[j] = UIbuttons.crop(256 * j, 57 * i, 256, 57);
                        break;
                    case 2:
                        UILvl1[j] = UIbuttons.crop(256 * j, 57 * i, 256, 57);
                        break;
                    case 3:
                        UIMainLvl[j] = UIbuttons.crop(256 * j, 57 * i, 256, 57);
                        break;

                }
            }
        }
        charge = boss.crop(250, 0, 125, 120);

        SpriteSheet sheetVida = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/heatlhBar.png"));
        SpriteSheet playerM = new SpriteSheet(ImageLoader.loadImage("/Sprites/Player/JoanSprite.png"));
        SpriteSheet spikeSheet = new SpriteSheet(ImageLoader.loadImage("/SpritesMainLevel/Space/saw_blade.png"));
        cargarJoan(playerM);
        SpriteSheet mainPlayer = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/Sheet.png"));
        cargarMainPlayer(mainPlayer);
        vida = sheetVida.crop(0, 0, 125, 201);
        halfLife = sheetVida.crop(266, 0, 125, 201);
        floor = ImageLoader.loadImage("/Testers/Floor.png");
        library = ImageLoader.loadImage("/Testers/library.png");
        CursorSpace = ImageLoader.loadImage("/Testers/spaceship.png");
        naveSemiOff = ImageLoader.loadImage("/Player/naveSemi.png");
        BookPile = ImageLoader.loadImage("/Testers/BookPile.png");
        retroFloor = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_1.png");
        rowTile = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/tile_12.png");
        platTile = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/tile_16.png");
        empty = ImageLoader.loadImage("/Tilesets/empty.png");
        chest = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Chest_1.png");
        elevatorTile = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_9.png");
        spikes = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_3.png");
        floorDecorator1 = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_5.png");
        enigmaMachineTeleporter = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/EnigmaMachine_Tile.png");
        platTiles = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_2.png");
        rightSing = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Sign_arrowRight.png");
        wallTile = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_8.png");
        levelerTile = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_10.png");
        pyramidFill_1 = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_7.png");
        pyramidFill_2 = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_6.png");
        pyramidFill_3 = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_5.png");
        spaceFloor = ImageLoader.loadImage("/SpritesMainLevel/Space/Tile_5.png");
        spaceWall = ImageLoader.loadImage("/SpritesMainLevel/Space/Tile_4.png");
        spaceWall2 = ImageLoader.loadImage("/SpritesMainLevel/Space/Tile_6.png");
        spaceCrate = ImageLoader.loadImage("/SpritesMainLevel/Space/crate.png");
        spaceBlocker = ImageLoader.loadImage("/SpritesMainLevel/Space/blocker.png");
        spaceTeleporter = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterNave.png");
        cargaSpikes(spikeSheet);
        cargarAnimacionTeleporter();
        minimize[0] = ImageLoader.loadImage("/UI/minimize.png");
        minimize[1] = ImageLoader.loadImage("/UI/minimizeHover.png");
        fondo8bits = ImageLoader.loadImage("/SpritesMainLevel/GameboyAssets/Tile_14.png");
        fillPortal();
    }

    /**
     * Metodo para cargar el gif de Joan Clarke.
     *
     * @param sheet Sprite Sheet de Joan Clarke.
     */
    public static void cargarJoan(SpriteSheet sheet) {
        int j = 0;
        for (int i = 0; i < 12; i++) {
            if (i < 3) {
                playerUp[i] = sheet.crop(i * 36, 0, 36, 36);
            } else if (i < 5) {
                playerDown[j] = sheet.crop(i * 36, 0, 36, 36);
                j++;
            } else if (i < 10 && i > 7) {
                playerLeft[j - 2] = sheet.crop(i * 36, 0, 36, 36);
                j++;
            } else if (i > 9) {
                playerRight[j - 4] = sheet.crop(i * 36, 0, 36, 36);
                j++;
            } else {
                switch (i) {
                    case 5:
                        playerStand = sheet.crop(i * 36, 0, 36, 36);
                }
            }
        }
    }

    public static void fillPortal() {
        for (int i = 0; i < teleporterTile.length; i++) {
            teleporterTile[i] = ImageLoader.loadImage("/SpritesMainLevel/Space/portalAnimations/frame_"+i+".png");
        }
    }

    public static void cargaSpikes(SpriteSheet sheet) {
        for (int i = 0; i < 3; i++) {
            spaceSpikes[i] = sheet.crop(i * 32, 0, 32, 32);
        }
    }

    public static void cargarMainPlayer(SpriteSheet sheet) {
        BufferedImage[] temporaryContainer = new BufferedImage[36];
        int a = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                temporaryContainer[a] = sheet.crop(j * 128, i * 128, 128, 128);
                a++;
            }
        }
        filterContainer(temporaryContainer);
    }

    public static void cargarAnimacionTeleporter() {
        teleporterAnimation[0] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationOff.png");
        teleporterAnimation[1] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationSemi.png");
        teleporterAnimation[2] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationOn.png");
    }

    public static void filterContainer(BufferedImage[] image) {
        mainPlayerStandStill[0] = image[0];
        mainPlayerStandStill[1] = image[6];
        mainPlayerStandStill[2] = image[12];
        mainPlayerStandStill[3] = image[18];
        mainPlayerLeft[0] = image[1];
        mainPlayerLeft[1] = image[7];
        mainPlayerLeft[2] = image[13];
        mainPlayerLeft[3] = image[19];
//        mainPlayerRight[0] = image[];
//        mainPlayerRight[1];
//        mainPlayerRight[2];
//        mainPlayerRight[3];
        mainPlayerUp[0] = image[2];
        mainPlayerFalling[0] = image[5];
        mainPlayerRunning[0] = image[4];
        mainPlayerRunning[1] = image[10];
        mainPlayerRunning[2] = image[16];
        mainPlayerRunning[3] = image[22];
    }

    @Override
    public void run() {
        init();
    }
}
