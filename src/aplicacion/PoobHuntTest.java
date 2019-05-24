package aplicacion;

import static org.junit.Assert.*;
import org.junit.Test;

public class PoobHuntTest{
	
	public PoobHuntTest(){}
	@Test
	public void deberianMoverPato() throws InterruptedException {
		PoobHunt.nuevoJuego();
		PoobHunt juego = PoobHunt.getJuego();
		juego.semilla(24);
		juego.prepareJugadores(1,false,0);
		juego.nivel();
		juego.ronda();
		assertTrue(juego.getPato(0).isVisible());
		assertTrue(juego.getPato(0).getX() == 349);
		assertTrue(juego.getPato(0).getY() == 384);
		Thread.sleep(150);
		juego.mover();
		assertTrue(juego.getPato(0).getX() == 369);
		assertTrue(juego.getPato(0).getY() == 364);
		for(int i = 0; i < 10; i++){
			Thread.sleep(150);
			juego.mover();
		}
		assertTrue(juego.getPato(0).getX() == 569);
		assertTrue(juego.getPato(0).getY() == 164);
    }
	
	@Test
	public void deberiaMatarPato() throws InterruptedException{
		PoobHunt.nuevoJuego();
		PoobHunt juego = PoobHunt.getJuego();
		juego.semilla(24);
		juego.prepareJugadores(1,false,0);
		juego.nivel();
		juego.ronda();
		assertTrue(juego.getPato(0).isVisible());
		Thread.sleep(150);
		juego.mover();
		Thread.sleep(350);
		juego.ApuntarN(0, 354,348);
		assertFalse(((Pato)juego.getPato(0)).isAlive());
	}
	
	@Test
	public void deberiaMatarDoble() throws InterruptedException{
		PoobHunt.nuevoJuego();
		PoobHunt juego = PoobHunt.getJuego();
		juego.semilla(650);
		juego.prepareJugadores(1,false,0);
		juego.nivel();
		juego.ronda();
		assertTrue(juego.getPato(0).isVisible());
		Thread.sleep(150);
		juego.mover();
		Thread.sleep(350);
		juego.ApuntarN(0, 598,364);
		assertTrue(((Pato)juego.getPato(0)).isAlive());
		Thread.sleep(500);
		juego.ApuntarN(0, 598,364);
		assertFalse(((Pato)juego.getPato(0)).isAlive());
	}
	
	@Test
	public void deberiaMatarBlindado() throws InterruptedException{
		PoobHunt.nuevoJuego();
		PoobHunt juego = PoobHunt.getJuego();
		juego.semilla(350);
		juego.prepareJugadores(1,false,0);
		juego.nivel();
		juego.ronda();
		assertTrue(juego.getPato(0).isVisible());
		Thread.sleep(150);
		juego.mover();
		assertTrue(((Pato)juego.getPato(0)).isAlive());
		Thread.sleep(350);
		juego.ApuntarN(0, 701,364); //Disparo en la cola
		assertTrue(((Pato)juego.getPato(0)).isAlive());
		Thread.sleep(500);
		juego.ApuntarN(0, 760,384);//Disparo en la cabeza
		assertTrue(((Pato)juego.getPato(0)).isAlive());
		
	}
	@Test
	public void deberiaFallarBalaSalvaVidas() throws InterruptedException{
		PoobHunt.nuevoJuego();
		PoobHunt juego = PoobHunt.getJuego();
		juego.semilla(24);
		juego.prepareJugadores(1,false,0);
		juego.getJugador(0).semilla(300);
		juego.nivel();
		juego.ronda();
		Thread.sleep(150);
		juego.mover();
		Thread.sleep(450);
		assertEquals(juego.getCargadorN(0), 3);
		juego.ApuntarN(0, 10,10);
		assertEquals(juego.getCargadorN(0), 3);
		Thread.sleep(600);
		juego.ApuntarN(0, 354,348);
		assertFalse(((Pato)juego.getPato(0)).isAlive());
	}
	
}