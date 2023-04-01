package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.ShelfieView.*;

public enum Type implements Serializable {
    SIX_COUPLES {
        /**
         * Returns number of couples in given shelfie and marks them with progressive number representing the
         * order in which they have been identified by the program
         **/
        public int numCouples(Shelfie shelfie) {
            int[][] checked = new int[ROWS][COLUMNS];
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    if (c < COLUMNS - 1 && shelfie.tile(r, c).get() != null
                            && Objects.equals(shelfie.tile(r, c + 1).get(), shelfie.tile(r, c).get()) &&
                            checked[r][c] == 0 && checked[r][c + 1] == 0) {
                        count++;
                        checked[r][c] = count;
                        checked[r][c + 1] = count;
                    }
                    if (r < ROWS - 1 && shelfie.tile(r, c).get() != null
                            && Objects.equals(shelfie.tile(r + 1, c).get(), shelfie.tile(r, c).get()) &&
                            checked[r][c] == 0 && checked[r + 1][c] == 0) {
                        count++;
                        checked[r][c] = count;
                        checked[r + 1][c] = count;
                    }
                }
            }
            return count;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            return numCouples(shelfie) >= 6;
        }
    },
    ALL_CORNERS {
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            return shelfie.tile(0, 0).get() != null &&
                    Objects.equals(shelfie.tile(0, 0).get(), shelfie.tile(0, COLUMNS - 1).get()) &&
                    Objects.requireNonNull(shelfie.tile(0, COLUMNS - 1).get()).equals(shelfie.tile(ROWS - 1, 0).get()) &&
                    Objects.requireNonNull(shelfie.tile(ROWS - 1, 0).get()).equals(shelfie.tile(ROWS - 1, COLUMNS - 1).get());
        }
    },
    FOUR_QUADRIPLETS {
        record Index(int r, int c) {
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            int[][] checked = new int[ROWS][COLUMNS];
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    if (checked[r][c] == 0 && shelfie.tile(r, c).get() != null) {
                        if (getQuadrupletCheck(shelfie, r, c, checked, count + 1) >= 4)
                            count++;
                    }
                }
            }
            return count >= 4;
        }

        /**
         * Returns the number of adjacent tiles to the one specified by given row and col, marking them if more than 4
         * with the number of quadriplet they belong to (specified by parameter marker), if not, marks them with -1
         * to ensure they don't get inspected further
         **/
        public int getQuadrupletCheck(Shelfie shelfie, int row, int col, int[][] checked, int marker) {
            List<Index> indexes = new ArrayList<>();
            indexes.add(new Index(row, col));
            checked[row][col] = marker;
            int prevSize = 0;
            do {
                prevSize = indexes.size();
                for (int i = 0; i < indexes.size(); i++) {
                    if (indexes.get(i).r < ROWS - 1
                            && Objects.equals(shelfie.tile(indexes.get(i).r + 1, indexes.get(i).c),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r + 1, indexes.get(i).c))) {
                        indexes.add(new Index(indexes.get(i).r + 1, indexes.get(i).c));
                        checked[indexes.get(i).r + 1][indexes.get(i).c] = marker;
                    }
                    if (indexes.get(i).c < COLUMNS - 1
                            && Objects.equals(shelfie.tile(indexes.get(i).r, indexes.get(i).c + 1),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r, indexes.get(i).c + 1))) {
                        indexes.add(new Index(indexes.get(i).r, indexes.get(i).c + 1));
                        checked[indexes.get(i).r][indexes.get(i).c + 1] = marker;
                    }
                    if (indexes.get(i).r > 0
                            && shelfie.tile(indexes.get(i).r - 1, indexes.get(i).c) == shelfie.tile(indexes.get(i).r,
                                    indexes.get(i).c)
                            && !indexes.contains(new Index(indexes.get(i).r - 1, indexes.get(i).c))) {
                        indexes.add(new Index(indexes.get(i).r - 1, indexes.get(i).c));
                        checked[indexes.get(i).r - 1][indexes.get(i).c] = marker;
                    }
                    if (indexes.get(i).c > 0
                            && Objects.equals(shelfie.tile(indexes.get(i).r, indexes.get(i).c - 1),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r, indexes.get(i).c - 1))) {
                        indexes.add(new Index(indexes.get(i).r, indexes.get(i).c - 1));
                        checked[indexes.get(i).r][indexes.get(i).c - 1] = marker;
                    }
                }
            } while (indexes.size() > prevSize);
            if (indexes.size() < 4)
                for (Index i : indexes)
                    checked[i.r][i.c] = -1;
            return indexes.size();
        }
    },
    TWO_SQUARES {
        /**
         * Returns the number of existing squares in a given shelfie, and marks the existing ones with
         * progressive numbers according to the order they have been found
         **/
        public int numSquares(Shelfie shelfie) {
            int count = 0;
            int[][] checked = new int[ROWS][COLUMNS];

            for (int r = 0; r < ROWS - 1; r++) {
                for (int c = 0; c < COLUMNS - 1; c++) {
                    if (checked[r][c] == 0 && checked[r + 1][c] == 0 && checked[r][c + 1] == 0 && checked[r + 1][c + 1] == 0) {
                        if (shelfie.tile(r, c).get() != null &&
                                Objects.equals(shelfie.tile(r + 1, c).get(), shelfie.tile(r, c).get()) &&
                                Objects.equals(shelfie.tile(r, c + 1).get(), shelfie.tile(r, c).get()) &&
                                Objects.equals(shelfie.tile(r + 1, c + 1).get(), shelfie.tile(r, c).get())) {
                            count++;
                            checked[r][c] = count;
                            checked[r + 1][c] = count;
                            checked[r][c + 1] = count;
                            checked[r + 1][c + 1] = count;
                        }
                    }
                }
            }
            return count;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            return numSquares(shelfie) >= 2;
        }
    },
    THREE_COLUMNS {
        /**
         * Returns the number of different colors present in a given column c of a shelfie, and if the number of
         * colors is less than 4, it marks the columns with a progressive number according to the order they have
         * been found
         */
        public int numColorsForColumn(Shelfie shelfie, int c, int[][] checked, int marker) {
            List<Color> colors = new ArrayList<Color>();
            boolean fullColumn = true;

            for (int r = 0; r < ROWS && fullColumn; r++) {
                if (shelfie.tile(r, c).get() != null
                        && !colors.contains(Objects.requireNonNull(shelfie.tile(r, c).get()).getColor()))
                    colors.add(Objects.requireNonNull(shelfie.tile(r, c).get()).getColor());
                if (shelfie.tile(r, c).get() == null)
                    fullColumn = false;
            }
            if (colors.size() <= 3 && fullColumn)
                for (int r = 0; r < ROWS; r++) {
                    checked[r][c] = marker;
                }
            return fullColumn ? colors.size() : COLUMNS;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            int[][] checked = new int[ROWS][COLUMNS];
            for (int c = 0; c < COLUMNS; c++) {
                if (numColorsForColumn(shelfie, c, checked, count + 1) <= 3)
                    count++;
            }
            return count >= 3;
        }
    },
    EIGHT_EQUAL_TILES {
        /** Returns the amount of tiles of a given color present in the shelfie */
        public int equalColoredTiles(Shelfie shelfie, Color color) {
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    if (shelfie.tile(r, c).get() != null
                            && Objects.requireNonNull(shelfie.tile(r, c).get()).getColor().equals(color))
                        count++;
                }
            }
            return count;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            for (Color c : Color.values()) {
                if (equalColoredTiles(shelfie, c) >= 8)
                    return true;
            }
            return false;
        }
    },
    DIAGONAL {
        /**
         * Returns true if the diagonal in the shelfie built from tile in position r,c is made by the same colored
         * tiles, and it's made by exactly 5 tiles, otherwise returns false
         */
        public boolean checkDiagonal(Shelfie shelfie, int r, int c) {
            boolean found = true;
            if (r >= ROWS - 4 || !(c == 0 || c == COLUMNS - 1) || shelfie.tile(r, c).get() == null)
                return false;
            if (c == 0)
                for (int i = 0; i < 4 && found; i++) {
                    if (!Objects.equals(shelfie.tile(r, c).get(), shelfie.tile(r + i, c + i).get()))
                        found = false;
                }
            if (c == COLUMNS - 1)
                for (int i = 0; i < 4 && found; i--) {
                    if (!Objects.equals(shelfie.tile(r, c).get(), shelfie.tile(r + i, c + i).get()))
                        found = false;
                }
            return found;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            boolean found = false;
            for (int r = 0; r < ROWS && !found; r++) {
                for (int c = 0; c < COLUMNS && !found; c++) {
                    found = checkDiagonal(shelfie, r, c);
                }
            }
            return found;
        }
    },
    FOUR_ROWS {
        /**
         * Returns the number of different colors present in a given row r of a shelfie, excluding null tiles
         */
        public int numColorsForRow(Shelfie shelfie, int r) {
            List<Color> count = new ArrayList<Color>();
            for (int c = 0; c < COLUMNS; c++) {
                //if there is at least a null tile the row doesn't count because it must be full
                if (shelfie.tile(r, c).get() == null)
                    return ROWS;
                if (!count.contains(shelfie.tile(r, c).get().getColor()))
                    count.add(shelfie.tile(r, c).get().getColor());
            }
            return count.size();
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                if (numColorsForRow(shelfie, r) <= 3)
                    count++;
            }
            return count >= 4;
        }
    },

    TWO_ALL_DIFF_COLUMNS {
        /**
        * Returns the number of different colors present in a given color c of a shelfie, excluding null tiles
        */
        public int numColorsForColumn(Shelfie shelfie, int c) {
            List<Color> count = new ArrayList<Color>();
            for (int r = 0; r < ROWS; r++) {
                if (shelfie.tile(r, c).get() != null && !count.contains(shelfie.tile(r, c).get().getColor()))
                    count.add(shelfie.tile(r, c).get().getColor());
            }
            return count.size();
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int c = 0; c < COLUMNS; c++) {
                if (numColorsForColumn(shelfie, c) == ROWS)
                    count++;
            }
            return count >= 2;
        }
    },
    TWO_ALL_DIFF_ROWS {
        /**
         * Returns the number of different colors present in a given row r of a shelfie, excluding null tiles
         */
        public int numColorsForRow(Shelfie shelfie, int r) {
            List<Color> count = new ArrayList<Color>();
            for (int c = 0; c < COLUMNS; c++) {
                if (shelfie.tile(r, c).get() != null && !count.contains(shelfie.tile(r, c).get().getColor()))
                    count.add(shelfie.tile(r, c).get().getColor());
            }
            return count.size();
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                if (numColorsForRow(shelfie, r) == COLUMNS)
                    count++;
            }
            return count >= 2;
        }
    },
    CROSS {
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            for (int r = 0; r < ROWS - 2; r++) {
                for (int c = 0; c < COLUMNS - 2; c++) {
                    if (shelfie.tile(r, c).get()!=null && 
                        shelfie.tile(r, c).equals(shelfie.tile(r + 2, c)) &&
                        shelfie.tile(r, c).equals(shelfie.tile(r, c + 2)) &&
                        shelfie.tile(r, c).equals(shelfie.tile(r + 1, c + 1)) &&
                        shelfie.tile(r, c).equals(shelfie.tile(r + 2, c + 2)))
                        return true;
                }
            }
            return false;
        }
    },
    TRIANGLE {
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
      
            for(int r=0;r<2;r++){
            if (    shelfie.tile(r+0, 0).get() != null && shelfie.tile(r+1, 0).get() == null &&
                    shelfie.tile(r+1, 1).get() != null && shelfie.tile(r+2, 1).get() == null &&
                    shelfie.tile(r+2, 2).get() != null && shelfie.tile(r+3, 2).get() == null &&
                    shelfie.tile(r+3, 3).get() != null && shelfie.tile(r+4, 3).get() == null &&
                    shelfie.tile(r+4, 4).get() != null)
                return true;}
            for(int r=0;r<2;r++){
            if (    shelfie.tile(r+0, 4).get() != null && shelfie.tile(r+1, 4).get() == null &&
                    shelfie.tile(r+1, 3).get() != null && shelfie.tile(r+2, 3).get() == null &&
                    shelfie.tile(r+2, 2).get() != null && shelfie.tile(r+3, 2).get() == null &&
                    shelfie.tile(r+3, 1).get() != null && shelfie.tile(r+4, 1).get() == null &&
                    shelfie.tile(r+4, 0).get() != null)
                return true;}
            
            return false;

        }
    };

    /**
     * @return true if the common goal of given type is achieved
     **/
    public abstract boolean checkCommonGoal(Shelfie shelfie);
}