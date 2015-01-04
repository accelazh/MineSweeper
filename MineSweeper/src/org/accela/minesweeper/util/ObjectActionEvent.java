package org.accela.minesweeper.util;

import java.awt.event.ActionEvent;

public class ObjectActionEvent extends ActionEvent
{
	private static final long serialVersionUID = 1L;
	
	private Object object=null;
	
	public ObjectActionEvent(Object source, int id, String command, Object object)
	{
		super(source, id, command);
		this.object=object;
	}

	public Object getObject()
	{
		return object;
	}
}
