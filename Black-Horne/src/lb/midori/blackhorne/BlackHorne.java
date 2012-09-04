package lb.midori.blackhorne;

import lb.midori.blackhorne.builders.GroundBuilder;
import lb.midori.blackhorne.builders.PlayerBuilder;
import lb.midori.blackhorne.components.Collidable;
import lb.midori.blackhorne.components.Renderable;
import lb.midori.blackhorne.constants.CConstants;

import com.apollo.Entity;
import com.apollo.components.Transform;
import com.apollo.managers.RenderManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BlackHorne implements ApplicationListener {

	private static final int VIRTUAL_WIDTH = 1920;
	private static final int VIRTUAL_HEIGHT = 1080;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
	
	

	private Camera camera;
	private Rectangle viewport;
	private Rectangle oldViewport;
	private SpriteBatch sb;

	private Texture playerTexture;
	private Texture groundTexture;
	private Texture backgroundTexture;

	private RenderManager<SpriteBatch> renderManager;

	private boolean doSleep;

	com.apollo.World worldA;
	com.badlogic.gdx.physics.box2d.World worldB;

	PlayerBuilder playerBuilder;
	GroundBuilder groundBuilder;

	@Override
	public void create() {
		sb = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		viewport = new Rectangle(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		initTextures();
		initPhysics();

		worldA = new com.apollo.World(worldB);
		renderManager = new RenderManager<SpriteBatch>(sb);
		worldA.setManager(renderManager);

		initBuilders();
		initEntities();
	}

	public void initTextures() {

		playerTexture = new Texture(Gdx.files.internal("data/playerWL1.png"));
		playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		backgroundTexture = new Texture(Gdx.files.internal("data/moon.jpg"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		groundTexture = new Texture(Gdx.files.internal("data/rocks.png"));
		groundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public void initPhysics() {
		Vector2 gravity = new Vector2(0.0f, -10.0f);
		doSleep = true;

		worldB = new World(gravity, doSleep);

		ContactListener contactListener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				Collidable collisionComponent = (Collidable) contact.getFixtureA().getBody().getUserData();
				Collidable collidedBodyComponent = (Collidable) contact.getFixtureB().getBody().getUserData();
				collisionComponent.Collided(collidedBodyComponent);
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
			}

		};
		worldB.setContactListener(contactListener);
	}

	public void initBuilders() {

		playerBuilder = new PlayerBuilder();
		groundBuilder = new GroundBuilder();
	}

	public void initEntities() {

		playerBuilder.buildEntity(worldA, worldB, playerTexture, 180f, 300f, 0.5f, 0.1f);
		playerBuilder.buildEntity(worldA, worldB, playerTexture, 200f, 600f, 0.5f, 0.1f);
		groundBuilder.buildEntity(worldA, worldB, groundTexture, 200f, 100f);
	}

	@Override
	public void dispose() {
		sb.dispose();
		playerTexture.dispose();
		groundTexture.dispose();
		backgroundTexture.dispose();
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.view.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		camera.projection.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
		worldB.step(1 / 60f, 6, 2);
		worldA.update(1 / 60f);

		sb.begin();
		renderManager.render(sb);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {

		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		if (aspectRatio > ASPECT_RATIO) {
			scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		} else if (aspectRatio < ASPECT_RATIO) {

			scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}

		float w = (float) VIRTUAL_WIDTH * scale;
		float h = (float) VIRTUAL_HEIGHT * scale;
		oldViewport = viewport;
		viewport = new Rectangle(crop.x, crop.y, w, h);
		camera.translate(-viewport.x + oldViewport.x, -viewport.y + oldViewport.y, 0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
