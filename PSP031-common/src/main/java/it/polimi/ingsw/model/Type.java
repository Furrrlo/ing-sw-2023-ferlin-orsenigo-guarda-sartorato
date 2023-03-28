package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.ShelfieView.*;

public enum Type implements Serializable {
    SIX_COUPLES {
        final int[][] checked = new int[ROWS][COLUMNS];

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int r = 0; r < ROWS - 1; r++) {
                for (int c = 0; c < COLUMNS - 1; c++) {
                    if (shelfie.tile(r + 1, c).equals(shelfie.tile(r, c)) &&
                            checked[r][c] == 0 && checked[r + 1][c] == 0) {
                        count++;
                        checked[r][c] = 1;
                        checked[r + 1][c] = 1;
                    }
                    if (shelfie.tile(r, c + 1).equals(shelfie.tile(r, c)) &&
                            checked[r][c] == 0 && checked[r + 1][c] == 0) {
                        count++;
                        checked[r][c] = 1;
                        checked[r][c + 1] = 1;
                    }
                }
            }
            return count >= 6;
        }
    },
    ALL_CORNERS {
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            return shelfie.tile(0, 0).equals(shelfie.tile(0, COLUMNS - 1)) &&
                    shelfie.tile(0, COLUMNS - 1).equals(shelfie.tile(ROWS - 1, 0)) &&
                    shelfie.tile(ROWS - 1, 0).equals(shelfie.tile(ROWS - 1, COLUMNS - 1));
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
                        if (getCheckedForTiles(shelfie, r, c, checked) >= 4)
                            count++;
                    }
                }
            }
            return count >= 4;
        }

        /**
         * @param shelfie
         * @return number of reached tiles and checks the inspected ones
         */
        public int getCheckedForTiles(Shelfie shelfie, int row, int col, int[][] checked) {
            List<Index> indexes = new ArrayList<>();
            indexes.add(new Index(row, col));
            checked[row][col] = 1;
            int prevSize = 0;
            do {
                prevSize = indexes.size();
                for (int i = 0; i < indexes.size(); i++) {
                    if (indexes.get(i).r < ROWS - 2
                            && Objects.equals(shelfie.tile(indexes.get(i).r + 1, indexes.get(i).c),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r + 1, indexes.get(i).c))) {
                        indexes.add(new Index(indexes.get(i).r + 1, indexes.get(i).c));
                        checked[indexes.get(i).r + 1][indexes.get(i).c] = 1;
                    }
                    if (indexes.get(i).c < COLUMNS - 2
                            && Objects.equals(shelfie.tile(indexes.get(i).r, indexes.get(i).c + 1),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r, indexes.get(i).c + 1))) {
                        indexes.add(new Index(indexes.get(i).r, indexes.get(i).c + 1));
                        checked[indexes.get(i).r][indexes.get(i).c + 1] = 1;
                    }
                    if (indexes.get(i).r > 0
                            && shelfie.tile(indexes.get(i).r - 1, indexes.get(i).c) == shelfie.tile(indexes.get(i).r,
                                    indexes.get(i).c)
                            && !indexes.contains(new Index(indexes.get(i).r - 1, indexes.get(i).c))) {
                        indexes.add(new Index(indexes.get(i).r - 1, indexes.get(i).c));
                        checked[indexes.get(i).r - 1][indexes.get(i).c] = 1;
                    }
                    if (indexes.get(i).c > 0
                            && Objects.equals(shelfie.tile(indexes.get(i).r, indexes.get(i).c - 1),
                                    shelfie.tile(indexes.get(i).r, indexes.get(i).c))
                            && !indexes.contains(new Index(indexes.get(i).r, indexes.get(i).c - 1))) {
                        indexes.add(new Index(indexes.get(i).r, indexes.get(i).c - 1));
                        checked[indexes.get(i).r][indexes.get(i).c - 1] = 1;
                    }
                }
            } while (indexes.size() > prevSize);
            return indexes.size();
        }
    },
    TWO_SQUARES {
        /**
         *
         * @param shelfie
         * @return the number of existing squares of given color in a given shelfie
         */
        public int numSquares(Shelfie shelfie) {
            int count = 0;
            int[][] checked = new int[ROWS][COLUMNS];

            for (int r = 0; r < ROWS - 1; r++) {
                for (int c = 0; c < COLUMNS - 1; c++) {
                    if (checked[r][c] == 0 && checked[r + 1][c] == 0 && checked[r][c + 1] == 0 && checked[r + 1][c + 1] == 0) {
                        if (shelfie.tile(r + 1, c).equals(shelfie.tile(r, c)) &&
                                shelfie.tile(r, c + 1).equals(shelfie.tile(r, c)) &&
                                shelfie.tile(r + 1, c + 1).equals(shelfie.tile(r, c))) {
                            count++;
                            checked[r][c] = 1;
                            checked[r + 1][c] = 1;
                            checked[r][c + 1] = 1;
                            checked[r + 1][c + 1] = 1;
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
         * @param shelfie : shelfie passed as parameter
         * @param c : index of column passed as parameter
         * @return how many different colors are present in a given column of a given shelfie
         */
        public int numColorsForColumn(Shelfie shelfie, int c) {
            List<Color> colors = new ArrayList<Color>();
            for (int r = 0; r < ROWS; r++) {
                if (shelfie.tile(r, c).get() != null && !colors.contains(shelfie.tile(r, c).get().getColor()))
                    colors.add(shelfie.tile(r, c).get().getColor());
            }
            return colors.size();
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int c = 0; c < COLUMNS; c++) {
                if (numColorsForColumn(shelfie, c) <= 3)
                    count++;
            }
            return count >= 3;
        }
    },
    EIGHT_EQUAL_TILES {
        /**
         *
         * @param shelfie
         * @param color
         * @return how many tiles of a given color are present in the shelfie
         */
        public int equalColoredTiles(Shelfie shelfie, Color color) {
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    if (shelfie.tile(r, c).get().getColor().equals(color))
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
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            for (int r = 0; r < ROWS - 4; r++) {
                if (Objects.equals(shelfie.tile(r, 0), shelfie.tile(r + 1, 1)) &&
                        shelfie.tile(r + 1, 1).equals(shelfie.tile(r + 2, 2)) &&
                        shelfie.tile(r + 2, 2).equals(shelfie.tile(r + 3, 3)) &&
                        shelfie.tile(r + 3, 3).equals(shelfie.tile(r + 4, 4)))
                    return true;
                if (Objects.equals(shelfie.tile(r, 4), shelfie.tile(r + 1, 3)) &&
                        shelfie.tile(r + 1, 3).equals(shelfie.tile(r + 2, 2)) &&
                        shelfie.tile(r + 2, 2).equals(shelfie.tile(r + 3, 1)) &&
                        shelfie.tile(r + 3, 1).equals(shelfie.tile(r + 4, 0)))
                    return true;
            }
            return false;
        }
    },
    FOUR_ROWS {
        //SIAMO ARRIVATI QUI
        /**
         *
         * @param shelfie
         * @param r
         * @return how many colors are present in a given row of a given shelfie
         */
        public int numColorsForRow(Shelfie shelfie, int r) {
            List<Color> count = new ArrayList<Color>();
            for (int c = 0; c < COLUMNS; c++) {
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
            if (count >= 4)
                return true;
            else
                return false;
        }
    },
    TWO_ALL_DIFF_COLUMNS {
        /**
         * @param shelfie : shelfie passed as parametre
         * @param c : index of column passed as parametre
         * @return true if the tiles of a given column of a given shelfie are all different, otherwise returns false
         */
        public boolean isDifferentColumn(Shelfie shelfie, int c) {
            for (int r = 0; r < ROWS; r++) {
                for (int j = r + 1; j < ROWS; j++)
                    if (shelfie.tile(r, c).equals(shelfie.tile(j, c)))
                        return false;
            }
            return true;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int c = 0; c < COLUMNS; c++) {
                if (isDifferentColumn(shelfie, c))
                    count++;
            }
            return count >= 2;
        }
    },
    TWO_ALL_DIFF_ROWS {
        /**
         *
         * @param shelfie
         * @param r
         * @return true if the tiles of a given row of a given shelfie are all different, otherwise return false
         */
        public boolean isDifferentRow(Shelfie shelfie, int r) {
            for (int c = 0; c < COLUMNS; c++) {
                for (int j = c + 1; j < COLUMNS; j++) {
                    if (shelfie.tile(r, c).equals(shelfie.tile(r, j)))
                        return false;
                }
            }
            return true;
        }

        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                if (isDifferentRow(shelfie, r))
                    count++;
            }
            if (count >= 2)
                return true;
            else
                return false;
        }
    },
    CROSS {
        @Override
        public boolean checkCommonGoal(Shelfie shelfie) {
            for (int r = 0; r < ROWS - 2; r++) {
                for (int c = 0; c < COLUMNS - 2; c++) {
                    if (shelfie.tile(r, c).equals(shelfie.tile(r + 2, c)) &&
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
            if (shelfie.tile(0, 0).get() != null && shelfie.tile(1, 0).get() == null &&
                    shelfie.tile(1, 1).get() != null && shelfie.tile(2, 1).get() == null &&
                    shelfie.tile(2, 2).get() != null && shelfie.tile(3, 2).get() == null &&
                    shelfie.tile(3, 3) != null && shelfie.tile(4, 3) == null &&
                    shelfie.tile(4, 4) != null)
                return true;
            if (shelfie.tile(0, 4) != null && shelfie.tile(1, 4) == null &&
                    shelfie.tile(1, 3) != null && shelfie.tile(2, 3) == null &&
                    shelfie.tile(2, 2) != null && shelfie.tile(3, 2) == null &&
                    shelfie.tile(3, 1) != null && shelfie.tile(4, 1) == null &&
                    shelfie.tile(4, 0) != null)
                return true;
            else
                return false;
        }
    };

    /**
     * @return true if the common goal of given type is achieved
     **/
    public abstract boolean checkCommonGoal(Shelfie shelfie);
}