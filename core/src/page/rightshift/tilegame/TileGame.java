package page.rightshift.tilegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	
	Texture playerTexture;

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	TiledMapTileLayer.Cell currentTileCell;

	Player player;
	UIManager uiManager;

	public TileGame(String[] arg) {
		if(arg.length > 0) {
			if (arg[0] == "--debug") {
				DebugStuff.enableDebug();
			}
		}
	}

	public void handleScreenChange() {
		float oldPosX = player.screenPos.x;
		float oldPosY = player.screenPos.y;

		player.screenPos.x = (float) Math.floor(player.pos.x / 16);
		player.screenPos.y = (float) Math.floor(player.pos.y / 12);

		if(oldPosX < player.screenPos.x) {
			camera.translate(512,0);
			oldPosX = player.screenPos.x;
		}

		if(oldPosX > player.screenPos.x) {
			camera.translate(-512,0);
			oldPosX = player.screenPos.x;
		}

		if(oldPosY < player.screenPos.y) {
			camera.translate(0,384);
			oldPosY = player.screenPos.y;
		}

		if(oldPosY > player.screenPos.y) {
			camera.translate(0,-384);
			oldPosY = player.screenPos.y;
		}
	}

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("maps/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
		playerTexture = new Texture("character.png");
		camera.translate(-256, -192);

		uiManager = new UIManager();
		player = new Player();
		camera.zoom = (float)0.5;
	}

	@Override
	public void render () {
		if(player.living == false) {
			System.exit(0);
		}

		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		uiManager.update(player);
		player.draw(playerTexture, camera);

		tiledMap.getLayers().get("base");
	}

	@Override
	public void dispose() {
		playerTexture.dispose();
		batch.dispose();

		tiledMap.dispose();
		uiManager.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

		if(keycode == Input.Keys.A) {
			if(CollisionHandler.isMoveAllowed(1, layer, player)) {
				player.pos.x -= 1;
				handleScreenChange();
			}
		}
		if(keycode == Input.Keys.D) {
			if(CollisionHandler.isMoveAllowed(2, layer, player)) {
				player.pos.x += 1;
				handleScreenChange();
			}
		}
		if(keycode == Input.Keys.S) {
			if(CollisionHandler.isMoveAllowed(4, layer, player)) {
				player.pos.y -= 1;
				handleScreenChange();
			}
		}
		if(keycode == Input.Keys.W) {
			if(CollisionHandler.isMoveAllowed(3, layer, player)) {
				player.pos.y += 1;
				handleScreenChange();
			}
		}

		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());

		TileEventHandler.fightEnemy(
				player,
				(TiledMapTileLayer) tiledMap.getLayers().get("Enemy Layer"),
				layer
		);

		TileEventHandler.interactWithTile(
				player,
				(TiledMapTileLayer) tiledMap.getLayers().get("Functional Layer")
		);

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
