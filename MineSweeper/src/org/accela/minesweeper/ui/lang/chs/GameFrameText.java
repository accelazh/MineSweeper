package org.accela.minesweeper.ui.lang.chs;

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
		frame.setTitle("扫雷");
		frame.getInputYourNameDialogUIRes().setTitle("新的高分");
		frame.getInputYourNameDialogUIRes().setMessage("恭喜，新的高分！\n请输入您的尊姓大名：");

		return true;
	}

}
