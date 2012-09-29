package lb.midori.blackhorne.controllers;

import java.util.HashMap;
import java.util.Map;

import lb.midori.blackhorne.components.Renderable;
import lb.midori.blackhorne.components.Collidable;

import com.apollo.Entity;
import com.badlogic.gdx.math.Vector2;

public class PlayerController {

	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}

	private com.apollo.World worldA;
	private Entity playerCharacter;
	private Collidable collidable;
	private Renderable renderable;

	static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	};

	public PlayerController(Entity playerCharacter) {
		this.playerCharacter = playerCharacter;
		this.worldA = playerCharacter.getWorld();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	/** The main update method **/
	public void update(float delta) {
		processInput(delta);
		playerCharacter.update(delta);
	}

	private void processInput(float delta) {

		//renderable = playerCharacter.getComponent(Renderable.class);
		renderable = (Renderable)  playerCharacter.getComponents().get(1);
		collidable = playerCharacter.getComponent(Collidable.class);

		if (collidable == null) {
		//	System.out.println("WHOOOOOOOOOOOOOOOOOOOOOO::::::::::::::::::::");
			return;
		}

		if (renderable == null) {
		//	System.out.println("WHYYYYYYYYYYYYYY::::::::::::::::::::");
			return;
		}
		System.out.println(collidable.getBody().getLinearVelocity().x);
	//	System.out.println("DOnnnnnnoooooo:::::::::::::::::");

		if (keys.get(Keys.JUMP)) {
		//	if (Math.abs(collidable.getBody().getLinearVelocity().y) < 0.01) {
				collidable.getBody().setLinearVelocity(collidable.getBody().getLinearVelocity().x, 5f);
				
				System.out.println("JUMPING::::::::::::::");
				//renderable.setState(3);
		//	}
		}

		if (keys.get(Keys.LEFT)) {
			float desiredVel = -6f;
			float velChange = desiredVel - collidable.getBody().getLinearVelocity().x;
			float impulse = collidable.getBody().getMass() * velChange;
	//		if (Math.abs(collidable.getBody().getLinearVelocity().y) < 0.1) {
				collidable.getBody().applyLinearImpulse((new Vector2(impulse, 0f)), collidable.getBody().getWorldCenter());
				
			//	renderable.incrementTime(delta);
			//	renderable.setState(1);
	//		}
		}

		if (keys.get(Keys.RIGHT)) {
			float desiredVel = 6f;
			float velChange = desiredVel - collidable.getBody().getLinearVelocity().x;
			float impulse = collidable.getBody().getMass() * velChange;
	//		if (Math.abs(collidable.getBody().getLinearVelocity().y) < 0.1) {
	//		if (Math.abs(collidable.getBody().getLinearVelocity().y) < 0.1) {
				collidable.getBody().applyLinearImpulse((new Vector2(impulse, 0f)), collidable.getBody().getWorldCenter());
				//collidable.getBody().applyForceToCenter(new Vector2(50f,0f));
				
			//	renderable.incrementTime(delta);
			//	renderable.setState(2);
	//		}
		}

		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) || (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
		//	 renderable.setState(0);
			collidable.getBody().applyLinearImpulse((new Vector2(-collidable.getBody().getLinearVelocity().x/2, 0f)), collidable.getBody().getWorldCenter());
		}
		
		
	}
}
