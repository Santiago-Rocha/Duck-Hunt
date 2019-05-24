package aplicacion;

public class BNormal extends Bala{
	
	public BNormal(int x, int y) {
		super(x, y,PoobHunt.BALA_NORMAL);
	}
	
	public void move() {}

	public void efecto(JugadorCazador j, boolean hit) {}
}
