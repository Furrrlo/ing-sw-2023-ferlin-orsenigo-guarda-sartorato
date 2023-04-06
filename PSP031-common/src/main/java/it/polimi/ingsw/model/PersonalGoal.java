package it.polimi.ingsw.model;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PersonalGoal implements PersonalGoalView {

    private final @Nullable Tile[][] personalGoal;
    private final int index;

    public static List<Color[][]> personalGoals = List.of(new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , Color.LIGHTBLUE, null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , Color.ORANGE   , null           , null           , null           },
            new Color[]{null           , null           , null           , Color.YELLOW   , null           },
            new Color[]{null           , null           , null           , null           , Color.GREEN    },
            new Color[]{Color.PINK     , null           , Color.BLUE     , null           , null           },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , null           , null           , Color.BLUE     },
            new Color[]{null           , null           , null           , Color.LIGHTBLUE, null           },
            new Color[]{null           , null           , null           , null           , Color.YELLOW   },
            new Color[]{Color.GREEN    , null           , Color.ORANGE   , null           , null           },
            new Color[]{null           , Color.PINK     , null           , null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            //@formatter:on 
    }, new Color[][] {
            //@formatter:off
            new Color[]{Color.YELLOW   , null           , null           , null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , Color.GREEN    , null           , null           , Color.LIGHTBLUE},
            new Color[]{null           , null           , Color.PINK     , null           , null           },
            new Color[]{Color.BLUE     , null           , null           , Color.ORANGE   , null           }, 
            new Color[]{null           , null           , null           , null           , null           },
            //@formatter:on
    }, new Color[][] {
        //@formatter:off
        new Color[]{null           , null           , null           , null           , null           },
                new Color[]{null           , Color.YELLOW   , Color.GREEN    , null           , null           },
                new Color[]{null           , null           , null           , Color.PINK     , null           },
                new Color[]{Color.LIGHTBLUE, null           , Color.BLUE     , null           , null           },
                new Color[]{null           , null           , null           , null           , null           },
                new Color[]{null           , null           , null           , null           , Color.ORANGE   },
        //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{Color.ORANGE   , null           , null           , Color.GREEN    , null           },
            new Color[]{null           , null           , null           , null           , Color.PINK     },
            new Color[]{null           , Color.BLUE     , Color.YELLOW   , null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , Color.LIGHTBLUE, null           , null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{Color.PINK     , null           , null           , null           , null           },
            new Color[]{null           , Color.ORANGE   , null           , Color.BLUE     , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , null           , null           , Color.YELLOW   , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , null           , Color.LIGHTBLUE, null           , Color.GREEN    },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , Color.YELLOW   , null           , null           },
            new Color[]{null           , null           , null           , null           , Color.ORANGE   },
            new Color[]{Color.LIGHTBLUE, null           , null           , null           , null           },
            new Color[]{null           , Color.PINK     , null           , null           , null           },
            new Color[]{null           , null           , null           , Color.BLUE     , null           },
            new Color[]{Color.GREEN    , null           , null           , null           , null           },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , null           , Color.ORANGE   , null           },
            new Color[]{null           , null           , null           , Color.YELLOW   , null           },
            new Color[]{Color.PINK     , null           , null           , null           , null           },
            new Color[]{null           , null           , Color.LIGHTBLUE, null           , null           },
            new Color[]{null           , Color.GREEN    , null           , null           , null           },
            new Color[]{null           , null           , null           , null           , Color.BLUE     },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{Color.BLUE     , null           , null           , null           , null           },
            new Color[]{null           , Color.LIGHTBLUE, null           , null           , Color.PINK     },
            new Color[]{null           , null           , null           , null           , Color.YELLOW   },
            new Color[]{null           , null           , Color.GREEN    , null           , null           },
            new Color[]{null           , null           , null           , null           , null           },
            new Color[]{null           , null           , Color.ORANGE   , null           , null           },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , null           , Color.PINK     , null           },
            new Color[]{null           , Color.BLUE     , null           , null           , null           },
            new Color[]{null           , null           , null           , Color.GREEN    , null           },
            new Color[]{Color.YELLOW   , null           , null           , null           , null           },
            new Color[]{null           , Color.ORANGE   , null           , null           , null           },
            new Color[]{null           , null           , null           , null           , Color.LIGHTBLUE},
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{null           , null           , null           , Color.LIGHTBLUE, null           },
            new Color[]{null           , null           , null           , null           , Color.GREEN    },
            new Color[]{null           , null           , Color.BLUE     , null           , null           },
            new Color[]{Color.ORANGE   , null           , null           , null           , null           },
            new Color[]{null           , Color.YELLOW   , null           , null           , null           },
            new Color[]{null           , null           , Color.PINK     , null           , null           },
            //@formatter:on
    }, new Color[][] {
            //@formatter:off
            new Color[]{Color.GREEN    , null           , null           , null           , null           },
            new Color[]{null           , null           , null           , null           , Color.ORANGE   },
            new Color[]{null           , null           , null           , Color.LIGHTBLUE, null           },
            new Color[]{null           , null           , Color.BLUE     , null           , null           },
            new Color[]{null           , Color.PINK     , null           , null           , null           },
            new Color[]{null           , null           , Color.YELLOW   , null           , null           },
            //@formatter:on
    });

    @SuppressWarnings("NullAway") // NullAway doesn't support array, see https://github.com/uber/NullAway/labels/jspecify
    public PersonalGoal(@Nullable Tile[][] personalGoal) {
        if (personalGoal.length != ROWS)
            throw new IllegalArgumentException("Provided shelf combination has the wrong row size");
        for (int row = 0; row < personalGoal.length; row++) {
            if (personalGoal[row].length != COLUMNS)
                throw new IllegalArgumentException("Provided shelf combination has the wrong column size at row " + row);
        }
        this.personalGoal = personalGoal;
        this.index = -1;
    }

    public PersonalGoal(int index) {
        this.personalGoal = colorToTiles(personalGoals.get(index));
        this.index = index;
    }

    @Override
    public void printPersonalGoal() {
        Shelfie shelfie = new Shelfie(personalGoals.get(index));
        System.out.println("PERSONAL GOAL");
        shelfie.printColoredShelfie();
    }

    @Override
    public boolean achievedPersonalGoal(Shelfie shelfie) {
        return shelfie.isOverlapping(new Shelfie(personalGoals.get(this.index)));
    }

    @Override
    public void printPersonalGoalOnShelfie(Shelfie shelfie) {
        if (achievedPersonalGoal(shelfie)) {
            int count = 0;
            int[][] checked = new int[ROWS][COLUMNS];
            for (int r = 0; r < ROWS; r++)
                for (int c = 0; c < COLUMNS; c++)
                    if (this.get(r, c) != null)
                        checked[r][c] = count + 1;
            Type.SIX_COUPLES.printCommonGoal(shelfie, checked, "Congratulation you achieved PERSONAL GOAL");
        }
    }

    @Override
    @SuppressWarnings("NullAway") // NullAway doesn't support array, see https://github.com/uber/NullAway/labels/jspecify
    public @Nullable Tile[][] getPersonalGoal() {
        return personalGoal;
    }

    @SuppressWarnings("NullAway") // NullAway doesn't support array, see https://github.com/uber/NullAway/labels/jspecify
    public @Nullable Tile[][] colorToTiles(@Nullable Color[][] tiles) {
        return Arrays.stream(tiles)
                .map(row -> Arrays.stream(row)
                        .map(color -> color == null ? null
                                : new Tile(color))
                        .toArray(Tile[]::new))
                .toArray(Tile[][]::new);
    }

    @Override
    @SuppressWarnings("NullAway") // NullAway doesn't support array, see https://github.com/uber/NullAway/labels/jspecify
    public @Nullable Tile get(int r, int c) {
        return personalGoal[r][c];
    }

    @Override
    @SuppressWarnings("NullAway") // NullAway doesn't support array, see https://github.com/uber/NullAway/labels/jspecify
    public Stream<TileAndCoords<@Nullable Tile>> tiles() {
        return IntStream.range(0, ROWS).boxed().flatMap(row -> IntStream.range(0, COLUMNS).boxed()
                .map(col -> new TileAndCoords<>(personalGoal[row][col], row, col)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PersonalGoal that))
            return false;
        return Arrays.deepEquals(personalGoal, that.personalGoal);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(personalGoal);
    }

    @Override
    public String toString() {
        return "PersonalGoal{" +
                "personalGoal=" + Arrays.toString(personalGoal) +
                '}';
    }
}