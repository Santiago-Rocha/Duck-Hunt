package aplicacion;


import java.io.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import presentacion.PoobHuntGUI;


public class PoobHunt implements Serializable {
	public static final int PATOS_X_RONDA = 2;
	public static final int PATOS_X_NIVEL = 10;
	public static final int BALAS_X_RONDA = 3;
	
	public static final String HIT_DUCK =  "resources/Hit0.png";
	public static final String BALA_NORMAL = "resources/B1.png";
	public static final String BALA_RICOCHET = "resources/B2.png";
	public static final String BALA_SALVAVIDAS = "resources/B3.png";
	public static final String BALA_DPUNTAJE = "resources/B4.png";
	
	public static final long segundo = 1000000000;
	
	private Random ran;
	private JugadorCazador[] jugadores;
	private Pato[] patos;
	private String[] muertes;
	private int indice;
	private int state; //0 --> jugando  | 1 -- > away  | 2 --> end
	private long time;
	private long time_x_ronda;
	private boolean playMouse,enPausa,onMute;
	
	private static PoobHunt juego = null; 
	
	private PoobHunt(){
		ran = new Random();
		onMute = false;
		preparePatos();
	}
	
	//Singleton
	public static PoobHunt getJuego(){
		if(juego == null)
			juego = new PoobHunt();
		return juego;
	}
	
	public static void nuevoJuego(){
		juego = new PoobHunt();
	}
	
	public static void cambieJuego(PoobHunt j){
		juego = j;
	}
	
	//Salvar-Guardar
	
	public void salvar (File f){
		try{
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(f));
			System.out.println(juego);
			oos.writeObject(juego);
			oos.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void abra (File f) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			cambieJuego((PoobHunt)ois.readObject() );
			juego.time = System.nanoTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Move el jugador
	public void JugadorNUp(int n){jugadores[n].moveUp();}
	public void JugadorNDown(int n){jugadores[n].moveDown();}
	public void JugadorNLeft(int n){jugadores[n].moveLeft();}
	public void JugadorNRight(int n){jugadores[n].moveRight();}
	
	public void ApuntarN(int n){
		if(jugadores[n].tieneBalas() && !enPausa && jugadores[n].puedeDisparar()){
			disparar(n, jugadores[n].getX(), jugadores[n].getY());
			if(!onMute)sonidoArma();
		}
	}
	
	public void ApuntarN(int n,int x, int y){
		if(jugadores[n].tieneBalas() && !enPausa && jugadores[n].puedeDisparar()){
			disparar(n,x,y);
			if(!onMute) sonidoArma();
		}
	}
	
	public void ronda(){
		if(patos[0] == null || isRondaFinished()){
			for(int i = 0; i < jugadores.length; i++) jugadores[i].nuevaRonda(jugadores.length == 2);
			int i = 0;
			if(indice == PATOS_X_NIVEL - PATOS_X_RONDA){
				patos[0] = new Jefe(ran.nextInt(Pato.limitX),Pato.limitY,10);
				i++;
			}
			for(; i < PATOS_X_RONDA; i++)patos[i] = PatoRandom();
				
		}
		for(JugadorCazador j: jugadores) if(!j.esHumano()) ((Maquina) j).selectTarget();
		time = System.nanoTime();
		time_x_ronda = segundo*5;
		state = 0;
	}
	
	public void nivel(){
		muertes = new String[PATOS_X_NIVEL]; Arrays.fill(muertes, HIT_DUCK);
		indice = 0;
		for(JugadorCazador j: jugadores) j.nuevoNivel();
	}

	public void mover(){
		for (Pato p : patos){
			p.move();
		}
		for(JugadorCazador j: jugadores) 
			if(!j.esHumano()){
				Maquina m = (Maquina) j;
				m.goTo();
				ApuntarN(1);
			}
		if(System.nanoTime() -time > time_x_ronda) alejarse();
	}
	
	public void alejarse(){
		time = Long.MAX_VALUE;
		state = 1;
		for(Pato p: patos){
			if(p.isAlive()){
				p.alejarse();
				indice++;
			}
		}
	}
	
	public void pausa(){
		if(!enPausa)time_x_ronda = time_x_ronda-(System.nanoTime()-time);
		else time = System.nanoTime();
		enPausa = !enPausa;
	}
	
	public void semilla(int s){
		onMute = true;
		ran.setSeed(s);
	}
	
	public String getMuertes(int i){
		return muertes[i];
	}
	
	public Elemento getPato(int i){
		return patos[i];
	}
	
	public Elemento getBalaN(int n,int i){
		return jugadores[n].getBala(i);
	}
	
	public JugadorCazador getJugador(int i){
		return jugadores[i];
	}
	
	public int getPuntajeJugadorN(int n){
		return jugadores[n].getScore();
	}
	
	public int getKills(int n){
		return jugadores[n].getKills();
	}
	
	public int getJugadores(){
		return jugadores.length;
	}
	
	public int getState(){
		return state;
	}
	
	public int getCargadorN(int n){
		return jugadores[n].getCargador();
	}
	
	public void endGame(){
		indice = PATOS_X_NIVEL;
		for(Pato p: patos) p.setVisible(false);
		for(JugadorCazador j: jugadores) j.nuevoNivel();
	}
	
	
	public boolean isFinished(){
		boolean ans = false;
		if(indice  ==  PATOS_X_NIVEL){
			for(JugadorCazador j : jugadores){
				ans |= j.isLoser(PATOS_X_NIVEL);
			}
		}
		if(!ans && (indice == 0 || indice == PATOS_X_NIVEL))
			nivel();
		return ans;
	}
	
	public boolean isRondaFinished(){
		boolean ans = true;
		for(Pato p: patos){
			ans &= !p.isVisible();
		}
		return ans;
	}
	
	public boolean isPlayingWithMouse(){
		return playMouse;
	}
	
	public boolean enPausa(){
		return enPausa;
	}
	
	
	public void prepareJugadores(int NJugadores, boolean playMouse, int machine){
		this.playMouse = playMouse;
		jugadores = new JugadorCazador[NJugadores];
		jugadores[0] = new Humano();
		if(NJugadores == 2) 
			if(machine > 0)
				jugadores[1]  = machine == 2 ? new Agresivo() : new Pasivo();
			else
				jugadores[1] = new Humano();
	}
	
	private void preparePatos(){
		patos = new Pato[PATOS_X_RONDA];
	}
	
	private void disparar(int n, int x, int y){
		boolean hit = false;
		Bala b = jugadores[n].disparo();
		int t[] = b.radio(x, y);
		for(Pato p :patos){
			if( p.disparo(t[0],t[1]) ){
				hit = true;
				muertes[indice] = muertes[indice].replace("0", Integer.toString(n+1));
				indice++;
				jugadores[n].addScore(p.getScore());
				jugadores[n].addMuerte();
				p.matar();
				for(JugadorCazador j: jugadores) if(!j.esHumano()) ((Maquina)j).selectTarget();
			}
		}
		b.efecto(jugadores[n], hit);
	}
	
	private Pato PatoRandom(){
		Pato pato = null;
		int[] seleccion = new int[]{1,1,1,2,3,4};
		int n = seleccion[ran.nextInt(seleccion.length)];
		if(n==1) pato = new Normal(ran.nextInt(Pato.limitX),Pato.limitY,10);
		else if(n==2) pato = new Rapido(ran.nextInt(Pato.limitX),Pato.limitY,10);
		else if(n==3) pato = new Doble(ran.nextInt(Pato.limitX),Pato.limitY,10);
		else if(n==4) pato = new Blindado(ran.nextInt(Pato.limitX),Pato.limitY,10);
		
		return pato;
	}
	
	private void sonidoArma(){
		try{
			InputStream is = PoobHuntGUI.class.getResourceAsStream("/sonidos/gun.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			Clip c = (Clip) AudioSystem.getLine(info);
			c.open(ais);
			c.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
