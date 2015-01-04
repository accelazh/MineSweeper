package org.accela.minesweeper.ui.skin.blue;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.skin.common.CustomMineFieldUI;
import org.accela.minesweeper.ui.skin.common.GameFrameUI;
import org.accela.minesweeper.ui.skin.common.GameMenuBarUI;
import org.accela.minesweeper.ui.skin.common.HighScoreDialogUI;
import org.accela.minesweeper.util.CompositeProcessor;

public class BlueUI extends GameUI
{
	private CompositeProcessor proc = null;

	public BlueUI()
	{
		proc = new CustomMineFieldUI()
				.compose(new BlueDigitPanelUI())
				.compose(new GameFrameUI())
				.compose(new GameMenuBarUI())
				
				.compose(new BlueGamePanelUI())
				.compose(new HighScoreDialogUI())
				.compose(new BlueMineFieldPanelUI())
				.compose(new BlueMineGridPanelUI())
				
				.compose(new BlueNumberPanelUI())
				.compose(new BlueSmilePanelUI())
				.compose(new BlueStatusPanelUI());
	}

	@Override
	protected CompositeProcessor getUISetter()
	{
		return this.proc;
	}
	
	@Override
	public String getName()
	{
		return "Blue";
	}

}
