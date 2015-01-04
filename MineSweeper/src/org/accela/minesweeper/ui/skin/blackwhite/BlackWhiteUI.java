package org.accela.minesweeper.ui.skin.blackwhite;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.skin.common.CustomMineFieldUI;
import org.accela.minesweeper.ui.skin.common.GameFrameUI;
import org.accela.minesweeper.ui.skin.common.GameMenuBarUI;
import org.accela.minesweeper.ui.skin.common.HighScoreDialogUI;
import org.accela.minesweeper.util.CompositeProcessor;

public class BlackWhiteUI extends GameUI
{
	private CompositeProcessor proc = null;

	public BlackWhiteUI()
	{
		proc = new CustomMineFieldUI().compose(new BlackWhiteDigitPanelUI())
				.compose(new GameFrameUI()).compose(new GameMenuBarUI())

				.compose(new BlackWhiteGamePanelUI())
				.compose(new HighScoreDialogUI())
				.compose(new BlackWhiteMineFieldPanelUI())
				.compose(new BlackWhiteMineGridPanelUI())

				.compose(new BlackWhiteNumberPanelUI())
				.compose(new BlackWhiteSmilePanelUI())
				.compose(new BlackWhiteStatusPanelUI());
	}

	@Override
	protected CompositeProcessor getUISetter()
	{
		return this.proc;
	}
	
	@Override
	public String getName()
	{
		return "Black&White";
	}

}
