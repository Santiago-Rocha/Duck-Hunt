package aplicacion;

import java.io.Serializable;
import java.util.*;

public abstract class JugadorCazador implements Serializable{
	
	protected static final int VELOCIDAD = 30;
	private static final int LIMIT_Y = 650;
	private static final int LIMIT_X = 900;
	
	
	private int x,y,score, lastScore,Nmuertes, totalMuertes;
	private long hitTime,v = 1000000000/2;
	private Random ran;
	private ArrayList<Bala> balas;
	
	public JugadorCazador(int x, int y) {
		this.x = x;
		this.y = y;
		balas = new ArrayList<Bala>();
		hitTime = System.nanoTime();
		ran = new Random();
	}
	
	//Metodos de movimiento
	public void moveUp(){
		if(y-VELOCIDAD > -16)
			y-=VELOCIDAD;
	}
	
	public void moveDown(){
		if(y+VELOCIDAD < LIMIT_Y)
			y+=VELOCIDAD;
	}
	
	public void moveRight(){
		if(x+VELOCIDAD < LIMIT_X)
			x+=VELOCIDAD;
	}
	public void moveLeft(){
		if(x-VELOCIDAD > -16)
			x-=VELOCIDAD;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	
	public Bala  disparo (){
		for(int i  = 0; i < balas.size(); i++)
			if(balas.get(i).isVisible()){
				balas.get(i).setVisible(false);
				return balas.get(i);
			}
		return null;
	};
	
	public void addScore(int value){
		lastScore = value;
		score+=value;
	}
	
	public void addMuerte(){
		Nmuertes++;
		totalMuertes++;
	}
	
	public void nuevoNivel(){
		Nmuertes = 0;
	}
	
	public void nuevaRonda(boolean jugadores){
		balas.clear();
		for(int i = 0; i < PoobHunt.BALAS_X_RONDA; i++)
			if(jugadores) 
				balas.add(BalaRandom(62+(i*35),520));
			else
				balas.add(BalaRandom(95+(i*35),570));
	}
	
	public Elemento getBala(int i){
		return balas.get(i);
	}

	public int getScore(){
		return score;
	}
	
	public int lastScore(){
		return lastScore;
	}
	
	public int getCargador(){
		return balas.size();
	}
	
	public int getKills(){
		return totalMuertes;
	}
	
	public boolean tieneBalas(){
		boolean ans = false;
		for(Bala b: balas){
			ans |= b.isVisible();
		}
		return ans;
	}
	
	public boolean puedeDisparar(){
		if(System.nanoTime() - hitTime > v){
			hitTime = System.nanoTime();
			return true;
		}
		return false;

	}
	
	public boolean isLoser(int totalPatos){
		return Nmuertes < totalPatos/2;
	}
	
	public void semilla(int s){
		ran.setSeed(s);
	}
	
	public abstract boolean esHumano();
	
	private Bala BalaRandom(int x, int y){
		Bala bala = null;
		int[] seleccion = new int[]{1,1,1,2,3};
		int n = seleccion[ran.nextInt(seleccion.length)];
		if(n==1) bala = new BNormal(x,y);
		else if(n==2) bala = new Salvavidas(x,y);
		else if(n==3) bala = new DoblePuntaje(x, y);
		return bala;
	}
}
