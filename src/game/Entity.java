package game;

import java.awt.*;

abstract public class Entity {
    protected int x, y;                               // Координаты сущности

    abstract public void draw(Graphics g);             // Прорисовка сущности

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Entity(){}

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
