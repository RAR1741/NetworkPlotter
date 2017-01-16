package networkPlotter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorPickerFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -4472423295938010431L;
	private JColorChooser chooser;
	private JButton apply;
	private ActionListener listener;
	
	public ColorPickerFrame(Color c)
	{
		super();
		this.setTitle("Color Picker");
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		chooser = new JColorChooser(c);
		chooser.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(chooser);
		apply = new JButton("Apply");
		apply.addActionListener(this);
		apply.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(apply);
		this.pack();
		this.setVisible(false);
	}
	
	public Color getColor()
	{
		return chooser.getColor();
	}
	
	public void setListener(ActionListener a)
	{
		listener = a;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "apply");
		listener.actionPerformed(ae);
	}
}
