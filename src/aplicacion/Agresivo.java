package aplicacion;

public class Agresivo extends Maquina {

	public void selectTarget() {
		Pato target = null;
		double distance = Integer.MAX_VALUE;
		int x = PoobHunt.getJuego().getJugador(0).getX();
		int y = PoobHunt.getJuego().getJugador(0).getY();
		for(int i = 0; i < PoobHunt.PATOS_X_RONDA; i++){
			Pato p = (Pato)PoobHunt.getJuego().getPato(i);
			if(p.isAlive() && (target == null || distance(p, x, y) < distance )){
				distance = distance(p, x, y);
				target = p;
			}
		}
		setTarget(target);
	}
	
	public void goTo(){
		selectTarget();
		super.goTo();
	}
	
	public double distance(Pato p, int x, int y){
		return Math.sqrt(Math.pow(p.getX()-x, 2)+Math.pow(p.getY()-y, 2));
	}

}
