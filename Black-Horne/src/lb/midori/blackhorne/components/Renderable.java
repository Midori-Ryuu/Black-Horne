package lb.midori.blackhorne.components;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Spatial;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Renderable extends Spatial<SpriteBatch> {

	@InjectComponent
	Transform transform;
	private Animation[] animations;
	private Animation currentAnimation;
	private TextureRegion textureRegion;
	private TextureRegion currentFrame;
	private int state;
	private float stateTime;
	private Collidable collidable;

	public Renderable(Animation[] animations) {
		stateTime = 0;
		state = 0;
		this.animations = animations;
		this.currentAnimation = animations[0];
		textureRegion = new TextureRegion();
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		textureRegion.setRegion(currentFrame);
	
	}

	
	public void initialize() {
		if (this.getOwner().getComponent(Collidable.class) != null) {
			collidable = this.getComponentFromOwner(Collidable.class);
		}
	}
	
	@Override
	public Layer getLayer() {
		// TODO Auto-generated method stub
		return new Layer() {

			@Override
			public int getLayerId() {
				// TODO Auto-generated method stub
				return 0;
			}

		};
	}

	@Override
	public void update(float delta) {
		stateTime+=delta/30f;
		if(stateTime > Float.MAX_VALUE-1)
			stateTime=0;
	}
	
	public void incrementTime(float delta) {
		stateTime += delta / 30f;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public void render(SpriteBatch sb) {

		if (collidable != null && collidable.getBody().getType()==BodyType.DynamicBody) {
			if (collidable.getBody().getLinearVelocity().x < -0.1)
				state = 1;
			else if (collidable.getBody().getLinearVelocity().x > 0.1)
				state = 2;
			else if (collidable.getBody().getLinearVelocity().x > -0.01 && collidable.getBody().getLinearVelocity().x < 0.01)
				state = 0;
		}

		currentAnimation = animations[state];
		
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		sb.draw(currentFrame, transform.getX() - currentFrame.getRegionWidth() / 2, transform.getY() - currentFrame.getRegionHeight() / 2, currentFrame.getRegionWidth() / 2,
				currentFrame.getRegionHeight() / 2, currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), 1f, 1f, transform.rotation);

	}
}
