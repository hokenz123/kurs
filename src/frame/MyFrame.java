package frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import game.*;

public class MyFrame
extends Frame
implements MouseListener, Runnable
{
    public static Block[] blocks;
    public static Ball[] balls;
    Thread animator;
    BufferStrategy bufferStrategy;
    static int last_x = -1;
    static int last_y = -1;
    public MyFrame(int width, int height)
    {
        setVisible(true);
        setSize(width, height);
//        Canvas canvas = new Canvas();
//        this.add(canvas);
//
//        // Using double buffering
//        canvas.createBufferStrategy(2);
//
//        bufferStrategy = canvas.getBufferStrategy();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
    public MyFrame(){}

    public void update(Graphics g){
        Graphics offgc;
        Image offscreen = null;
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

    public void paint(Graphics g)
    {
//        System.out.println("painting...");
        drawing(g);
//        g.dispose();
//        bufferStrategy.show();
    }

    public void init(){
        this.addMouseListener(this);
    }

    public static void main(String[] args)
    {
        MyFrame f = new MyFrame(1280, 720);

        f.init();

        Graphics g = f.getGraphics();

        try {
            Thread.sleep(500);
        }
        catch (Exception e) {
        }

        int m = 20;
        int n = 4;
        blocks = new Block[m*n];
        for (int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){
                blocks[n*i+j] = new Block(i+1, j, 30);
            }
        }
        balls = new Ball[1];
        balls[0] = new Ball();
        f.start();
        Block.drawAll(blocks, g);
        balls[0].draw(g);
    }
    private static void drawing(Graphics g){
        Block.drawAll(blocks, g);
        if(balls == null) return;
        balls[0].draw(g);
    }

    public void start(){
        animator = new Thread(this);
        animator.start();
    }

    public void run(){
        while(true){
            if (!balls[0].isMoving()){
                try { Thread.sleep(200); }
                catch (InterruptedException e){}
                continue;
            }
            if (balls[0].getAngle() == 0)
                continue;
            balls[0].move();
            repaint();
            try { Thread.sleep(5); }
            catch (InterruptedException e){}
//            repaint(balls[0].getX()-5, balls[0].getY()-5, Ball.R+10, Ball.R+10);

//            repaint();
        }
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX()/Block.side;
        int y = (e.getY()-50)/Block.side;
        for (Block value : blocks) {
            if (value.getNX() == x && value.getNY() == y) {
                value.damage(5);
                break;
            }
        }
        last_x = e.getX();
        last_y = e.getY();
        balls[0].move(last_x, last_y);
        System.out.println(balls[0].getAngle());
        repaint();
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
