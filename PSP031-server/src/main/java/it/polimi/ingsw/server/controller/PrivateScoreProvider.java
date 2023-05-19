package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.Property;
import it.polimi.ingsw.model.Provider;
import it.polimi.ingsw.model.SerializableProperty;
import it.polimi.ingsw.server.model.ServerPlayer;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.function.Consumer;

class PrivateScoreProvider implements Provider<Integer>, Serializable {

    private final Property<Integer> scoreProperty;

    private final ServerPlayer player;

    public PrivateScoreProvider(ServerPlayer player) {
        this.player = player;
        this.scoreProperty = new SerializableProperty<>(calculateScore());
        registerObservers();
    }

    @Serial
    private Object readResolve() throws ObjectStreamException {
        registerObservers();
        return this;
    }

    private void registerObservers() {
        final Consumer<Object> observer = v -> scoreProperty.set(calculateScore());
        player.getShelfie().tiles().forEach(t -> t.tile().registerObserver(observer));
        player.publicScore().registerObserver(observer);
    }

    private int calculateScore() {
        return player.publicScore().get() + getPersonalGoalScore();
    }

    private int getPersonalGoalScore() {
        return switch (player.getShelfie().numTilesOverlappingWithPersonalGoal(player.getPersonalGoal())) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> throw new IllegalStateException("Unexpected value during personalGoal score check");
        };
    }

    @Override
    public Integer get() {
        return scoreProperty.get();
    }

    @Override
    public void registerObserver(Consumer<? super Integer> o) {
        scoreProperty.registerObserver(o);
    }

    @Override
    public void registerWeakObserver(Consumer<? super Integer> o) {
        scoreProperty.registerWeakObserver(o);
    }

    @Override
    public void unregisterObserver(Consumer<? super Integer> o) {
        scoreProperty.unregisterObserver(o);
    }
}