package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Graphics;

import com.apollo.components.spatial.Spatial;

public abstract class SlickSpatial extends Spatial<Graphics> {

	public abstract void render(Graphics g);

}
