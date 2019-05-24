package presentacion;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class BoardJuego extends JPanel{
	private BufferedImage fondo,flyAway, fondoEnd,W1,W2,kill;
	private ArrayList<Sprite> patos;
	private ArrayList<Sprite> jugadores;
	private ArrayList<Sprite> balas;
	private ArrayList<String> scores;
	private ArrayList<String> kills;
	private Sprite[] Hits;
	protected Boton guardar, abrir, reiniciar;
	private boolean showAway, showPause, showEnd;
	

	public BoardJuego(int n) {
		super(new BorderLayout());
		scores = new ArrayList<String>();
		kills = new ArrayList<String>();
		patos = new ArrayList<Sprite>();
		balas = new ArrayList<Sprite>();
		Hits = new Sprite[10]; for(int i = 0; i < 10; i++) Hits[i] = new Sprite(0, 580, true);  //Cambiar el hard coding
		jugadores = new ArrayList<Sprite>();
		try {
			fondo = ImageIO.read(new File("resources/fondo"+n+"J.png"));
			flyAway = ImageIO.read(new File("resources/awayLabel.png"));
			fondoEnd = ImageIO.read(new File("resources/fondoE.png"));
			W1 = ImageIO.read(new File("resources/W1.png"));
			W2 = ImageIO.read(new File("resources/W2.png"));
			kill = ImageIO.read(new File("resources/kill.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showedDog(boolean value){
		//showedDog = value;
	}
	
	public void showedGame(){
		//showedGame = true;
		//showedDog = false;
		showAway = false;
	}
	
	public void showEnd(){
		//showedGame = false;
		//showedDog = false;
		showAway = false;
		//showEnd = true;
	}
	
	public void showAway(){
		showAway = true;
	}
	
	public void PrepareCursor(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("resources/target0.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
		setCursor(c);
	}
	
	public void setScore(int n,int i) {
		scores.set(n, Integer.toString(i));
	}
	
	public void setKills(int n, int i){
		kills.set(n, Integer.toString(i));
	}
	
	public Sprite getHit(int i){
		return Hits[i];
	}
	
	public Sprite getBala(int i){
		return balas.get(i);
	}
	
	public Sprite getJugador(int i){
		return jugadores.get(i);
	}

	public Sprite getSprite(int i) {
		return patos.get(i);
	}
	
	public void addScore(){
		scores.add("");
		kills.add("");
	}

	public void addSprite() {
		patos.add(new Sprite(0, 0, false));
	}
	
	public void addCargador(){
		for(int i = 0; i < 3; i++) balas.add(new Sprite(0,0,true));
	}
	
	public void addJugador(){
		jugadores.add(new Sprite(0, 550,true,32,32));
		addCargador();
		addScore();
	}

	public void End(){
		showEnd = true;
		reiniciar = new Boton("reiniciar", 10, 70);  add(reiniciar);
		repaint();
	}

	public void Pause(){
		guardar = new Boton("guardar1", 10, 10); add(guardar);
		abrir = new Boton("abrir",10,40); add(abrir);
		reiniciar = new Boton("reiniciar", 10, 70);  add(reiniciar);
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if(showEnd){
			g.drawImage(fondoEnd, 0, 0, this);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.BOLD, 28));
			if (scores.size() == 1){
				g.drawImage(W1, 100, 500,this);
				g.drawImage(kill, 120, 550, this);
				g.drawString("X", 170, 580);
				g.drawString(kills.get(0), 200, 580);
				g.drawString("=", 240, 580);
				g.drawString(scores.get(0), 270, 580);
			}
			else {
				g.drawImage(W1, 100, 500,this);
				g.drawImage(kill, 120, 550, this);
				g.drawString("X", 170, 580);
				g.drawString(kills.get(0), 200, 580);
				g.drawString("=", 230, 580);
				g.drawString(scores.get(0), 270, 580);
				g.drawImage(W2, 400, 500,this);
				g.drawImage(kill, 420, 550, this);
				g.drawString("X", 470, 580);
				g.drawString(kills.get(1), 500, 580);
				g.drawString("=", 530, 580);
				g.drawString(scores.get(1), 570, 580);
				
			}
		}
		else{
			g.drawImage(fondo, 0, 0, this);
			for (Sprite h: Hits)h.paint((Graphics2D) g);
			for(Sprite b: balas) b.paint((Graphics2D) g);
			for (Sprite s : patos) s.paint((Graphics2D) g);	
			for(Sprite j: jugadores) j.paint((Graphics2D) g);
			if(showAway) g.drawImage(flyAway, 350, 100, this);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.BOLD, 36));
			if (scores.size() == 1)
				g.drawString(scores.get(0), 730, 590);
			else {
				g.drawString(scores.get(0), 100, 615);
				g.drawString(scores.get(1), 760, 615);
			}
		}
		paintComponents(g);
	}

}
