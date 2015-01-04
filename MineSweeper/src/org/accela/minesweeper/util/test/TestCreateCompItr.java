package org.accela.minesweeper.util.test;

import java.awt.Component;
import java.util.Iterator;

import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.GameFrame;
import org.junit.Before;
import org.junit.Test;

public class TestCreateCompItr
{

	private GameFrame frame = null;

	@Before
	public void setUp() throws Exception
	{
		frame = new GameFrame();
	}

	// Check the results
	@Test
	public void test()
	{
		Iterator<Component> itr = Util.createCompItr(frame);
		while (itr.hasNext())
		{
			Component c = itr.next();
			System.out.println(c.getClass().getSimpleName() + "\t\t\t"
					+ c.getName());
		}

	}

	@Test
	public void test2()
	{
		Iterator<Component> itr = Util.createCompItr(frame
				.getCustomMineFieldDialog());
		while (itr.hasNext())
		{
			Component c = itr.next();
			System.out.println(c.getClass().getSimpleName() + "\t\t\t"
					+ c.getName());
		}

	}

	@Test
	public void test3()
	{
		Iterator<Component> itr = Util
				.createCompItr(frame.getHighScoreDialog());
		while (itr.hasNext())
		{
			Component c = itr.next();
			System.out.println(c.getClass().getSimpleName() + "\t\t\t"
					+ c.getName());
		}

	}

}
