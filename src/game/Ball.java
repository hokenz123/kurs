package game;

import java.awt.*;


public class Ball extends Entity {
    private double angle = 0;
    public final static int R = 10;
    private final int dist = 3;
    private boolean isMoving = false;
    public static int[] bounds = {0, 1200, 50, 600};

    public Ball(){
        x = 1000;
        y = 600;
    }

    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.fillOval(x ,y , R, R);
    }

    public void move(){
        if (angle == 0) return;
        for (Block b : frame.MyFrame.blocks) {
            if (this.isColliding(b)) {
                boolean leftCollision = b.getX()+Block.side >= x && b.getX() <= x;
                boolean rightCollision = b.getX() <= x+Ball.R && b.getX()+Block.side >= x+Ball.R;
                if (leftCollision || rightCollision)
                    angle = -angle;
                 else
                    angle = -angle+Math.PI;
                break;
            }
        }
        this.x += dist * Math.cos(angle);
        this.y += dist * Math.sin(angle);
//        if (this.y < bounds[2]){
////            if (Math.tan(angle) > 0){
////                angle = Math.PI+angle;
////            } else {
////                angle += Math.PI;
////            }
//            angle = -angle;
//        }
//
//        if (this.x > bounds[1]){
//            this.x -= 50;
//            if (angle < 0){
//                angle += Math.PI/2;
//            } else {
//                angle -= Math.PI/2;
//            }
//            return;
//        }
//
//
//        if (this.x < bounds[0] || this.y > bounds[3]){
////            isMoving = false;
//            if (angle < 0){
//                angle += Math.PI/2;
//            } else {
//                angle -= Math.PI/2;
//            }
//        }
        if (x < 0){
            x = Ball.R;
            angle = -angle+Math.PI;
            return;
        }
        if (y < 50){
            y = 50+Ball.R;
            angle = -angle;
            return;
        }
        if (this.y >= bounds[3]){
            angle = -angle;
        }
        if (this.x >= bounds[1]){
            angle = -angle+Math.PI;
        }
    }

    public void move(int x, int y){
        angle = Math.atan(((double)y-(double)this.y)/((double)x-(double)this.x));

        if (x<this.x)
            angle += Math.PI;

        this.x += dist * Math.cos(angle);
        this.y += dist * Math.sin(angle);

        isMoving = true;
    }

    public boolean isColliding(Block block){
        int sq1Left = block.getX();
        int sq1Right = sq1Left+Block.side;
        int sq1Top = block.getY();
        int sq1Bottom = sq1Top+Block.side;

        boolean xCollide = sq1Right >= x && sq1Left <= (x+ R);
        boolean yCollide = sq1Bottom >= y && sq1Top <= (y+ R);

        if (xCollide && yCollide && !block.isKilled()){
            block.damage(7);
            return true;
        }
        return false;
    }

    public double getAngle(){
        return angle;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public boolean isMoving(){
        return isMoving;
    }

    public void stopMoving(){
        isMoving = false;
    }
}
