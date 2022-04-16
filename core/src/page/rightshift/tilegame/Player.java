package page.rightshift.tilegame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;
import page.rightshift.tilegame.weapons.DullDagger;
import page.rightshift.tilegame.weapons.WeaponMelee;

public class Player {
    public Vector2 pos;
    public Vector2 selectedTile;
    public Vector2 screenPos;
    public int buildType;
    private final SpriteBatch batch;

    // JTGRPG stuff
    public WeaponMelee weapon;
    public int hitpoints;
    public boolean living;
    public int gold;

    public Vector2 toPixelPos() {
        Vector2 v = new Vector2();

        v.x = this.pos.x * 32;
        v.y = this.pos.y * 32;

        return v;
    }

    public void draw(Texture t, @NotNull OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(t, this.toPixelPos().x, this.toPixelPos().y);
        batch.end();
    }

    Player() {
        pos = new Vector2();
        pos.x = 1;
        pos.y = 1;
        screenPos = new Vector2();
        screenPos.x = 0;
        screenPos.y = 0;
        selectedTile = new Vector2();
        batch = new SpriteBatch();

        weapon = new DullDagger();
        hitpoints = 10;
        living = true;
        gold = 10;
    }
}
