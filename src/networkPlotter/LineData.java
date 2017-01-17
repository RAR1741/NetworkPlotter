package networkPlotter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class LineData extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -1177907420798848640L;
	
	private JCheckBox enabled;
	private JButton colorPicker;
	private String name;
	private ColorPickerFrame cp;
	private JButton remove;
	
	public LineData(String name, boolean enabled)
	{
		this(name, new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)), enabled);
	}
	
	public LineData(String name, Color color, boolean e)
	{
		super();
		Globals.colors.put(name, color);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.name = name;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		enabled = new JCheckBox(name);
		enabled.addActionListener(this);
		enabled.setSelected(e);
		this.add(enabled);
		colorPicker = new JButton("  ");
		colorPicker.setBackground(color);
		colorPicker.setContentAreaFilled(false);
		colorPicker.setOpaque(true);
		colorPicker.addActionListener(this);
		this.add(colorPicker);
		cp = new ColorPickerFrame(color);
		cp.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				colorPicker.setBackground(cp.getColor());
				colorPicker.repaint();
				Globals.colors.put(name, cp.getColor());
			}
		});
		cp.setListener(this);
		remove = new JButton("X");
		remove.addActionListener(this);
		this.add(remove);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(enabled))
		{
			if(enabled.isSelected())
			{
				Globals.enabled.add(name);
			}
			else
			{
				while(Globals.enabled.contains(name))
				{
					Globals.enabled.remove(name);
				}
			}
		}
		else if(e.getSource().equals(colorPicker))
		{
			cp.setVisible(true);
		}
		else if(e.getSource().equals(remove))
		{
			remove();
		}
		else if(e.getSource().equals(cp))
		{
			colorPicker.setBackground(cp.getColor());
			colorPicker.repaint();
			Globals.colors.put(name, cp.getColor());
		}
	}
	
	public void remove()
	{
		Globals.data.remove(name);
		Globals.enabled.remove(name);
		Globals.colors.remove(name);
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
}
