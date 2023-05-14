package it.polimi.ingsw.client.javafx;

import it.polimi.ingsw.model.PersonalGoalView;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PersonalGoalComponent extends ImageButton {

    public PersonalGoalComponent(PersonalGoalView personalGoal) {
        var img = new Image(FxResources.getResourceAsStream(personalGoal.getIndex() == 0
                ? "assets/personal goal cards/Personal_Goals.png"
                : "assets/personal goal cards/Personal_Goals" + (personalGoal.getIndex() + 1) + ".png"));
        setImage(img);

        if (Platform.isSupported(ConditionalFeature.SHAPE_CLIP)) {
            clipProperty().bind(layoutBoundsProperty().map(bounds -> {
                var radius = Math.min(20, 20 * Math.min(bounds.getWidth() / 140d, bounds.getHeight() / 210d));
                Rectangle clip = new Rectangle(bounds.getWidth(), bounds.getHeight());
                clip.setArcWidth(radius);
                clip.setArcHeight(radius);
                return clip;
            }));
        }

        if (Platform.isSupported(ConditionalFeature.EFFECT))
            setEffect(new DropShadow(5, Color.WHITE));
    }
}