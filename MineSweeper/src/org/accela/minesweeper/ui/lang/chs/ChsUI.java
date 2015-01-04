package org.accela.minesweeper.ui.lang.chs;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.skin.common.CustomMineFieldUI;
import org.accela.minesweeper.ui.skin.common.HighScoreDialogUI;
import org.accela.minesweeper.util.CompositeProcessor;

public class ChsUI extends GameUI
{
	private CompositeProcessor proc=null;
	
	public ChsUI()
	{
		proc=new CustomMineFieldDialogTextAndShotcut()
		.compose(new GameMenuTextAndShotcut())
		.compose(new HighScoreDialogTextAndShotcut())
		.compose(new GameFrameText())
		.compose(new HighScoreDialogUI())
		.compose(new CustomMineFieldUI());
	}
	
	@Override
	protected CompositeProcessor getUISetter()
	{
		return this.proc;
	}
	
	@Override
	public String getName()
	{
		return "ÖÐÎÄ(¼ò)";
	}

}
