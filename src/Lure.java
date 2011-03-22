import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class Lure implements FishingPhysics {
	protected Image image = null;
	protected Vector2f lureVector = null;
	protected float power = 0.0f;
	protected boolean inWater = false;
	protected float angle = 0.0f;
	
	public Lure(String filename) throws SlickException {
		image = new Image(filename);
		lureVector = new Vector2f();
	}
	
	public void setPosition(float x, float y) {
		lureVector.x = x - (image.getWidth() / 2);
		lureVector.y = y;
	}
	
	public Vector2f getPosition() {
		return lureVector;
	}
	
	public void setPower(float pow) {
		power = Math.abs(pow);
	}
	
	public void draw() {
		image.draw(lureVector.x, lureVector.y);
	}
}
