package lb.midori.blackhorne;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Black-Horne";
		cfg.useGL20 = true;
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.fullscreen=true;
		
		new LwjglApplication(new BlackHorne(), cfg);
	}
}
