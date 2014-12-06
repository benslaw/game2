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
    
    //jayMan is the player
    public static class jayMan implements Game2.constants {
        
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
        //returns jayMan's x coordinate
        public int jays_x() {
            return jay_x;
        }
        
        //not super necessary as jay_y can just be called, but this just
        //returns jayMan's y coordinate
        public int jays_y() {
            return jay_y;
        }
        
        //move function --> takes a string as input. if string is up, jayMan moves
        //one block up on the screen. if string is down, jay moves one block
        //down on the screen. the same holds for moving left and right. jayMan
        //cannot move off the edges of the screen
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
        
        //returns jayMan's health
        public int jay_health() {
            return health;
        }
        
        //used to reset jayMan's health to the full value of 3
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
        
//        public WorldImage fireJayzor(String str) {
//            WorldImage temp = theWorld;
//            if(str.equals("w")) {
//                //fire jayzor up
//                for(int i = 0; i < jay_y - 13; i++) {
//                    temp = temp.overlayImages(new RectangleImage(new Posn(jay_x, i), 1, 1, new Black()));
//                }
//            } else if(str.equals("a")) {
//                //fire jayzor left
//                for(int i = 0; i < jay_x - 13; i++) {
//                    temp = temp.overlayImages(new RectangleImage(new Posn(i, jay_y), 1, 1, new Black()));
//                }
//            } else if(str.equals("s")) {
//                //fire jayzor down
//                for(int i = jay_y + 13; i < window_h; i++) {
//                    temp = temp.overlayImages(new RectangleImage(new Posn(jay_x, i), 1, 1, new Black()));
//                }
//            } else {
//                //fire jayzor right
//                for(int i = jay_x + 13; i < window_w; i++) {
//                    temp = temp.overlayImages(new RectangleImage(new Posn(i, jay_y), 1, 1, new Black()));
//                }
//            }
//            return temp;
//        }
//        
//        public ArrayList<Posn> buildJayzor(String str) {
//            ArrayList<Posn> jayzor = new ArrayList<Posn>();
//            if(str.equals("w")) {
//                for(int i = 0; i < jay_y - 13; i++) {
//                    jayzor.add(new Posn(jay_x, i));
//                }
//            } else if(str.equals("a")) {
//                for(int i = 0; i < jay_x - 13; i++) {
//                    jayzor.add(new Posn(i, jay_y));
//                }
//            } else if(str.equals("s")) {
//                for(int i = jay_y + 13; i < window_h; i++) {
//                    jayzor.add(new Posn(jay_x, i));
//                }
//            } else {
//                for(int i = jay_x + 13; i < window_w; i++) {
//                    jayzor.add(new Posn(i, jay_y));
//                }
//            }
//            return jayzor;
//        }
//        
//        public WorldImage jayzorImage(ArrayList<Posn> x) {
//            WorldImage newWorld = theWorld;
//            for(int i = 0; i < x.size(); i++) {
//                newWorld.overlayImages(new RectangleImage(x.get(i), 5, 5, new Black()));
//            }
//            return newWorld;
//        }
//        
    }
    
    //the enemies that jayMan has to fight
    public static class enemy implements Game2.constants {
        
        int enemy_x;
        int enemy_y;
        int type;
        int health;
        Color enemyColor;
        
        //constructor
        enemy() {}
        
        //assign new values to the enemy's x and y coordinates using a random integer generator
        public void intialize_enemy() {
            this.enemy_x = (randomInt(1,numspacesw - 1))*25 + (int) Math.floor(25/2);
            this.enemy_y = (randomInt(1,numspacesh - 1))*25 + (int) Math.floor(25/2);
        }
        
        //assign a type to the enemy. there are three enemy types which correspond to 
        //three different levels of difficulty
        public void enemyType() {
            int num = randomInt(0,100);
            if(num % 3 == 0) {
                this.type = 1;
                this.enemyColor = new Color(0,255,0);
            } else if(num % 3 == 1) {
                this.type = 2;
                this.enemyColor = new Color(255,0,0);
            } else {
                this.type = 3;
                this.enemyColor = new Color(0,0,255);
            }
        }
        
        //returns a new Posn data structure with the enemy's x and y values
        public Posn where_is_enemy() {
            return new Posn(enemy_x, enemy_y);
        }
        
        //return the enemy's x coordinate
        public int enemys_x() {
            return enemy_x;
        }
        
        //return the enemy's y coordinate
        public int enemys_y() {
            return enemy_y;
        }
        
        //assigns the enemies health according to their type, higher type means
        //higher health and therefore higher difficulty
        public void reset_health() {
            if(type == 1) {
                health = 3;
            } else if(type == 2) {
                health = 6;
            } else {
                health = 9;
            }
        }
        
        //return the enemy's health
        public int enemy_health() {
            return health;
        }
        
        //returns a new WorldImage representing the enemy. the enemy's current health
        //is also shown on the body of the enemy
        public WorldImage enemyImage() {
            return new OverlayImages( new RectangleImage(this.where_is_enemy(), 25, 25, enemyColor),
                    new TextImage(this.where_is_enemy(), "" + this.enemy_health(), new White()));
        }
        
        //random integer generator used in other functions
        public int randomInt(int min, int max) {
            return rand.nextInt((max - min) + 1) + min;
        }
        
        //according to a random integer, move the enemy up, down, left, or right
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
    
    //enemies class used to organize multiple enemies
    public static class enemies implements Game2.constants {
        
        ArrayList<Game2.enemy> all_enemies = new ArrayList<Game2.enemy>();
        int counter;
        
        //constructor
        enemies() {}
        
        //takes an integer as input, creates and collects the input number of enemies
        //in a workable ArrayList of enemies
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
        
        //enemiesImage scrolls through the ArrayList of enemies and adds all 
        //enemies contained within the arraylist to the world
        public WorldImage enemiesImage(ArrayList<enemy> enemies) {
            WorldImage newWorld = theWorld;
            for(int i = 0; i < enemies.size(); i++) {
                WorldImage temp = enemies.get(i).enemyImage();
                newWorld = newWorld.overlayImages(temp);
            }
            return newWorld;
        }
        
        //returns a boolean representing whether the arraylist containing the
        //enemies is empty (i.e. if all enemies are dead)
        public boolean isEmpty() {
            return this.all_enemies.isEmpty();
        }
        
    }
    
    //the main workhorse of the game, room controls the world etc.
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
        boolean timerStatus = this.stopTimer;
        
        //constructor
        public Room(jayMan jay, enemy enemy, enemies enemies) {
            this.jay = jay;
            this.enemy = enemy;
            this.enemies = enemies;
        }        
        
        //for each level, a different number of enemies spawn. as the level increases,
        //so does the number of enemies per level.
        public void make_enemies() {
            counter = level;
            enemies.create_enemies(counter);
        }
        
        //for each tick of the in-game clock, all of the enemies move
        public void onTick() {
            //move mobs
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
                enemies.all_enemies.get(i).move();
            }
        }

        //the makeImage function has two modes: the regular world and the inventory
        //based on key input it can switch between the two modes
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

        //onKeyEvent --> translates player key input into game actions, including:
        // -- moving jayMan with the arrow keys
        // -- shooting the jayzors with WASD
        // -- opening/closing the inventory screen
        // -- using items while in the inventory screen
        public void onKeyEvent(String str) {
            //arrow keys move jayMan
            if (str.equals("up") || str.equals("down")
                    || str.equals("left") || str.equals("right")) {
                jay.move(str);
            } else if (str.equals("i")) {
                //open/close inventory
                this.stopTimer = !this.stopTimer;
                timerStatus = !timerStatus;
                if (sent == 0) {
                    sent = 1;
                } else {
                    sent = 0;
                }
            } else if (str.equals("w") || str.equals("a")
                    || str.equals("s") || str.equals("d")) {
                //fire jayzors
//                jay.fireJayzor(str);
//                jay.jayzorImage(jay.buildJayzor(str));
                jayzorLanded(str);
            }
            //keystrokes only available while in the inventory screen
            if (sent == 1) {
                //use superburst
                if (str.equals("f") && superBurst) {
                    for (int i = 0; i < enemies.all_enemies.size(); i++) {
                        enemies.all_enemies.get(i).health = 0;
                    }
                    cleanEnemies();
                    superBurst = false;
                }
                //use healthpack 1
                if (str.equals("g") && healthPack && jay.health < 3) {
                    jay.health = jay.health + 1;
                    healthPack = false;
                }
                //use healthpack 2
                if (str.equals("h") && healthPack2 && jay.health < 3) {
                    jay.health = jay.health + 1;
                    healthPack2 = false;
                }
            }
        }

        //used to test whether a collision has occurred between jayMan and an enemy
        public boolean collisionHuh() {
            boolean temp = false;
            for(int i = 0; i < enemies.all_enemies.size(); i++) {
                if((jay.where_is_Jay().x == enemies.all_enemies.get(i).where_is_enemy().x) && 
                        (jay.where_is_Jay().y == enemies.all_enemies.get(i).where_is_enemy().y)) {
                    jay.health = jay.health - 1;
                    temp = true;
                }
            }
            return temp;
        }
        
        //returns a boolean signifying whether jayMan is dead (i.e. his health is zero)
        public boolean isJayManDead() {
            if(jay.health == 0) {
                return true;
            } else {
                return false;
            }
        }
        
        //when all enemies are killed, this function is used to create the next level
        public void resetNewLevel() {
            level++;
            this.make_enemies();
        }
        
        //worldEnds --> called on each tick of the clock, used to determine whether:
        // -- all enemies are dead and the level should increment
        // -- jayMan is dead and the world should end
        // -- a collision has occurred and the world continues
        public WorldEnd worldEnds() {
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
                collisionHuh();
                return new WorldEnd(false, this.makeImage());
            }
        }
        
        //cleanEnemies removes all enemies that are dead (i.e. have a health of zero)
        //and returns a new arraylist of enemies that are still alive
        public void cleanEnemies() {
            ArrayList<enemy> temp = new ArrayList<enemy>();
            for (int i = 0; i < enemies.all_enemies.size(); i++) {
                if (enemies.all_enemies.get(i).enemy_health() > 0) {
                    temp.add(enemies.all_enemies.get(i));
                } else {
                    score = score + enemies.all_enemies.get(i).type * 100;
                }
            }
            enemies.all_enemies = temp;
        }
        
        //jayzorLanded is fed the string from onKeyEvent, and "fires" a jayzor
        //in the appropriate direction. if an enemy is in the jayzors line of sight, it
        //loses one health point and the players score increases by 100 points
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
    
    //creates the player, enemy, and enemies and throws them into a Room
    public static class ActualWorld implements constants {
        
        //constructor
        ActualWorld() {}
        
        jayMan player = new jayMan();
        enemy theEnemy = new enemy();
        enemies theEnemies = new enemies();
        
        Room playField = new Room(player, theEnemy, theEnemies);
        
    }
    
    //auxiliary testing world used solely for testing, not player-interactive
    public static class TestingWorld implements constants {
        
        //constructor
        TestingWorld() {}
        
        jayMan player = new jayMan();
        enemy theEnemy = new enemy();
        enemies theEnemies = new enemies();
        
        Room playField = new Room(player, theEnemy, theEnemies);

        //testing suite, which includes testing for the following:
        // -- moving up, down, left, right on screen boundaries
        // -- moving up, down, left, right when not on a boundary
        // -- firing a jayzor up, down, left, right and hitting an enemy
        // -- collisionHuh detects collisions between jayMan and an enemy
        // -- when a collision occurs between jayMan and an enemy, jayMan loses one health
        // -- the world pauses while the inventory is open
        // -- the world resumes when the inventory is closed
        // -- when the inventory is open, if jayMan is at full health, the healthpacks can't be used
        // -- when the inventory is open, if jayMan is not a full health, the healthpacks can be used
        // -- when the superburst is used, the player is instantly bumped to the next level
        public static void test() throws Exception {
            TestingWorld t = new TestingWorld();
            t.player.reset_health();
            t.playField.make_enemies();
            Posn jayOriginalPosn = t.playField.jay.where_is_Jay();
            int currentX = t.playField.jay.jays_x();
            int currentY = t.playField.jay.jays_y();
            //the following block of ifs checks whether jayMan has the ability to physically move off
            //of the screen boundary
            if (currentX < 25) {
                t.playField.onKeyEvent("left");
                Testeez.check("move left on boundary", jayOriginalPosn, t.playField.jay.where_is_Jay());
            } else if (currentX >= window_w - 25) {
                t.playField.onKeyEvent("right");
                Testeez.check("move right on boundary", jayOriginalPosn, t.playField.jay.where_is_Jay());
            } else if (currentY < 25) {
                t.playField.onKeyEvent("up");
                Testeez.check("move up on boundary", jayOriginalPosn, t.playField.jay.where_is_Jay());
            } else if (currentY >= window_h - 25) {
                t.playField.onKeyEvent("down");
                Testeez.check("move down on boundary", jayOriginalPosn, t.playField.jay.where_is_Jay());
            }
            //this block checks whether calling one of the move key events successfully moves jayMan
            t.playField.onKeyEvent("left");
            Testeez.check_ints("move left", jayOriginalPosn.x - 25, t.playField.jay.where_is_Jay().x);
            t.playField.onKeyEvent("right");
            Testeez.check_ints("move right", jayOriginalPosn.x, t.playField.jay.where_is_Jay().x);
            t.playField.onKeyEvent("up");
            Testeez.check_ints("move up", jayOriginalPosn.y - 25, t.playField.jay.where_is_Jay().y);
            t.playField.onKeyEvent("down");
            Testeez.check_ints("move down", jayOriginalPosn.y, t.playField.jay.where_is_Jay().y);
            //there should be one enemy in the world at this point
            //if jayMan moves so that either his x-coordinate or his y-coordinate matches that
            //of the enemy, he will be able to shoot it successfully
            //for the sake of testing, we're just going to set his coordinate to make it easier
            enemy oneEnemy = t.playField.enemies.all_enemies.get(0);
            //right and left jayzors
            int oneEnemyY = oneEnemy.where_is_enemy().y;
            t.playField.jay.jay_y = oneEnemyY;
            int beforeEnemyHealth = oneEnemy.health;
            if(oneEnemy.enemys_x() > t.playField.jay.jays_x()) {
                t.playField.onKeyEvent("d");
                Testeez.check_ints("jayzor right lands", beforeEnemyHealth - 1, oneEnemy.health);
            }
            if(oneEnemy.enemys_x() < t.playField.jay.jays_x()) {
                t.playField.onKeyEvent("a");
                Testeez.check_ints("jayzor left lands", beforeEnemyHealth - 1, oneEnemy.health);
            }
            //this just resets jayMan's position to the original one
            t.playField.jay.jay_x = jayOriginalPosn.x;
            t.playField.jay.jay_y = jayOriginalPosn.y;
            //up and down jayzors
            int oneEnemyX = oneEnemy.where_is_enemy().x;
            t.playField.jay.jay_x = oneEnemyX;
            int beforeEnemyHealth2 = oneEnemy.health;
            if(oneEnemy.enemys_y() > t.playField.jay.jays_y()) {
                t.playField.onKeyEvent("s");
                Testeez.check_ints("jayzor down lands", beforeEnemyHealth2 - 1, oneEnemy.health);
            }
            if(oneEnemy.enemys_y() < t.playField.jay.jays_y()) {
                t.playField.onKeyEvent("w");
                Testeez.check_ints("jayzor up lands", beforeEnemyHealth2 - 1, oneEnemy.health);
            }
            //collision testing -- jayMan should lose one health each time he collides with an enemy
            int jayOriginalHealth = t.playField.jay.health;
            if((t.playField.jay.where_is_Jay().x == oneEnemyX) && (t.playField.jay.where_is_Jay().y == oneEnemyY)) {
                Testeez.check("proper collision", t.playField.collisionHuh(), true);
                Testeez.check_ints("jay loses health", t.playField.jay.health + 1, jayOriginalHealth);
            }
            //check inventory opening and closing using the timer as a litmus for world swap
            t.playField.onKeyEvent("i");
            Testeez.check("inventory open -- world paused", t.playField.timerStatus, true);
            t.playField.onKeyEvent("i");
            Testeez.check("inventory closed -- world unpaused", t.playField.timerStatus, false);
            t.playField.onKeyEvent("i");
            //check healthpacks don't add when jayMan is at full health
            int jayCurrentHealth = t.playField.jay.health;
            t.playField.onKeyEvent("g");
            int jayAfterHealth = t.playField.jay.health;
            if(jayCurrentHealth == 3) {
                Testeez.check_ints("healthpack shouldn't add", jayCurrentHealth, jayAfterHealth);
            }
            int jayCurrentHealth2 = t.playField.jay.health;
            t.playField.onKeyEvent("h");
            int jayAfterHealth2 = t.playField.jay.health;
            if(jayCurrentHealth2 == 3) {
                Testeez.check_ints("healthpack2 shouldn't add", jayCurrentHealth2, jayAfterHealth2);
            }
            //check healthpacks do add when jayMan is below full health
            int jayCurrentHealth3 = t.playField.jay.health;
            t.playField.onKeyEvent("g");
            int jayAfterHealth3 = t.playField.jay.health;
            if(jayCurrentHealth3 < 3) {
                Testeez.check_ints("healthpack3 should add", jayCurrentHealth3 + 1, jayAfterHealth3);
            }
            int jayCurrentHealth4 = t.playField.jay.health;
            t.playField.onKeyEvent("h");
            int jayAfterHealth4 = t.playField.jay.health;
            if(jayCurrentHealth4 < 3) {
                Testeez.check_ints("healthpack4 should add", jayCurrentHealth4 + 1, jayAfterHealth4);
            }
            //testing superBurst, using level value as a litmus since using the superburst should kill
            //all enemies and automatically advance the player to the next level
            int levelBefore = t.playField.level;
            t.playField.onKeyEvent("f");
            t.playField.onKeyEvent("i");
            t.playField.worldEnds();
            int levelAfter = t.playField.level;
            Testeez.check_ints("superburst", levelBefore + 1, levelAfter);
        }
        
    }
       
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5000; i++) {
            TestingWorld.test();
//            System.out.println("Test world #" + (i+1) + " passed!");
        }
        System.out.println("All tests passed!");
        ActualWorld x = new ActualWorld();
        x.player.reset_health();
        x.playField.make_enemies();
        x.playField.bigBang(500, 500, 0.2);
    }
}
