package org.accela.minesweeper.ui.lang.en;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.GameFrame;


public class GameFrameText extends CompositeProcessor
{
	public GameFrameText()
	{
		super(GameFrame.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		GameFrame frame = (GameFrame) obj;
		frame.setTitle("Mine Sweeper");
		frame.getInputYourNameDialogUIRes().setTitle("New High Score!");
		frame.getInputYourNameDialogUIRes().setMessage("New high score, congratulations! \nPlease input your name: ");
		
		return true;
	}

}
