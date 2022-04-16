package page.rightshift.tilegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public class UIManager {
    private final Label posLabel;
    private final Label weaponLabel;
    private final Label hitpointLabel;
    private final Label goldLabel;

    private BitmapFont font;
    private final SpriteBatch batch;
    private String posLabelString;

    public Stage stage;

    void update(@NotNull Player player) {
        posLabelString = "Pos: (" + (int)player.pos.x + ", " + (int)player.pos.y + ")";

        posLabel.setText(posLabelString);
        weaponLabel.setText("Weapon: " + player.weapon.name + " (WP: " + player.weapon.weaponPower + ")");
        hitpointLabel.setText("Hitpoints: " + player.hitpoints);
        goldLabel.setText("Gold in wallet: " + player.gold);

        batch.begin();
        stage.draw();
        batch.end();
    }

    void dispose() {
        stage.dispose();
        font.dispose();
    }

    UIManager() {
        stage = new Stage();
        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        batch = new SpriteBatch();

        posLabelString = "Pos: (0, 0)";

        font = new BitmapFont();

        style.font = font;
        posLabel = new Label(posLabelString, style);
        weaponLabel = new Label("", style);
        hitpointLabel = new Label("Hitpoints: 10", style);
        goldLabel = new Label("Gold in wallet: 10", style);

        posLabel.setPosition(0, Gdx.graphics.getHeight() - 24);
        hitpointLabel.setPosition(0, Gdx.graphics.getHeight() - 48);
        weaponLabel.setPosition(0, Gdx.graphics.getHeight() - 64);
        goldLabel.setPosition(0, Gdx.graphics.getHeight() - 96);

        stage.addActor(posLabel);
        stage.addActor(weaponLabel);
        stage.addActor(hitpointLabel);
        stage.addActor(goldLabel);
    }
}
