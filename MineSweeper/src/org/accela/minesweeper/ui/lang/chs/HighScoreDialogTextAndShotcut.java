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
		dialog.setTitle("ɨ��Ӣ�۰�");

		HighScoreDialog.Panel panel = dialog.getPanel();

		// �������ֶ��ڲ��ֹ���������Ҫ
		panel.getLowLevelLabel().setText   ("����:");
		panel.getMiddleLevelLabel().setText("�м�:");
		panel.getHighLevelLabel().setText  ("�߼�:");

		panel.getLowScoreLabel().setText("999");
		panel.getMiddleScoreLabel().setText("999");
		panel.getHighScoreLabel().setText("999");
		
		panel.getLowScoreUnitLabel().setText("��");
		panel.getMiddleScoreUnitLabel().setText("��");
		panel.getHighScoreUnitLabel().setText("��");

		panel.getLowScoreNameLabel().setText("����");
		panel.getMiddleScoreNameLabel().setText("����");
		panel.getHighScoreNameLabel().setText("����");
		
		panel.getOkButton().setText("ȷ��");
		panel.getResetButton().setText("���¼Ʒ�(R)");
		
		panel.getResetButton().setMnemonic('r');
		
		return true;
	}

}
