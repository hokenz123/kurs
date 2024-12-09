package game;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Entity {
    public double dx, dy;
    public final static int D = 10;
    private int damage = 1;
    private boolean isMoving = false;
    Color color = Color.gray;

    public static int[] bounds = {
            0,                          // Граница по х слева
            1200,                       // Граница по х справа 1230/2-10/2=615-5=610
            50,                         // Граница по у сверху
            600                         // Граница по у снизу  670-5=665
    };
    public static void changeBounds(int[] bounds){
        if (bounds.length != 4) return;
        Ball.bounds = bounds;
    }

    public Ball(){
        super((double) bounds[1]/2-(double) Ball.D /2, (double) bounds[3]-(double) Ball.D /2, D, D);
        // Координаты стартовой позиции
        x = (double) bounds[1]/2-(double) Ball.D /2;
        y = (double) bounds[3]-(double) Ball.D /2;
        width = D;
        height = D;
        type = "Ball";
    }

    public void draw(Graphics g){
        g.setColor(color);
//        g.fillOval(x ,y , D, D);
        Ellipse2D.Double ball = new Ellipse2D.Double(x, y,  D, D);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.fill(ball);
    }

    @Override
    void whenCollides(Entity b){
        if(b.getType().equals("Block")) {
            if (((Block) b).isKilled()) return;
            // Координаты центров шара и блока, с которым он сталкивается
            double ballCenterX = x + (double) Ball.D / 2;
            double ballCenterY = y + (double) Ball.D / 2;
            double blockCenterX = b.getX() + (double) Block.side / 2;
            double blockCenterY = b.getY() + (double) Block.side / 2;

            // Расстояние между центрами блока и шара
            double diffX = ballCenterX - blockCenterX;
            double diffY = ballCenterY - blockCenterY;

            // Минимальное расстояние, чтобы разделить по оси х или у
            int minDist = Ball.D / 2 + Block.side / 2;

            // Глубина коллизии
            double depthX = diffX > 0 ? minDist - diffX : -minDist - diffX;
            double depthY = diffX > 0 ? minDist - diffY : -minDist - diffY;

            if (depthX != 0 && depthY != 0) {
                if (!(Math.abs(depthX) < Math.abs(depthY))) {
                    dy = -dy;
                    if (depthY > 0) {             // Коллизия сверху от шара
                        y += b.getY() + Block.side - y;
                    } else {                     // Коллизия снизу от шара
                        y -= y + Ball.D - b.getY();
                    }
                } else {
                    dx = -dx;
                    if (depthX > 0) {            // Коллизия слева от шара
                        x += b.getX() + Block.side - x;
                    } else {                     // Коллизия справа от шара
                        x -= x + Ball.D - b.getX();
                    }
                }
                ((Block) b).damage(damage);
            }
        } else if (b.getType().equals("PowerUp")){
            ((PowerUp)b).effect(this);
        }

        x += dx;
        y += dy;
    }

    public void move(){
//        for (Block b : MyFrame.blocks) {
//            this.isCollidingWidth(b);
//        }

        if (x < 0 && y > 50 & y <= bounds[3]){
            dx = -dx;
        }
        if (y < 50){
            y += 50-y;
            dy = -dy;
        }

        if (y >= bounds[3] && dy > 0){
            isMoving = false;
            x = (double) bounds[1]/2-(double) Ball.D /2;
            y = (double) bounds[3]-(double) Ball.D /2;
//            dy = -dy;
        }

        if (x >= bounds[1] && y > 50 & y <= bounds[3]){
            x = bounds[1]-1;
            dx = -dx;
        }

        x += dx;
        y += dy;
    }

    public void reverseMove(int k){
        int l = 10;
        x += -dx*(k*l);
        y += -dy*(k*l);
    }

    public void move(int x, int y){
        if (this.x == x && this.y == y){
            isMoving = false;
            return;
        }
        double vecX = x-(double) Ball.D/2-this.x;
        double vecY = y-(double) Ball.D/2-this.y;
        double speed = Math.sqrt(vecX*vecX+vecY*vecY);
        double temp = 1/speed * 2;
        dx = vecX*temp;
        dy = vecY*temp;

        this.x += dx;
        this.y += dy;

        isMoving = true;
    }
/*
    public boolean isColliding(Block block){
        int sq1Left = block.getX();
        int sq1Right = sq1Left+Block.side;
        int sq1Top = block.getY();
        int sq1Bottom = sq1Top+Block.side;

        boolean xCollide = sq1Right >= x && sq1Left <= (x + D);
        boolean yCollide = sq1Bottom >= y && sq1Top <= (y + D);

//        if (xCollide && yCollide && !block.isKilled()){
//            block.damage(1);
//            return true;
//        }
//        return false;
        return xCollide && yCollide && !block.isKilled();
    }*/

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
        isMoving = false;
    }

    public boolean isMoving(){
        return isMoving;
    }

//    public void stopMoving(){
//        isMoving = false;
//    }

    public void setDamage(int d){
        damage = d;
    }

    public int getDamage(){
        return damage;
    }

    public void setColor(Color color){ this.color = color; }
}
