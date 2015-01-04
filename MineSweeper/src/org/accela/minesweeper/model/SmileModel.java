package org.accela.minesweeper.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import org.accela.minesweeper.model.operation.Operation;
import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.view.SmilePanel;

public class SmileModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String BUTTON_CLICK_ACTION_COMMAND = "buttonDown";

	public static final String UPDATED_ACTION_COMMAND = "updated";

	public static enum State
	{
		UP_SMILE, UP_NERVOUS, DOWN_SMILE, UP_DEAD, UP_WIN
	}

	private boolean glow = false;

	private State state = null;

	private State savedStateForPressed = null;

	private boolean downAndMovedOut = false;

	private EventManager eventManager = new EventManager();

	private boolean curMouseIn = false;

	public SmileModel()
	{
		this.state = State.UP_SMILE;
	}

	public void reset()
	{
		this.state = State.UP_SMILE;
		this.savedStateForPressed = null;
		this.downAndMovedOut = false;
	}

	public void addActionListener(ActionListener l)
	{
		eventManager.addActionListener(l);
	}

	public void removeActionListener(ActionListener l)
	{
		eventManager.removeActionListener(l);
	}

	public boolean isGlow()
	{
		return glow;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public void onOperation(Operation op)
	{
		handleCurMouseIn(op);

		boolean modified = false;
		if (this.procNervous(op))
		{
			modified = true;
		}
		if (this.procGlow(op))
		{
			modified = true;
		}
		if (this.procClick(op))
		{
			modified = true;
		}

		if (modified)
		{
			eventManager.fireActionEvent(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, UPDATED_ACTION_COMMAND));
		}
	}

	private void handleCurMouseIn(Operation op)
	{
		if (op.getSource().getClass() != SmilePanel.class)
		{
			return;
		}

		if (op.getType() == Operation.Type.MOUSE_ENTERED)
		{
			this.curMouseIn = true;
		}
		else if (op.getType() == Operation.Type.MOUSE_EXITED)
		{
			this.curMouseIn = false;
		}
		else
		{
			// do nothing
		}
	}

	private boolean procNervous(Operation op)
	{
		if (op.getSource().getClass() != SmilePanel.class
				&& this.state.equals(State.UP_SMILE)
				&& op.getType().equals(Operation.Type.MOUSE_PRESSED))
		{
			this.state = State.UP_NERVOUS;
			return true;
		}
		else if (this.state.equals(State.UP_NERVOUS)
				&& op.getType().equals(Operation.Type.MOUSE_RELEASED))
		{
			this.state = State.UP_SMILE;
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean procGlow(Operation op)
	{
		if (op.getSource().getClass() != SmilePanel.class)
		{
			return false;
		}

		if (!glow && op.getType().equals(Operation.Type.MOUSE_ENTERED)
				&& !op.isLeftMouseBtnDown())
		{
			glow = true;
			return true;
		}
		else if (glow && op.getType().equals(Operation.Type.MOUSE_EXITED))
		{
			glow = false;
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean procClick(Operation op)
	{
		if (op.isRightMouseBtnDown())
		{
			return false;
		}

		if ((op.getSource().getClass() == SmilePanel.class)
				&& (this.state.equals(State.UP_SMILE)
						|| this.state.equals(State.UP_NERVOUS)
						|| this.state.equals(State.UP_WIN) || this.state
							.equals(State.UP_DEAD))
				&& (op.getType().equals(Operation.Type.MOUSE_PRESSED)))
		{
			this.savedStateForPressed = this.state;
			this.state = State.DOWN_SMILE;
			this.downAndMovedOut = false;

			return true;
		}
		else if (op.getSource().getClass() == SmilePanel.class
				&& this.state.equals(State.DOWN_SMILE)
				&& op.getType().equals(Operation.Type.MOUSE_RELEASED))
		{
			this.state = State.UP_SMILE;
			if (curMouseIn)
			{
				this.eventManager.fireActionEvent(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED,
						BUTTON_CLICK_ACTION_COMMAND));
			}
			this.downAndMovedOut = false;

			return true;
		}
		else if (op.getSource().getClass() != SmilePanel.class
				&& op.getType().equals(Operation.Type.MOUSE_RELEASED))
		{
			this.downAndMovedOut = false;
			return false;
		}
		else if (op.getSource().getClass() == SmilePanel.class
				&& this.state.equals(State.DOWN_SMILE)
				&& op.getType().equals(Operation.Type.MOUSE_EXITED)
				&& op.isLeftMouseBtnDown())
		{
			assert (this.savedStateForPressed != null);
			this.state = this.savedStateForPressed;
			this.downAndMovedOut = true;

			return true;
		}
		else if (op.getSource().getClass() == SmilePanel.class
				&& op.getType().equals(Operation.Type.MOUSE_ENTERED)
				&& op.isLeftMouseBtnDown())
		{
			if (this.downAndMovedOut)
			{
				this.downAndMovedOut = false;
				this.savedStateForPressed = this.state;
				this.state = State.DOWN_SMILE;

				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
