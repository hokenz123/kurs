package game;

import java.awt.*;

abstract public class Entity {
    protected double x, y;                               // Координаты сущности
    protected double width, height;                      // Длина высота
    protected String type = "Entity";

    abstract public void draw(Graphics g);            // Прорисовка сущности

    public Entity(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Entity(double x, double y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(){}

    public boolean isCollidingWidth(Entity a){
        double sq1Left = x;
        double sq1Right = x+width;
        double sq1Top = y;
        double sq1Bottom = y+height;

        double sq2Left = a.getX();
        double sq2Right = a.getX()+a.getWidth();
        double sq2Top = a.getY();
        double sq2Bottom = a.getY()+a.getHeight();

        boolean xCollide = sq1Right >= sq2Left && sq1Left <= sq2Right;
        boolean yCollide = sq1Bottom >= sq2Top && sq1Top <= sq2Bottom;

        if (xCollide && yCollide) {
            a.whenCollides(this);
            this.whenCollides(a);
        }
        return xCollide && yCollide;
    }

    abstract void whenCollides(Entity b);

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public String getType() { return type; }
}