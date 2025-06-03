package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; //Ukuran awalnya 16x16
    final int scale = 3; //dikali 3 biar jadi gede ukurannya (disesuaikan dengan layar skrg)

    public final int tileSize = originalTileSize * scale; //Ukurannya bakal 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHight = tileSize * maxScreenRow; // 576px

    // WORLD
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionCheck cCol = new CollisionCheck(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //gambar dulu "dibelakang" baru ditampilin, tpi sekarang dah otomatis
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//
//        while (gameThread != null){
//
//            update();
//
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime(); // Sisa waktunya
//                remainingTime = remainingTime/1000000; // Dijadiin milisec
//
//                if (remainingTime < 0){
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
