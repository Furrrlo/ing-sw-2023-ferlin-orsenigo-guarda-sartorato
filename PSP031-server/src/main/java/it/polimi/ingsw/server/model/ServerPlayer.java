package it.polimi.ingsw.server.model;

import it.polimi.ingsw.model.*;

import java.util.Objects;
import java.util.function.Function;

public class ServerPlayer implements ServerPlayerView {

    private final String nick;
    private final Shelfie shelfie;
    private final PersonalGoal personalGoal;
    private final Property<Boolean> connected;
    private final Provider<Integer> score;

    /**
     * @implNote requires a scoreProviderFactory so that it can resolve a circular dependency
     *           between player and the provider impl, that most likely needs the player instance
     *           to calculate the score.
     */
    public ServerPlayer(String nick,
                        PersonalGoal personalGoal,
                        Function<ServerPlayer, Provider<Integer>> scoreProviderFactory) {
        this.nick = nick;
        this.personalGoal = personalGoal;
        this.shelfie = new Shelfie();
        this.connected = new SerializableProperty<>(true);
        this.score = scoreProviderFactory.apply(this);
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public Shelfie getShelfie() {
        return shelfie;
    }

    @Override
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    @Override
    public Property<Boolean> connected() {
        return connected;
    }

    @Override
    public Provider<Integer> score() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ServerPlayer that))
            return false;
        return nick.equals(that.nick) &&
                shelfie.equals(that.shelfie) &&
                personalGoal.equals(that.personalGoal) &&
                connected.get().equals(that.connected.get()) &&
                score.get().equals(that.score.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nick, shelfie, personalGoal, connected.get(), score.get());
    }

    @Override
    public String toString() {
        return "ServerPlayer{" +
                "nick='" + nick + '\'' +
                ", shelfie=" + shelfie +
                ", personalGoal=" + personalGoal +
                ", connected=" + connected +
                ", score=" + score +
                '}';
    }
}