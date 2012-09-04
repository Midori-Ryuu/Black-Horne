package com.gamadu.apollowarrior.managers;

import org.newdawn.slick.GameContainer;

import com.apollo.Entity;
import com.apollo.components.Transform;
import com.apollo.managers.Manager;
import com.apollo.utils.Timer;

public class EnemyShipSpawnManager extends Manager {
	private Timer spawnTimer;
	private GameContainer container;
	
	public EnemyShipSpawnManager(GameContainer container) {
		this.container = container;
	}

	@Override
	public void initialize() {
		spawnTimer = new Timer(1000, true) {
			@Override
			public void execute() {
				Entity enemyShip = world.createEntity("EnemyShip");
				enemyShip.getComponent(Transform.class).setLocation((float)Math.random()*container.getWidth(), 60);
				world.addEntity(enemyShip);
			}
		};
	}
	
	@Override
	public void update(float delta) {
		spawnTimer.update(delta);
	}

}
