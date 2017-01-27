package networkPlotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
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
	private LineSettingsFrame ls;
	private JButton settings;
	
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
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(color));
		this.setMaximumSize(new Dimension(100000,30));
		enabled = new JCheckBox(name);
		enabled.addActionListener(this);
		enabled.setSelected(e);
		this.add(enabled, BorderLayout.WEST);
		colorPicker = new JButton("  ");
		colorPicker.setBackground(color);
		colorPicker.setContentAreaFilled(false);
		colorPicker.setOpaque(true);
		colorPicker.addActionListener(this);
		this.add(colorPicker, BorderLayout.CENTER);
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
		settings = new JButton("⚙");
		settings.addActionListener(this);
		ls = new LineSettingsFrame(name, this);
		this.add(settings, BorderLayout.EAST);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Set<Entry<Integer, Double>> tmpSet = Globals.data.get(name).entrySet();
		if(tmpSet.size() != 0)
		{
			double val = ((Entry<Integer, Double>)tmpSet.toArray()[tmpSet.size()-1]).getValue();
			val = (double)Math.round(val * 1000d) / 1000d;
			colorPicker.setText(Double.toString(val));
		}
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
		else if(e.getSource().equals(settings))
		{
			ls.setVisible(true);
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
		Container parent = this.getParent();
		parent.remove(this);
		parent.revalidate();
		parent.repaint();
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
}
