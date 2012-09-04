package lb.midori.blackhorne.components;

import lb.midori.blackhorne.constants.CConstants;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.Transform;
import com.apollo.components.spatial.Spatial;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderable extends Spatial<SpriteBatch>{

	@InjectComponent
	Transform transform;
	private Texture texture;
	private TextureRegion textureRegion;
	
	public Renderable(Texture texture)
	{
		this.texture=texture;
		textureRegion = new TextureRegion();
		textureRegion.setRegion(texture);
		
	}
	
	@Override
	public Layer getLayer() {
		// TODO Auto-generated method stub
		return new Layer()
		{

			@Override
			public int getLayerId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
		};
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		//System.out.println(transform.getX());
	//	sb.draw(texture, transform.getX()- texture.getWidth() / 2, transform.getY() - texture.getHeight() / 2);
		//sb.draw(textureRegion, transform.getX()- texture.getWidth() / 2, transform.getY() - texture.getHeight() / 2, 0, 0, texture.getWidth(), texture.getHeight(), 1f, 1f, transform.getRotation());
		//sb.draw(textureRegion, transform.getX()- texture.getWidth() / 2, transform.getY() - texture.getHeight() / 2, 0, 0, texture.getWidth(), texture.getHeight(), 1f, 1f, transform.rotation);
		sb.draw(textureRegion, transform.getX()-texture.getWidth()/2, transform.getY() - texture.getHeight()/2,texture.getWidth()/2,texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1f, 1f, transform.rotation);
		
		//System.out.println(transform.getRotation());
		//System.out.println("Transform: =----- "+transform.getX()+"   "+transform.getY());
	}
	
	

}
