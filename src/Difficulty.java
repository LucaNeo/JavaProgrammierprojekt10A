package src;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}
class DifficultySettings {
 private int enemyHealth;
 private int enemySpeed;
 private int startingMoney;
 private int TowerCost;

 public DifficultySettings(Difficulty difficulty) {
     switch (difficulty) {
         case EASY:
             enemyHealth  = 50;
             enemySpeed = 1;
             startingMoney = 3500;
             TowerCost = 250;
             break;
             case MEDIUM:
                 enemyHealth = 100;
                 enemySpeed = 2;
                 startingMoney = 2000;
                 TowerCost = 250;
                 break;
                 case HARD:
                     enemyHealth  = 200;
                     enemySpeed = 3;
                     startingMoney = 1000;
                     TowerCost = 450;
                     break;

     }
 }

    public int getEnemySpeed() {
        return enemySpeed;
    }

    public int getTowerCost() {
        return TowerCost;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }
}
