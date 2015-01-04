package org.accela.minesweeper.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.accela.minesweeper.model.MineGridModel.State;
import org.accela.minesweeper.model.operation.Operation;
import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.util.Matrix;
import org.accela.minesweeper.util.ObjectActionEvent;
import org.accela.minesweeper.view.MineGridPanel;

public class MineFieldModel implements ActionListener, Iterable<MineGridModel>
{
	public static final String GRID_UPDATED_ACTION_COMMAND = "gridUpdated";
	public static final String GRID_DOWN_ACTION_COMMAND = "gridDown";
	public static final String FLAG_ON_ACTION_COMMAND = "flagOn";
	public static final String FLAG_OFF_ACTION_COMMAND = "flagOff";

	private Matrix<MineGridModel> grids = new Matrix<MineGridModel>(
			new MineGridModel[0]);

	private EventManager eventManager = new EventManager();

	private GameState gameState = null;

	private boolean noGridDown = true;

	private int mineCountOnStart = 0;

	private MineGridModel curGridMouseIn = null;

	public MineFieldModel(GameState gameState)
	{
		this.gameState = gameState;

		this.setFieldSize(1, 1);
	}

	public void addActionListener(ActionListener l)
	{
		eventManager.addActionListener(l);
	}

	public void removeActionListener(ActionListener l)
	{
		eventManager.removeActionListener(l);
	}

	public int getMineCountOnStart()
	{
		return mineCountOnStart;
	}

	public void setMineCountOnStart(int mineCount)
	{
		this.mineCountOnStart = mineCount;
	}

	public Dimension getFieldSize()
	{
		return grids.getSize();
	}

	public void setFieldSize(int width, int height)
	{
		if (width < 1 || height < 1)
		{
			throw new IllegalArgumentException(
					"width and height should not be less than 1");
		}

		if (grids.getWidth() == width && grids.getHeight() == height)
		{
			MineGridModel template = new MineGridModel(this, gameState);
			for (MineGridModel g : this)
			{
				if (null == g)
				{
					g = new MineGridModel(this, gameState);
				}
				else
				{
					g.setMine(template.isMine());
					g.setState(template.getState());
					g.setMarkState(template.getMarkState());
					g.setBombState(template.getBombState());
				}
			}

			return;
		}

		for (MineGridModel g : grids)
		{
			if (g != null)
			{
				g.removeActionListener(this);
			}
		}

		this.grids.setSize(width, height);
		for (int i = 0; i < grids.getWidth(); i++)
		{
			for (int j = 0; j < grids.getHeight(); j++)
			{
				grids.set(i, j, new MineGridModel(this, gameState));
			}
		}

		this.initGrids();
	}

	public void reset(int width, int height, int mineCount)
	{
		this.setFieldSize(width, height);
		this.noGridDown = true;
		this.mineCountOnStart = mineCount;
	}

	private void initGrids()
	{
		for (MineGridModel g : grids)
		{
			g.addActionListener(this);
		}
	}

	public MineGridModel getGrid(int x, int y)
	{
		return grids.get(x, y);
	}

	public Point getGridPos(MineGridModel g)
	{
		return grids.getPos(g);
	}

	public MineGridModel[] getGridAround(MineGridModel grid)
	{
		return grids.getObjAround(grid);
	}

	public int countMineAround(MineGridModel grid)
	{
		int sum = 0;
		for (MineGridModel g : getGridAround(grid))
		{
			if (g.isMine())
			{
				sum++;
			}
		}

		return sum;
	}

	public int countFlagAround(MineGridModel grid)
	{
		int sum = 0;
		for (MineGridModel g : getGridAround(grid))
		{
			if ((g.getState().equals(MineGridModel.State.UP_GLOW) || g
					.getState().equals(MineGridModel.State.UP_NORMAL))
					&& g.getMarkState().equals(MineGridModel.MarkState.FLAG))
			{
				sum++;
			}
		}

		return sum;
	}

	public void uncoverAllBombsAndWrongBombs(List<MineGridModel> modified)
	{
		for (MineGridModel g : grids)
		{
			if (g.isMine())
			{
				if (!g.isFlagOn())
				{
					g.setState(State.DOWN);
					modified.add(g);
				}
			}
			else
			{
				if (g.isFlagOn())
				{
					g.setState(State.DOWN);
					g.setBombState(MineGridModel.BombState.WRONG);
					modified.add(g);
				}
			}
		}
	}

	public boolean isMineAllFlaged()
	{
		for (MineGridModel g : grids)
		{
			if (g.isMine() && !g.isFlagOn())
			{
				return false;
			}
		}

		return true;
	}

	public boolean hasWrongFlag()
	{
		for (MineGridModel g : grids)
		{
			if (!g.isMine() && g.isFlagOn())
			{
				return true;
			}
		}

		return false;
	}

	public void onOperation(Operation op)
	{
		this.handleCurGridMouseIn(op);

		if (gameState.getState().equals(GameState.State.DEAD)
				|| gameState.getState().equals(GameState.State.WIN))
		{
			return;
		}
		if (op.getMouseEvent() != null
				&& (op.getType() == Operation.Type.MOUSE_PRESSED
						|| op.getType() == Operation.Type.MOUSE_RELEASED || op
						.getType() == Operation.Type.MOUSE_CLICKED))
		{
			if (op.getMouseEvent().getButton() != MouseEvent.BUTTON1
					&& op.getMouseEvent().getButton() != MouseEvent.BUTTON3)
			{
				return;
			}
		}

		Set<MineGridModel> gridsToProc = new HashSet<MineGridModel>();
		if (op.getSource().getClass() == MineGridPanel.class)
		{
			MineGridPanel gp = (MineGridPanel) op.getSource();
			Point pos = gp.getPosition();
			gridsToProc.add(this.getGrid(pos.x, pos.y));
		}
		if (this.getCurGridMouseIn() != null)
		{
			gridsToProc.add(this.getCurGridMouseIn());
		}

		Set<MineGridModel> modified = new HashSet<MineGridModel>();
		for (MineGridModel g : gridsToProc)
		{
			g.onOperation(modified, op);
		}

		if (modified.size() > 0)
		{
			eventManager.fireActionEvent(new ObjectActionEvent(this,
					ActionEvent.ACTION_PERFORMED, GRID_UPDATED_ACTION_COMMAND,
					modified.toArray(new MineGridModel[0])));
		}
	}

	public MineGridModel getCurGridMouseIn()
	{
		return curGridMouseIn;
	}

	private void handleCurGridMouseIn(Operation op)
	{
		if (op.getSource().getClass() != MineGridPanel.class)
		{
			return;
		}

		MineGridPanel gp = (MineGridPanel) op.getSource();
		Point gpPos = gp.getPosition();
		MineGridModel g = this.getGrid(gpPos.x, gpPos.y);
		if (op.getType() == Operation.Type.MOUSE_ENTERED)
		{
			this.curGridMouseIn = g;
		}
		else if (op.getType() == Operation.Type.MOUSE_EXITED)
		{
			this.curGridMouseIn = null;
		}
		else
		{
			// do nothing
		}
	}

	@Override
	public Iterator<MineGridModel> iterator()
	{
		return grids.iterator();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(MineGridModel.FLAG_ON_ACTION_COMMAND))
		{
			ObjectActionEvent oe = (ObjectActionEvent) e;
			eventManager.fireActionEvent(new ObjectActionEvent(this,
					ActionEvent.ACTION_PERFORMED, FLAG_ON_ACTION_COMMAND, oe
							.getObject()));
		}
		else if (e.getActionCommand().equals(
				MineGridModel.FLAG_OFF_ACTION_COMMAND))
		{
			ObjectActionEvent oe = (ObjectActionEvent) e;
			eventManager.fireActionEvent(new ObjectActionEvent(this,
					ActionEvent.ACTION_PERFORMED, FLAG_OFF_ACTION_COMMAND, oe
							.getObject()));
		}
		else if (e.getActionCommand().equals(
				MineGridModel.GRID_DOWN_ACTION_COMMAND))
		{
			if (noGridDown)
			{
				noGridDown = false;
				this.putMines(this.getGridPos((MineGridModel) e.getSource()));
			}

			List<MineGridModel> downList = new LinkedList<MineGridModel>();
			this.gridDownRecursively(
					downList,
					((MineGridModel[]) ((ObjectActionEvent) e).getObject()));
			eventManager.fireActionEvent(new ObjectActionEvent(this,
					ActionEvent.ACTION_PERFORMED,
					MineGridModel.GRID_DOWN_ACTION_COMMAND, downList
							.toArray(new MineGridModel[0])));
		}
		else
		{
			assert (false);
		}
	}

	private void putMines(Point posNoMine)
	{
		for (MineGridModel g : grids)
		{
			g.setMine(false);
		}

		Random rand = new Random();
		int mineLeft = this.mineCountOnStart;
		while (mineLeft > 0)
		{
			int r = rand.nextInt(grids.getWidth() * grids.getHeight());
			Point pos = new Point(r % grids.getWidth(), r / grids.getWidth());
			if (grids.get(pos.x, pos.y).isMine() || pos.equals(posNoMine))
			{
				continue;
			}
			else
			{
				grids.get(pos.x, pos.y).setMine(true);
				mineLeft--;
			}
		}
	}

	private void gridDownRecursively(List<MineGridModel> retList,
			MineGridModel[] gs)
	{
		Queue<MineGridModel> queue = new LinkedList<MineGridModel>();
		queue.addAll(Arrays.asList(gs));
		retList.addAll(Arrays.asList(gs));

		while (queue.size() > 0)
		{
			MineGridModel g = queue.remove();

			if (this.countMineAround(g) != 0)
			{
				continue;
			}
			for (MineGridModel gAround : this.getGridAround(g))
			{
				if (gAround.getState().equals(MineGridModel.State.DOWN))
				{
					continue;
				}
				if (gAround.getMarkState().equals(MineGridModel.MarkState.FLAG))
				{
					continue;
				}

				gAround.toDownNoCheckNoEvent();
				queue.add(gAround);
				retList.add(gAround);
			}

		}
	}
}
