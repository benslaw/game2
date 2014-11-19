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
        int numspacesh = window_h/25;
        int numspacesw = window_w/25;
        public Color background = new Color(255, 255, 255);
        Random rand = new Random();
        public WorldImage theWorld =
                new RectangleImage(new Posn(window_w/2, window_h/2), 
                window_w, window_h, background);
    }
    
    public static class jayMan implements Game2.constants {
        
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
            } else if (str.equals("left")) {
                if(jay_x >= 25) {
                    jay_x = jay_x - 25;
                }
            } else if (str.equals("right")) {
                if(jay_x <= window_w - 25) {
                    jay_x = jay_x + 25;
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
    
    public static class enemy implements Game2.constants {
        
        int enemy_x;
        int enemy_y;
        int type;
        int health;
        Color enemyColor;
//        int num = randomInt(0,100);
        
        enemy() {}
        
        public void intialize_enemy() {
            this.enemy_x = (randomInt(1,numspacesw - 1))*25;
            this.enemy_y = (randomInt(1,numspacesh - 1))*25;
        }
        
        public void enemyType() {
            int num = randomInt(0,100);
            if(num % 3 == 0) {
                this.type = 0;
                this.enemyColor = new Color(255,0,0);
            } else if(num % 3 == 1) {
                this.type = 1;
                this.enemyColor = new Color(0,255,0);
            } else {
                this.type = 2;
                this.enemyColor = new Color(0,0,255);
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
            return new OverlayImages( new RectangleImage(this.where_is_enemy(), 25, 25, enemyColor),
                    new TextImage(this.where_is_enemy(), "" + this.enemy_health(), new White()));
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
    
    public static class enemies implements Game2.constants {
        
        ArrayList<Game2.enemy> all_enemies = new ArrayList<Game2.enemy>();
        int counter;
        
        enemies() {}
        
        public ArrayList<enemy> create_enemies(int num_enemies) {
            for(int i = 0; i < num_enemies; i++) {
                enemy e = new enemy();
                e.enemyType();
                e.reset_health();
                e.intialize_enemy();
                all_enemies.add(e);
            }
            return all_enemies;
        }
        
        public WorldImage enemiesImage(ArrayList<enemy> enemies) {
            WorldImage newWorld = theWorld;
            for(int i = 0; i < enemies.size(); i++) {
                RectangleImage temp = new RectangleImage
                        (enemies.get(i).where_is_enemy(), 25, 25, 
                        enemies.get(i).enemyColor);
                newWorld = newWorld.overlayImages(temp);
            }
            return newWorld;
        }
        
    }
    
    public static class Room extends World implements constants {
        
        jayMan jay;
        enemy enemy;
        enemies enemies;
        
        public Room(jayMan jay, enemy enemy, enemies enemies) {
            this.jay = jay;
            this.enemy = enemy;
            this.enemies = enemies;
        }        
        
        public void onTick() {
            //move mobs
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
                enemies.all_enemies.get(i).move();
            }
        }
        
        public WorldImage makeImage() {
            TextImage playerHealth = new TextImage(new Posn(window_w-50, 
                    window_h - 25), "Health: " + jay.health, new Red());
            return enemies.enemiesImage(enemies.all_enemies).overlayImages(jay.jayImage(), playerHealth);
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
    
    public static class ActualWorld implements constants {
        
        ActualWorld() {}
        
        jayMan player = new jayMan();
        enemy theEnemy = new enemy();
        enemies theEnemies = new enemies();
        
        Room playField = new Room(player, theEnemy, theEnemies);
        
    }
    
    public static void main(String[] args) {
        ActualWorld x = new ActualWorld();
        x.playField.bigBang(1000, 1000, 1);
    }
}
