package org.accela.minesweeper.model.operation;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Operation implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static enum Type
	{
		MOUSE_CLICKED,
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_ENTERED,
		MOUSE_EXITED,
		MOUSE_DRAGGED,
		MOUSE_MOVED,
		MOUSE_MOTION,
		MOUSE_WHEEL_MOVED,

		KEY_TYPED,
		KEY_PRESSED,
		KEY_RELEASED
	};

	private Type type = null;

	private MouseEvent mouseEvent = null;

	private KeyEvent keyEvent = null;

	private boolean questionMarkEnabled = true;

	public Operation(Type type, MouseEvent mouseEvent, KeyEvent keyEvent,
			boolean questionMarkEnabled)
	{
		this.type = type;
		this.mouseEvent = mouseEvent;
		this.keyEvent = keyEvent;
		this.questionMarkEnabled = questionMarkEnabled;
	}

	public Type getType()
	{
		return this.type;
	}

	public MouseEvent getMouseEvent()
	{
		return this.mouseEvent;
	}

	public KeyEvent getKeyEvent()
	{
		return this.keyEvent;
	}

	public Object getSource()
	{
		if (this.mouseEvent != null)
		{
			return this.mouseEvent.getSource();
		}
		else if (this.keyEvent != null)
		{
			return this.keyEvent.getSource();
		}
		else
		{
			assert (false);
			return null;
		}
	}

	public boolean isQuestionMarkEnabled()
	{
		return questionMarkEnabled;
	}

	public boolean isLeftMouseBtnDown()
	{
		if(null==this.getMouseEvent())
		{
			return false;
		}
		return (getMouseEvent().getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK;
	}

	public boolean isRightMouseBtnDown()
	{
		if(null==this.getMouseEvent())
		{
			return false;
		}
		return (getMouseEvent().getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK;
	}

	@Override
	public String toString()
	{
		return "Operation [type=" + type + ", mouseEvent=" + mouseEvent
				+ ", keyEvent=" + keyEvent + ", questionMarkEnabled="
				+ questionMarkEnabled + "]";
	}
}
