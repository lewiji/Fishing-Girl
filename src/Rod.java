import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.FastTrig;


public class Rod extends Sprite {
	
	protected enum RodState {
		Default, CastBack, CastForward, ReelIn, LureOut, Return;
	}
	
	protected RodState state;
	protected Sprite owner;
	protected Lure lure;
	protected float rodTipX;
	protected float rodTipY;
	protected boolean inMotion;
	private float rodSpeed = 2f;

	public Rod() throws SlickException {
		super(Resources.rod);
		state = RodState.Default;
		lure = new Lure(this, Resources.lureSmall);
		x = Constants.ROD_X;
		y = Constants.ROD_Y;
		pivotX = 0;
		pivotY  = 7;
	}
	
	public void update(int delta) {
		// get tip of fishing rod vector
		rodTipX = x + this.image.getWidth() * (float)FastTrig.cos(Math.toRadians(this.angle));
		rodTipY = y + this.image.getWidth() * (float)FastTrig.sin(Math.toRadians(this.angle)) + 10;
		
		lure.pivotX = rodTipX - lure.x;
		lure.pivotY = rodTipY - lure.y;
		
		adjustCastingAngle(delta);
		rodReturn(delta);
		
		if (state.equals(RodState.LureOut)) {
			lure.update(delta);
			if (lure.distance < 1) {
				state = RodState.ReelIn;
				resetLure();
			}
		}
	}

	/**
	 * Moves rod back and forth
	 * @param delta	Time between frames
	 */
	private void adjustCastingAngle(int delta) {
		if (state.equals(RodState.CastBack)) {
			// If rod state is castback, rotate rod back
			this.rotate(-rodSpeed);
			
			if (angle < Constants.MAX_ANGLE) {
				state = RodState.CastForward;
			}
			
			resetLure();
		} 
		else if (state.equals(RodState.CastForward)) {
			// If rod state is castforward, rotate rod forward
			this.rotate(rodSpeed);
			
			if (angle > 0f) {
				state = RodState.CastBack;
			}
			
			resetLure();
		}
	}

	/**
	 * Sets lure X and Y to rod tip, and sets power of lure
	 */
	private void resetLure() {
		lure.x = rodTipX - lure.w2;
		lure.y = rodTipY - lure.h2;
		lure.distance = 0;
		lure.angle = 0;
		lure.isInWater = false;
		lure.setVelocity(0.2f * Math.abs(this.angle), 0.05f * -Math.abs(this.angle));
	}

	/**
	 * If rod has been cast, return rod to casting position
	 * @param delta	Delta time between frames
	 * 	
	 */
	private void rodReturn(int delta) {
		if (angle < 0.0f && state.equals(RodState.LureOut))
		{
			this.angle = this.angle + rodSpeed * 3;
		} else if (angle > 0.0f && state.equals(RodState.LureOut)) {
			this.angle = this.angle - rodSpeed;
		}
	}

}
