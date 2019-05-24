package presentacion;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel{
	
	public static final String fondoInit = "resources/fondo1.png";
	
	protected JButton jugdor1, jugador2 ,abrir,controles, salir, JvsD, JvsJ, JvsM, JvsM2 ,playM, playK, goBack,imagencontrol;
	private BufferedImage fondo;
	
	public Board(String root){
		super(null);
		prepareElementosInicio();
		try{
			fondo = ImageIO.read(new File(root));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void prepareElementosInicio(){
		removeAll();
		jugdor1 = new Boton("1J", 300, 300);
		add(jugdor1);
		
		jugador2 = new  Boton("2J", 300, 360);
		add(jugador2);
		
		salir = new Boton("salir", 300, 540);
		add(salir);
		
		abrir = new Boton("AbrirB", 283, 420);
		add(abrir);
		
		controles = new Boton("Controles",293,480);
		add(controles);
		 
		repaint();
	}
	
	public void prepareElementosControl() {
		removeAll();
		imagencontrol = new Boton("instrucciones",170,300);
		add(imagencontrol);
		goBack = new Boton("atras",300,550);
		add(goBack);
		repaint();
	}
	
	public void prepareElementos1J(){
		removeAll();
		playM = new Boton("mouse",290,300);
		add(playM);
		
		playK = new Boton("key",290,380);
		add(playK);
		
		goBack = new Boton("atras", 330, 460);
		add(goBack);
		repaint();
	}
	
	public void prepareElementos2J(){
		removeAll();
		JvsJ = new Boton("JvsJ", 340, 300);
		add(JvsJ);
		
		JvsM = new Boton("JvsM", 340, 360);
		add(JvsM);
		
		/*JvsD = new Boton("JvsD",340,480);
		add(JvsD);*/
		
		JvsM2 = new Boton("JvsM2",340,420);
		add(JvsM2);
		
		goBack = new Boton("atras", 320, 540);
		add(goBack);
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(fondo, 0, 0, this);
    }
	
}