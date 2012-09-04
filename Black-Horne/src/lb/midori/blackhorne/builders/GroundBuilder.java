package lb.midori.blackhorne.builders;

import lb.midori.blackhorne.components.Collidable;
import lb.midori.blackhorne.components.Renderable;
import lb.midori.blackhorne.constants.CConstants;

import com.apollo.Entity;
import com.apollo.components.Transform;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GroundBuilder {

	public Entity buildEntity(com.apollo.World worldA, com.badlogic.gdx.physics.box2d.World worldB, Texture texture, float x, float y) {
		final Entity e = new Entity(worldA);
		PolygonShape box = new PolygonShape();
		box.setAsBox(texture.getWidth() / 2 * CConstants.rB, texture.getHeight() / 2 * CConstants.rB);
		e.setComponent(new Transform(x, y));
		e.setComponent(new Renderable(texture));
		e.setComponent(new Collidable(worldB, BodyType.StaticBody, "Ground", box, x, y, 1f, 1f));
		e.addToWorld();
		return e;
	}

}
