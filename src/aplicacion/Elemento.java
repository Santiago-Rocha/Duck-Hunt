package aplicacion;

import java.io.Serializable;

public abstract class Elemento implements Serializable {

	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	protected String root;
	protected boolean visible;

	public Elemento(int x, int y, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		visible = true;
	}

	public abstract void move();

	public String getRoot() {
		return root;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setRoot(String r) {
		root = r;
	}
	
	public void setVisible(boolean v){
		visible = v;
	}

}
