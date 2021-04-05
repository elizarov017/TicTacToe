import acm.program.*;
import acm.graphics.*;

import java.awt.Color;
import java.awt.event.*;

/**
 * Гра в хрестики нулики на дошці розмірності 3*3
 * @author Yaroshepta B.P.
 *
 */

public class HW11 extends GraphicsProgram {

	public static final int HEIGHT = 600;
	public static final int WIDTH = 600;

	private GObject gobjAtA;
	private GPoint last;
	public int counter = 0, one1, one2, one3, two1, two2, two3, three1, three2, three3;

	public void init() {
		this.setSize(WIDTH, HEIGHT);
		addMouseListeners();
	}
	
	/**
	 * Method which takes all mouse releases
	 */
	public void mouseReleased(MouseEvent a) {
		last = new GPoint(a.getPoint());
		gobjAtA = getElementAt(last);
		if (gobjAtA == null)
			createPlayground();
		else {
			if (counter <= 9 & gobjAtA.getX() >= 0) {
				println("one step");
				oneStep(gobjAtA);
			} else if (gobjAtA.getX() < 0) {
				restart();
			}
		}
	}

	/**
	 * Method to restart the game
	 */
	private void restart() {
		println("restarting...");
		counter = 0;
		one1 = 0;
		one2 = 0;
		one3 = 0;
		two1 = 0;
		two2 = 0;
		two3 = 0;
		three1 = 0;
		three2 = 0;
		three3 = 0;
		GRect newg = new GRect(0, 0, WIDTH, HEIGHT);
		newg.setFilled(true);
		newg.setColor(Color.WHITE);
		newg.setFillColor(Color.WHITE);
		add(newg);
		createPlayground();

	}

	/**
	 * Method of creating playground 3*3
	 */
	private void createPlayground() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				GRect square = new GRect(j * WIDTH / 3, i * HEIGHT / 3, WIDTH / 3, HEIGHT / 3);
				add(square);
			}
		}
	}

	/**
	 * method of drawing a cross in GObj a 
	 * @param a GObj
	 */
	private void theX(GObject a) {
		double x, y, x1, y1, sizeY, sizeX;
		x = a.getX();
		y = a.getY();
		sizeX = WIDTH / 9;
		sizeY = HEIGHT / 9;
		x1 = x + sizeX;
		y1 = y + sizeY;
		GLine one = new GLine(x1, y1, x1 + sizeX, y1 + sizeY);
		GLine two = new GLine(x1, y1 + sizeY, x1 + sizeX, y1);
		add(one);
		add(two);
	}

	/**
	 * method of drawing a circle in GObj a 
	 * @param a GObj
	 */
	private void theO(GObject a) {
		double x, y, x1, y1, sizeX, sizeY;
		x = a.getX();
		y = a.getY();
		sizeX = WIDTH / 9;
		sizeY = HEIGHT / 9;
		x1 = x + sizeX;
		y1 = y + sizeY;
		GOval one = new GOval(x1, y1, sizeX, sizeY);
		add(one);
	}

	/**
	 * one step of game
	 * @param a GObject in which player did his step
	 */
	private void oneStep(GObject a) {
		double x, y;
		int row, coloumn, id;
		boolean f = false;
		x = a.getX();
		y = a.getY();
		coloumn = coloumn(x);
		row = row(y);
		id = getID(coloumn, row);
		println(id);
		if (id == 0) {
			if (counter % 2 == 0) {
				theX(gobjAtA);
				setID(coloumn, row);
			} else {
				theO(gobjAtA);
				setID(coloumn, row);
			}
			a.sendToFront();
			f = checkingWin();
			if (f) {
				end();
			}
			else if (counter < 9) {
				counter++;
				println("counter = " + counter);
			}
		}
		if (counter == 9 & f == false) {
			end();
		}

	}

	/**
	 * end of game
	 */
	private void end() {
		int id;
		if (counter % 2 == 0)
			id = 1;
		else
			id = 2;
		GRect ending = new GRect(-1, -1, HEIGHT, WIDTH);
		ending.setVisible(false);
		add(ending);
		GRect end = new GRect(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 6);
		end.setFilled(true);
		end.setFillColor(Color.WHITE);
		add(end);
		if (counter != 9) {
			String win = "Player " + id + " wins!";
			GLabel endi = new GLabel(win, WIDTH / 3, HEIGHT / 3);
			endi.setFont("Arial-36");
			add(endi);
			GLabel rest = new GLabel("click anywhere to restart", WIDTH / 3, HEIGHT / 3 + 40);
			rest.setFont("Arial-20");
			add(rest);
		} else {
			GLabel endi = new GLabel("DRAW!", WIDTH / 3 + 18, HEIGHT / 3 + 17);
			endi.setFont("Arial-50");
			add(endi);
			GLabel rest = new GLabel("click anywhere to restart", WIDTH / 3, HEIGHT / 3 + 40);
			rest.setFont("Arial-20");
			add(rest);
		}
	}

	/**
	 * method of taking the row of square in whicn player played
	 * @param y getY of square
	 * @return number of row
	 */
	private int row(double y) {
		int row;
		double a;
		if (y == 0)
			row = 0;
		else {
			a = WIDTH / y;
			if (a == 3) {
				row = 1;
			} else
				row = 2;
		}
		return row;
	}

	/**
	 * method of taking the coloumn of square in whicn player played
	 * @param x getx of square
	 * @return number of coloumn
	 */
	private int coloumn(double x) {
		int coloumn;
		double a;
		if (x == 0)
			coloumn = 0;
		else {
			a = WIDTH / x;
			if (a == 3) {
				coloumn = 1;
			} else
				coloumn = 2;
		}
		return coloumn;
	}

	/**
	 * method gets id of square in which player played 
	 * @param c coloumn
	 * @param r row
	 * @return id
	 */
	private int getID(int c, int r) {
		int id = 0;
		if (r == 0 & c == 0)
			id = one1;
		else if (r == 1 & c == 0)
			id = two1;
		else if (r == 2 & c == 0)
			id = three1;
		else if (r == 0 & c == 1)
			id = one2;
		else if (r == 1 & c == 1)
			id = two2;
		else if (r == 2 & c == 1)
			id = three2;
		else if (r == 0 & c == 2)
			id = one3;
		else if (r == 1 & c == 2)
			id = two3;
		else if (r == 2 & c == 2)
			id = three3;
		return id;
	}

	/**
	 * method sets id of square in which player played 
	 * @param c coloumn
	 * @param r row
	 */
	private void setID(int c, int r) {
		int id;
		if (counter % 2 == 0)
			id = 1;
		else
			id = 2;
		if (r == 0 & c == 0)
			one1 = id;
		else if (r == 1 & c == 0)
			two1 = id;
		else if (r == 2 & c == 0)
			three1 = id;
		else if (r == 0 & c == 1)
			one2 = id;
		else if (r == 1 & c == 1)
			two2 = id;
		else if (r == 2 & c == 1)
			three2 = id;
		else if (r == 0 & c == 2)
			one3 = id;
		else if (r == 1 & c == 2)
			two3 = id;
		else if (r == 2 & c == 2)
			three3 = id;
	}

	/**
	 * method checks did any player win
	 * @return true for win and false for none
	 */
	private boolean checkingWin() {
		int id;
		double x, y;
		if (counter % 2 == 0)
			id = 1;
		else
			id = 2;
		if (one1 == id & one2 == id & one3 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, y, WIDTH - x, y);
			add(endLine);
			return true;
		} else if (one1 == id & two2 == id & three3 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, y, WIDTH - x, HEIGHT - y);
			add(endLine);
			return true;
		} else if (one1 == id & two1 == id & three1 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, y, x, HEIGHT - y);
			add(endLine);
			return true;
		} else if (one2 == id & two2 == id & three2 == id) {
			x = WIDTH / 2;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, y, x, HEIGHT - y);
			add(endLine);
			return true;
		} else if (one3 == id & two3 == id & three3 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(WIDTH - x, y, WIDTH - x, HEIGHT - y);
			add(endLine);
			return true;
		} else if (two1 == id & two2 == id & two3 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 2;
			GLine endLine = new GLine(x, y, WIDTH - x, y);
			add(endLine);
			return true;
		} else if (three1 == id & three2 == id & three3 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, HEIGHT - y, WIDTH - x, HEIGHT - y);
			add(endLine);
			return true;
		} else if (one3 == id & two2 == id & three1 == id) {
			x = WIDTH / 6;
			y = HEIGHT / 6;
			GLine endLine = new GLine(x, HEIGHT - y, WIDTH - x, y);
			add(endLine);
			return true;
		} else
			return false;
	}
}
