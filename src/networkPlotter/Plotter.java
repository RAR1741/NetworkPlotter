package networkPlotter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

public class Plotter extends JPanel implements MouseMotionListener
{
	private static final long serialVersionUID = 6406146367544069608L;
	long start;
	double y;
	double y2;
	public Plotter()
	{
		super();
		start = System.currentTimeMillis();
		y = 0;
		y2 = 600;
		this.addMouseMotionListener(this);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				prevx = e.getX();
			}
		});
		this.setPreferredSize(new Dimension(700, 600));
	}
	
	private int scroll = 0;
	private final static int BOTTOM = 20;
	private int Hscale = 100;
	
	@Override
	public void paint(Graphics g)
	{
		LinkedHashMap<String, Map<Integer, Integer>> tmp = new LinkedHashMap<String, Map<Integer, Integer>>(Globals.data);
		Graphics2D g2d = (Graphics2D) g;
		if(Globals.autoscroll)
		{
			for(String s : Globals.enabled)
			{
				for(Map.Entry<Integer, Integer> e : tmp.get(s).entrySet())
				{
					if(e.getKey()/Hscale > this.getWidth())
					{
						scroll = e.getKey()/Hscale - this.getWidth();
					}
				}
			}
		}
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, this.getHeight()-BOTTOM, this.getWidth(), this.getHeight()-BOTTOM);
		for(int i = -scroll; i < (this.getWidth()); i+=50)
		{
			if(i >= -30 && i <= this.getWidth())
			{
				g2d.drawLine(i, 0, i, this.getHeight()-BOTTOM);
			}
		}
		for(String s : Globals.enabled)
		{
			Integer lastx = null, lasty = null;
			g2d.setColor(Globals.colors.get(s));
			synchronized(tmp.get(s)){ for(Iterator<Map.Entry<Integer, Integer>> iter = tmp.get(s).entrySet().iterator(); iter.hasNext(); )
			{
				Entry<Integer, Integer> e = iter.next();
				if((e.getKey()/Hscale)-scroll-3>=0 && (e.getKey()/Hscale)-scroll-3<=this.getWidth())
				{
					g2d.fillOval((e.getKey()/Hscale)-scroll-3, this.getHeight()-e.getValue()-3-BOTTOM, 6, 6);
					if(lastx == null)
					{
						lastx = (e.getKey()/Hscale);
						lasty = e.getValue();
					}
					int h = this.getHeight();
					g2d.drawLine(lastx-scroll, h-lasty-BOTTOM, (e.getKey()/Hscale)-scroll, h-e.getValue()-BOTTOM);
					lastx = (e.getKey()/Hscale);
					lasty = e.getValue();
				}
			}}
		}
		g2d.setColor(Color.white);
		g2d.fillRect(0, this.getHeight()-BOTTOM, this.getWidth(), this.getHeight()-BOTTOM);
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, this.getHeight()-BOTTOM, this.getWidth(), this.getHeight()-BOTTOM);
		for(int i = -scroll; i < (this.getWidth()); i+=50)
		{
			if(i >= -30 && i <= this.getWidth())
			{
				g2d.drawString(Double.toString((i+scroll)/(1000.0/Hscale)), i, this.getHeight()-8);
			}
		}
		g2d.dispose();
		update();
	}
	
	public void update()
	{
		y+=0.05;
		y2-=0.02;
		Globals.data.get("Test").put((int)(System.currentTimeMillis()-start), (int)y);
		Globals.data.get("Test2").put((int)(System.currentTimeMillis()-start), (int)y2);
		Globals.data.get("sin(x)").put((int)(System.currentTimeMillis()-start), (int)(Math.sin((System.currentTimeMillis()%Integer.MAX_VALUE)/1000.0)* 100 + 200));
	}
	
	public int prevx;
	@Override
	public void mouseDragged(MouseEvent e)
	{
		scroll += prevx - e.getX();
		prevx = e.getX();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
