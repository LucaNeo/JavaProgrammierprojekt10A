package src;

public class GridData {
        int rows;
        int cols;
        boolean[][] placeableTiles;

        public GridData(int rows, int cols, boolean[][] tiles) {
            this.rows = rows;
            this.cols = cols;
            this.placeableTiles = tiles;
        }


}
