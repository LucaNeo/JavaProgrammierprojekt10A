package src;

import java.awt.*;

public interface Tower {
    void draw(Graphics g, int chunkSize);
    int getCost();
    public default void move() {

    }
    public default void draw(Graphics g) {
    }
}
