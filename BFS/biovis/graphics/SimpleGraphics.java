package BFS.biovis.graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BFS.biovis.data.Matrix;

public final class SimpleGraphics
{
	
	
	private static JFrame initFrame(String title)
	{
		JFrame ret = new JFrame(title);
		
		ret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ret.setLayout(null);
		
		return ret;
	}
	
	public static BufferedImage getMatrixAsImage(Matrix mat, int factor, boolean inColor)
	{
		BufferedImage img = new BufferedImage(mat.getWidth() * factor, mat.getHeight() * factor, BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < img.getHeight(); y += factor)
		{
			for(int x = 0; x < img.getWidth(); x += factor)
			{
				int color = 0;
				if(inColor) { color = (int)(((mat.get(x / factor, y / factor, 0)) + 1) * 8388607); }
				else { color = (int)(((mat.get(x / factor, y / factor, 0)) + 1) * 127); }
				
				for(int i = 0; i < factor; i++)
				{
					for(int j = 0; j < factor; j++)
					{
						if(inColor) { img.setRGB(x + i, y + j, color); }
						else { img.setRGB(x + i, y + j, (color | color << 8 | color << 16)); }
					}
				}
			}
		}
		return img;
	}
	public static JLabel getMatrixAsLabel(Matrix mat, int factor, boolean inColor)
	{
		BufferedImage img = getMatrixAsImage(mat, factor, inColor);
		JLabel imgLabel = new JLabel(new ImageIcon(img));
		imgLabel.setBounds(0, 0, img.getWidth(), img.getHeight());
		return imgLabel;
	}
	public static JFrame displayMatrixImage(Matrix mat, int factor, boolean inColor)
	{
		JFrame frame = initFrame(inColor ? "Greyscale Matrix" : "Color Matrix");
		if(factor < 1) { factor = 1; }
		JLabel imgLabel = getMatrixAsLabel(mat, factor, inColor);
		frame.getContentPane().add(imgLabel);

		frame.setVisible(true);
		int top = frame.getInsets().top;
		frame.setVisible(false);
		frame.setSize(imgLabel.getWidth(), imgLabel.getHeight() + top);
		frame.setVisible(true);
		return frame;
	}
	public static JFrame displayMatrixImageWithInterface(Matrix mat, int factor, boolean inColor)
	{
		JFrame frame = initFrame(inColor ? "Greyscale Matrix" : "Color Matrix");
		if(factor < 25) { factor = 25; }
		JLabel imgLabel = getMatrixAsLabel(mat, factor, inColor);
		frame.getContentPane().add(imgLabel);
		imgLabel.setBounds(factor, factor, imgLabel.getWidth(), imgLabel.getHeight());
		
		JPanel colPanel = new JPanel(null);
		colPanel.setBounds(factor, 0, imgLabel.getWidth(), factor);
		int index = 1;
		for(int i = 0; index <= 167; i += factor)
		{
			JPanel numPanel = new JPanel(null);
			JLabel number = new JLabel(String.valueOf(index));
			number.setBounds(0, 0, factor, factor);
			numPanel.setBounds(i, 0, factor, factor);
			number.setForeground(Color.WHITE);
			numPanel.setBackground((index % 2 == 0) ? Color.BLACK : Color.DARK_GRAY);
			numPanel.add(number);
			colPanel.add(numPanel);
			index++;
		}
		JPanel rowPanel = new JPanel(null);
		rowPanel.setBounds(0, factor, factor, imgLabel.getHeight());
		rowPanel.setBackground(Color.BLACK);
		index = 1;
		for(int i = 0; index <= 167; i += factor)
		{
			JPanel numPanel = new JPanel(null);
			JLabel number = new JLabel(String.valueOf(index));
			number.setBounds(0, 0, factor, factor);
			numPanel.setBounds(0, i, factor, factor);
			number.setForeground(Color.WHITE);
			numPanel.setBackground((index % 2 == 0) ? Color.BLACK : Color.DARK_GRAY);
			numPanel.add(number);
			rowPanel.add(numPanel);
			index++;
		}
		
		frame.addKeyListener(new NaviKeyListener(imgLabel, colPanel, rowPanel, factor));

		frame.getContentPane().add(colPanel); 
		frame.getContentPane().add(rowPanel);
		frame.getContentPane().setComponentZOrder(imgLabel, 2);
		frame.setVisible(true);
		int top = frame.getInsets().top;
		frame.setVisible(false);
		frame.setSize(imgLabel.getWidth(), imgLabel.getHeight() + top);
		frame.setVisible(true);
		return frame;
	}
	
	
	public static class NaviKeyListener implements KeyListener
	{
		private int factor;
		private JComponent comp;
		private JPanel horzPanel, vertPanel;
		
		public NaviKeyListener(JComponent component, JPanel horz, JPanel vert, int factor)
		{
			this.comp = component;
			this.factor = factor;
			this.horzPanel = horz;
			this.vertPanel = vert;
		}
		
		@Override public void keyPressed(KeyEvent e) 
		{  
			Rectangle bounds = comp.getBounds();
			Rectangle hBounds = horzPanel.getBounds();
			Rectangle vBounds = vertPanel.getBounds();
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_D:
					comp.setBounds(bounds.x - factor, bounds.y, bounds.width, bounds.height);
					horzPanel.setBounds(hBounds.x - factor, hBounds.y, hBounds.width, hBounds.height);
					break;
				case KeyEvent.VK_S:
					comp.setBounds(bounds.x, bounds.y - factor, bounds.width, bounds.height);
					vertPanel.setBounds(vBounds.x, vBounds.y - factor, vBounds.width, vBounds.height);
					break;
				case KeyEvent.VK_A:
					comp.setBounds(bounds.x + factor, bounds.y, bounds.width, bounds.height);
					horzPanel.setBounds(hBounds.x + factor, hBounds.y, hBounds.width, hBounds.height);
					break;
				case KeyEvent.VK_W:
					comp.setBounds(bounds.x, bounds.y + factor, bounds.width, bounds.height);
					vertPanel.setBounds(vBounds.x, vBounds.y + factor, vBounds.width, vBounds.height);
					break;
			}
		}
		@Override public void keyReleased(KeyEvent e) 
		{  
			
		}
		@Override public void keyTyped(KeyEvent e) {  }
	}
}