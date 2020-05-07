package SecondMinigame;

import MainG.Handler;
import Tilemaps.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DialogueLoader{

    private Handler handler;

    private boolean dialogueMark = true;

    public static int tutorialState = 1;
    public static int timeAnimation = 1;
    public static int info = 0;
    public static int timeAnimationMark = 2500;

    Font textFont = new Font("pixelart", Font.PLAIN, 22);

    public DialogueLoader(Handler handler) {
        this.handler = handler;
    }

    public void render(Graphics2D g) {
        if (dialogueMark) {
            timeAnimation++;
            g.setFont(textFont);
            
            g.drawImage(Assets.astronautTalker, 25, 470, 995, 189, null);
            g.setColor(Color.black);
            if (info == 0) {
                showTutorial(g);
            }else{
                showSomeInfo(g,info);
            }
        }
    }

    public boolean getDialogueMark() {
        return dialogueMark;
    }

    public void setDialogueMark(){
        dialogueMark = !dialogueMark;
    }
    
    public void showTutorial(Graphics2D g) {
        g.setColor(Color.BLACK);
        switch (tutorialState) {
            case 1: // Explicas el contexto
                g.drawString("Bienvenida Astronauta", 280, 545);
                g.drawString("Ahora mismo eres Katherine Johnson,estas a bordo del Apolo ", 280, 575);
                g.drawString("Tu mision, es estar al mando del manejo de la nave y cambiar", 280, 605);
                g.drawString("la historia", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    tutorialState = 2;
                    timeAnimation = 0;
                }
                break;
            case 2:
                g.drawString("¿Como que no sabes quien eres?", 280, 545);
                g.drawString("Eres la mujer que mando al Apolo 11 a la Luna en el año 1969", 280, 575);
                g.drawString("Es tu momento de cambiar la historia, llega y reescribela ", 280, 605);
                g.drawString("Y ahora te explico que tienes que hacer Katherine", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    tutorialState = 3;
                    timeAnimation = 0;
                }
                break;
            case 3:
                g.drawString("Tu mision es llegar a la Luna, pero no la tienes facil,", 280, 545);
                g.drawString("Debes esquivar los asteroides y acabar con los Aliens", 280, 575);
                g.drawString("Si... es un secreto de Estado, pero si existen los Aliens", 280, 605);
                g.drawString("Ahora te enseño a tripular la nave", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    tutorialState = 4;
                    timeAnimation = 0;
                }
                break;
            case 4:
                g.drawString("Con las Teclas AWSD te encargas de dirigir la nave, intenta moverte", 280, 575);
                g.drawString("Yo te espero...", 280, 605);
                if (handler.getKeyManager().down == true || handler.getKeyManager().up == true
                        || handler.getKeyManager().right == true || handler.getKeyManager().left == true) {
                    tutorialState = 5;
                }
                break;
            case 5:
                g.drawString("Excelente Katherine, ahora te enseñaremos a usar el Rayo Laser", 280, 575);
                g.drawString("Simplemente presiona la Q, y ya dispara, y preparate para tu viaje", 280, 605);
                if (handler.getKeyManager().space == true) {
                    tutorialState = 6;
                }
                break;
            default:
                dialogueMark = false;
                timeAnimation = 0;
                info = 1;
        }
    }

    public void showSomeInfo(Graphics2D g, int info) {
        switch (info) {
            case 1:
                g.drawString("Parece que esto es lo tuyo viajera", 280, 545);
                g.drawString("Hablando de ti, para refrescar tu memoria", 280, 575);
                g.drawString("Eres una joven muy brillante, entraste a la Universidad de 15 años", 280, 605);
                g.drawString("Te graduaste 3 años despues, algo que para ese entonces era increible", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    info = 2;
                    dialogueMark = false;
                    timeAnimation = 0;
                }
                break;
            case 2:
                g.drawString("¡WOW!, que lejos has llegado, mucha gente creia que no llegarian", 280, 545);
                g.drawString("Sin embargo, llegaste a la NASA en el año 1950, un gran logro", 280, 575);
                g.drawString("Mientras todas las mujeres de adentro no opinabas, tu cuestionabas", 280, 605);
                g.drawString("y así comenzo tu gran camino para la NASA.", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    info = 3;
                    dialogueMark = false;
                    timeAnimation = 0;
                }
                break;
            case 3:
                g.drawString("Trabajaste en la llegada del Apolo 11", 280, 545);
                g.drawString("Fuiste la pieza angular para que llegaramos a la Luna", 280, 575);
                g.drawString("Marcaste un antes y un despues para las mujeres", 280, 605);
                g.drawString("Acaba con la vieja historia, construye una nueva", 280, 635);
                if (timeAnimation >= timeAnimationMark) {
                    info = 4;
                    dialogueMark = false;
                    timeAnimation = 0;
                }
                break;
        }
    }

    public int getInfo() {
        return info;
    }
}