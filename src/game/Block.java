package game;
import java.awt.*;

public class Block extends Entity {
    int nx, ny;                                         // Нормализованные координаты
    public final static int side = 40;                  // Длина стороны
    private int health;                                 // Здоровье блока
    private boolean killed = false;                     // Флаг убит ли блок

    private Color getColor(){
        if (health <= 10) return Color.red;
        if (health <= 20) return Color.yellow;
        if (health <= 30) return Color.green;
        return Color.magenta;
    }
    public void draw(Graphics g){
        if (!killed){
            g.setColor(this.getColor());
            g.fillRect(x, y, side, side);
            g.setColor(Color.black);
            g.drawRect(x, y, side, side);
            g.drawString(health+"", x+(side/2-(health+"").length()*5+5), y+side/2+5);
        }
    }
    public Block(int nx, int ny, int health){
        super(nx*side, ny*side);
        this.nx = nx;
        this.ny = ny;
        y+=50;                  // Учет заголовка окна
        this.health = health;
    }
    public void damage(int value){
        if (health-value > 0) {
            health -= value;
            return;
        }
        killed = true;
    }

    public static void drawAll(Block[] blocks, Graphics g){
        if (blocks == null) return;
        for (Block block : blocks){
            block.draw(g);
        }
    }

    public int getNX(){
        return nx;
    }

    public int getNY(){
        return ny;
    }

    public boolean isKilled(){
        return killed;
    }

    public void kill(){
        killed = true;
    }
}
