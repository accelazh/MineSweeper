package org.accela.minesweeper.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventManager
{
	private Map<String, List<PropertyChangeListener>> propertyListeners = new HashMap<String, List<PropertyChangeListener>>();

	private List<ActionListener> actionListeners = new LinkedList<ActionListener>();

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener)
	{
		List<PropertyChangeListener> list = propertyListeners.get(propertyName);
		if (null == list)
		{
			list = new LinkedList<PropertyChangeListener>();
			propertyListeners.put(propertyName, list);
		}

		list.add(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener)
	{
		List<PropertyChangeListener> list = propertyListeners.get(propertyName);
		if (list != null)
		{
			list.remove(listener);
		}
	}

	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue)
	{
		List<PropertyChangeListener> list = propertyListeners.get(propertyName);
		if (null == list)
		{
			return;
		}

		PropertyChangeEvent e = new PropertyChangeEvent(this, propertyName,
				oldValue, newValue);
		for (PropertyChangeListener l : list)
		{
			if (l != null)
			{
				l.propertyChange(e);
			}
		}
	}

	// ===================================================================

	public void addActionListener(ActionListener l)
	{
		actionListeners.add(l);
	}

	public void removeActionListener(ActionListener l)
	{
		actionListeners.remove(l);
	}

	public void fireActionEvent(ActionEvent e)
	{
		for (ActionListener l : actionListeners)
		{
			if (l != null)
			{
				l.actionPerformed(e);
			}
		}
	}
}
