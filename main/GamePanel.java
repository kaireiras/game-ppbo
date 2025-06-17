package main;

import entity.Entity;
import entity.Player;
import entity.Player2;
import objek.SuperObjek;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public ArrayList<Entity> entitylist = new ArrayList<>();
    final int originalTileSize = 16; //Ukuran awalnya 16x16
    final int scale = 3; //dikali 3 biar jadi gede ukurannya (disesuaikan dengan layar skrg)

    public final int tileSize = originalTileSize * scale; //Ukurannya bakal 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD
    public final int maxWorldCol = 48;
    public final int maxWorldRow = 48;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionCheck cCol = new CollisionCheck(this);
    public AssetSetter aSet = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public Player2 player2 = new Player2(this, keyH);
    public Entity enemy[] = new Entity[20];
    public SuperObjek obj[] = new SuperObjek[10];
    public UI ui = new UI(this);
    public EventHandler eHand = new EventHandler(this);

    //Game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //gambar dulu "dibelakang" baru ditampilin, tpi sekarang dah otomatis
        this.addKeyListener(keyH);
        this.setFocusable(true);
        gameState = playState;
    }

    public void setGame() {
        aSet.setObjek();
        aSet.setEnemy();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.1666 sec
        double delta = 0;
        long lastTime = System.nanoTime(); // Waktu awal mula program jalan
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){

            currentTime = System.nanoTime(); // Waktu awal lopp dimulai
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime; 

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " +drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        player.update();
        player2.update();

        for(int i = 0; i < enemy.length; i++){
            if(enemy[i] != null){
                enemy[i].update();
            }
        }

    }

    public void drawMiniMap(Graphics2D g2){
        // ukuran mini map
        int mapWidth = 150;
        int mapHeight = 150;

        // posisi mini map di layar
        int x = (screenWidth - mapWidth) / 2;
        int y = (screenHeight - mapHeight) - 20;

        // background kotak mini map
        g2.setColor(new Color(0, 0, 0, 150));
        g2. fillRoundRect(x, y, mapWidth, mapHeight, 10, 10);

        // Hitung skala mini map
        double scaleX = (double)mapWidth / (maxWorldCol * tileSize);
        double scaleY = (double)mapHeight / (maxWorldRow * tileSize);

        // Gambar tile yang bisa dilihat di mini map
        for (int row = 0; row < maxWorldRow; row++){
            for(int col = 0; col < maxWorldCol; col ++){
                int tileNum = tileM.mapTileNum[col][row];
                if(!tileM.tile[tileNum].collision){
                    g2.setColor(Color.LIGHT_GRAY);
                } else{
                    g2.setColor(Color.DARK_GRAY);
                }
                int miniX = x + (int)(col * tileSize * scaleX);
                int miniY = y + (int)(row * tileSize * scaleY);
                int miniTileWidth = Math.max(1, (int)Math.ceil(tileSize * scaleX));
                int miniTileHeight = Math.max(1, (int)Math.ceil(tileSize * scaleY));
                g2.fillRect(miniX, miniY, miniTileWidth, miniTileHeight);
            }
        }

        g2.setStroke(new BasicStroke(1));

        // Gambar player di mini map
        int miniplayerX = x + (int)(player.worldX * scaleX);
        int miniplayerY = y + (int)(player.worldY * scaleY);
        int playerSize = (int)(tileSize * scaleX);
        g2.setColor(Color.RED);
        g2.fillOval(miniplayerX, miniplayerY,playerSize, playerSize);

        int miniplayer2X = x + (int)(player2.worldX * scaleX);
        int miniplayer2Y = y + (int)(player2.worldY * scaleY);
        g2.setColor(Color.BLUE);
        g2.fillOval(miniplayer2X, miniplayer2Y, playerSize, playerSize);


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        for(int i = 0; i < enemy.length; i++) {
            if(enemy[i] != null) {
                enemy[i].draw(g2);
            }
        }

        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);
            drawMiniMap(g2);
            player.draw(g2);
            player2.draw(g2);
            ui.draw(g2);
            g2.dispose();
        }

        for (int i = 0; i < enemy.length; i++){
            if(enemy[i] != null){
                entitylist.add(enemy[i]);
            }
        }

    }
}
