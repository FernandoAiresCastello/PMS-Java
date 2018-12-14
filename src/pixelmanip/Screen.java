package pixelmanip;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;

	public final int WIDTH = 640;
	public final int HEIGHT = 480;

	private Application app;
	private Image image;

	public Screen(Application app) {
		this.app = app;
		setPreferredSize(app.getSize());
		app.add(this);
		new Worker().execute();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, this);
	}

	private class Worker extends SwingWorker<Void, Image> {
		
		private final Color[] colors = { 
			Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
			Color.MAGENTA, Color.CYAN, Color.YELLOW, 
			Color.ORANGE, Color.WHITE
		};

		protected void process(List<Image> chunks) {
			for (Image bufferedImage : chunks) {
				image = bufferedImage;
				repaint();
			}
		}

		protected Void doInBackground() throws Exception {
			int frames = 0;
			int[] mem = new int[WIDTH * HEIGHT];
			long start = System.currentTimeMillis();
			long end = start + 15000;
			long last = start;
			while (last < end) {
				int col = colors[frames % colors.length].getRGB();
				for (int y = 0; y < HEIGHT; y++)
					for (int x = 0; x < WIDTH; x++)
						mem[x + y * WIDTH] = col;
				Image img = createImage(new MemoryImageSource(WIDTH, HEIGHT, mem, 0, WIDTH));
				BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = bi.createGraphics();
				g2.drawImage(img, 0, 0, null);
				g2.dispose();
				publish(bi);
				last = System.currentTimeMillis();
				frames++;
			}
			
			String result = String.format("Frames:%s, FPS:%s", frames, ((double) frames / (last - start) * 1000));
			JOptionPane.showMessageDialog(app, result, "Results", JOptionPane.INFORMATION_MESSAGE);
			app.exit();
			
			return null;
		}
	}
}
