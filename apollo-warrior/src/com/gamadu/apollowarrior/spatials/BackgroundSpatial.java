package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.apollo.Layer;
import com.gamadu.apollowarrior.ApolloWarrior;

public class BackgroundSpatial extends SlickSpatial {
	private Shape screen;
	private ShapeFill fill;

	public BackgroundSpatial() {
		screen = new Rectangle(0, 0, ApolloWarrior.WIDTH, ApolloWarrior.HEIGHT);
		fill = new GradientFill(0, 0, Color.black, 0, ApolloWarrior.HEIGHT, new Color(0,0,0.1f));
	}

	@Override
	public void render(Graphics g) {
		g.fill(screen, fill);
	}

	@Override
	public Layer getLayer() {
		return Layers.Background;
	}

}
