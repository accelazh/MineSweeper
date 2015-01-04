package org.accela.minesweeper.ui.skin.common;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.HighScoreDialog;

public class HighScoreDialogUI extends CompositeProcessor
{
	public HighScoreDialogUI()
	{
		super(HighScoreDialog.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		HighScoreDialog dialog = (HighScoreDialog) obj;
		HighScoreDialog.Panel panel = dialog.getPanel();

		panel.setBorder(new EmptyBorder(4 * Common.GUI_STANDARD_INTERVAL,
				4 * Common.GUI_STANDARD_INTERVAL,
				4 * Common.GUI_STANDARD_INTERVAL,
				4 * Common.GUI_STANDARD_INTERVAL));
		// =================== clear size ======================

		// 先clear掉PreferredSize，才能计算出依据控件中文字长度的大小
		panel.getLowLevelLabel().setPreferredSize(null);
		panel.getMiddleLevelLabel().setPreferredSize(null);
		panel.getHighLevelLabel().setPreferredSize(null);

		panel.getLowScoreLabel().setPreferredSize(null);
		panel.getMiddleScoreLabel().setPreferredSize(null);
		panel.getHighScoreLabel().setPreferredSize(null);

		panel.getLowScoreUnitLabel().setPreferredSize(null);
		panel.getMiddleScoreUnitLabel().setPreferredSize(null);
		panel.getHighScoreUnitLabel().setPreferredSize(null);

		panel.getLowScoreNameLabel().setPreferredSize(null);
		panel.getMiddleScoreNameLabel().setPreferredSize(null);
		panel.getHighScoreNameLabel().setPreferredSize(null);
		
		// ==================== set size ======================

		Dimension levelLabelSize = Util.calLargestPreferredSize(
				panel.getLowLevelLabel(),
				panel.getMiddleLevelLabel(),
				panel.getHighLevelLabel());
		Dimension unitLabelSize = Util.calLargestPreferredSize(
				panel.getLowScoreUnitLabel(),
				panel.getMiddleScoreUnitLabel(),
				panel.getHighScoreUnitLabel());
		Dimension nameLabelSize = Util.calLargestPreferredSize(
				panel.getLowScoreNameLabel(),
				panel.getMiddleScoreNameLabel(),
				panel.getHighScoreNameLabel());
		nameLabelSize.width = levelLabelSize.width * 2;
		Dimension scoreLabelSize = new Dimension(levelLabelSize.width,
				levelLabelSize.height);

		panel.getLowLevelLabel().setPreferredSize(levelLabelSize);
		panel.getMiddleLevelLabel().setPreferredSize(levelLabelSize);
		panel.getHighLevelLabel().setPreferredSize(levelLabelSize);

		panel.getLowScoreLabel().setPreferredSize(scoreLabelSize);
		panel.getMiddleScoreLabel().setPreferredSize(scoreLabelSize);
		panel.getHighScoreLabel().setPreferredSize(scoreLabelSize);

		panel.getLowScoreUnitLabel().setPreferredSize(unitLabelSize);
		panel.getMiddleScoreUnitLabel().setPreferredSize(unitLabelSize);
		panel.getHighScoreUnitLabel().setPreferredSize(unitLabelSize);

		panel.getLowScoreNameLabel().setPreferredSize(nameLabelSize);
		panel.getMiddleScoreNameLabel().setPreferredSize(nameLabelSize);
		panel.getHighScoreNameLabel().setPreferredSize(nameLabelSize);

		panel.getLowScoreLabel().setHorizontalAlignment(JTextField.RIGHT);
		panel.getMiddleScoreLabel().setHorizontalAlignment(JTextField.RIGHT);
		panel.getHighScoreLabel().setHorizontalAlignment(JTextField.RIGHT);

		panel.getLowScoreNameLabel().setHorizontalAlignment(JTextField.RIGHT);
		panel.getMiddleScoreNameLabel()
				.setHorizontalAlignment(JTextField.RIGHT);
		panel.getHighScoreNameLabel().setHorizontalAlignment(JTextField.RIGHT);

		return false;
	}

}
