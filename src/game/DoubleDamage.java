package game;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DoubleDamage extends Entity implements PowerUp{
    boolean isUsed = false;

    public DoubleDamage(int x, int y){
        super(x, y);
        type = "PowerUp";
    }

    @Override
    public void draw(Graphics g){
        if (isUsed) return;
        g.setColor(Color.red);
//        g.fillOval(x, y, 20, 20);
        Ellipse2D.Double ball = new Ellipse2D.Double(x, y, 20, 20);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.fill(ball);
    }

    @Override
    void whenCollides(Entity b) {
        if(isUsed) return;
        effect((Ball)b);
    }

    public void effect(Ball ball){
        ball.setDamage(ball.getDamage()*2);
        ball.setColor(Color.red);
        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }
}