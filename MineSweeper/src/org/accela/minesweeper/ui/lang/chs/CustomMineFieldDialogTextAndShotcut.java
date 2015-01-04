package org.accela.minesweeper.ui.lang.chs;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.CustomMineFieldDialog;


public class CustomMineFieldDialogTextAndShotcut extends CompositeProcessor
{

	public CustomMineFieldDialogTextAndShotcut()
	{
		super(CustomMineFieldDialog.class, null);
	}
	
	@Override
	protected boolean doProcess(Object obj)
	{
		CustomMineFieldDialog dialog=(CustomMineFieldDialog)obj;
		dialog.setTitle("�Զ�������");
				
		CustomMineFieldDialog.Panel panel=dialog.getPanel();
		
		panel.getHeightLabel().setText("�߶�(H):");
		panel.getWidthLabel().setText ("���(W):");
		panel.getMineLabel().setText  ("����(M):");
		
		panel.getOkButton().setText("ȷ��");
		panel.getCancelButton().setText("ȡ��");
		
		return true;
	}

}
