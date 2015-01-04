package org.accela.minesweeper.ui.lang.en;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.skin.common.CustomMineFieldUI;
import org.accela.minesweeper.ui.skin.common.HighScoreDialogUI;
import org.accela.minesweeper.util.CompositeProcessor;

public class EnUI extends GameUI
{

	private CompositeProcessor proc = null;

	public EnUI()
	{
		proc = new CustomMineFieldDialogTextAndShotcut()
				.compose(new GameMenuTextAndShotcut())
				.compose(new HighScoreDialogTextAndShotcut())
				.compose(new GameFrameText()).compose(new HighScoreDialogUI())
				.compose(new CustomMineFieldUI());
		;
	}

	@Override
	protected CompositeProcessor getUISetter()
	{
		return this.proc;
	}

	@Override
	public String getName()
	{
		return "English(US)";
	}
}
