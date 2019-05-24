package aplicacion;

public class Jefe  extends Pato{
	
	private int shots;
	
	public Jefe(int x, int y, int MvSec) {
		super(x, y, 300,3*MvSec,"pjefeR0","pjefeL0","kjefe0","ajefe0");
		shots =2;
	}
	
	public boolean disparo(int x, int y){
		boolean ans = super.disparo(x, y);
		if(ans)
			if(shots != 0) {
				shots--;
				return false;
			}
		return ans;
	}

}
