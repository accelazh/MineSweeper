package org.accela.minesweeper.model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.accela.minesweeper.model.operation.Operation;
import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.util.ObjectActionEvent;
import org.accela.minesweeper.view.MineGridPanel;

public class MineGridModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String GRID_DOWN_ACTION_COMMAND = MineFieldModel.GRID_DOWN_ACTION_COMMAND;
	public static final String FLAG_ON_ACTION_COMMAND = MineFieldModel.FLAG_ON_ACTION_COMMAND;
	public static final String FLAG_OFF_ACTION_COMMAND = MineFieldModel.FLAG_OFF_ACTION_COMMAND;

	private MineFieldModel parent = null;

	private GameState gameState = null;

	public static enum State
	{
		UP_NORMAL, UP_GLOW, DOWN_TRYING, DOWN
	}

	private State state = null;

	public static enum MarkState
	{
		NONE, FLAG, QUESTION
	}

	private MarkState markState = null;

	public static enum BombState
	{
		NORMAL, WRONG, DEAD
	}

	private BombState bombState = null;

	private boolean mine = false;

	private EventManager eventManager = new EventManager();

	public MineGridModel(MineFieldModel parent, GameState gameState)
	{
		this.parent = parent;
		this.gameState = gameState;

		this.state = State.UP_NORMAL;
		this.markState = MarkState.NONE;
		this.bombState = BombState.NORMAL;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public MarkState getMarkState()
	{
		return markState;
	}

	public void setMarkState(MarkState markState)
	{
		this.markState = markState;
	}

	public BombState getBombState()
	{
		return bombState;
	}

	public void setBombState(BombState bombState)
	{
		this.bombState = bombState;
	}

	public MineFieldModel getParent()
	{
		return parent;
	}

	public boolean isMine()
	{
		return mine;
	}

	public void setMine(boolean mine)
	{
		this.mine = mine;
	}

	public void addActionListener(ActionListener l)
	{
		eventManager.addActionListener(l);
	}

	public void removeActionListener(ActionListener l)
	{
		eventManager.removeActionListener(l);
	}

	public Point getPosition()
	{
		return this.getParent().getGridPos(this);
	}

	public boolean isFlagOn()
	{
		return (this.getState().equals(MineGridModel.State.UP_GLOW) || this
				.getState().equals(MineGridModel.State.UP_NORMAL))
				&& this.getMarkState().equals(MineGridModel.MarkState.FLAG);
	}

	public void onOperation(Set<MineGridModel> needUpdate, Operation op)
	{
		procGlow(needUpdate, op);
		procSingleBtnDownEnterExit(needUpdate, op);
		procRightClick(needUpdate, op);
		procLeftClick(needUpdate, op);
		procBothBtnDownEnterExit(needUpdate, op);
		procBothBtnClick(needUpdate, op);
	}

	private void procGlow(Set<MineGridModel> needUpdate, Operation op)
	{
		if (!(gameState.getState().equals(GameState.State.READY) || gameState
				.getState().equals(GameState.State.PLAYING)))
		{
			return;
		}
		if(op.getSource().getClass()!=MineGridPanel.class)
		{
			return;
		}
		MineGridPanel gp = (MineGridPanel) op.getSource();
		if (!gp.getPosition().equals(this.getPosition()))
		{
			return;
		}

		if (this.state.equals(State.UP_NORMAL)
				&& op.getType().equals(Operation.Type.MOUSE_ENTERED)
				&& !op.isLeftMouseBtnDown())
		{
			this.state = State.UP_GLOW;
			needUpdate.add(this);
		}
		else if (this.state.equals(State.UP_GLOW)
				&& op.getType().equals(Operation.Type.MOUSE_EXITED))
		{
			this.state = State.UP_NORMAL;
			needUpdate.add(this);
		}
		else
		{
			// do nothing
		}
	}

	private void procLeftClick(Set<MineGridModel> needUpdate, Operation op)
	{
		if (op.getMouseEvent() == null)
		{
			return;
		}
		if (op.getMouseEvent().getButton() != MouseEvent.BUTTON1)
		{
			return;
		}
		if (op.isRightMouseBtnDown())
		{
			return;
		}
		if (!this.equals(this.getParent().getCurGridMouseIn()))
		{
			return;
		}

		if (op.getType().equals(Operation.Type.MOUSE_PRESSED))
		{
			toDownTrying();
			needUpdate.add(this);
		}
		else if (op.getType().equals(Operation.Type.MOUSE_RELEASED))
		{
			toDown(new MineGridModel[] { this }, this);
			needUpdate.add(this);
		}
		else
		{
			// do nothing
		}
	}

	private void procRightClick(Set<MineGridModel> needUpdate, Operation op)
	{
		if (op.getMouseEvent() == null)
		{
			return;
		}
		if (op.getMouseEvent().getButton() != MouseEvent.BUTTON3)
		{
			return;
		}
		if (op.isLeftMouseBtnDown())
		{
			return;
		}
		if (!(this.state.equals(State.UP_GLOW) || this.state
				.equals(State.UP_NORMAL)))
		{
			return;
		}
		if (!this.equals(this.getParent().getCurGridMouseIn()))
		{
			return;
		}

		if (op.getType().equals(Operation.Type.MOUSE_RELEASED))
		{
			if (this.markState.equals(MarkState.NONE))
			{
				this.markState = MarkState.FLAG;
				this.bombState = BombState.WRONG;
				this.eventManager.fireActionEvent(new ObjectActionEvent(this,
						ActionEvent.ACTION_PERFORMED, FLAG_ON_ACTION_COMMAND,
						new MineGridModel[] { this }));
				needUpdate.add(this);
			}
			else if (this.markState.equals(MarkState.FLAG))
			{
				if (op.isQuestionMarkEnabled())
				{
					this.markState = MarkState.QUESTION;
					this.bombState = BombState.NORMAL;
				}
				else
				{
					this.markState = MarkState.NONE;
					this.bombState = BombState.NORMAL;
				}

				this.eventManager.fireActionEvent(new ObjectActionEvent(this,
						ActionEvent.ACTION_PERFORMED, FLAG_OFF_ACTION_COMMAND,
						new MineGridModel[] { this }));
				needUpdate.add(this);
			}
			else if (this.markState.equals(MarkState.QUESTION))
			{
				this.markState = MarkState.NONE;
				this.bombState = BombState.NORMAL;
				needUpdate.add(this);
			}
			else
			{
				assert (false);
			}
		}
		else
		{
			// do nothing
		}
	}

	private void procSingleBtnDownEnterExit(Set<MineGridModel> needUpdate, Operation op)
	{
		if(op.getSource().getClass()!=MineGridPanel.class)
		{
			return;
		}
		MineGridPanel gp = (MineGridPanel) op.getSource();
		if (!gp.getPosition().equals(this.getPosition()))
		{
			return;
		}

		if (this.state.equals(State.DOWN_TRYING)
				&& op.getType().equals(Operation.Type.MOUSE_EXITED)
				&& op.isLeftMouseBtnDown() && !op.isRightMouseBtnDown())
		{
			this.state = State.UP_NORMAL;
			needUpdate.add(this);
		}
		else if (op.getType().equals(Operation.Type.MOUSE_ENTERED)
				&& op.isLeftMouseBtnDown() && !op.isRightMouseBtnDown())
		{
			toDownTrying();
			needUpdate.add(this);
		}
		else
		{
			// do nothing
		}
	}

	private void procBothBtnClick(Set<MineGridModel> needUpdate, Operation op)
	{
		if (!this.equals(this.getParent().getCurGridMouseIn()))
		{
			return;
		}

		if (op.getType().equals(Operation.Type.MOUSE_PRESSED)
				&& op.isRightMouseBtnDown() && op.isLeftMouseBtnDown())
		{
			List<MineGridModel> grids = new LinkedList<MineGridModel>(
					Arrays.asList(this.getParent().getGridAround(this)));
			grids.add(this);
			for (MineGridModel g : grids)
			{
				g.toDownTrying();
				needUpdate.add(g);
			}
		}
		else if (op.getType().equals(Operation.Type.MOUSE_RELEASED)
				&& (op.isRightMouseBtnDown() || op.isLeftMouseBtnDown()))
		{
			List<MineGridModel> grids = new LinkedList<MineGridModel>(
					Arrays.asList(this.getParent().getGridAround(this)));

			int flagAround = this.getParent().countFlagAround(this);
			int mineAround = this.getParent().countMineAround(this);

			if (this.getState() == State.DOWN && flagAround >= mineAround)
			{
				toDown(grids.toArray(new MineGridModel[0]), this);
				needUpdate.addAll(grids);
			}
			else
			{
				for (MineGridModel g : grids)
				{
					if (g.state.equals(State.DOWN_TRYING))
					{
						g.state = State.UP_NORMAL;
						needUpdate.add(g);
					}
				}
				
				if(this.state==State.DOWN_TRYING)
				{
					this.state=State.UP_GLOW;
					needUpdate.add(this);
				}
			}
		}
		else
		{
			// do nothing
		}
	}

	private void procBothBtnDownEnterExit(Set<MineGridModel> needUpdate, Operation op)
	{
		if(op.getSource().getClass()!=MineGridPanel.class)
		{
			return;
		}
		
		if (op.getType().equals(Operation.Type.MOUSE_ENTERED)
				&& op.isRightMouseBtnDown() && op.isLeftMouseBtnDown())
		{
			List<MineGridModel> grids = new LinkedList<MineGridModel>(
					Arrays.asList(this.getParent().getGridAround(this)));
			grids.add(this);
			for (MineGridModel g : grids)
			{
				g.toDownTrying();
				needUpdate.add(g);
			}
		}
		else if (op.getType().equals(Operation.Type.MOUSE_EXITED)
				&& op.isRightMouseBtnDown() && op.isLeftMouseBtnDown())
		{
			List<MineGridModel> grids = new LinkedList<MineGridModel>(
					Arrays.asList(this.getParent().getGridAround(this)));
			grids.add(this);
			for (MineGridModel g : grids)
			{
				if (g.state.equals(State.DOWN_TRYING))
				{
					g.state = State.UP_NORMAL;
					needUpdate.add(g);
				}
			}
		}
		else
		{
			// do nothing
		}
	}

	private boolean toDownTrying()
	{
		MineGridModel g = this;

		if ((g.getState().equals(State.UP_NORMAL) || g.getState().equals(
				State.UP_GLOW))
				&& !g.markState.equals(MarkState.FLAG))
		{
			g.state = State.DOWN_TRYING;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean toDownNoEvent()
	{
		return toDownNoEvent(this);
	}

	private static void toDown(MineGridModel[] gs, MineGridModel src)
	{
		List<MineGridModel> gDown = new LinkedList<MineGridModel>();
		for (MineGridModel g : gs)
		{
			boolean ret = toDownNoEvent(g);
			if (ret)
			{
				gDown.add(g);
			}
		}

		if (gDown.size() > 0)
		{
			src.eventManager.fireActionEvent(new ObjectActionEvent(src,
					ActionEvent.ACTION_PERFORMED, GRID_DOWN_ACTION_COMMAND,
					gDown.toArray(new MineGridModel[0])));
		}
	}

	private static boolean toDownNoEvent(MineGridModel g)
	{
		if (g.state.equals(State.DOWN_TRYING))
		{
			g.state = State.DOWN;
			g.bombState = BombState.DEAD;

			return true;
		}
		else
		{
			return false;
		}
	}

	public void toDownNoCheckNoEvent()
	{
		this.state = State.DOWN;
		this.bombState = BombState.DEAD;
	}

}
