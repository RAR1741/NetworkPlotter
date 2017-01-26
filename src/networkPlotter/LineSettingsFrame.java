package networkPlotter;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LineSettingsFrame extends JFrame implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = -4615618764467787348L;
	
	private String name;
	private JSpinner maxSpinner;
	private JSpinner minSpinner;
	private JButton remove;
	
	public LineSettingsFrame(String name)
	{
		super();
		this.name = name;
		this.setTitle(name + " Settings");
		this.getContentPane().setLayout(new GridLayout(0, 1));
		maxSpinner = new JSpinner(new SpinnerNumberModel(100.0, -100000.0, 100000.0, 1.0));
		minSpinner = new JSpinner(new SpinnerNumberModel(-100.0, -100000.0, 100000.0, 1.0));
		maxSpinner.addChangeListener(this);
		minSpinner.addChangeListener(this);
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("Maximum Value:"));
		tmp.add(maxSpinner);
		this.add(tmp);
		tmp = new JPanel();
		tmp.add(new JLabel("Minimum Value:"));
		tmp.add(minSpinner);
		this.add(tmp);
		remove = new JButton("Remove");
		remove.addActionListener(this);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(remove))
		{
			
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource().equals(maxSpinner))
		{
			//TODO change max value for line
		}
		else if(e.getSource().equals(minSpinner))
		{
			//TODO change min value for line
		}
	}
	
	public void remove()
	{
		Globals.data.remove(name);
		Globals.enabled.remove(name);
		Globals.colors.remove(name);
	}
}
