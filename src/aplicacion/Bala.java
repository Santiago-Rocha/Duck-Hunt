package aplicacion;

import java.io.Serializable;

public abstract class  Bala  extends Elemento{

	public Bala(int x, int y,String type) {
		super(x, y, 0, 20);
		setRoot(type);
	}
	
	public int[] radio(int x, int y){
		return new int[]{x+16,y+16};
	}
	
	public abstract void efecto(JugadorCazador j, boolean hit);
	
	public void move(){}
}
