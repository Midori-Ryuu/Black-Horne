package lb.midori.blackhorne.builders;

import lb.midori.blackhorne.components.Collidable;
import lb.midori.blackhorne.components.Renderable;
import lb.midori.blackhorne.constants.CConstants;

import com.apollo.Entity;
import com.apollo.components.Transform;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PlayerBuilder {

	public Entity buildEntity(com.apollo.World worldA, com.badlogic.gdx.physics.box2d.World worldB, Texture texture, float x, float y,float density, float friction) {
		final Entity e = new Entity(worldA);
		PolygonShape box = new PolygonShape();
		box.setAsBox(texture.getWidth() / 2 * CConstants.rB, texture.getHeight() / 2 * CConstants.rB);
		
		//box.setAsBox(texture.getWidth() / 2 * CConstants.rB, texture.getHeight() / 2 * CConstants.rB, new Vector2(x*CConstants.rB,y*CConstants.rB),0f);
		e.setComponent(new Transform(x, y));
		e.setComponent(new Renderable(texture));
		e.setComponent(new Collidable(worldB, BodyType.DynamicBody, "Player", box, x, y, density, friction));
		e.addToWorld();
		return e;
	}

}
