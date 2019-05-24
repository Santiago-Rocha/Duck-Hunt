package aplicacion;

public class DoblePuntaje extends Bala{
	public DoblePuntaje(int x, int y) {
		super(x, y,PoobHunt.BALA_DPUNTAJE);
	}

	public int[] radio(int x, int y){
		return new int[]{x+16,y+16};
	}

	public void efecto(JugadorCazador j, boolean hit) {
		if(hit)
			j.addScore(j.lastScore());
	}

}
