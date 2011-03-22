import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;


public class Main extends BasicGame {
	
	float WATER_HEIGHT = 290f;
	
	Image background = null;
	Image cliff = null;
	Image landscape = null;
	Image water = null;
	
	Image bear = null;
	
	Lure lure = null;
	
	Image rod = null;
	Vector2f rodVector = null;
	Vector2f rodTipVector = null;
	float rodRotationAmount = 0.1f;
	
	boolean buttonDown = false;
	boolean casting = false;
	
	public Main() {
		super("Fishing Girl");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		background = new Image("data/background.png");
		cliff = new Image("data/cliff.png");
		landscape = new Image("data/landscape4.png");
		landscape = landscape.getScaledCopy(0.75f);
		bear = new Image("data/bearPurple.png");
		water = new Image("data/watertile.png");
		
		rod = new Image("data/fishingRod1.png");
		rodVector = new Vector2f(145.0f, 227.0f);
		rodTipVector = new Vector2f();
		
		lure = new Lure("data/lureSmall.png");		
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();

		rodTipVector.x = rodVector.x + rod.getWidth() * (float)FastTrig.cos(Math.toRadians(rod.getRotation()));
		rodTipVector.y = rodVector.y + rod.getWidth() * (float)FastTrig.sin(Math.toRadians(rod.getRotation())) + 10;
		
		if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && !casting) {
			if (!buttonDown) {
				buttonDown = true;
			}
			
			rod.setCenterOfRotation(0, 7);
			if ((rod.getRotation() < -120.0f && rodRotationAmount < 0)) {
				rodRotationAmount = -rodRotationAmount;	
			}
			
			if (rod.getRotation() > 0.0f && rodRotationAmount > 0) {
				rodRotationAmount = -rodRotationAmount;
			}

			rod.rotate(rodRotationAmount * dt);
			
			lure.setPower(rod.getRotation() / 120.0f);
			lure.angle = 0;
			lure.setPosition(rodTipVector.x, rodTipVector.y);
		}
		
		if (casting) {
			processLurePhysics(dt, input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON));
		}
		
		if (!input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && buttonDown) {
			if (!casting) {
				casting = true;
			}
			
			if (rodRotationAmount < 0) {
				rodRotationAmount = -rodRotationAmount;
			}
			
			if (rod.getRotation() < 0.0f)
			{
				rod.rotate((rodRotationAmount * dt) * 3);
			} else {
				buttonDown = false;
			}
		}
		

		
	}

	private void processLurePhysics(float dt, boolean isMouseDown) {
		if (!lure.inWater) { 
			lure.getPosition().x = lure.getPosition().x + lure.power;
			lure.getPosition().y = (lure.getPosition().y + lure.gravity) - lure.power;
			
			if (lure.power >= 0) {
				lure.power = lure.power - lure.airResistance;
			} else {
				lure.power = 0;
			}
		} else {
			float distance = lure.getPosition().distance(rodTipVector);
			
			if (lure.angle < Math.toRadians(90)) {
				lure.angle = lure.angle + 0.0002f;
				lure.getPosition().x = rodTipVector.x + (distance * (float)FastTrig.cos(lure.angle));
			} else {
				lure.angle = lure.angle + 0.0001f;
			}
			
			if (!isMouseDown) {
				lure.getPosition().y = rodTipVector.y + (distance * (float)FastTrig.sin(lure.angle));
			} else {
				lure.angle = lure.angle + 0.00022f;
			}
			
			if (distance < 1f) {
				casting = false;
				lure.inWater = false;
			}
			
		}
		
		if ((!lure.inWater) && (lure.getPosition().y > WATER_HEIGHT)) {
			lure.inWater = true;
			lure.angle = (float)(Math.atan2(rodTipVector.y,rodTipVector.x) - Math.atan2(lure.getPosition().y,lure.getPosition().x));
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		background.draw(0,-175);
		
		landscape.draw(5, 123);
		water.draw(140, 270);
		cliff.draw(-600, 250);
		
		bear.draw(110, 220);
		rod.draw(rodVector.x, rodVector.y);
		
		
		if (casting) {
			g.setColor(Color.orange);
			g.setLineWidth(2.5f);
			g.drawLine(rodTipVector.x, 
						rodTipVector.y, 
						lure.getPosition().x + lure.image.getWidth()/2, 
						lure.getPosition().y + lure.image.getHeight()/2);
		}
		
		lure.draw();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = 
			new AppGameContainer(new Main());
 
         app.setDisplayMode(800, 600, false);
         app.start();
	}

}
