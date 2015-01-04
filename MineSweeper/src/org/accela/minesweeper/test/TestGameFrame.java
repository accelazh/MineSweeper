package org.accela.minesweeper.test;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.UIManager;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.lang.chs.ChsUI;
import org.accela.minesweeper.ui.skin.blue.BlueUI;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.BasicButtonPanel.BtnState;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.MineFieldPanel;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.MineGridPanel.BtnDownState;
import org.accela.minesweeper.view.MineGridPanel.BtnUpState;
import org.accela.minesweeper.view.MineGridPanel.IconState;
import org.accela.minesweeper.view.NumberPanel;
import org.accela.minesweeper.view.SmilePanel;
import org.junit.Before;
import org.junit.Test;

public class TestGameFrame
{
	private GameFrame frame = null;

	private GameUI skin = null;

	private GameUI lang = null;

	private Random rand = new Random();

	private MineGridListener mgListener = new MineGridListener();

	private SmilePanelListener spListener = new SmilePanelListener();

	private NumberPanelListener npListener = new NumberPanelListener();

	private boolean[][] mineGrids = null;

	@Before
	public void setUp() throws Exception
	{
		Util.setSwingFont(new Font("MS Sans Serif", Font.PLAIN, 12));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame = new GameFrame();

		frame.getGamePanel().getStatus().getLeftNumber().setDigitCount(3);
		frame.getGamePanel().getStatus().getRightNumber().setDigitCount(3);

		frame.getGamePanel().getStatus().getSmile()
				.addMouseListener(spListener);
		frame.getGamePanel().getStatus().getLeftNumber()
				.addMouseListener(npListener);
		frame.getGamePanel().getStatus().getRightNumber()
				.addMouseListener(npListener);

		//setGridSize(15, 20);
		setGridSize(70, 40);
		
//		MineFieldPanel fp=frame.getGamePanel().getMineField();
//		for(int x=0;x<fp.getGridSize().width;x++)
//		{
//			for(int y=0;y<fp.getGridSize().height;y++)
//			{
//				MineGridPanel g=fp.getGrid(x, y);
//				g.addMouseListener(new MouseAdapter(){
//
//					@Override
//					public void mouseEntered(MouseEvent e)
//					{
//						MineGridPanel eg=(MineGridPanel)e.getSource();
//						System.out.println("Mouse Entered: "+eg.getPosition());
//					}
//
//					@Override
//					public void mouseExited(MouseEvent e)
//					{
//						MineGridPanel eg=(MineGridPanel)e.getSource();
//						System.out.println("Mouse Exited: "+eg.getPosition());
//					}
//				});
//			}
//		}
	}

	private void setGridSize(int width, int height)
	{
		frame.getGamePanel().getMineField().setFieldSize(width, height);
		this.addMineGridListener();

		this.mineGrids = new boolean[height][width];
		for (int i = 0; i < this.mineGrids.length; i++)
		{
			for (int j = 0; j < this.mineGrids[i].length; j++)
			{
				this.mineGrids[i][j] = (rand.nextInt(5) > 0) ? false : true;
			}
		}
	}

	private Point[] getPosAround(Point pos)
	{
		Point[] posAround = new Point[] {
				new Point(pos.x - 1, pos.y - 1),
				new Point(pos.x - 1, pos.y),
				new Point(pos.x - 1, pos.y + 1),
				new Point(pos.x, pos.y - 1),
				new Point(pos.x, pos.y + 1),
				new Point(pos.x + 1, pos.y - 1),
				new Point(pos.x + 1, pos.y),
				new Point(pos.x + 1, pos.y + 1) };

		return posAround;
	}

	private boolean checkInBound(Point pos)
	{
		Dimension size = this.frame.getGamePanel().getMineField().getFieldSize();
		assert (size.width == mineGrids[0].length);
		assert (size.height == mineGrids.length);

		return (pos.x >= 0 && pos.x < size.width)
				&& (pos.y >= 0 && pos.y < size.height);
	}

	private Point[] getPosAroundInBound(Point pos)
	{
		Point[] posAround = getPosAround(pos);
		List<Point> posInBound = new LinkedList<Point>();
		for (Point p : posAround)
		{
			if (checkInBound(p))
			{
				posInBound.add(p);
			}
		}

		return posInBound.toArray(new Point[0]);
	}

	private int countMineAround(Point pos)
	{
		int sum = 0;
		Point[] around = getPosAroundInBound(pos);
		for (Point p : around)
		{
			if (mineGrids[p.y][p.x])
			{
				sum++;
			}
		}

		return sum;
	}

	private void coverMineGrid(Point pos)
	{
		this.frame.getGamePanel().getMineField().getGrid(pos.x, pos.y)
				.setBtnState(BtnState.UP);
		this.getGrid(pos).setIconState(IconState.CLEAR);
	}

	private void uncoverMineGrid(Point pos)
	{
		MineGridPanel g = getGrid(pos);
		g.setBtnState(BtnState.DOWN);

		if (mineGrids[pos.y][pos.x])
		{
			markMineGridBomb(pos);
			g.setBtnDownState(BtnDownState.DEAD);
			return;
		}

		int mineAround = countMineAround(pos);
		System.out.println("mineAround: " + mineAround);
		if (0 == mineAround)
		{
			g.setBtnDownState(BtnDownState.SAFE);
		}
		else
		{
			g.setIconState(MineGridPanel.numToIconState(mineAround));
			g.setBtnDownState(BtnDownState.ALERT);
		}

	}

	private void markMineGridBomb(Point pos)
	{
		int r = rand.nextInt(3);
		switch (r)
		{
		case 0:
			getGrid(pos).setIconState(IconState.BOMB);
			break;
		case 1:
			getGrid(pos).setIconState(IconState.WRONG_BOMB);
			break;
		case 2:
			getGrid(pos).setIconState(IconState.DEAD_BOMB);
			break;
		default:
			assert (false);
			break;
		}
	}

	private MineGridPanel getGrid(Point pos)
	{
		return this.frame.getGamePanel().getMineField().getGrid(pos.x, pos.y);
	}

	private void markMineGridAnyIcon(Point pos)
	{
		int r = rand.nextInt(6 + Common.MINE_GRID_MAX_NUMBER
				- Common.MINE_GRID_MIN_NUMBER + 1);

		MineGridPanel g = getGrid(pos);
		if (0 == r)
		{
			g.setIconState(IconState.CLEAR);
		}
		else if (1 == r)
		{
			g.setIconState(IconState.FLAG);
		}
		else if (2 == r)
		{
			g.setIconState(IconState.QUESTION);
		}
		else if (3 == r)
		{
			g.setIconState(IconState.BOMB);
		}
		else if (4 == r)
		{
			g.setIconState(IconState.WRONG_BOMB);
		}
		else if (5 == r)
		{
			g.setIconState(IconState.DEAD_BOMB);
		}
		else
		{
			g.setIconState(MineGridPanel.numToIconState(r - 6
					+ Common.MINE_GRID_MIN_NUMBER));
		}

	}

	private class MineGridListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			MineGridPanel g = (MineGridPanel) e.getSource();
			Point pos = g.getParentField().getGridPos(g);

			if (e.getButton() == 1)
			{
				if (g.getBtnState().equals(BtnState.UP))
				{
					uncoverMineGrid(pos);
				}
				else
				{
					coverMineGrid(pos);
				}
			}
			else if (e.getButton() == 3)
			{
				markMineGridAnyIcon(pos);
			}
			else
			{
				// do nothing
			}

			g.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			MineGridPanel g = (MineGridPanel) e.getSource();

			g.setBtnUpState(BtnUpState.GLOW);
			g.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			MineGridPanel g = (MineGridPanel) e.getSource();

			g.setBtnUpState(BtnUpState.NORMAL);
			g.repaint();
		}

	}

	private class SmilePanelListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			SmilePanel panel = (SmilePanel) e.getSource();

			int r = rand.nextInt(4);
			if (0 == r)
			{
				panel.setIconState(SmilePanel.IconState.SMILE);
			}
			else if (1 == r)
			{
				panel.setIconState(SmilePanel.IconState.NERVOUS);
			}
			else if (2 == r)
			{
				panel.setIconState(SmilePanel.IconState.DEAD);
			}
			else
			{
				panel.setIconState(SmilePanel.IconState.WIN);
			}

			if (r % 2 == 0)
			{
				panel.setBtnState(BtnState.UP);
			}
			else
			{
				panel.setBtnState(BtnState.DOWN);
			}

			int fieldWidth = rand.nextInt(10) + 10;
			int fieldHeight = rand.nextInt(10) + 10;
			setGridSize(fieldWidth, fieldHeight);
			frame.pack();
			frame.revalidate();

			int numberWidthLeft = rand.nextInt(5) + 1;
			int numberWidthRight = rand.nextInt(5) + 1;
			frame.getGamePanel().getStatus().getLeftNumber()
					.setDigitCount(numberWidthLeft);
			frame.getGamePanel().getStatus().getRightNumber()
					.setDigitCount(numberWidthRight);
			frame.pack();
			frame.revalidate();

			panel.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			SmilePanel s = (SmilePanel) e.getSource();

			s.setBtnUpState(SmilePanel.BtnUpState.GLOW);
			s.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			SmilePanel s = (SmilePanel) e.getSource();

			s.setBtnUpState(SmilePanel.BtnUpState.NORMAL);
			s.repaint();
		}
	}

	private class NumberPanelListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			NumberPanel panel = (NumberPanel) e.getSource();
			panel.setNumber(rand.nextInt());

			int orignNum = panel.getNumber();
			panel.setNumber(panel.getNumber());
			System.out.println("NumberPanel.getNumber() == " + orignNum);

			panel.repaint();
		}
	}

	private void addMineGridListener()
	{
		MineFieldPanel field = frame.getGamePanel().getMineField();
		Dimension size = field.getFieldSize();
		for (int x = 0; x < size.width; x++)
		{
			for (int y = 0; y < size.height; y++)
			{
				MineGridPanel grid = field.getGrid(x, y);
				grid.addMouseListener(mgListener);
			}
		}
	}

	@Test
	public void test() throws InterruptedException
	{
		lang = new ChsUI();
		lang.install(frame);
		skin = new BlueUI();
		//skin = new ClassicUI();
		//skin = new BlackWhiteUI();
		skin.install(frame);

		frame.pack();
		frame.revalidate();

		frame.setVisible(true);

		while (true)
		{
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) throws Exception
	{
		TestGameFrame t = new TestGameFrame();
		t.setUp();
		t.test();
	}

}
