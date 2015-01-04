package org.accela.minesweeper.util.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.accela.minesweeper.util.BatchSetter;
import org.junit.Before;
import org.junit.Test;

public class TestBatchSetter
{
	static class Box
	{
		public String box = "box";

		public Box()
		{
			// do nothing
		}

		public Box(String box)
		{
			this.box = box;
		}
	}

	static class Class1
	{
		private int a = 0;
		private String b = null;
		private Box box = null;

		public int getA()
		{
			return a;
		}

		public void setA(int a)
		{
			this.a = a;
		}

		public String getB()
		{
			return b;
		}

		public void setB(String b)
		{
			this.b = b;
		}

		public Box getBox()
		{
			return box;
		}

		public void setBox(Box box)
		{
			this.box = box;
		}
	}

	static class Class2
	{
		private int a = 0;
		private String b = null;
		private Box box = null;

		public int getA()
		{
			return a;
		}

		public void setA(int a)
		{
			this.a = a;
		}

		public String getB()
		{
			return b;
		}

		public void setB(String b)
		{
			this.b = b;
		}

		public Box getBox()
		{
			return box;
		}

		public void setBox(Box box)
		{
			this.box = box;
		}
	}

	static class Class3
	{
		private String name = null;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public Class3()
		{
			//do nothing
		}

		public Class3(String name)
		{
			this.name = name;
		}

		private int a = 0;
		private String b = null;
		private Box box = null;

		public int getA()
		{
			return a;
		}

		public void setA(int a)
		{
			this.a = a;
		}

		public String getB()
		{
			return b;
		}

		public void setB(String b)
		{
			this.b = b;
		}

		public Box getBox()
		{
			return box;
		}

		public void setBox(Box box)
		{
			this.box = box;
		}
	}

	private List<Object> whereToSet = null;
	private BatchSetter bs = null;

	@Before
	public void setUp() throws Exception
	{
		whereToSet = new LinkedList<Object>();
		whereToSet.add(new Class1());
		whereToSet.add(new Class2());
		whereToSet.add(new Class3(null));
		whereToSet.add(new Class1());
		whereToSet.add(new Class2());
		whereToSet.add(new Class3("c3a"));
		whereToSet.add(new Class1());
		whereToSet.add(new Class2());
		whereToSet.add(new Class3("c3b"));

		bs = new BatchSetter();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test()
	{
		Map<String, Object> whatToSet = new HashMap<String, Object>();

		whatToSet.put(Class1.class.getName() + "/*/a", 10);
		whatToSet.put("Class1/null/b", "b");
		whatToSet.put("Class1//box", new Box("ERROR!"));

		whatToSet.put(Class2.class.getName() + "/*/box",
				new Box("Class2's Box"));

		whatToSet.put("Class3/*/a", 11);
		whatToSet.put("Class3/*/b", null);
		whatToSet.put("Class3/c3a/a", 12);
		whatToSet.put("Class3/c3b/a", 13);
		whatToSet.put("Class3/c3b/b", "c3b");

		bs.batchSet(whatToSet, whereToSet.iterator());

		for (Object obj : whereToSet)
		{
			if (obj instanceof Class1)
			{
				Class1 c = (Class1) obj;

				assert (c.a == 10);
				assert (c.b.equals("b"));
				assert (c.box == null);
			}
			else if (obj instanceof Class2)
			{
				Class2 c = (Class2) obj;

				assert (c.a == 0);
				assert (c.b == null);
				assert (c.box.box.equals("Class2's Box"));
			}
			else if (obj instanceof Class3)
			{
				Class3 c = (Class3) obj;

				if (null == c.name)
				{
					assert (c.a == 11);
					assert (c.b == null);
					assert (c.box == null);
				}
				else if (c.name.equals("c3a"))
				{
					assert (c.a == 12);
					assert (c.b == null);
					assert (c.box == null);
				}
				else if (c.name.equals("c3b"))
				{
					assert (c.a == 13);
					assert (c.b.equals("c3b"));
					assert (c.box == null);
				}
				else
				{
					assert (false);
				}
			}
			else
			{
				assert (false);
			}
		}

	}

}
