package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.apollo.Entity;
import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.annotate.InjectTaggedEntity;
import com.apollo.components.Transform;

public class EnemySpatial extends SlickSpatial {
	@InjectComponent
	Transform transform;
	
	@InjectTaggedEntity("Player")
	Entity player;
	
	private Image ship;
	
	public EnemySpatial(Image ship) {
		this.ship = ship;
	}
	
	@Override
	public void initialize() {
		System.out.println("I have player: " + player);
	}
	
	@Override
	public void render(Graphics g) {
		ship.drawCentered(transform.getX(), transform.getY());
	}

	@Override
	public Layer getLayer() {
		return Layers.Ships;
	}

}
