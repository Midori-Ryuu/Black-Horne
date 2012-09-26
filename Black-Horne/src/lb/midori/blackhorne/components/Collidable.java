package lb.midori.blackhorne.components;

import com.apollo.Component;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import lb.midori.blackhorne.constants.*;

public class Collidable extends Component {

	@InjectComponent
	Transform transform;
	private Body body;
	private Body collidedBody;
	private boolean collided;

	public Collidable(com.badlogic.gdx.physics.box2d.World worldB, BodyType bodyType, String userData, PolygonShape box, float x, float y, float density, float friction) {
		// super();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType; //
		// System.out.println(transform);

		bodyDef.position.set(x * CConstants.rB, y * CConstants.rB);
		bodyDef.angle = 0f * MathUtils.degreesToRadians;

		body = worldB.createBody(bodyDef);

		if (bodyType == BodyType.StaticBody) {
			body.createFixture(box, 1.0f);
		} else {
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = density;
			fixtureDef.friction = friction;
			body.createFixture(fixtureDef);
		}

		body.setUserData(this);

		collided = false;
	}

	public void initialize() {
	}

	public void Collided(Collidable collisionComponent) {

		collided = true;
		collidedBody = collisionComponent.getBody();
		System.out.println("From collided: ---  This" + body.getUserData() + " has collided with" + collidedBody.getUserData());
	}

	public Body getCollidedBody() {
		return collidedBody;
	}

	public void setCollidedBody(Body collidedBody) {
		this.collidedBody = collidedBody;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public void update(float delta) {
		//this.getComponentFromOwner(Renderable.class);
		// body.getTransform().setPosition(new Vector2(100f,100f));
		if (transform == null)
			return;
		transform.setX(body.getPosition().x * CConstants.bR);
		transform.setY(body.getPosition().y * CConstants.bR);
		if (collided == true) {
			System.out.println("From update: ---  This" + body.getUserData() + " has collided with" + collidedBody.getUserData());

			collided = false;
		}
		
		
		
		//transform.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		transform.setRotation(0);
		body.setTransform(transform.x* CConstants.rB, transform.y* CConstants.rB, 0);
	}
}
