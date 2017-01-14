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
	
	public LineData(String name)
	{
		this(name, Color.red);
	}
	
	public LineData(String name, Color color)
	{
		super();
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.name = name;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		enabled = new JCheckBox(name);
		enabled.addActionListener(this);
		enabled.setSelected(true);
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
	}
}
