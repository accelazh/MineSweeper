package org.accela.minesweeper.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.DigitPanel;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.MineFieldPanel;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.NumberPanel;


public abstract class GameUI
{
	private CompositeProcessor uiSetter = null;

	private PropertyChangeListener newGridsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getPropertyName().equals(
					MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY))
			{
				if(evt.getOldValue()!=null)
				{
					for(MineGridPanel g: (MineGridPanel[])evt.getOldValue())
					{
						g.removePropertyChangeListener(MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY, newGridsListener);
					}
				}
				
				uiSetter.process(Arrays.asList((Object[]) evt.getNewValue()));
			}
		}
	};

	private PropertyChangeListener newDigitsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getPropertyName().equals(
					NumberPanel.NEW_DIGITS_CHANGED_PROPERTY))
			{
				if(evt.getOldValue()!=null)
				{
					for(DigitPanel g: (DigitPanel[])evt.getOldValue())
					{
						g.removePropertyChangeListener(NumberPanel.NEW_DIGITS_CHANGED_PROPERTY, newDigitsListener);
					}
				}
				
				uiSetter.process(Arrays.asList((Object[]) evt.getNewValue()));
			}
		}
	};

	public void install(GameFrame frame)
	{
		this.uiSetter = getUISetter();
		uiSetter.process(frame);

		frame
				.getGamePanel()
				.getMineField()
				.addPropertyChangeListener(
						MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY,
						newGridsListener);
		frame
				.getGamePanel()
				.getStatus()
				.getLeftNumber()
				.addPropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
		frame
				.getGamePanel()
				.getStatus()
				.getRightNumber()
				.addPropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
	}

	public void uninstall(GameFrame frame)
	{
		frame
				.getGamePanel()
				.getMineField()
				.removePropertyChangeListener(
						MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY,
						newGridsListener);
		frame
				.getGamePanel()
				.getStatus()
				.getLeftNumber()
				.removePropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
		frame
				.getGamePanel()
				.getStatus()
				.getRightNumber()
				.removePropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
	}

	public abstract String getName();
	
	protected abstract CompositeProcessor getUISetter();
}
