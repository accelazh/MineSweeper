package org.accela.minesweeper.ui.lang.chs;

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
		dialog.setTitle("扫雷英雄榜");

		HighScoreDialog.Panel panel = dialog.getPanel();

		// 对齐文字对于布局管理器跟重要
		panel.getLowLevelLabel().setText   ("初级:");
		panel.getMiddleLevelLabel().setText("中级:");
		panel.getHighLevelLabel().setText  ("高级:");

		panel.getLowScoreLabel().setText("999");
		panel.getMiddleScoreLabel().setText("999");
		panel.getHighScoreLabel().setText("999");
		
		panel.getLowScoreUnitLabel().setText("秒");
		panel.getMiddleScoreUnitLabel().setText("秒");
		panel.getHighScoreUnitLabel().setText("秒");

		panel.getLowScoreNameLabel().setText("匿名");
		panel.getMiddleScoreNameLabel().setText("匿名");
		panel.getHighScoreNameLabel().setText("匿名");
		
		panel.getOkButton().setText("确定");
		panel.getResetButton().setText("重新计分(R)");
		
		panel.getResetButton().setMnemonic('r');
		
		return true;
	}

}
