package org.accela.minesweeper.ui.skin.classic;

import org.accela.minesweeper.ui.GameUI;
import org.accela.minesweeper.ui.skin.common.CustomMineFieldUI;
import org.accela.minesweeper.ui.skin.common.GameFrameUI;
import org.accela.minesweeper.ui.skin.common.GameMenuBarUI;
import org.accela.minesweeper.ui.skin.common.HighScoreDialogUI;
import org.accela.minesweeper.util.CompositeProcessor;

public class ClassicUI extends GameUI
{
	private CompositeProcessor proc = null;

	public ClassicUI()
	{
		proc = new CustomMineFieldUI().compose(new ClassicDigitPanelUI())
				.compose(new GameFrameUI()).compose(new GameMenuBarUI())

				.compose(new ClassicGamePanelUI())
				.compose(new HighScoreDialogUI())
				.compose(new ClassicMineFieldPanelUI())
				.compose(new ClassicMineGridPanelUI())

				.compose(new ClassicNumberPanelUI())
				.compose(new ClassicSmilePanelUI())
				.compose(new ClassicStatusPanelUI());
	}

	@Override
	protected CompositeProcessor getUISetter()
	{
		return this.proc;
	}

	@Override
	public String getName()
	{
		return "Classic";
	}
}
