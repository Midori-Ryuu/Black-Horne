package com.gamadu.apollowarrior.builders;

import com.apollo.Entity;
import com.apollo.EntityBuilder;
import com.apollo.EventHandler;
import com.apollo.World;
import com.apollo.components.Transform;
import com.gamadu.apollowarrior.components.Expires;
import com.gamadu.apollowarrior.components.Movement;
import com.gamadu.apollowarrior.spatials.BulletSpatial;

public class BulletBuilder implements EntityBuilder {

	@Override
	public Entity buildEntity(final World world) {
		final Entity e = new Entity(world);
		e.setComponent(new Transform());
		e.setComponent(new BulletSpatial());
		e.setComponent(new Movement());
		e.setComponent(new Expires(5000));
		
		e.addEventHandler("EXPIRED", new EventHandler() {
			@Override
			public void handleEvent() {
			}
		});
		
		e.addEventHandler("EXPLODED", new EventHandler() {
			@Override
			public void handleEvent() {
				Entity explosion = world.createEntity("BulletExplosion");
				explosion.getComponent(Transform.class).setLocation(e.getComponent(Transform.class));
				world.addEntity(explosion);
			}
		});
		
		return e;
	}

	
}
