package networkPlotter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 4974031669602868511L;
	JCheckBox check;
	JTextField ip;
	JTextField add;
	JPanel space;
	JSpinner maxSpinner;
	
	public ControlPanel()
	{
		super();
		this.setPreferredSize(new Dimension(150, 600));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		check = new JCheckBox("Autoscroll");
		check.setAlignmentX(LEFT_ALIGNMENT);
		check.setSelected(true);
		check.addActionListener(this);
		this.add(check);
		this.add(new JLabel("Robot IP", JLabel.LEFT));
		ip = new JTextField();
		ip.setAlignmentX(LEFT_ALIGNMENT);
		ip.setColumns(30);
		ip.setMaximumSize(ip.getPreferredSize());
		ip.setText("roborio-1782-frc.local");
		ip.addActionListener(this);
		this.add(ip);
		JPanel tmp = new JPanel();
		tmp.setMaximumSize(new Dimension(500, 30));
		tmp.add(new JLabel("Max Value: "));
		tmp.setAlignmentX(LEFT_ALIGNMENT);
		maxSpinner = new JSpinner(new SpinnerNumberModel(100.0, -10000.0, 10000.0, 1.0));
		maxSpinner.addChangeListener(this);
		tmp.add(maxSpinner);
		this.add(tmp);
		this.add(new JLabel("Add more data:",JLabel.LEFT));
		add = new JTextField();
		add.setAlignmentX(LEFT_ALIGNMENT);
		add.setColumns(30);
		add.setMaximumSize(add.getPreferredSize());
		add.addActionListener(this);
		space = new JPanel();
		space.setLayout(new BoxLayout(space, BoxLayout.Y_AXIS));
		space.setAlignmentX(LEFT_ALIGNMENT);
		this.add(add);
		this.add(space);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(check))
		{
			Globals.autoscroll = check.isSelected();
		}
		else if(e.getSource().equals(ip))
		{
			NetworkTable.setIPAddress(ip.getText());
		}
		else if(e.getSource().equals(add))
		{
			addLineData(add.getText());
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource().equals(maxSpinner))
		{
			Globals.vertMax = (double) maxSpinner.getValue();
		}
	}
	
	public void addLineData(String name)
	{
		space.add(new LineData(name));
		Globals.enabled.add(name);
		Globals.data.put(name, Globals.data.get(name));
		space.revalidate();
		space.repaint();
	}
}
