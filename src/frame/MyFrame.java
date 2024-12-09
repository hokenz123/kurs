package frame;

import java.awt.*;
import java.awt.event.*;

import game.*;

public class MyFrame
extends Frame
implements MouseListener, KeyListener, Runnable
{
    int windowWidth;
    int windowHeight;
    public static Block[] blocks;
    public static Ball[] balls;
    public static int[] firstBallCoords = new int[2];
    public static PowerUp[] powerUps;
    public static int ballCount = 1000;
    Thread animator;
    static int last_x = -1;
    static int last_y = -1;
    public MyFrame(int width, int height)
    {
        windowWidth = width;
        windowHeight = height;
        setVisible(true);
        setSize(width, height);
        int[] newBounds = {0, width-50, 50, height-50};
        Ball.changeBounds(newBounds);

        init();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    public void update(Graphics g){                 // Двойная буферизация
        Graphics offgc;
        Image offscreen;
        Rectangle box = g.getClipRect();

        // create the offscreen buffer and associated Graphics
        offscreen = createImage(box.width, box.height);
        offgc = offscreen.getGraphics();
        // clear the exposed area
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, box.width, box.height);
        offgc.setColor(getForeground());
        // do normal redraw
        offgc.translate(-box.x, -box.y);
        paint(offgc);
        // transfer offscreen to window
        g.drawImage(offscreen, box.x, box.y, box.width, box.height, this);
        offscreen.flush();
    }

    public void paint(Graphics g) {
        Block.drawAll(blocks, g);
        if(balls == null) return;
        for(Ball ball : balls){
            if (ball.getY() <= 675)
                ball.draw(g);
        }
        if(powerUps == null) return;
        for(int i = 0; i < powerUps.length; ++i){
            if (!powerUps[i].isUsed())
                powerUps[i].draw(g);
        }

//        g.setColor(Color.black);
//        g.drawLine((1280-50)/2, 720-50, last_x, last_y);
    }

    public void init(){
        this.addMouseListener(this);
        this.addKeyListener(this);
    }

    public static void main()
    {
        final int width = 1280;
        final int height = 720;
        MyFrame f = new MyFrame(width, height);

        Graphics g = f.getGraphics();

        try {
            Thread.sleep(500);
        }
        catch (Exception e) {
        }

        int m = 29;
        int n = 10;
        blocks = new Block[m*n];
        for (int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){
                blocks[n*i+j] = new Block(i+1, j+1, 100);
//                if (i%2 == 0) blocks[n*i+j].kill();
            }
        }

        powerUps = new PowerUp[10];
        for (int i = 0; i < 10; ++i){
            powerUps[i] = new DoubleDamage((i+1)*Block.side, 50);
        }

        balls = new Ball[ballCount];
        for (int i = 0; i < balls.length; i++){
            balls[i] = new Ball();
        }
        f.start();
        f.paint(g);
    }

    public void start(){
        animator = new Thread(this);
        animator.start();
    }

    public void run(){
        while(true){
            boolean moves = false;
            for (Ball ball : balls){
                if (ball.isMoving()){
                    moves = true;
                    break;
                }
            }
            if (!moves){
                try { Thread.sleep(200); }
                catch (InterruptedException e){}
                continue;
            }

            for (Ball ball : balls){
                if (!ball.isMoving()) continue;

                ball.move();

                for (PowerUp p : powerUps)
                    ball.isCollidingWidth((Entity) p);
                for (Block b : blocks)
                    ball.isCollidingWidth(b);
            }

            last_x = MouseInfo.getPointerInfo().getLocation().x;
            last_y = MouseInfo.getPointerInfo().getLocation().y;
            repaint();
            try { Thread.sleep(1); }
            catch (InterruptedException e){}
        }
    }

    public void mousePressed(MouseEvent e){
        last_x = e.getX();
        last_y = e.getY();

        for (int i = 0; i < ballCount; ++i){
            balls[i].move(last_x, last_y);
            balls[i].reverseMove(i);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            for (Ball ball : balls) {
                ball.setPos(610, 665);
            }
        }
    }

    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}