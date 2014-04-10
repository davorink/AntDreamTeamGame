
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.Sound;



public class GUI extends BasicGame implements Runnable
{
	static int screenwidth = 1024;
	static int screenheight = 600;
	int gx = 0, gy = 0;
	float xaccel = 0, yaccel = 0;
	private Image hexagon, solid, anthill;
	private Image cell, hillcell;
	private Image antred, antblack, bg, overview, logo, dreamteamlogo;
	private Image singlematch, results, redwins, blackwins, drawPicture, ps2r;
	private boolean w, a, s, d;
	private int zoomlevel = 0;
	private Random r = new Random();
	private int titleframephase = 0;
	private JFileChooser brainChooser, worldChooser;
	private ArrayList<GUIEffect> effects, bgeffects;
	private ArrayList<ArrayList<GUIButton>> buttons;
	private TextField fieldRed, fieldBlack;
	private String playerRed, playerBlack, brainRedFile, brainBlackFile, worldFile;
	private boolean scentsOn;
	private TeamColor winningteam;
	private int logotime;
	
	private Cell[][] cells;
	private Ant[] ants;
	
	private int state = S_NONE;
	//private GameEngine thisengine;
	
	// game states, why the cunting fuck do i have to fucking do it like this
	// why on earth can't i fucking just use an enum like i could in C
	// java is a fucking dogshit language burn you cunts
	public static final int	S_NONE = 0; 				// should never happen
	public static final int	S_TITLE = 1; 				// title screen
	public static final int	S_GAMEPLAY = 2;			// gameplay screen
	public static final int	S_GAMESELECT = 3;			// game select options
	public static final int	S_TOURNAMENTSELECT = 4;	// tournament select options
	public static final int	S_ROUNDSTATS = 5;			// end of round statistics (for tournament)
	public static final int	S_ENDSTATS = 6;			// end of game stats (for single game + tournament)
	public static final int	S_LOGO = 7;				// logo at start :)
	public static final int	NUMSTATES = 8;	
	//private Cell[][] cells; // grid of cells used for drawing
	//private Ant[] ants; // array of ants used for drawing
	private int round, redAnts, blackAnts, redFood, blackFood; // just for stat keeping
	private Image blaze;
	
	private Sound menumusic, disco, tournament, resultsmusic, draw, dreamteam, select;
	private Music gameplay1; private Music gameplay2;
	
	
	public GUI(String gamename) throws CellException
	{
		super(gamename);
		//thisengine = xe;
		/*tempcells = new int[150][150];
		for(int i=1; i<=100; i++) {
			tempcells[(int) Math.floor(Math.random()*150)][(int) Math.floor(Math.random()*150)]=(int) Math.ceil(Math.random()*3);
		}
		for(int i=1; i<=20; i++) {
			tempcells[(int) Math.floor(Math.random()*150)][(int) Math.floor(Math.random()*150)]=4+(int) Math.floor(Math.random()*13);
		}
		for(int i=1; i<=9; i++) {
			tempcells[1][i] = 3+i;
		}*/
		
		cells = new Cell[150][150];
		for(int i=0; i<150; i++) for(int j=0; j<150; j++) cells[i][j]=new Cell(CellType.CLEAR, 0);
		
		effects = new ArrayList<GUIEffect>();
		bgeffects = new ArrayList<GUIEffect>();
		buttons = new ArrayList<ArrayList<GUIButton>>();
		for(int i=0;i<NUMSTATES;i++) {
			buttons.add(new ArrayList<GUIButton>());
		}
		scentsOn=true;
	}
	
	
	public static void update(int f) {
		/*nig=f;
		tempcells = new int[150][150];
		for(int i=1; i<=100; i++) {
			tempcells[r.nextInt(150)][(int) Math.floor(Math.random()*150)]=(int) Math.ceil(Math.random()*3);
		}
		for(int i=1; i<=20; i++) {
			tempcells[(int) Math.floor(Math.random()*150)][(int) Math.floor(Math.random()*150)]=4+(int) Math.floor(Math.random()*13);
		}
		for(int i=1; i<=9; i++) {
			tempcells[1][i] = 3+i;
		}*/
	}
	
	private void drawHexagonGrid(Graphics g, int x, int y, int w, int h) throws CellException
	{
		if(zoomlevel==0) g.drawImage(overview, x-21, y-19);
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				if(zoomlevel > 0) { // zoomed in renderer
					int drawx = x+(i*30+((j%2>0)?15:0));
					int drawy = y+(j*23);
					if(drawx<-31 || drawx>screenwidth || drawy<-32 || drawy>screenheight) continue;
					g.drawImage(hexagon, drawx-12, drawy-12);
					Cell c = cells[i][j];
					if(c.getState()==CellType.ROCKY) {
						g.drawImage(solid, drawx, drawy);
					}
					
					if(c.getAnthillColor() != null) {
						if(c.getAnthillColor()==TeamColor.RED) {
							g.drawImage(anthill, drawx-11, drawy-11, new Color(1f, 0f, 0f, 1f));
						} else {
							g.drawImage(anthill, drawx-11, drawy-11, new Color(0.5f, 0.5f, 0.5f, 1f));
						}
					}
					if(c.getAnt()!=null) {
						if(c.getAnt().getColor()==TeamColor.RED) {
							antred.setRotation((float) (c.getAnt().getDirection()*60));
							if(c.getAnt().getHasFood()) g.drawImage(antred, drawx, drawy+1, new Color(1f, 0.5f, 1f, 1f));
							else g.drawImage(antred, drawx, drawy+1);
						} else {
							antblack.setRotation((float) (c.getAnt().getDirection()*60));
							if(c.getAnt().getHasFood()) g.drawImage(antblack, drawx, drawy+1, new Color(1f, 0.5f, 1f, 1f));
							else g.drawImage(antblack, drawx, drawy+1);
						}
					}
					if(c.getFoodAmount()>0) {
						g.setColor(new Color(1f,0.5f,1f,1f));
						g.drawString(""+(c.getFoodAmount()), drawx+10, drawy+7);
					}
							
					
				} else { // zoomed out renderer
					int drawx = x+(i*4+((j%2>0)?2:0));
					int drawy = y+(j*4);
					if(drawx<-31 || drawx>screenwidth || drawy<-32 || drawy>screenheight) continue;
					Cell c = cells[i][j];
					if(scentsOn) {
						for(int p=0; p<=5; p++) if(c.getMarker(TeamColor.RED, p)) {
							g.setColor(new Color(1f,0f,0f,0.2f));
							g.fillRect(drawx, drawy, 4, 4);
						}
						for(int q=0; q<=5; q++) if(c.getMarker(TeamColor.BLACK, q)) {
							g.setColor(new Color(0f,0f,0f,0.2f));
							g.fillRect(drawx, drawy, 4, 4);
						}
					}
					if(c.getState()==CellType.ROCKY) {
						g.drawImage(cell, drawx, drawy/*, new Color(1f, 0f, 0f, 1f)*/);
					}
					if(c.getAnthillColor() != null) {
						if(c.getAnthillColor()==TeamColor.RED) {
							g.drawImage(hillcell, drawx, drawy, new Color(1f, 0f, 0f, 1f));
						} else {
							g.drawImage(hillcell, drawx, drawy, new Color(0.5f, 0.5f, 0.5f, 1f));
						}
					}
					if(c.getFoodAmount()>0) {
						g.setColor(new Color(1f,0f,1f,1f));
						g.fillRect(drawx, drawy, 4, 4);
					}
					if(c.getAnt()!=null) {
						if(c.getAnt().getColor()==TeamColor.RED) {
							g.setColor(new Color(1f,0f,0f,1f));
							g.fillRect(drawx, drawy, 4, 4);
						} else {
							g.setColor(new Color(0f,0f,0f,1f));
							g.fillRect(drawx, drawy, 4, 4);
						}
						try {if(c.getAnt().getHasFood()) { g.setColor(new Color(1f,0.5f,1f,0.7f)); g.drawRect(drawx, drawy, 3, 3); } } catch (NullPointerException e){}
					}
				}
			}
		}
	}
	
	public void roundUpdate(Cell[][] xcells, Ant[] xants, int xround, int xredAnts, int xblackAnts, int xredFood, int xblackFood) {
		cells = xcells; 
		ants = xants;
		round = xround;
		redAnts = xredAnts;
		blackAnts = xblackAnts;
		redFood = xredFood;
		blackFood = xblackFood;;
		//System.out.println("cells are now " + cells);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
		switch(state) {
		case S_GAMEPLAY:
		case S_ENDSTATS:
			if(key == Input.KEY_Z) { zoomlevel=(zoomlevel>0?0:1); gx=(int)((float)zoomlevel>0f?(30f/4f):(4f/30f)); gy=(int)((float)zoomlevel>0f?(23f/4f):(4f/23f)); 
										if(state==S_GAMEPLAY) { if(zoomlevel==1) { gameplay2.setPosition(gameplay1.getPosition()); gameplay2.loop(); }
										else { gameplay1.setPosition(gameplay2.getPosition()); gameplay1.loop(); } } }
			if(key == Input.KEY_E) { if(scentsOn) effects.add(new GUIMessageEffect("Scent visibility off")); else effects.add(new GUIMessageEffect("Scent visibility on")); scentsOn=!scentsOn; } 
			if(state==S_GAMEPLAY) break;
			if(key == Input.KEY_SPACE){
				state = S_TITLE;
				resultsmusic.stop();
				draw.stop();
				menumusic.loop();
				effects.add(new GUIScreenFlashEffect(24));
				effects.add(new GUITitleTextEffect("media/letters1.png", 267, 71, 10));
				effects.add(new GUITitleTextEffect("media/letters2.png", 430, 71, 34));
				effects.add(new GUITitleTextEffect("media/letters3.png", 611, 71, 58));
				effects.add(new GUITitleTextEffect("media/letters4.png", 267, 174, 18));
				effects.add(new GUITitleTextEffect("media/letters5.png", 411, 174, 26));
				effects.add(new GUITitleTextEffect("media/letters6.png", 531, 174, 42));
				effects.add(new GUITitleTextEffect("media/letters7.png", 651, 174, 50));
				effects.add(new GUITitleTextEffect("media/letters8.png", 232, 36, 66));
			}
			break;
		case S_TITLE:
			/*if(key == Input.KEY_SPACE) {
				state=S_GAMEPLAY;
			}*/
			break;
		default:
			break;
		}
	}
	
	public void mouseWheelMoved(int change) {
		if(state==S_GAMEPLAY || state==S_ENDSTATS) {
			if (change < 0 && zoomlevel == 1) {zoomlevel = 0; if(state==S_GAMEPLAY) { gameplay1.setPosition(gameplay2.getPosition()); gameplay1.loop(); } }
			if (change > 0 && zoomlevel == 0) {zoomlevel = 1; if(state==S_GAMEPLAY) { gameplay2.setPosition(gameplay1.getPosition()); gameplay2.loop(); } }
		}
	}
	
	public void endGame(Player winner) {
		gameplay1.stop(); gameplay2.stop();
		if(winner == null) {
			winningteam = null;
			draw.loop();
		} else {
			winningteam = winner.getColor();
			resultsmusic.loop();
		}
		effects.add(new GUIScreenFlashEffect());
		state = S_ENDSTATS;
	}
	
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		hexagon = new Image("media/hexagon2.png");
		solid = new Image("media/solid.png");
		cell = new Image("media/cell.png");
		antred = new Image("media/antred.png");
		antblack = new Image("media/antblack.png");
		bg = new Image("media/bg.png");
		anthill = new Image("media/anthill.png");
		overview = new Image("media/overview2.png");
		logo = new Image("media/logo2.png");
		hillcell = new Image("media/hillcell.png");
		blaze = new Image("media/blaze.png");
		singlematch = new Image("media/singlematch.png");
		results = new Image("media/results.png");
		redwins = new Image("media/redwins.png");
		blackwins = new Image("media/blackwins.png");
		drawPicture = new Image("media/draw.png");
		dreamteamlogo = new Image("media/dreamteam.png");
		ps2r = new Image("media/ps2r.png");
		
		menumusic = new Sound("media/menumusic.ogg");
		disco = new Sound("media/disco.ogg");
		resultsmusic = new Sound("media/results.ogg");
		draw = new Sound("media/draw.ogg");
		tournament = new Sound("media/tournament.ogg");
		dreamteam = new Sound("media/dreamteam.ogg");
		select = new Sound("media/select.ogg");
		
		gameplay1 = new Music("media/gameplay1.ogg");
		gameplay2 = new Music("media/gameplay2.ogg");
		
		
		dreamteam.play();
		
		state=S_LOGO;
		///*try { */effects.add(new GUIGenericEffect(320, 240)); /*} catch (NullPointerException e) { System.out.println("fuck"); }*/
		
		
		// initialise buttons //
		buttons.get(S_TITLE).add(new GUIImageButton(416, 362, "media/singlematch0001.png", "media/singlematch0002.png"));
		buttons.get(S_TITLE).add(new GUIImageButton(416, 465, "media/tournament0001.png", "media/tournament0002.png"));
		
		//buttons.get(S_GAMESELECT).add(new GUIButton(202, 148, "Red Player Name"));
		//buttons.get(S_GAMESELECT).add(new GUIButton(202, 221, "Black Player Name"));
		buttons.get(S_GAMESELECT).add(new GUIButton(615, 148, "Red Player Brain"));
		buttons.get(S_GAMESELECT).add(new GUIButton(615, 221, "Black Player Brain"));
		buttons.get(S_GAMESELECT).add(new GUIButton(221, 337, "Select World"));
		buttons.get(S_GAMESELECT).add(new GUIButton(6150, 3370, "Remove World"));
		buttons.get(S_GAMESELECT).add(new GUIButton(390, 511, "Random World"));
		buttons.get(S_GAMESELECT).add(new GUIButton(390, 551, "Start Game"));
		

		brainChooser = new JFileChooser(System.getProperty("user.dir"));
		brainChooser.setFileFilter(new FileNameExtensionFilter("Brain files", new String[] {"brain"}));
		worldChooser = new JFileChooser(System.getProperty("user.dir") );
		worldChooser.setFileFilter(new FileNameExtensionFilter("World files", new String[] {"world"}));
		//int returnVal = sdf.showOpenDialog(null);
		
		fieldRed = new TextField(gc, gc.getDefaultFont(), 302,148,250,24, new ComponentListener(){

			public void componentActivated(AbstractComponent arg0) {
				//  Auto-generated method stub
				
			}});
		
		fieldBlack = new TextField(gc, gc.getDefaultFont(), 302,221,250,24, new ComponentListener(){

			public void componentActivated(AbstractComponent arg0) {
				//  Auto-generated method stub
				
			}});
		
		brainRedFile = brainBlackFile = worldFile = null;
		
		logotime = 250;
		

	}
	
	public static int bx = 100, by = 30;

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = screenheight - Mouse.getY();
		switch(state) {
			case S_LOGO:
				if(logotime--==0) {state=S_TITLE; menumusic.loop(1f, 1f); effects.add(new GUIScreenFlashEffect(24));
				effects.add(new GUITitleTextEffect("media/letters1.png", 267, 71, 10));
				effects.add(new GUITitleTextEffect("media/letters2.png", 430, 71, 34));
				effects.add(new GUITitleTextEffect("media/letters3.png", 611, 71, 58));
				effects.add(new GUITitleTextEffect("media/letters4.png", 267, 174, 18));
				effects.add(new GUITitleTextEffect("media/letters5.png", 411, 174, 26));
				effects.add(new GUITitleTextEffect("media/letters6.png", 531, 174, 42));
				effects.add(new GUITitleTextEffect("media/letters7.png", 651, 174, 50));
				effects.add(new GUITitleTextEffect("media/letters8.png", 232, 36, 66));
				
				}
			break;
			case S_GAMESELECT:
				bgeffects.add(new GUIFlyingEffect(8));
					if(buttons.get(S_GAMESELECT).get(0).overButton() && Mouse.isButtonDown(0)) { // red player brain
						select.play();
						int returnVal = brainChooser.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							brainRedFile = brainChooser.getSelectedFile().getAbsolutePath();
						}
					}
					if(buttons.get(S_GAMESELECT).get(1).overButton() && Mouse.isButtonDown(0)) { // black player brain
						select.play();
						int returnVal = brainChooser.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							brainBlackFile = brainChooser.getSelectedFile().getAbsolutePath();
						}
					}
					if(buttons.get(S_GAMESELECT).get(2).overButton() && Mouse.isButtonDown(0)) { // world
						select.play();
						int returnVal = worldChooser.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							worldFile = worldChooser.getSelectedFile().getAbsolutePath();
						}
					}
					if(buttons.get(S_GAMESELECT).get(4).overButton() && Mouse.isButtonDown(0)) { // random world
						select.play();
						worldFile = null;
					}
					if(buttons.get(S_GAMESELECT).get(5).overButton() && Mouse.isButtonDown(0)) { // PLAY
						select.play();
						disco.stop();
						gameplay1.loop(1f, 1f);
						zoomlevel = 0;
						state=S_GAMEPLAY;
						try { effects.add(new GUIScreenFlashEffect()); } catch (NullPointerException e) { System.out.println("fuck"); }
						
						playerRed = fieldRed.getText();
						playerBlack = fieldBlack.getText();
						fieldRed.setFocus(false);
						fieldBlack.setFocus(false);
						
						final Player[] testPlayers = new Player[2];
						BrainEngine brainEngine1 = null, brainEngine2 = null;

						try {
							brainEngine1 = new BrainEngine();
							brainEngine1.setPath(brainRedFile);
							brainEngine2 = new BrainEngine();
							brainEngine2.setPath(brainBlackFile);
						} catch (InvalidInstruction e1) {
							//  Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidInstructionsSet e1) {
							//  Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							//  Auto-generated catch block
							e1.printStackTrace();
						}
						
						testPlayers[0] = new Player(playerRed, brainEngine1.getBrainInstructions());
						testPlayers[1] = new Player(playerBlack, brainEngine2.getBrainInstructions());
						
						try {
							
							Thread t = new Thread(new Runnable() {
						         public void run()
						         {
						        	 try {
										AntGame.engine.runOneVsOne(testPlayers, worldFile);
									} catch (Exception e) {
										// Auto-generated catch block
										e.printStackTrace();
									}
						         }
						}); t.start();
						} catch (Exception e) {e.printStackTrace();}
						
					}
				break;
			case S_TITLE:
				if(buttons.get(S_TITLE).get(0).overButton() && Mouse.isButtonDown(0)) {
					select.play();
					effects.add(new GUIScreenFlashEffect());
					menumusic.stop();
					disco.loop(); 
					state=S_GAMESELECT;
				}
				for(int ss=0;++ss<5;bgeffects.add(new GUITitleStarEffect()));
				break;
			case S_GAMEPLAY:
			case S_ENDSTATS:
				w = gc.getInput().isKeyDown(Input.KEY_W);
				s = gc.getInput().isKeyDown(Input.KEY_S);
				a = gc.getInput().isKeyDown(Input.KEY_A);
				d = gc.getInput().isKeyDown(Input.KEY_D);
				if(w) yaccel+=1;
				if(s) yaccel-=1;
				if(a) xaccel+=1;
				if(d) xaccel-=1;
				gx+=xaccel;
				gy+=yaccel;
				if(xaccel>0) xaccel-=0.5;
				if(xaccel<0) xaccel+=0.5;
				if(yaccel>0) yaccel-=0.5;
				if(yaccel<0) yaccel+=0.5;
				if(xaccel>16) xaccel=16;
				if(xaccel<-16) xaccel=-16;
				if(yaccel>16) yaccel=16;
				if(yaccel<-16) yaccel=-16;
				if(gx>screenwidth/2) { gx=screenwidth/2; xaccel=-xaccel; }
				if(gy>screenheight/2) { gy=screenheight/2; yaccel=-yaccel; }
				if(zoomlevel==0) {
					if( (screenwidth/2)-gx > 602 ) { gx=(-602)+screenwidth/2; xaccel=-xaccel; }
					if( (screenheight/2)-gy > 600 ) { gy=(-600)+screenheight/2; yaccel=-yaccel; }
				}
				if(state==S_ENDSTATS) break;
				break;
			default:
				break;
		}
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawImage(bg, 0, 0, screenwidth, screenheight, 0, 0, 640, 480);
		try {
			for(GUIEffect e : bgeffects) {
				if(state!=S_TITLE && (e instanceof GUITitleStarEffect)) e.destroy();
				if(!e.isActive()) e=null;
				else try{e.render(g);}catch(ConcurrentModificationException f){} }
		} catch (NullPointerException e) {
			
		}
		switch(state) {
		case S_LOGO:
			g.setColor(new Color(0f, 0f, 0f, 1f));
			g.fillRect(0, 0, screenwidth, screenheight);
			g.drawImage(dreamteamlogo, 229, 231, new Color(1f, 1f, 1f, logotime>120?((240f-(float)logotime)/120f):((float)logotime/120f)));
			break;
		case S_GAMEPLAY:
		case S_ENDSTATS:
			try {
				drawHexagonGrid(g, gx, gy, 150, 150);
			} catch (CellException e1) {
				// Auto-generated catch block
				e1.printStackTrace();
			}
			//g.drawImage(antred, gx+1, gy+1);
			g.setColor(new Color(0f,0f,0f,0.5f));
			g.fillRect(10, screenheight-70, screenwidth-20, 60);
			g.setColor(new Color(1f,1f,1f,1f));
			//g.drawString("grid X speed: "+xaccel, 20, screenheight-65);
			//g.drawString("grid Y speed: "+yaccel, 20, screenheight-35);
			g.drawString("Red Player: " + playerRed, 20, screenheight-65);
			g.drawString("Red Ants: "+ redAnts + " - Red Food: "+redFood, 20, screenheight-35);
			//g.drawString("Zoom level: " + (zoomlevel>0?"Zoomed":"Overview"), screenwidth>>2, screenheight-65);
			g.drawString("Round: " + round, (float) (screenwidth*0.33), screenheight-65);
			g.drawString((GameEngine.maxround-round) + " rounds left", (float) (screenwidth*0.33), screenheight-35);
			//g.drawString("grid X position: "+((screenwidth/2)-gx), screenwidth>>1, screenheight-65);
			//g.drawString("grid Y position: "+((screenheight/2)-gy), screenwidth>>1, screenheight-35);
			g.drawString("Black Player: "+ playerBlack, (float) (screenwidth*0.66), screenheight-65);
			g.drawString("Black Ants: "+blackAnts+ " - Black Food: "+blackFood, (float) (screenwidth*0.66), screenheight-35);
			if(state==S_GAMEPLAY) break; // extra stuff to be drawn after game ends here
			g.drawImage(results, 194, 120);
			if(winningteam == null) {
				g.drawImage(drawPicture, 64, 310);
			} else if(winningteam == TeamColor.RED){
				g.drawImage(redwins, 179, 310);
			} else if(winningteam == TeamColor.BLACK){
				g.drawImage(blackwins, 133, 310);
			}
			g.drawImage(ps2r,261,412);
			break;
		case S_GAMESELECT:
			g.setColor(new Color(1f, 1f, 1f, 1f));
			//g.drawString("Match", 4, 4);
			g.drawImage(singlematch, 0, 0);
			g.drawString("Players", 123, 102);
			//g.drawString("Red Player Name", 202, 148);
			//g.drawString("Black Player Name", 202, 221);
			//g.drawString("Red Player Brain", 615, 148);
			//g.drawString("Black Player Brain", 615, 221);
			g.drawString("Worlds", 130, 283);
			//g.drawString("Add World", 221, 337);
			g.setColor(new Color(0f, 1f, 1f, 1f));
			for(GUIButton b : buttons.get(S_GAMESELECT)) b.render(g);
			g.setColor(new Color(1f, 1f, 1f, 1f));
			g.drawString(brainBlackFile==null?"No black brain file selected":brainBlackFile,  315,  251);
			g.drawString(brainRedFile==null?"No red brain file selected":brainRedFile,  315,  178);
			g.drawString(worldFile==null?"No world file selected, random world will be generated":worldFile,  221,  367);
			fieldRed.render(gc, g);
			fieldBlack.render(gc, g);
			break;
		case S_TITLE:
			//g.drawImage(bg, 0, 0, screenwidth, screenheight, 0, 0, 640, 480);
			g.drawImage(blaze, (screenwidth/2)-(blaze.getWidth()/2), (titleframephase), new Color(1f,1f,1f,0.3f));
			titleframephase-=4;
			if(titleframephase<(-blaze.getHeight())+screenheight) g.drawImage(blaze, (screenwidth/2)-(blaze.getWidth()/2), (titleframephase+blaze.getHeight()), new Color(1f,1f,1f,0.3f));
			if(titleframephase<=(-blaze.getHeight())) titleframephase=0;
			g.drawImage(logo, (screenwidth/2)-(logo.getWidth()/2), 40);
			/*g.setColor(new Color(0f, 1f, 1f, 1f));
			g.fillRect(320, 240, 100, 30);*/
			
			for(GUIButton b : buttons.get(S_TITLE)) b.render(g);
			break;
		default:
			break;
		}
		try {
			for(GUIEffect e : effects)
				{ if(state!=S_TITLE && (e instanceof GUITitleTextEffect)) e.destroy();
				if(!e.isActive()) e=null;
				else try{e.render(g);}catch(ConcurrentModificationException f){} }
		} catch (NullPointerException e) {
			
		}
	}

	public void run()
	{
		 System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(this);
			appgc.setDisplayMode(screenwidth, screenheight, false);
			//appgc.setVSync(true); // seems to make things worse
			appgc.setTargetFrameRate(60);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}