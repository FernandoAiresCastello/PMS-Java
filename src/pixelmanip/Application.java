package pixelmanip;

import javax.swing.JFrame;

public class Application extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Screen screen;
	
	public Application() {
		setTitle("Pixel Manipulation FPS Sample");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void run() {
		setSize(640, 480);
		setLocationRelativeTo(null);
		setVisible(true);
		screen = new Screen(this);
	}
	
	public void exit() {
		System.exit(0);
	}
}
