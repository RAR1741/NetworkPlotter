package networkPlotter;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main
{
	public static JFrame frame;
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		Globals.init();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame = new JFrame("NetworkPlotter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new Plotter(), new ControlPanel());
		frame.add(split);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Globals.enabled.add("Test");
		Globals.enabled.add("Test2");
		Globals.enabled.add("sin(x)");
		Globals.colors.put("Test2", Color.blue);
		Globals.colors.put("sin(x)", Color.green);
		Globals.data.put("Test", Globals.data.get("Test"));
		Globals.data.put("Test2", Globals.data.get("Test2"));
		Globals.data.put("sin(x)", Globals.data.get("sin(x)"));
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-1782-frc.local");
		while(true)
		{
			
			frame.repaint();
			Thread.yield();
		}
	}
}
