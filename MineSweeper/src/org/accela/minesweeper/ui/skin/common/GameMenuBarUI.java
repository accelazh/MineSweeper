package org.accela.minesweeper.ui.skin.common;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.GameMenuBar;


public class GameMenuBarUI extends CompositeProcessor
{
	public GameMenuBarUI()
	{
		super(GameMenuBar.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		//do nothing
		return true;
	}

}
