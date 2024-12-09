package game;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Block extends Entity {
    int nx, ny;                                         // Нормализованные координаты
    public final static int side = 40;                  // Длина стороны
    private int health;                                 // Здоровье блока
    private boolean killed = false;                     // Флаг убит ли блок

    private Color getColor(){
        if (health <= 10) return new Color(219, 216, 240);
        if (health <= 20) return new Color(255, 238, 136);
        if (health <= 30) return new Color(237, 106, 94);
        if (health <= 40) return new Color(62, 146, 204);
        return new Color(132, 169, 192);
    }
    public void draw(Graphics g){
        if (!killed){
            Rectangle2D.Double rect = new Rectangle2D.Double(x, y, side, side);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(this.getColor());
            g2d.fill(rect);
            g2d.setColor(Color.black);
            g2d.draw(rect);
            g2d.drawString(health+"", (int)( x+(side/2-(health+"").length()*5+5)), (int)(y+side/2+5)); // Нарисовать текст по середине блока
        }
    }
    public Block(int nx, int ny, int health){
        super(nx*side, ny*side, side, side);
        this.nx = nx;
        this.ny = ny;
        y+=50;                  // Учет заголовка окна
        this.health = health;
        type = "Block";
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

    void whenCollides(Entity b) {}
}