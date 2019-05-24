package aplicacion;


public abstract class Maquina extends JugadorCazador {
	
	private Pato target;
	private long time,segundo = 1000000000;
	private int tX, tY;
	public Maquina() {
		super(300, 300);
		time = System.nanoTime();
	}
	
	public abstract void selectTarget();
	
	public void goTo(){
		if(target !=null && target.isAlive() && tieneBalas() && System.nanoTime()-time > segundo/15){
			int[] distancia = ED();
			tX = distancia[0];
			tY = distancia[1];
			if(tX > 0)  moveRight();
			else moveLeft();
			if(tY > 0) moveDown();
			else  moveUp();
			time = System.nanoTime();
		}
	}
	
	public void setTarget(Pato p){
		target = p;
	}
	
	public boolean puedeDisparar(){
		return target!=null && Math.abs(tX) < 70 && Math.abs(tY) < 50 && super.puedeDisparar(); 
	}
	
	
	private int[] ED(){
		return new int[]{target.getX()-getX(),target.getY()-getY()};
	}
	
	public boolean esHumano() {
		return false;
	}
}
