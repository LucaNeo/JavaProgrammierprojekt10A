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


 public DifficultySettings(Difficulty difficulty) {
     switch (difficulty) {
         case EASY:
             setEnemyHealth(50);
             setEnemySpeed(1);
             setStartingMoney(3500);

             break;
             case MEDIUM:
                 setEnemyHealth(100);
                 setEnemySpeed(2);
                 setStartingMoney(2000);

                 break;
                 case HARD:
                     setEnemyHealth(200);
                     setEnemySpeed(3);
                     setStartingMoney(1000);

                     break;

     }
 }

    public static float getEnemySpeedMultiplier() {
     if (currentDifficulty == Difficulty.EASY) {
         return 0.9F;
     }
     if (currentDifficulty == Difficulty.MEDIUM) {
         return 1F;
     }
     if (currentDifficulty == Difficulty.HARD) {
         return 1.2F;
     }else
         return 1.0F;
//        return ((long) switch (currentDifficulty) {
//            case EASY -> 0.9;   // Gegner sind 10% langsamer
//            case MEDIUM -> 1.0; // Standard-Geschwindigkeit
//            case HARD -> 1.2;    // Gegner sind 20% schneller
//        });

    }

    public static float getEnemyHealthMultiplier() {
     if (currentDifficulty == Difficulty.EASY) {
         return 0.8F;
     }
     if (currentDifficulty == Difficulty.MEDIUM) {
         return 1F;
     }
     if (currentDifficulty == Difficulty.HARD) {
         return 1.2F;
     }
        return (int) switch (currentDifficulty) {
            case EASY -> 0.8;   // Gegner sind 20% weniger hp
            case MEDIUM -> 1.0; // Standard hp
            case HARD -> 1.2;    // Gegner sind 20% mehr hp
        };
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public void setStartingMoney(int startingMoney) {
        this.startingMoney = startingMoney;
    }

    public static void setCurrentDifficulty(Difficulty currentDifficulty) {
        DifficultySettings.currentDifficulty = currentDifficulty;
    }



    public int getEnemySpeed() {
        return enemySpeed;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }
    private static Difficulty currentDifficulty = Difficulty.MEDIUM; // Standard

    public static Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        currentDifficulty = difficulty;
    }
}
