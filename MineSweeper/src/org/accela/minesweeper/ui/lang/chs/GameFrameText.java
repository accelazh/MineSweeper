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
		frame.setTitle("ɨ��");
		frame.getInputYourNameDialogUIRes().setTitle("�µĸ߷�");
		frame.getInputYourNameDialogUIRes().setMessage("��ϲ���µĸ߷֣�\n�������������մ�����");

		return true;
	}

}
