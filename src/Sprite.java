import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.FastTrig;


public class Sprite {
	
	protected float x = 0f; //
	protected float y = 0f; // coords
	protected float w2 = 0f; //
	protected float h2 = 0f; // half width / height
	protected Image image; // image
	protected float angle = 0f; // rotation angle
	protected float pivotX = 0f; //
	protected float pivotY = 0f; // centre of rotation
	
	public Sprite(String filename) throws SlickException {
		setImage(new Image(filename));
	}
	
	public Sprite(Image image) {
		setImage(image);
	}
	
	public void render(Graphics g) {
		float rx = x + pivotX;
		float ry = y + pivotY;
		g.rotate(rx, ry, angle);
		image.draw(x, y);
		g.rotate(rx, ry, -angle);
	}
	
	public void render(Graphics g, float x, float y) {
		float rx = x + pivotX;
		float ry = y + pivotY;
		g.rotate(rx, ry, angle);
		image.draw(x, y);
		g.rotate(rx, ry, -angle);
	}
	
	public void renderCentred(Graphics g) {
		image.draw(x - w2, y - h2);
	}
	
	public void renderCentred(Graphics g, float x, float y) {
		image.draw(x - w2, y - h2);
	}
	
	public void rotate(float amount) {
		angle = (angle + amount) % 360.0f;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
		this.w2 = ((float) image.getWidth()) * 0.5f;
		this.h2 = ((float) image.getWidth()) * 0.5f;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPivotPoint(float px, float py) {
		pivotX = px;
		pivotY = py;
	}
}
