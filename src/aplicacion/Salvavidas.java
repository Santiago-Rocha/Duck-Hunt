package aplicacion;

public class Salvavidas extends Bala {
	
	public boolean efectoActivado;
	
	public Salvavidas(int x, int y) {
		super(x, y,PoobHunt.BALA_SALVAVIDAS);
	}
	
	public int[] radio(int x, int y){
		return new int[]{x+16,y+16};
	}
	

	public void move() {}

	public void efecto(JugadorCazador j, boolean hit) {
		if(!efectoActivado && !hit){
			efectoActivado = true;
			setVisible(true);
		}
	}
}
