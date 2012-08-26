package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;

public class BulletSpatial extends SlickSpatial {
	@InjectComponent
	private Transform transform;
	
	@Override
	public Layer getLayer() {
		return Layers.Projectiles;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(transform.getX()-2,transform.getY()-2,4,4);		
	}


}
