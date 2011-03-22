import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class Lure extends Sprite {
	protected Rod rod;
	protected float velocityX = 0.0f;
	protected float velocityY = 0.0f;
	protected boolean inWater = false;
	protected float angle = 0.0f;
	protected float distance = 0.0f;
	protected float lureSpeed = 0.002f;
	protected boolean isInWater = false;
	
	public final float gravity = 0.3f;
	public final float airResistance = 0.99f;
	public final float waterResistance = 0.4f;
	
	public Lure(Rod rod, String filename) throws SlickException {
		super(filename);
		this.rod = rod;
	}
	
	public void update(int delta) {
		if (!isInWater && y > Constants.WATER_HEIGHT) {
			isInWater = true;
			//this.angle = (float)(Math.atan2(Constants.WATER_HEIGHT,rod.rodTipX) - Math.atan2(y,x));
		}
		
		if (isInWater) {
			if (this.angle <= Math.toRadians(90)) {
				this.angle += lureSpeed;
			}
			x = (float) (rod.rodTipX + Math.cos(this.angle)*distance);
			y = (float) (rod.rodTipY + Math.sin(this.angle)*distance);
		} else {
			x += velocityX;
			y += velocityY;
			velocityY += gravity;
			velocityX *= airResistance;
			
			float xDist = Math.abs(rod.rodTipX - x);  
			float yDist = Math.abs(rod.rodTipY - y);
			distance = (float) Math.sqrt((xDist * xDist) + (yDist * yDist)); 
		}
	}
	
	public void setVelocity(float vx, float vy) {
		this.velocityX = vx;
		this.velocityY = vy;
	}
	

}
