package src;

public class Shot {

    public GamePanel gamePanel ;


    Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public  void attack() {
        for (int i = 0; i < gamePanel.towers1.size(); i++) {
            Enemy1 targetenemy;
            for (int j = 0; j < gamePanel.wave.enemy1.length; j++) {
              double deltax = gamePanel.towers1.get(i).x - gamePanel.wave.enemy1[j].x;
                double deltay = gamePanel.towers1.get(i).y - gamePanel.wave.enemy1[j].y;
                double rangelenth = Math.sqrt(deltax * deltax + deltay * deltay);
                if (rangelenth<=gamePanel.towers1.get(i).range*gamePanel.CHUNK_SIZE) {
                     targetenemy = gamePanel.wave.enemy1[j];


                }


            }

                                 }


        }




}
