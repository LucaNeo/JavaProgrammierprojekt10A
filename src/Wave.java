package src;

public class Wave {
    public Enemy1[] enemy1 = new Enemy1[10];

    public Wave() {
    }

    public void createWave1(){
        for (int i = 0; i < enemy1.length; i++) {
            enemy1[i] = new Enemy1(2, i * -1.5); // |1.5| ist der Abstand der Enemies in Chunks
        }
    }

    public void clearWave(){
        //clear Wave 1
        for (int i = 0; i < enemy1.length; i++) {
            enemy1[i] = null;
        }
    }
}
