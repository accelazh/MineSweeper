package org.accela.minesweeper.ui.skin.common;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.GameFrame;


public class GameFrameUI extends CompositeProcessor
{
	public GameFrameUI()
	{
		super(GameFrame.class, null);
	}
	
	@Override
	protected boolean doProcess(Object obj)
	{
		GameFrame frame=(GameFrame)obj;
		frame.setIconImage(Util.createImage("common/frame/icon.png"));
		
		return true;
	}

}
