/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game2;
import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;
import java.util.*;
/**
 *
 * @author Ben
 */
class Testeez {

    //thanks Jay!
    public static void check(String label, Object x, Object y) throws Exception {
        if (x != y) {
            throw new Exception("\n" + label + ": " + x + " should equal " + y + " but it don't :(");
        }
    }
    
    public static void check_ints(String label, int x, int y) throws Exception {
        if (x != y) {
            throw new Exception("\n" + label + ": " + x + " should equal " + y + " but it don't :(");
        }
    }
}

public class Game2 {

    interface constants {
        //set of constants about the world that are used through all classes
        
        public static final int window_w = 1000;
        public static final int window_h = 1000;
        int numspaces = window_h/25;
        public Color background = new Color(255, 255, 255);
        Random rand = new Random();
        public WorldImage theWorld =
                new RectangleImage(new Posn(window_w/2, window_h/2), 
                window_w, window_h, background);
    }
    
    public static class jayMan implements constants {
        
        //slideyJay only has one important field, being the height
        int jay_y;
        int jay_x;
        int health;
        
        //constructor
        jayMan() {}        
        
        //find where jayMan is
        public Posn where_is_Jay() {
            return new Posn(jay_x,jay_y);
        }
        
        //not super necessary as jay_x can just be called, but this just
        //returns jay's x coordinate
        public int jays_x() {
            return jay_x;
        }
        
        //not super necessary as jay_y can just be called, but this just
        //returns jay's y coordinate
        public int jays_y() {
            return jay_y;
        }
        
        //move function --> takes a string as input. if string is up, jay moves
        //one block up on the screen. if string is down, jay moves one block
        //down on the screen. movement in the left and right directions is
        //prohibited, and therefore not included.
        public void move(String str) {
            if(str.equals("up")) {
                if(jay_y >= 25) {
                    jay_y = jay_y - 25;
                }
            } else if(str.equals("down")) {
                if(jay_y <= window_h - 25) {
                    jay_y = jay_y + 25;
                }
            }
        }
        
        public int jay_health() {
            return health;
        }
        
        public void reset_health() {
            health = 3;
        }
        
        //jayImage() --> returns a new WorldImage of jay read from the a file
        //at the location specified by where_is_Jay(). if the file cannot be found,
        //switch to the commented line above it to produce a blue square in place
        //of the image of Jay
        public WorldImage jayImage() {
//            return new RectangleImage(this.where_is_Jay(), 25, 25, new Blue());
            return new FromFileImage(this.where_is_Jay(), "jay_face_resized.jpg");
        }
        
    }
    
    public class enemy implements constants {
        
        int enemy_x;
        int enemy_y;
        int type;
        int health;
        int num = randomInt(0,100);
        
        enemy() {}
        
        public void enemyType() {
            if(num % 3 == 0) {
                type = 0;
            } else if(num % 3 == 1) {
                type = 1;
            } else {
                type = 2;
            }
        }
        
        public Posn where_is_enemy() {
            return new Posn(enemy_x, enemy_y);
        }
        
        public int enemys_x() {
            return enemy_x;
        }
        
        public int enemys_y() {
            return enemy_y;
        }
        
        public void reset_health() {
            if(type == 0) {
                health = 3;
            } else if(type == 1) {
                health = 6;
            } else {
                health = 9;
            }
        }
        
        public int enemy_health() {
            return health;
        }
        
        public WorldImage enemyImage() {
            return new RectangleImage(this.where_is_enemy(), 25, 25, new Red());
        }
        
        public int randomInt(int min, int max) {
            return rand.nextInt((max - min) + 1) + min;
        }
        
        public void move() {
            int in = randomInt(0,100);
            if(in % 4 == 0) {
                //move enemy up
                if(this.enemys_y() >= 25) {
                    enemy_y = this.enemys_y() - 25;
                }
            } else if(in % 4 == 1) {
                //move enemy right
                if(this.enemys_x() <= window_w - 25) {
                    enemy_x = this.enemys_x() + 25;
                }
            } else if(in % 4 == 2) {
                // move enemy down
                if(this.enemys_y() <= window_h - 25) {
                    enemy_y = this.enemys_y() + 25;
                }
            } else {
                //move enemy left
                if(this.enemys_x() >= 25) {
                    enemy_x = this.enemys_x() - 25;
                }
            }
        }
        
    }
    
    public class Room extends World implements constants {
        
        jayMan jay;
        
        public void onTick() {
            //move mobs
        }
        
        public void onKeyEvent(String str) {
            if(str.equals("up") || str.equals("down") ||
                    str.equals("left") || str.equals("right")) {
                jay.move(str);
            } else if (str.equals("i")) {
                //open inventory
            }
        }
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
