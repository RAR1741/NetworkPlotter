package networkPlotter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Globals
{
	public static Map<String, Map<Integer, Double>> data;
	public static List<String> enabled;
	public static Map<String, Color> colors;
	public static Map<String, MinMaxPair> minMax;
	public static boolean autoscroll;
	public static boolean update;
	
	public static void init()
	{
		autoscroll = true;
		update = true;
		data = new LinkedHashMap<String, Map<Integer, Double>>()
		{
			private static final long serialVersionUID = 1475914646030268172L;
			@Override
			public Map<Integer, Double> get(Object o)
			{
				if(!containsKey(o))
				{
					Map<Integer, Double> tmp = new LinkedHashMap<Integer, Double>()
					{
						private static final long serialVersionUID = 1L;
						@Override
						public Double get(Object o)
						{
							if(!containsKey(o))
							{
								return 0.0;
							}
							return super.get(o);
						}
					};
					this.put((String)o, tmp);
					return tmp;
				}
				return super.get(o);
			}
		};
		enabled = new ArrayList<>();
		colors = new LinkedHashMap<String, Color>()
		{
			private static final long serialVersionUID = -387697450451675379L;
			@Override
			public Color get(Object o)
			{
				if(!containsKey(o))
				{
					return Color.red;
				}
				return super.get(o);
			}
		};
		minMax = new HashMap<String, MinMaxPair>()
		{
			private static final long serialVersionUID = 6430770363258542638L;
			@Override
			public MinMaxPair get(Object o)
			{
				if(!containsKey(o))
				{
					return new MinMaxPair(-100, 100);
				}
				return super.get(o);
			}
		};
	}
}
