package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.gamadu.apollowarrior.ApolloWarrior;
import com.gamadu.apollowarrior.components.Health;

public class HealthbarSpatial extends SlickSpatial {
	@InjectComponent
	private Health health;

	@Override
	public void render(Graphics g) {
		int percentageHealth = Math.round(100*health.getHealthStatus());
		g.setColor(Color.white);
		g.drawString("Current health: " +percentageHealth+"%", 10, ApolloWarrior.HEIGHT-25);
	}

	@Override
	public Layer getLayer() {
		return Layers.Interface;
	}

}
