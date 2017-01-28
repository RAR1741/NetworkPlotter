package networkPlotter;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main
{
	public static JFrame frame;
	
	static
	{
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-1782-frc.local");
	}
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		Globals.init();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame = new JFrame("NetworkPlotter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new Plotter(), new ControlPanel());
		split.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				split.setDividerLocation((split.getWidth()-200.0)/split.getWidth());
			}
		});
		frame.add(split);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		while(true)
		{
			frame.repaint();
			Thread.sleep(10);
		}
	}
}
