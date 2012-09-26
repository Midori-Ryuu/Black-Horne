package lb.midori.blackhorne.screens;

import lb.midori.blackhorne.builders.GroundBuilder;
import lb.midori.blackhorne.builders.PlayerBuilder;
import lb.midori.blackhorne.components.Collidable;
import lb.midori.blackhorne.components.Renderable;
import lb.midori.blackhorne.controllers.PlayerController;

import com.apollo.Entity;
import com.apollo.managers.RenderManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class GamePlay implements Screen, InputProcessor {

	private static final int VIRTUAL_WIDTH = 2000;
	private static final int VIRTUAL_HEIGHT = 1000;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

	private Camera camera;
	private Rectangle viewport;
	private Rectangle oldViewport;
	private SpriteBatch sb;

	private Texture playerTexture;
	private Texture groundTexture;
	private Texture backgroundTexture;
	
	private Animation[] playerAnimations;
	private Animation [] groundAnimation;

	private RenderManager<SpriteBatch> renderManager;
	
	Box2DDebugRenderer debugRenderer;

	private boolean doSleep;

	com.apollo.World worldA;
	com.badlogic.gdx.physics.box2d.World worldB;

	PlayerBuilder playerBuilder;
	GroundBuilder groundBuilder;

	PlayerController playerController;

	Entity ground;
	Entity player;
	Collidable collidable;

	private int width, height;

	public void initTextures() {

		playerTexture = new Texture(Gdx.files.internal("data/playerS1.png"));
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

		player = playerBuilder.buildEntity(worldA, worldB, playerAnimations, 200f, 200f, 1f, 3.5f);
		
		playerBuilder.buildEntity(worldA, worldB, playerAnimations, 600f, 200f, 1f, 3.5f);
		
		playerController = new PlayerController(player);
		collidable = player.getComponent(Collidable.class);
		// playerBuilder.buildEntity(worldA, worldB, playerTexture, 180f, 300f,
		// 0.5f, 0.1f);
		
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 150f,80f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 250f,70f);		
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 350f, 50f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 450f, 50f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 550f, 50f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 650f, 50f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 750f, 50f);
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 850f, 50f);
		
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 50f,170f);		
		groundBuilder.buildEntity(worldA, worldB, groundAnimation, 950f, 150f);

	}

	public void initAnimations() {
		
		Texture walkSheet; 
		TextureRegion[] walkLeftFrames;
		TextureRegion[] walkRightFrames;
		TextureRegion[] standFrames;
		
		TextureRegion groundFrame;
		
		final int FRAME_COLS = 4; 
		final int FRAME_ROWS = 4;
		//float stateTime=0;
		
		walkSheet = new Texture(Gdx.files.internal("data/animation_sheet.png"));
		walkSheet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, (walkSheet.getWidth() / FRAME_COLS), (walkSheet.getHeight() / FRAME_ROWS)); 
		Animation walkLeftAnimation;
		Animation walkRightAnimation;
		Animation standAnimation;		
		
		
		groundFrame = new TextureRegion(groundTexture);
		walkLeftFrames = new TextureRegion[FRAME_COLS];
		walkRightFrames = new TextureRegion[FRAME_COLS];
		standFrames = new TextureRegion[1];
		standFrames[0] = tmp[0][0];
		//int index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			
			walkLeftFrames[i] = tmp[i][1];
			walkRightFrames[i] = tmp[i][3];
			
		}
		
		walkLeftAnimation = new Animation(0.025f, walkLeftFrames); // #11
		walkRightAnimation = new Animation(0.025f, walkRightFrames); // #11
		standAnimation = new Animation(0.025f, standFrames); // #11
		
	    playerAnimations = new Animation[3];
		playerAnimations[0]=standAnimation;
		playerAnimations[1]=walkLeftAnimation;
		playerAnimations[2]=walkRightAnimation;
		
		groundAnimation = new Animation[2];
		groundAnimation[0] = new Animation(0.025f,groundFrame);
		groundAnimation[1] = new Animation(0.025f,groundFrame);
		//stateTime = 0f;
	}

	@Override
	public void show() {
		sb = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		viewport = new Rectangle(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		initTextures();
		initAnimations();
		initPhysics();

		worldA = new com.apollo.World(worldB);
		renderManager = new RenderManager<SpriteBatch>(sb);
		worldA.setManager(renderManager);

		initBuilders();
		initEntities();

	
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		 debugRenderer = new Box2DDebugRenderer( true, true, true, true );
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.view.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		camera.projection.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

		playerController.update(delta);
		worldB.step(delta, 6, 2);
		worldA.update(delta);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); 
				
		sb.begin();
		sb.draw(backgroundTexture, camera.position.x - backgroundTexture.getWidth() / 2, camera.position.y - backgroundTexture.getHeight() / 2);
		renderManager.render(sb);
		sb.end();
		//debugRenderer.render( worldB,camera.combined);
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
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	// * InputProcessor methods ***************************//

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			playerController.leftPressed();
			// System.out.println("blablabla");
		}
		if (keycode == Keys.RIGHT)
			playerController.rightPressed();
		if (keycode == Keys.Z)
			playerController.jumpPressed();
		if (keycode == Keys.X)
			playerController.firePressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			playerController.leftReleased();
		if (keycode == Keys.RIGHT)
			playerController.rightReleased();
		if (keycode == Keys.Z)
			playerController.jumpReleased();
		if (keycode == Keys.X)
			playerController.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			playerController.leftPressed();
		}
		if (x > width / 2 && y > height / 2) {
			playerController.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			playerController.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
			playerController.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
