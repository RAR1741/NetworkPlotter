package networkPlotter;

import java.util.Map;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ControlPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 4974031669602868511L;
	JCheckBox check;
	JCheckBox update;
	JTextField ip;
	JTextField add;
	JTextField exportName;
	JPanel space;
	JButton export;
	JButton discover;
	JScrollPane scroll;
	
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
		update = new JCheckBox("Update");
		update.setAlignmentX(LEFT_ALIGNMENT);
		update.setSelected(true);
		update.addActionListener(this);
		this.add(update);
		this.add(new JLabel("Robot IP", JLabel.LEFT));
		ip = new JTextField();
		ip.setAlignmentX(LEFT_ALIGNMENT);
		ip.setColumns(30);
		ip.setMaximumSize(ip.getPreferredSize());
		ip.setText("roborio-1782-frc.local");
		ip.addActionListener(this);
		this.add(ip);
		JPanel tmp = new JPanel();
		tmp.setAlignmentX(LEFT_ALIGNMENT);
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		exportName = new JTextField();
		exportName.setColumns(20);
		exportName.setMaximumSize(exportName.getPreferredSize());
		tmp.add(exportName);
		export = new JButton("Export");
		export.addActionListener(this);
		tmp.add(export);
		this.add(tmp);
		discover = new JButton("Discover");
		discover.addActionListener(this);
		this.add(discover);
		this.add(new JLabel("Add more data:",JLabel.LEFT));
		add = new JTextField();
		add.setAlignmentX(LEFT_ALIGNMENT);
		add.setColumns(30);
		add.setMaximumSize(add.getPreferredSize());
		add.addActionListener(this);
		this.add(add);
		space = new JPanel();
		space.setLayout(new BoxLayout(space, BoxLayout.Y_AXIS));
		space.setAlignmentX(LEFT_ALIGNMENT);
		scroll = new JScrollPane(space, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setAlignmentX(LEFT_ALIGNMENT);
		this.add(scroll);
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
			NetworkTable.shutdown();
			NetworkTable.setIPAddress(ip.getText());
			NetworkTable.initialize();
		}
		else if(e.getSource().equals(add))
		{
			addLineData(add.getText(), true);
		}
		else if(e.getSource().equals(export))
		{
			Globals.update = false;
			try
			{
				new File(System.getProperty("user.home")+ "/NetworkPlotter/").mkdir();
				PrintWriter pw;
				for(Map.Entry<String, Map<Integer, Double>> entry : Globals.data.entrySet())
				{
					pw = new PrintWriter(new File(System.getProperty("user.home")+ "/NetworkPlotter/" + exportName.getText() + "-" + entry.getKey() + ".csv"));
					pw.println("time," + entry.getKey() + ",");
					int tmp = 0;
					for(Map.Entry<Integer, Double> entry2 : Globals.data.get(entry.getKey()).entrySet())
					{
						if(tmp % 10 == 0)
						{
							pw.println(entry2.getKey() + "," + entry2.getValue() + ",");
						}
						tmp++;
					}
					pw.close();
					Globals.update = true;
				}
				JOptionPane.showMessageDialog(this, "File exported successfully");
			}
			catch (FileNotFoundException e1)
			{
				JOptionPane.showMessageDialog(this, "File export failed");
				e1.printStackTrace();
			}
			Globals.update = true;
		}
		else if(e.getSource().equals(update))
		{
			Globals.update = update.isSelected();
		}
		else if(e.getSource().equals(discover))
		{
			NetworkTable table = NetworkTable.getTable("logging");
			Globals.data.clear();
			Globals.enabled.clear();
			Globals.colors.clear();
			space.removeAll();
			space.revalidate();
			space.repaint();
			for(String s : table.getKeys())
			{
				addLineData(s, false);
			}
			scroll.repaint();
			scroll.revalidate();
		}
	}
	
	public void addLineData(String name, boolean enabled)
	{
		space.add(new LineData(name, enabled));
		if(enabled)
		{
			Globals.enabled.add(name);
		}
		Globals.data.put(name, Globals.data.get(name));
		space.revalidate();
		space.repaint();
	}
}
