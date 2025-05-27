package src;

public class Wave {
    public Enemy1[] enemy1 = new Enemy1[10];
    private final int CHUNK_SIZE = 72;

    public Wave() {
    }

    public void createWave1(){
        enemy1[0] = new Enemy1(2 * CHUNK_SIZE,0);
        for (int i = 0; i < 5; i++) {
            if (enemy1[i] != null && enemy1[i].getY() >= 1 * CHUNK_SIZE){
                enemy1[i + 1] = new Enemy1(2 * CHUNK_SIZE,0);
            }
        }
    }

    public void clearWave(){
    }
}
