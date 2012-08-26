package lb.midori.blackhorne;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BlackHorne implements ApplicationListener {

	private static final int VIRTUAL_WIDTH = 1920;
	private static final int VIRTUAL_HEIGHT = 1080;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
	private static final float bR = 30f;
	private static final float rB = 1/30f;
	private Camera camera;
	private Rectangle viewport;
	private Rectangle oldViewport;
	private SpriteBatch sb;

	private Texture texture;
	private Texture texture2;
	private Texture texture3;

	World world;
	Body groundBody;
	Body body;
	Body body2;
	PolygonShape groundBox;

	@Override
	public void create() {
		sb = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		//texture = new Texture(Gdx.files.internal("data/playerS1.png"));
		texture = new Texture(Gdx.files.internal("data/playerWLx1.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//texture2 = new Texture(Gdx.files.internal("data/moon.jpg"));
		texture2 = new Texture(Gdx.files.internal("data/moonx.jpg"));
		texture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		viewport = new Rectangle(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		texture3 = new Texture(Gdx.files.internal("data/rocks.png"));
		texture3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		initPhysics();
	}

	public void initPhysics() {
		Vector2 gravity = new Vector2(0.0f, -10.0f);
		boolean doSleep = true;

		world = new World(gravity, doSleep);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(200f*rB, 300f*rB);

		groundBody = world.createBody(groundBodyDef);
		groundBox = new PolygonShape();

		groundBox.setAsBox(texture3.getWidth()*rB/2, texture3.getHeight()*rB/2);
		groundBody.createFixture(groundBox, 0.0f);
		// -------------------
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(200*rB, 800*rB);

		body = world.createBody(bodyDef);
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(texture.getWidth()*rB/2, texture.getHeight()*rB/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 1.0f;
		body.createFixture(fixtureDef);
		//----------------------
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyType.DynamicBody;
		bodyDef2.position.set(200*rB, 1300*rB);

		body2 = world.createBody(bodyDef2);
		PolygonShape dynamicBox2 = new PolygonShape();
		dynamicBox2.setAsBox(texture.getWidth()*rB/2, texture.getHeight()*rB/2);

		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = dynamicBox2;
		fixtureDef2.density = 100.0f;
		fixtureDef2.friction = 0.1f;
		body2.createFixture(fixtureDef2);

	}

	@Override
	public void dispose() {
		sb.dispose();
		texture.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.view.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		camera.projection.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

		world.step(1.0f / 60.f, 6, 2);
		Vector2 position = body.getPosition();
		Vector2 position2 = body2.getPosition();
		float angle = body.getAngle();
		//System.out.printf("%4.2f %4.2f %4.2f\n", position.x*bR, position.y*bR, angle);

		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(texture2, camera.position.x - texture2.getWidth() / 2, camera.position.y - texture2.getHeight() / 2);
		sb.draw(texture, position.x*bR - texture.getWidth() / 2, position.y*bR - texture.getHeight() / 2);
		sb.draw(texture, position2.x*bR - texture.getWidth() / 2, position2.y*bR - texture.getHeight() / 2);
		sb.draw(texture3, groundBody.getPosition().x*bR - texture3.getWidth() / 2, groundBody.getPosition().y*bR - texture3.getHeight() / 2);
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

	// @Override
	// public void resize(int width, int height) {
	// float aspectRatio = (float) width / (float) height;
	// float scale = 1f;
	// Vector2 crop = new Vector2(0f, 0f);
	// // if (aspectRatio > ASPECT_RATIO) {
	// //scale = (float) height / (float) VIRTUAL_HEIGHT;
	// crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
	// // } else if (aspectRatio < ASPECT_RATIO) {
	// // scale = (float) width / (float) VIRTUAL_WIDTH;
	// crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
	// // } else {
	// // scale = (float) width / (float) VIRTUAL_WIDTH;
	// // }
	//
	// float w = (float) VIRTUAL_WIDTH * scale;
	// float h = (float) VIRTUAL_HEIGHT * scale;
	// oldViewport = viewport;
	// viewport = new Rectangle(crop.x, crop.y, w, h);
	// System.out.println("new viewport: "+viewport);
	// camera.translate(-viewport.x+oldViewport.x, -viewport.y+oldViewport.y,
	// 0);
	// System.out.println("X: "+(viewport.x-oldViewport.x));
	// System.out.println("Y: "+(viewport.y-oldViewport.y));
	// }

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
