package lb.midori.blackhorne;

import lb.midori.blackhorne.screens.GamePlay;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main extends Game {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Black-Horne";
		cfg.useGL20 = true;
		cfg.width = 1000;
		cfg.height = 500;
		cfg.fullscreen=false;
		
		Main main = new Main();
		
		new LwjglApplication(main, cfg);
		main.create();
	}

	@Override
	public void create() {
		
		this.setScreen(new GamePlay());
		
		
	}
}
