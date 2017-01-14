package networkPlotter;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorPickerFrame extends JFrame
{
	private static final long serialVersionUID = -4472423295938010431L;
	private JColorChooser chooser;
	
	public ColorPickerFrame(Color c)
	{
		super();
		this.setTitle("Color Picker");
		chooser = new JColorChooser(c);
		this.add(chooser);
		this.pack();
		this.setVisible(false);
	}
	
	public Color getColor()
	{
		System.out.println(chooser.getColor());
		return chooser.getColor();
		
	}
}
