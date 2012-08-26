package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;

public class ExplosionSpatial extends SlickSpatial {
	@InjectComponent
	private Transform transform;
	private int radius;
	
	public ExplosionSpatial(int radius) {
		this.radius = radius;
	}
	
	@Override
	public Layer getLayer() {
		return Layers.Effects;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillOval(transform.getX()-radius,transform.getY()-radius,radius*2, radius*2);		
	}


}
