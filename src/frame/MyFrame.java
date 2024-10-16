package frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import game.*;

public class MyFrame
extends Frame
implements MouseListener, Runnable
{
    public static Block[] blocks;
    public static Ball[] balls;
    Thread animator;
    static int last_x = -1;
    static int last_y = -1;
    public MyFrame(int width, int height)
    {
        setVisible(true);
        setSize(width, height);
        int[] newBounds = {0, width-50, 50, height-50};
        Ball.changeBounds(newBounds);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    public void update(Graphics g){
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
        final int width = 1920;
        final int height = 720;
        MyFrame f = new MyFrame(width, height);

        f.init();

        Graphics g = f.getGraphics();

        try {
            Thread.sleep(500);
        }
        catch (Exception e) {
        }

        int m = 37;
        int n = 10;
        blocks = new Block[m*n];
        for (int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){
                blocks[n*i+j] = new Block(i+1, j+1, 30);
                if (i%2 == 0) blocks[n*i+j].kill();
            }
        }
        balls = new Ball[5];
        for (int i = 0; i < balls.length; i++){
            balls[i] = new Ball();
        }
        f.start();
        drawing(g);
    }
    private static void drawing(Graphics g){
        Block.drawAll(blocks, g);
        if(balls == null) return;
        for (Ball ball : balls){
            ball.draw(g);
        }
    }

    public void start(){
        animator = new Thread(this);
        animator.start();
    }

    public void run(){
        int i = 0;
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
                i = 0;
                continue;
            }
            if (balls[0].getAngle() == 0)
                continue;
            if (i <= balls.length)
                for (int k = 0; k < i; k++){
//                    balls[k].move(5);
                }
            else
                for(Ball ball : balls)
                    if(ball.isMoving())
                        ball.move();
            repaint();
            try { Thread.sleep(3); }
            catch (InterruptedException e){}
            i++;
        }
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX()/Block.side;
        int y = (e.getY()-50)/Block.side;
//        for (Block value : blocks) {
//            if (value.getNX() == x && value.getNY() == y) {
//                value.damage(5);
//                break;
//            }
//        }
        last_x = e.getX();
        last_y = e.getY();
        for (Ball ball : balls){
            ball.move(last_x, last_y);
        }
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
