package aplicacion;

public abstract class Pato extends Elemento{
	
	public static final int limitX = 900 - 77;
	public static final int limitY = 450 - 66;
	
	//Variables de imagen
	private final String right;
	private final String left;
	private final String kill;
	private final String away;
	
	//variables de juego
	private final int scoreKill;
	private boolean alive;
	
	//Variables de movimiento
	private int MvSec;
	private final long segundo = 1000000000;
	private long time;
	

	
	public Pato(int x, int y, int scoreKill, int MvSec ,String r, String l, String k, String aw){
		super(x,y,20,20);
		//finals
		right = "resources/"+r+".png";
		left =  "resources/"+l+".png";
		kill = "resources/"+k+".png";
		away = "resources/"+aw+".png";
		this.scoreKill = scoreKill;
		time = System.nanoTime();
		this.MvSec = MvSec;
		setRoot(right);
		alive = true;
	}

	
	public void move(){
		if(!alive)
			MvSec = 10;
		
		if(System.nanoTime() - time > segundo/MvSec){
				
			if (x + dx > limitX || x + dx <= 0){
				if( getX() + dx > limitX ) setRoot(left);
				if( getX() + dx <= 0 ) setRoot(right);
				dx *= -1;
			}
			if (y + dy > limitY || y + dy <= 0)
				dy *= -1;
	
			x += dx;
			y += dy;
			
			

			
			animar();
			if( !alive && getY() + dy > limitY) setVisible(false);
			if( !alive && getY() + dy < 0 ) setVisible(false);
			time = System.nanoTime();
		}
	}
	
	private void animar(){
		if(!root.contains("k")){
			int s = 1 - Integer.parseInt(root.substring(root.length()-5, root.length()-4)); 
			setRoot(root.substring(0,root.length()-5)+s+".png");
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public boolean disparo(int x, int y){
		return alive && (getX() <= x && x <= getX()+77) && (getY() <= y && y <= getY()+66);
	}
	
	public void alejarse(){
		setRoot(away);
		alive = false;
		dx = 0;
		dy = -20;
	}
	
	public void matar(){
		setRoot(kill);
		alive = false;
		dx = 0;
		dy = 15;
	}
	
	public int getScore(){
		return scoreKill;
	}
}
