package com.gamadu.apollowarrior.spatials;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Node;

public class PlayerNode extends Node<Graphics> {
	@InjectComponent
	Transform transform;
	private Image shipImage;
	
	public PlayerNode(Image ship) {
		this.shipImage = ship;
	}

	@Override
	public void initialize() {
	}
	
	@Override
	protected void attachChildren() {
		addChild(new HealthbarSpatial());
	}

	@Override
	public void render(Graphics g) {
		shipImage.drawCentered(transform.getX(), transform.getY());
	}

	@Override
	public Layer getLayer() {
		return Layers.Ships;
	}

}
