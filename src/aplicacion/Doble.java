package aplicacion;

public class Doble extends Pato{
	private boolean escudo;
	
	public Doble(int x, int y, int MvSec) {
		super(x, y, 200,MvSec,"pdobleR0","pdobleL0","kdoble0","adoble0");
		escudo = true;
	}
	
	public boolean disparo(int x, int y){
		boolean ans = super.disparo(x, y);
		if(ans)
			if(escudo) return escudo = false;
		return ans;
	}
}
