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

public class BlackHorne implements ApplicationListener {

	private static final int VIRTUAL_WIDTH = 1024;
	private static final int VIRTUAL_HEIGHT = 640;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

	private Camera camera;
	private Rectangle viewport;
	private Rectangle oldViewport;
	private SpriteBatch sb;
	
	private Texture texture;
	private Sprite sprite;

	@Override
	public void create() {
		// float w = Gdx.graphics.getWidth();
		// float h = Gdx.graphics.getHeight();

		// camera = new OrthographicCamera(1, h / w);
		sb = new SpriteBatch();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		texture = new Texture(Gdx.files.internal("data/playerS1.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		viewport = new Rectangle(0,0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		System.out.println("viewport: "+viewport);
		// TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		// sprite = new Sprite(region);
		// sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		// sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
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
		System.out.println(camera.position);
		camera.update();
	//	if(viewport.x>oldViewport.x)
			
		
		//camera.position.set(viewport.width / 2, viewport.height / 2, 0);
		camera.view.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		camera.projection.setToOrtho2D(viewport.x, viewport.y, viewport.width, viewport.height);
		//camera.apply(Gdx.gl10);
	//	camera.translate(x, y, z)
	//	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+camera.position);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(texture, camera.position.x-texture.getWidth()/2, camera.position.y-texture.getHeight()/2);
		sb.end();
	}

	
	// @Override
	// public void resize(int width, int height) {
	// float aspectRatio = (float) width / (float) height;
	// float scale = 1f;
	// Vector2 crop = new Vector2(0f, 0f);
	// if (aspectRatio > ASPECT_RATIO) {
	// scale = (float) height / (float) VIRTUAL_HEIGHT;
	// crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
	// } else if (aspectRatio < ASPECT_RATIO) {
	// scale = (float) width / (float) VIRTUAL_WIDTH;
	// crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
	// } else {
	// scale = (float) width / (float) VIRTUAL_WIDTH;
	// }
	//
	// float w = (float) VIRTUAL_WIDTH * scale;
	// float h = (float) VIRTUAL_HEIGHT * scale;
	// viewport = new Rectangle(crop.x, crop.y, w, h);
	//
	// }

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
	//	if (aspectRatio > ASPECT_RATIO) {
			//scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
	//	} else if (aspectRatio < ASPECT_RATIO) {
		//	scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
	//	} else {
		//	scale = (float) width / (float) VIRTUAL_WIDTH;
	//	}

		float w = (float) VIRTUAL_WIDTH * scale;
		float h = (float) VIRTUAL_HEIGHT * scale;
		oldViewport = viewport;
		viewport = new Rectangle(crop.x, crop.y, w, h);
		System.out.println("new viewport: "+viewport);
		camera.translate(-viewport.x+oldViewport.x, -viewport.y+oldViewport.y, 0);
		System.out.println("X: "+(viewport.x-oldViewport.x));
		System.out.println("Y: "+(viewport.y-oldViewport.y));
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
