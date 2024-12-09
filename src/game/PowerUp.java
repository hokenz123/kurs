package game;

import java.awt.Graphics;

public interface PowerUp {

    boolean isUsed();

    void effect(Ball ball);
    void draw(Graphics g);
}