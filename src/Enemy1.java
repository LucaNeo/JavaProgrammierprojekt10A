package src;

// Autor Luca/Titus
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Enemy1 extends Enemy {

    private double x, y;
    private float health = 100;
    private final float maxHealth;
    private double speed = 0.05;
    private final Image image;

    public Enemy1(double x, double y) {
        super(x, y);
        this.x = x;
        this.y = y;
        maxHealth = health * DifficultySettings.getEnemyHealthMultiplier();
        System.out.println(DifficultySettings.getEnemyHealthMultiplier());
        this.health = (int) (health * DifficultySettings.getEnemyHealthMultiplier());
        this.speed = speed * DifficultySettings.getEnemySpeedMultiplier();

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-midlander.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public int getHealth() { return (int) health; }

    @Override
    public void draw(Graphics2D g2d, int offsetX, int CHUNK_SIZE) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) health / maxHealth));
        System.out.println(health / maxHealth);
        g2d.drawImage(image, (int) Math.round(x * CHUNK_SIZE) + offsetX, (int) Math.round(y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public void move() {
        if (x == 2 && y < 7) {
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x < 6 && y == 7){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 6 && y > 4 && y < 8){
            y = Double.parseDouble(String.format("%.2f", y -= speed).replace(',', '.'));
        }
        if (x < 10 && x > 5 && y == 4){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 10 && y < 10){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x > 4 && y == 10){
            x = Double.parseDouble(String.format("%.2f", x -= speed).replace(',', '.'));
        }
        if (x == 4 && y > 9 && y < 14){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile, int CHUNK_SIZE) {
        double centerPointX = x + 0.5;
        double centerPointY = y + 0.5;
        double tolerance = 0.5;

        if (projectile.getX() <= centerPointX + tolerance && projectile.getX() >= centerPointX - tolerance && projectile.getY() <= centerPointY + tolerance && projectile.getY() >= centerPointY - tolerance) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void takeDamage(int damage){
        health -= damage;
    }
}