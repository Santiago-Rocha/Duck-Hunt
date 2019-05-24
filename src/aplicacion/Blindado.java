package aplicacion;

public class Blindado extends Pato{

	public Blindado(int x, int y, int MvSec) {
		super(x, y, 200,MvSec,"pblindadoR0","pblindadoL0","kblindado0","ablindado0");
	}
	
	public boolean disparo(int x, int y){
		if(dx > 0)
			return isAlive() && (getX()+20 <= x && x <= getX()+60) && (getY() <= y && y <= getY()+55);
		else
			return isAlive() && (getX() <= x && x <= getX()+40) && (getY() <= y && y <= getY()+55);
	}

}
