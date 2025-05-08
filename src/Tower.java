package src;

import java.awt.*;

public interface Tower {
    void draw(Graphics g, int chunkSize);
    int getCost();
}
