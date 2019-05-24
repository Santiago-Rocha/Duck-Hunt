package presentacion;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

import aplicacion.PoobHunt;

public class PoobHuntGUI extends JFrame implements Runnable, KeyListener {

	// Pantalla
	private Board tableroInicio;
	private BoardJuego tableroJuego;
	private JPanel principal;
	private CardLayout layout;
	
	//sonidos
	private Clip c;
	
	// threads
	private Thread t;
	
	//movimientos
	private boolean j1Up, j1Down, j1Right, j1Left, j2Up, j2Down, j2Right, j2Left;

	// Juego
	private PoobHunt juego;

	private PoobHuntGUI() {
		super("Poob Hunt");
		prepareElementos();
		prepareAcciones();
		addKeyListener(this);
		setFocusable(true);
	}

	private void prepareElementos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900, 680));
		setResizable(false);
		setLocationRelativeTo(null);
		prepareTablero();

	}
	
	private void salvar(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File f = new File("./"+fileChooser.getSelectedFile().getName()+".dat");
			juego.salvar(f);
		}
	
	}
	
	private void abrir(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			PoobHunt.abra(f);
			juego  = PoobHunt.getJuego();
			c.stop();
			prepareElementosJuego(juego.getJugadores(),juego.isPlayingWithMouse());
			actualizar();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void prepareAcciones() {
		tableroInicio.jugdor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementos1J();
				prepareAcciones1J();
			}
		});
		
		tableroInicio.salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salga();
			}
		});
		
		tableroInicio.jugador2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementos2J();
				prepareAcciones2J();
			}
		});
		
		tableroInicio.controles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementosControl();
				prepareAccionesControl();
			}
		});
		
		tableroInicio.abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				salga();
			}

		});
	}
	
	public void prepareAcciones1J(){
		tableroInicio.playM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(0, 1, true);
			}
		});
		
		tableroInicio.playK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(0,1,false);
			}
		});
		
		tableroInicio.goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementosInicio();
				prepareAcciones();
			}
		});
	}
	
	public void prepareAcciones2J(){
		tableroInicio.JvsJ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(0,2,false);
			}
		});
		
		/*tableroInicio.JvsD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});*/
		
		tableroInicio.JvsM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(1,2, false);
			}
		});
		
		tableroInicio.JvsM2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar(2,2, false);
			}
		});
		
		tableroInicio.goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementosInicio();
				prepareAcciones();
			}
		});
	}
	
	public void prepareAccionesControl() {
		tableroInicio.goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableroInicio.prepareElementosInicio();
				prepareAcciones();
			}
		});
	}
	
	public void prepareAccionesJuego(){
		tableroJuego.guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		
		tableroJuego.abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});
		preapreReinicio();
	}
	
	public void preapreReinicio(){
		tableroJuego.reiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juego.endGame();
				reiniciar();
			}
		});
	}

	private void salga() {
		int result = JOptionPane.showConfirmDialog(null, "Esta seguro que desea salir ?", "Confirmacion de salida : ",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION)
			System.exit(0);
		else if (result == JOptionPane.NO_OPTION)
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void prepareElementosJuego(int players, boolean mouse){
		tableroJuego = new BoardJuego(players);
		principal.add(tableroJuego, "tjue");
		t = new Thread(this);
		prepareJugadores();
		layout.show(principal, "tjue");
		t.start();
	}
	
	public void iniciar(int machine, int players, boolean mouse) {
		c.stop();
		PoobHunt.nuevoJuego();
		juego = PoobHunt.getJuego();
		juego.prepareJugadores(players, mouse, machine);
		prepareElementosJuego(players, mouse);
		
	}
	
	private void reiniciar(){
		sonidoIntro();
		tableroInicio.prepareElementosInicio();
		prepareAcciones();
		layout.show(principal, "tini");
		tableroInicio.repaint();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void End(){
		for(int i = 0; i < juego.getJugadores(); i++){
			tableroJuego.setKills(i, juego.getKills(i));
		}
		tableroJuego.End();
		preapreReinicio();
	}

	public void actualizar() {
		actualizarHits();
		actualizarPatos();
		actualizarScore();
		actualizarBalas();
		if(juego.getJugadores()== 2 && !juego.getJugador(1).esHumano()) actualizarJugadores();
		if(juego.getState() == 1) tableroJuego.showAway();
		tableroJuego.repaint();
	}
	
	private void actualizarHits(){
		for (int i = 0; i < PoobHunt.PATOS_X_NIVEL; i++) {
			Sprite s = tableroJuego.getHit(i);
			s.setRoot(juego.getMuertes(i));
			s.setX(360 + (i*26));
			s = tableroJuego.getHit(i);
		}
	}
	
	private void actualizarBalas(){
		
		for(int i = 0; i < juego.getJugadores(); i++){
			int j;
			for (j = 0; j < juego.getCargadorN(i) ; j++) {
				Sprite s =  tableroJuego.getBala((i*3)+j);
				s.setRoot(juego.getBalaN(i, j).getRoot());
				s.setX(juego.getBalaN(i, j).getX()+(i*665));
				s.setY(juego.getBalaN(i, j).getY());
				s.setVisible(juego.getBalaN(i, j).isVisible());
			}
			
			for(; j < PoobHunt.BALAS_X_RONDA; j++) tableroJuego.getBala((i*3)+j).setVisible(false);
		}
	}
	
	private void actualizarScore() {
		for(int i = 0; i < juego.getJugadores(); i++){
			tableroJuego.setScore(i, juego.getPuntajeJugadorN(i));
		}
	}
	
	private void actualizarPatos(){
		for (int i = 0; i < 2; i++) {
			Sprite s;
			try {
				s = tableroJuego.getSprite(i);
			} catch (IndexOutOfBoundsException ex) {
				tableroJuego.addSprite();
				s = tableroJuego.getSprite(i);
			}
			if (juego.getPato(i).isVisible()) {
				s.setX(juego.getPato(i).getX());
				s.setY(juego.getPato(i).getY());
				s.setRoot(juego.getPato(i).getRoot());
			} 
			s.setVisible(juego.getPato(i).isVisible());
		}
	}
	
	private void actualizarJugadores(){
		for(int i = 0; i < juego.getJugadores(); i++){
			Sprite s = tableroJuego.getJugador(i);
			s.setX(juego.getJugador(i).getX());
			s.setY(juego.getJugador(i).getY());
		}
	}

	private void prepareJugadores(){
		if(juego.isPlayingWithMouse()){
			tableroJuego.PrepareCursor();
			tableroJuego.addScore();
			tableroJuego.addCargador();
			tableroJuego.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me){
					juego.ApuntarN(0, me.getX(), me.getY());
					
				}
			});
		}
		else{
			for (int i = 0; i < juego.getJugadores(); i++){
				tableroJuego.addJugador();
				tableroJuego.getJugador(i).setRoot("resources/target"+i+".png");
			}
			actualizarJugadores();
		}
	}
	
	private void prepareTablero() {
		layout = new CardLayout();
		principal = new JPanel(layout);
		tableroInicio = new Board(Board.fondoInit);
		add(principal);
		principal.add(tableroInicio, "tini");
		layout.show(principal, "tini");
		sonidoIntro();
	}

	public static void main(String[] args) {
		PoobHuntGUI juego = new PoobHuntGUI();
		juego.setVisible(true);
	}
	
	private void sonidoIntro(){
		try{
			InputStream is = PoobHuntGUI.class.getResourceAsStream("/sonidos/intro.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			c = (Clip) AudioSystem.getLine(info);
			c.open(ais);
			c.start();
			c.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (!juego.isFinished()) {
				juego.ronda();
				tableroJuego.showedGame();
				actualizar();
				Thread.sleep(100);
				while (!juego.isRondaFinished()) {
					if(!juego.enPausa()){
						juego.mover();
						actualizar();
					}
					Thread.sleep(20);
				}
			}
			End();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(juego != null){	
			
			if(keyCode ==  KeyEvent.VK_H){
				juego.pausa();
				if(juego.enPausa()){
					tableroJuego.Pause();
					prepareAccionesJuego();
				}
				else tableroJuego.removeAll();
				
			}
			
			if(!juego.isPlayingWithMouse()){
				if(keyCode == KeyEvent.VK_W) j1Up = true;
				if(keyCode == KeyEvent.VK_S) j1Down = true;
				if(keyCode == KeyEvent.VK_D) j1Right = true;
				if(keyCode == KeyEvent.VK_A) j1Left = true;
				if(keyCode == KeyEvent.VK_SPACE) {
					actualizarBalas();
					juego.ApuntarN(0);
				}
				
				if(j1Up) juego.JugadorNUp(0);
				if(j1Down) juego.JugadorNDown(0);
				if(j1Right) juego.JugadorNRight(0);
				if(j1Left) juego.JugadorNLeft(0);
				
				
				if(juego.getJugadores() == 2 && juego.getJugador(1).esHumano()){
					if(keyCode == KeyEvent.VK_UP) j2Up = true;
					if(keyCode == KeyEvent.VK_DOWN) j2Down = true;
					if(keyCode == KeyEvent.VK_RIGHT) j2Right = true;
					if(keyCode == KeyEvent.VK_LEFT) j2Left = true;
					if(keyCode == KeyEvent.VK_P){
						actualizarBalas();
						juego.ApuntarN(1);
					}
					
					if(j2Up) juego.JugadorNUp(1);
					if(j2Down) juego.JugadorNDown(1);
					if(j2Right) juego.JugadorNRight(1);
					if(j2Left) juego.JugadorNLeft(1);
					
				}
				actualizarJugadores();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_W) j1Up = false;
		if(keyCode == KeyEvent.VK_S) j1Down = false;
		if(keyCode == KeyEvent.VK_D) j1Right = false;
		if(keyCode == KeyEvent.VK_A) j1Left = false;
		if(keyCode == KeyEvent.VK_UP) j2Up = false;
		if(keyCode == KeyEvent.VK_DOWN) j2Down = false;
		if(keyCode == KeyEvent.VK_RIGHT) j2Right = false;
		if(keyCode == KeyEvent.VK_LEFT) j2Left = false;
	}
	public void keyTyped(KeyEvent e) {}

}
