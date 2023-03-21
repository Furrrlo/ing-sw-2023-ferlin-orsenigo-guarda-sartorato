package it.polimi.ingsw.model;
import java.util.function.Consumer;

/**
 * 
 */
public interface Provider<T> {

    T get();

    /**
     * @param o 
     * @return
     */
    public void registerObserver(Consumer<T> o);

    /**
     * @param o 
     * @return
     */
    void unregisterObserver(Consumer<T> o);

}