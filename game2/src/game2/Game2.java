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
        
        public static final int window_w = 500;
        public static final int window_h = 500;
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
        int jay_y = window_h/2 + (int) Math.floor(25/2);
        int jay_x = window_w/2 + (int) Math.floor(25/2);
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
        
        public WorldImage fireJayzor(String str) {
            WorldImage temp = theWorld;
            if(str.equals("w")) {
                //fire jayzor up
                for(int i = 0; i < jay_y - 13; i++) {
                    temp = temp.overlayImages(new RectangleImage(new Posn(jay_x, i), 1, 1, new Black()));
                }
            } else if(str.equals("a")) {
                //fire jayzor left
                for(int i = 0; i < jay_x - 13; i++) {
                    temp = temp.overlayImages(new RectangleImage(new Posn(i, jay_y), 1, 1, new Black()));
                }
            } else if(str.equals("s")) {
                //fire jayzor down
                for(int i = jay_y + 13; i < window_h; i++) {
                    temp = temp.overlayImages(new RectangleImage(new Posn(jay_x, i), 1, 1, new Black()));
                }
            } else {
                //fire jayzor right
                for(int i = jay_x + 13; i < window_w; i++) {
                    temp = temp.overlayImages(new RectangleImage(new Posn(i, jay_y), 1, 1, new Black()));
                }
            }
            return temp;
        }
        
        public ArrayList<Posn> buildJayzor(String str) {
            ArrayList<Posn> jayzor = new ArrayList<Posn>();
            if(str.equals("w")) {
                for(int i = 0; i < jay_y - 13; i++) {
                    jayzor.add(new Posn(jay_x, i));
                }
            } else if(str.equals("a")) {
                for(int i = 0; i < jay_x - 13; i++) {
                    jayzor.add(new Posn(i, jay_y));
                }
            } else if(str.equals("s")) {
                for(int i = jay_y + 13; i < window_h; i++) {
                    jayzor.add(new Posn(jay_x, i));
                }
            } else {
                for(int i = jay_x + 13; i < window_w; i++) {
                    jayzor.add(new Posn(i, jay_y));
                }
            }
            return jayzor;
        }
        
        public WorldImage jayzorImage(ArrayList<Posn> x) {
            WorldImage newWorld = theWorld;
            for(int i = 0; i < x.size(); i++) {
                newWorld.overlayImages(new RectangleImage(x.get(i), 5, 5, new Black()));
            }
            return newWorld;
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
            this.enemy_x = (randomInt(1,numspacesw - 1))*25 + (int) Math.floor(25/2);
            this.enemy_y = (randomInt(1,numspacesh - 1))*25 + (int) Math.floor(25/2);
        }
        
        public void enemyType() {
            int num = randomInt(0,100);
            if(num % 3 == 0) {
                this.type = 1;
                this.enemyColor = new Color(255,0,0);
            } else if(num % 3 == 1) {
                this.type = 2;
                this.enemyColor = new Color(0,255,0);
            } else {
                this.type = 3;
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
            if(type == 1) {
                health = 3;
            } else if(type == 2) {
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
                WorldImage temp = enemies.get(i).enemyImage();
                newWorld = newWorld.overlayImages(temp);
            }
            return newWorld;
        }
        
        public boolean isEmpty() {
            return this.all_enemies.isEmpty();
        }
        
    }
    
    public static class Room extends World implements constants {
        
        jayMan jay;
        enemy enemy;
        enemies enemies;
        int counter;
        int level = 1;
        int score;
        boolean superBurst = true;
        boolean healthPack = true;
        boolean healthPack2 = true;
        WorldImage invent = new RectangleImage(new Posn(window_w/2, window_h/2),
                window_w, window_h, new White());
        int sent = 0;
        
        public Room(jayMan jay, enemy enemy, enemies enemies) {
            this.jay = jay;
            this.enemy = enemy;
            this.enemies = enemies;
        }        
        
        public void make_enemies() {
            counter = level;
            enemies.create_enemies(counter);
        }
        
        public void onTick() {
            //move mobs
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
                enemies.all_enemies.get(i).move();
            }
        }

        public WorldImage makeImage() {
            if (sent == 0) {
                TextImage playerHealth = new TextImage(new Posn(window_w - 50,
                        window_h - 25), "Health: " + jay.health, new Red());
                TextImage scoreBox = new TextImage(new Posn(window_w - 50, 25), "Score: " + score, new Red());
                TextImage levelBox = new TextImage(new Posn(50, 25), "Level: " + level, new Red());
                WorldImage temp = enemies.enemiesImage(enemies.all_enemies).overlayImages(jay.jayImage(), playerHealth);
                WorldImage temp2 = temp.overlayImages(scoreBox, levelBox);
                return temp2;
            } else {
                WorldImage temp = invent;
                if (superBurst) {
                    temp = temp.overlayImages(new RectangleImage(new Posn((window_w / 2 - 100), window_h / 2),
                            50, 50, new Blue()), new TextImage(new Posn((window_w / 2 - 100), window_h / 2),
                            "f", new Black()));
                }
                if (healthPack) {
                    temp = temp.overlayImages(new RectangleImage(new Posn((window_w / 2), window_h / 2),
                            50, 50, new Red()), new TextImage(new Posn((window_w / 2), window_h / 2),
                            "g", new Black()));
                }
                if (healthPack2) {
                    temp = temp.overlayImages(new RectangleImage(new Posn((window_w / 2 + 100), window_h / 2),
                            50, 50, new Red()), new TextImage(new Posn((window_w / 2 + 100), window_h / 2),
                            "h", new Black()));
                }
                temp = temp.overlayImages(new TextImage(new Posn(window_w / 2, window_h / 5), "Inventory", 50, new Black()));
                return temp;
            }
        }

        public void onKeyEvent(String str) {
            if (str.equals("up") || str.equals("down")
                    || str.equals("left") || str.equals("right")) {
                jay.move(str);
            } else if (str.equals("i")) {
                //open inventory
                this.stopTimer = !this.stopTimer;
                if (sent == 0) {
                    sent = 1;
                } else {
                    sent = 0;
                }
            } else if (str.equals("w") || str.equals("a")
                    || str.equals("s") || str.equals("d")) {
                //fire lasers
//                jay.fireJayzor(str);
                jay.jayzorImage(jay.buildJayzor(str));
                jayzorLanded(str);
            }
            if (sent == 1) {
                if (str.equals("f") && superBurst) {
                    for (int i = 0; i < enemies.all_enemies.size(); i++) {
                        enemies.all_enemies.get(i).health = 0;
                    }
                    cleanEnemies();
                    superBurst = false;
                }
                if (str.equals("g") && healthPack) {
                    jay.health = jay.health + 1;
                    healthPack = false;
                }
                if (str.equals("h") && healthPack2) {
                    jay.health = jay.health + 1;
                    healthPack2 = false;
                }
            }
        }
        
        public String posnToString(Posn x) {
            return new String("( " + x.x + ", " + x.y + " )");
        }
        
        public boolean collisionHuh() {
            boolean temp = false;
//            System.out.println("collision1");
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
//                System.out.println(posnToString(jay.where_is_Jay()));
//                System.out.println(posnToString(enemies.all_enemies.get(i).where_is_enemy()));
                if((jay.where_is_Jay().x == enemies.all_enemies.get(i).where_is_enemy().x) && 
                        (jay.where_is_Jay().y == enemies.all_enemies.get(i).where_is_enemy().y)) {
                    jay.health = jay.health - 1;
//                    System.out.println("collision2");
                    temp = true;
                }
            }
//            System.out.println("collision3");
            return temp;
        }
        
        public boolean isJayManDead() {
            if(jay.health == 0) {
                return true;
            } else {
                return false;
            }
        }
        
        public void resetNewLevel() {
            level++;
            this.make_enemies();
        }
        
        public WorldEnd worldEnds() {
//            System.out.println("asdf");
            if(enemies.isEmpty()) {
                this.resetNewLevel();
                return new WorldEnd(false, this.makeImage());
            } else if(this.isJayManDead()) {
                return new WorldEnd(true, new OverlayImages(this.makeImage(),
                        new TextImage(new Posn(window_w/2, window_h/2),
                        "You killed Jay!", new Red()).overlayImages(
                        new TextImage(new Posn(window_w/2, window_h/2+25),
                        "Score: " + score, new Red()))));
            } else {
//                System.out.println("end1");
                collisionHuh();
//                System.out.println("end2");
                return new WorldEnd(false, this.makeImage());
            }
        }
        
        public void cleanEnemies() {
            ArrayList<enemy> temp = new ArrayList<enemy>();
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
                if(enemies.all_enemies.get(i).enemy_health() > 0) {
                    temp.add(enemies.all_enemies.get(i));
                } else {
                    score = score + enemies.all_enemies.get(i).type * 100;
                }
//                if(enemies.all_enemies.get(i).enemy_health() <= 0) {
//                    enemy current = enemies.all_enemies.get(i);
//                    score = score + current.type * 100;
//                    enemies.all_enemies.remove(current);
//                }
            }
            enemies.all_enemies = temp;
        }
        
        public boolean jayzorLanded(String str) {
            boolean temp = false;
            for (int j = 0; j < enemies.all_enemies.size(); j++) {
                if (str.equals("w") && (enemies.all_enemies.get(j).enemys_y() < jay.jay_y) &&
                        (enemies.all_enemies.get(j).enemys_x() == jay.jay_x)) {
                    //fire jayzor up
                    enemies.all_enemies.get(j).health--;
                    score = score + 100;
                    cleanEnemies();
                    temp = true;
                } else if (str.equals("a") && enemies.all_enemies.get(j).enemys_x() < jay.jay_x && 
                        (enemies.all_enemies.get(j).enemys_y() == jay.jay_y)) {
                    //fire jayzor left
                    enemies.all_enemies.get(j).health--;
                    score = score + 100;
                    cleanEnemies();
                    temp = true;
                } else if (str.equals("s") && enemies.all_enemies.get(j).enemys_y() > jay.jay_y && 
                        (enemies.all_enemies.get(j).enemys_x() == jay.jay_x)) {
                    //fire jayzor down
                    enemies.all_enemies.get(j).health--;
                    score = score + 100;
                    cleanEnemies();
                    temp = true;
                } else if (str.equals("d") && enemies.all_enemies.get(j).enemys_x() > jay.jay_x && 
                        (enemies.all_enemies.get(j).enemys_y() == jay.jay_y)) {
                    //fire jayzor right
                    enemies.all_enemies.get(j).health--;
                    score = score + 100;
                    cleanEnemies();
                    temp = true;
                }
            }
            return temp;
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
//        System.out.println("asdf1");
        ActualWorld x = new ActualWorld();
        x.player.reset_health();
        x.playField.make_enemies();
        x.playField.bigBang(500, 500, 0.2);
    }
}
