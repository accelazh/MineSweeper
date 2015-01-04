package org.accela.minesweeper.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.util.ObjectActionEvent;

/**
 * @author software
 * 
 */
public class FlagCounterModel
{
	public static final String FLAG_COUNT_CHANGED_ACTION_COMMAND = "flagCountChanged";

	private int flagCount = 0;

	private EventManager eventManager = new EventManager();

	public void addActionListener(ActionListener l)
	{
		eventManager.addActionListener(l);
	}

	public void removeActionListener(ActionListener l)
	{
		eventManager.removeActionListener(l);
	}

	public int getFlagCount()
	{
		return flagCount;
	}

	public void setFlagCount(int flagCount)
	{
		this.flagCount = flagCount;
		this.eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				FLAG_COUNT_CHANGED_ACTION_COMMAND, this.flagCount));
	}

	public void decrFlagCount()
	{
		this.setFlagCount(this.getFlagCount() - 1);
	}

	public void incrFlagCount()
	{
		this.setFlagCount(this.getFlagCount() + 1);
	}

}
