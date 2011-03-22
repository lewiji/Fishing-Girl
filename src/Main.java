import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Main extends BasicGame {
	
	Image background = null;
	Image cliff = null;
	Image landscape = null;
	BigImage water = null;
	Image bear = null;
		
	Rod rod = null;

	
	boolean rodInMotion = false;
	boolean casting = false;
	
	public Main() {
		super("Fishing Girl");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
		gc.setTargetFrameRate(60);
		gc.setSmoothDeltas(true);
		
		// init graphics and vectors
		background = new Image("data/background.png");
		
		cliff = new Image("data/cliff.png");
		
		landscape = new Image("data/landscape4.png");
		landscape = landscape.getScaledCopy(0.75f);
		
		bear = new Image("data/bearPurple.png");
		
		water = new BigImage("data/watertile.png");
				
		rod = new Rod();
		rod.x = Constants.ROD_X;
		rod.y = Constants.ROD_Y;
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
		// get keyboard input
		Input input = gc.getInput();
		
				
		// casting - only able to re-cast when fishing line has returned
		if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && rod.state.equals(rod.state.Default)) {
			rod.state = rod.state.CastBack;
		}
		
		// once reeled in, reset to default state once mouse button has been lifted
		if (!input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && rod.state.equals(rod.state.ReelIn)) {
			rod.state = rod.state.Default;
		}
		
		// if mouse button is released and rod is moving then change state to cast lure
		if (!input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && 
			(rod.state.equals(rod.state.CastBack) || rod.state.equals(rod.state.CastForward))) {
			rod.state = rod.state.LureOut;
		}
		
		if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON) && rod.state.equals(rod.state.LureOut)) {
			rod.lure.distance = rod.lure.distance -= 1.5f;
		}
		
		rod.update(dt);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (rod.state.equals(rod.state.LureOut)) {
			g.translate(-rod.lure.x / 2, -rod.lure.y/ 2);
		}
		
		g.drawImage(background, 0, -175);
		
		g.drawImage(landscape, 5, 123);
		g.drawImage(water, 140, 270);
		g.drawImage(cliff, -600, 250);
		
		g.drawImage(bear, 110, 220);
		
		if (rod.state.equals(rod.state.LureOut)) {
			g.setColor(Color.orange);
			g.setLineWidth(1.5f);
			
			g.drawLine(rod.rodTipX, 
						rod.rodTipY, 
						rod.lure.x + rod.lure.w2, 
						rod.lure.y + rod.lure.h2);
		}
		rod.render(g);
		rod.lure.render(g);		
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
