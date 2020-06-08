package Tilemaps;

import java.awt.image.BufferedImage;

public class Assets implements Runnable {

    public static BufferedImage astronautTalker, pursoid, fondoMenu, fondoSpaceInvaders,
            spriteNina, naveOff, naveOn, naveSemiOff, asteroids, bullet, laser, LaserAlien, enemy,
            vida, floor, library, BookPile, AutoMissil, charge, pursoidBullet, halfLife, playerStand,
            retroFloor, rowTile, platTile, empty, chest, elevatorTile, spikes, enigmaMachineTeleporter,
            floorDecorator1, platTiles, rightSing, wallTile, levelerTile, pyramidFill_1, pyramidFill_2, pyramidFill_3,
            spaceFloor, spaceWall, spaceWall2, spaceChest, spaceCrate, spaceBlocker, spaceTeleporter,
            CursorSpace, lastBackground, fondo8bits,pauseBackgroundLvl1,pauseBackgroundLvl2,pauseBackgroundMain,
            Title, Title2, Answer,AnswerHover, MenuMain, Menu1,QuitLvl2,Table;

    public static BufferedImage cityPlataformerBackground[] = new BufferedImage[56];
    public static BufferedImage playerDown[] = new BufferedImage[2];
    public static BufferedImage playerUp[] = new BufferedImage[3];
    public static BufferedImage playerRight[] = new BufferedImage[2];
    public static BufferedImage playerLeft[] = new BufferedImage[2];
    public static BufferedImage teleporterAnimation[] = new BufferedImage[3];

    public static BufferedImage timeStamp[] = new BufferedImage[61];
    public static BufferedImage spaceBackgroundPlat[] = new BufferedImage[30];

    public static BufferedImage spaceSpikes[] = new BufferedImage[3];

    public static BufferedImage mainPlayerUpL[] = new BufferedImage[1];
    public static BufferedImage mainPlayerRunningL[] = new BufferedImage[4];
    public static BufferedImage mainPlayerLeft[] = new BufferedImage[4];
    public static BufferedImage mainPlayerFallingL[] = new BufferedImage[1];
    public static BufferedImage mainPlayerStandStillL[] = new BufferedImage[4];

    public static BufferedImage mainPlayerUpR[] = new BufferedImage[1];
    public static BufferedImage mainPlayerRunningR[] = new BufferedImage[4];
    public static BufferedImage mainPlayerRight[] = new BufferedImage[4];
    public static BufferedImage mainPlayerFallingR[] = new BufferedImage[1];
    public static BufferedImage mainPlayerStandStillR[] = new BufferedImage[4];

    public static BufferedImage aerialEnemy[] = new BufferedImage[4];
    public static BufferedImage downEnemy[] = new BufferedImage[4];
    
    public static BufferedImage BookOpenning[] = new BufferedImage[6];
    public static BufferedImage Books[] = new BufferedImage[6];
    public static BufferedImage[] QuizBook= new BufferedImage[18];
    
    public static BufferedImage Questions[]= new BufferedImage[6];
    public static BufferedImage QuizAnswers[]= new BufferedImage[6];

    public static BufferedImage teleporterTile[] = new BufferedImage[9];
    public static BufferedImage Boss[] = new BufferedImage[2];

    public static BufferedImage backgroundMenu[] = new BufferedImage[60];

    public static BufferedImage backgroundLevel2[] = new BufferedImage[18];

    public static BufferedImage minimize[] = new BufferedImage[2];
    public static BufferedImage UILvl1[] = new BufferedImage[9];
    public static BufferedImage UILvl2[] = new BufferedImage[9];
    public static BufferedImage UIMenu[] = new BufferedImage[9];
    public static BufferedImage UIMainLvl[] = new BufferedImage[9];
    
    public static BufferedImage UIHelperMenu[] = new BufferedImage[7];
    public static BufferedImage UIHelperMain[] = new BufferedImage[3];
    public static BufferedImage UIHelperLvl1[]= new BufferedImage[2];
    public static BufferedImage UIHelperLvl2[] = new BufferedImage[2];
    
    

    private static final int WIDHT = 131;
    private static final int HEIGHT = 110;

    /**
     * El metodo init() de la clase assets carga todas las imagenes que necesitaremos para los minijuegos
     */
    public static void init() {
        int randomEffect = randomNumberEffectGenerator();
        SpriteSheet timeStapMark = new SpriteSheet(ImageLoader.loadImage("/Tilesets/timeStamp ("+randomEffect+").png"));
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/Sheet.png"));
        SpriteSheet DownEnemy = new SpriteSheet(ImageLoader.loadImage("/Tilesets/pursoidSprite.png"));
        SpriteSheet AerialEnemy = new SpriteSheet(ImageLoader.loadImage("/Tilesets/LaserAlienSprite.png"));
        SpriteSheet boss = new SpriteSheet(ImageLoader.loadImage("/Tilesets/BossSpriteH.png"));
        SpriteSheet UIbuttons = new SpriteSheet(ImageLoader.loadImage("/UI/UIsprite.png"));
        SpriteSheet sheetR = new SpriteSheet(ImageLoader.loadImage("/Sprites/Tilesets/SheetRight.png"));
        spriteNina = sheet.crop(0, 0, WIDHT, HEIGHT);
        fondoSpaceInvaders = ImageLoader.loadImage("/Backgrounds/spaceInvaders.png");
        pauseBackgroundLvl1 = ImageLoader.loadImage("/Backgrounds/pauseBackgroundLvl1.png");
        pauseBackgroundLvl2 = ImageLoader.loadImage("/Backgrounds/pauseBackgroundLvl2.png");
        pauseBackgroundMain = ImageLoader.loadImage("/Backgrounds/pauseBackgroundMain.png");
        Table = ImageLoader.loadImage("/Backgrounds/Table.png");
        naveOff = ImageLoader.loadImage("/Player/naveOff.png");
        naveOn = ImageLoader.loadImage("/Player/naveOn.png");
        astronautTalker = ImageLoader.loadImage("/HUD/HUD_DIALOGUE.png");
        enemy = ImageLoader.loadImage(("/Tilesets/Pursoid.png"));
        pursoid = ImageLoader.loadImage("/Tilesets/Pursoid.png");
        LaserAlien = ImageLoader.loadImage("/Tilesets/LaserAlien.png");
        laser = ImageLoader.loadImage("/Tilesets/laser.png");
        Title = ImageLoader.loadImage("/UI/Title.png");
        Title2 = ImageLoader.loadImage("/UI/Title2.png");
        Answer = ImageLoader.loadImage("/UI/Answer.png");
        AnswerHover = ImageLoader.loadImage("/UI/Answer2.png");
        MenuMain = ImageLoader.loadImage("/UI/MenuMain.png");
        Menu1 = ImageLoader.loadImage("/UI/Menu1.png");
        QuitLvl2 = ImageLoader.loadImage("/UI/QuitLvl2.png");
        
        
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

        for (int i = 0; i < 6; i++) {
            Books[i]=ImageLoader.loadImage("/UI/Book"+(i+1)+".png");
        }
        
        for (int i = 0; i < 18; i++) {
            QuizBook[i]=ImageLoader.loadImage("/UI/frame-"+(i+1)+".png");
        }
        
        for (int i = 0; i < 6; i++) {
            Questions[i]=ImageLoader.loadImage("/UI/Pregunta "+(i+1)+".1.png");
        }
        for (int i = 0; i < 6; i++) {
            QuizAnswers[i]=ImageLoader.loadImage("/UI/Pregunta "+(i+1)+".2.png");
        }
        
        
        
        for (int i = 0; i < 7; i++) {
            UIHelperMenu[i]=ImageLoader.loadImage("/UI/UIHelper/UIHelperMenu-"+(i+1)+".png");
        }
        for (int i = 0; i < 2; i++) {
            UIHelperLvl1[i]=ImageLoader.loadImage("/UI/UIHelper/UIHelperLvl1-"+(i+1)+".png");
        }
        for (int i = 0; i < 3; i++) {
            UIHelperMain[i]=ImageLoader.loadImage("/UI/UIHelper/UIHelperMain-"+(i+1)+".png");
        }
        for (int i = 0; i < 2; i++) {
            UIHelperLvl2[i]=ImageLoader.loadImage("/UI/UIHelper/UIHelperLvl2-"+(i+1)+".png");
        }
        
        for (int i = 0; i < 6; i++) {
            BookOpenning[i]=ImageLoader.loadImage("/UI/Book-"+(i+1)+".gif");
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
        cargarMainPlayerR(sheetR);
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
        fillBackgroundSpacePlat();
        fillBackgroundCityPlat();
        cargarTimeStamp(timeStapMark);
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
            teleporterTile[i] = ImageLoader.loadImage("/SpritesMainLevel/Space/portalAnimations/frame_" + i + ".png");
        }
    }

    public static void fillBackgroundCityPlat() {
        for (int i = 0; i < cityPlataformerBackground.length; i++) {
            cityPlataformerBackground[i] = ImageLoader.loadImage("/SpritesMainLevel/Backgrounds/CityP/frame_1 (" + (i + 1) + ").png");
        }
    }

    public static void fillBackgroundSpacePlat() {
        for (int i = 0; i < spaceBackgroundPlat.length; i++) {
            spaceBackgroundPlat[i] = ImageLoader.loadImage("/SpritesMainLevel/Backgrounds/SpaceP/frame_1 (" + (i + 1) + ").png");
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

    public static void cargarMainPlayerR(SpriteSheet sheet) {
        BufferedImage[] temporaryContainer = new BufferedImage[36];
        int a = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                temporaryContainer[a] = sheet.crop(j * 128, i * 128, 128, 128);
                a++;
            }
        }
        filterContainerR(temporaryContainer);
    }

    public static void cargarTimeStamp(SpriteSheet sheet) {
        int a = 0;
        int limit = 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < limit; j++) {
                if (i == 7) {
                    limit = 5;
                }
                timeStamp[a] = sheet.crop(j * 100, i * 100, 100, 100);
                a++;
            }
        }
    }

    public static void cargarAnimacionTeleporter() {
        teleporterAnimation[0] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationOff.png");
        teleporterAnimation[1] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationSemi.png");
        teleporterAnimation[2] = ImageLoader.loadImage("/SpritesMainLevel/Space/teleporterAnimationOn.png");
    }

    public static void filterContainer(BufferedImage[] image) {
        mainPlayerStandStillL[0] = image[0];
        mainPlayerStandStillL[1] = image[6];
        mainPlayerStandStillL[2] = image[12];
        mainPlayerStandStillL[3] = image[18];
        mainPlayerLeft[0] = image[1];
        mainPlayerLeft[1] = image[7];
        mainPlayerLeft[2] = image[13];
        mainPlayerLeft[3] = image[19];
        mainPlayerUpL[0] = image[2];
        mainPlayerFallingL[0] = image[5];
        mainPlayerRunningL[0] = image[4];
        mainPlayerRunningL[1] = image[10];
        mainPlayerRunningL[2] = image[16];
        mainPlayerRunningL[3] = image[22];
    }

    public static void filterContainerR(BufferedImage[] image) {
        mainPlayerStandStillR[0] = image[5];
        mainPlayerStandStillR[1] = image[11];
        mainPlayerStandStillR[2] = image[17];
        mainPlayerStandStillR[3] = image[23];
        mainPlayerRight[0] = image[4];
        mainPlayerRight[1] = image[10];
        mainPlayerRight[2] = image[16];
        mainPlayerRight[3] = image[22];
        mainPlayerUpR[0] = image[3];
        mainPlayerFallingR[0] = image[0];
        mainPlayerRunningR[0] = image[1];
        mainPlayerRunningR[1] = image[7];
        mainPlayerRunningR[2] = image[13];
        mainPlayerRunningR[3] = image[19];
    }

    public static int randomNumberEffectGenerator(){
        int random = (int) (Math.random() * 13 + 1);
        while(random == 2){
            random = (int) (Math.random() * 13 + 1);
        }
        return random;
    }

    @Override
    public void run() {
        init();
    }
}
