package com.gamadu.apollowarrior.spatials;

import com.apollo.Layer;

public enum Layers implements Layer {
	Background,
	Ships, 
	Effects,
	Projectiles, 
	Interface;

	@Override
	public int getLayerId() {
		return ordinal();
	}

}
