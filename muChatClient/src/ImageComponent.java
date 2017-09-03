

import java.awt.*;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
	
	// The image to display
	private Image img;
	
	ImageComponent(String path) {
		img = Toolkit.getDefaultToolkit().getImage(path);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(img.getWidth(null), img.getHeight(null));
	}
}
