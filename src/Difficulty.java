package src;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}
class DifficultySettings {
    private static Difficulty currentDifficulty = Difficulty.MEDIUM; // Standard

    public DifficultySettings(Difficulty difficulty) {
    }

    public static float getEnemySpeedMultiplier() {
        if (currentDifficulty == Difficulty.EASY) {
            return 0.9F;
        } else if (currentDifficulty == Difficulty.MEDIUM) {
            return 1F;
        } else if (currentDifficulty == Difficulty.HARD) {
            return 1.2F;
        } else {
            return 1.0F;
        }
    }

    public static float getEnemyHealthMultiplier() {
        if (currentDifficulty == Difficulty.EASY) {
            return 0.8F;
        } else if (currentDifficulty == Difficulty.MEDIUM) {
            return 1.0F;
        } else if (currentDifficulty == Difficulty.HARD) {
            return 1.2F;
        } else {
            return 1.0F;
        }
    }

    public static void setCurrentDifficulty(Difficulty currentDifficulty) {
        DifficultySettings.currentDifficulty = currentDifficulty;
    }

    public static Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }
}
