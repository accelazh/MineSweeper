package org.accela.minesweeper.ui.lang.en;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.HighScoreDialog;


public class HighScoreDialogTextAndShotcut extends CompositeProcessor
{

	public HighScoreDialogTextAndShotcut()
	{
		super(HighScoreDialog.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		HighScoreDialog dialog = (HighScoreDialog) obj;
		dialog.setTitle("High Score");

		HighScoreDialog.Panel panel = dialog.getPanel();

		// 对齐文字对于布局管理器跟重要
		panel.getLowLevelLabel().setText   ("Beginner:");
		panel.getMiddleLevelLabel().setText("Intermediate:");
		panel.getHighLevelLabel().setText  ("Expert:");

		panel.getLowScoreLabel().setText("999");
		panel.getMiddleScoreLabel().setText("999");
		panel.getHighScoreLabel().setText("999");
		
		panel.getLowScoreUnitLabel().setText("Sec");
		panel.getMiddleScoreUnitLabel().setText("Sec");
		panel.getHighScoreUnitLabel().setText("Sec");

		panel.getLowScoreNameLabel().setText("Anonymous");
		panel.getMiddleScoreNameLabel().setText("Anonymous");
		panel.getHighScoreNameLabel().setText("Anonymous");
		
		panel.getOkButton().setText("Ok");
		panel.getResetButton().setText("Reset Score(R)");
		
		panel.getResetButton().setMnemonic('r');
		
		return true;
	}

}
