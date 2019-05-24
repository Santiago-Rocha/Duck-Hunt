package aplicacion;

public class Pasivo extends Maquina{
	public void selectTarget(){
		Pato target = null;
		for(int i = 0; i < PoobHunt.PATOS_X_RONDA; i++){
			Pato p = (Pato)PoobHunt.getJuego().getPato(i);
			if(p.isAlive() && (target == null || p.getScore() > target.getScore())) target = p;
		}
		setTarget(target);
	}

}
