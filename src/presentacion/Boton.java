package presentacion;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;



public class Boton extends JButton {
	
	private BufferedImage imagen;
	public Boton(String root, int x, int y){
		super();
		setRequestFocusEnabled(false);
		try{
			imagen = ImageIO.read(new File("resources/"+root+".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBounds(x, y, imagen.getWidth(), imagen.getHeight());
	}
	
	public void paint(Graphics g){
		g.drawImage(imagen, 0, 0, null);
	}
}
