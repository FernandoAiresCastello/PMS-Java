package pixelmanip;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		Application app = new Application();
		SwingUtilities.invokeLater(app::run);
	}
}
