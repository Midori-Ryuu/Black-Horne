package com.gamadu.apollowarrior.managers;

import org.newdawn.slick.GameContainer;

import com.apollo.Entity;
import com.apollo.annotate.InjectManager;
import com.apollo.components.Transform;
import com.apollo.managers.GroupManager;
import com.apollo.managers.Manager;
import com.apollo.managers.TagManager;
import com.apollo.utils.Bag;
import com.gamadu.apollowarrior.Groups;
import com.gamadu.apollowarrior.Tags;
import com.gamadu.apollowarrior.components.Health;

public class CollisionManager extends Manager {
	@InjectManager
	GroupManager groupManager;
	
	@InjectManager
	TagManager tagManager;

	private GameContainer container;

	private Bag<Entity> computerBullets;

	private Bag<Entity> computerShips;

	private Entity playerShip;

	private Bag<Entity> playerBullets;
	
	public CollisionManager(GameContainer container) {
		this.container = container;
	}

	@Override
	public void initialize() {
		computerBullets = groupManager.getEntityGroup(Groups.ComputerBullets);
		computerShips = groupManager.getEntityGroup(Groups.ComputerShips);
		playerShip = tagManager.getEntity(Tags.Player);
		playerBullets = groupManager.getEntityGroup(Groups.PlayerBullets);
	}
	
	@Override
	public void update(int delta) {
		// Player bullets collide with computer ships.
		for(int a = 0; playerBullets.size() > a; a++) {
			Entity playerBullet = playerBullets.get(a);
			Transform pt = playerBullet.getComponent(Transform.class);
			for(int i = 0; computerShips.size() > i; i++) {
				Entity cs = computerShips.get(i);
				Transform ct = cs.getComponent(Transform.class);
				if(pt.getDistanceTo(ct) < 20) {
					world.deleteEntity(playerBullet);
					world.deleteEntity(cs);
					playerBullet.fireEvent("EXPLODED");
					cs.fireEvent("EXPLODED");
				}
			}
		}
		
		// Computer bullets collide with player ship.
		Transform pt = playerShip.getComponent(Transform.class);
		for(int i = 0; computerBullets.size() > i; i++) {
			Entity cb = computerBullets.get(i);
			Transform ct = cb.getComponent(Transform.class);
			if(pt.getDistanceTo(ct) < 20) {
				world.deleteEntity(cb);
				cb.fireEvent("EXPLODED");
				playerShip.getComponent(Health.class).addDamage(13); // 13 is bad luck.
			}
		}
	}

}
