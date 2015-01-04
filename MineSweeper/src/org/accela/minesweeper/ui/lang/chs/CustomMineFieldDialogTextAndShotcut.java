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
		dialog.setTitle("自定义雷区");
				
		CustomMineFieldDialog.Panel panel=dialog.getPanel();
		
		panel.getHeightLabel().setText("高度(H):");
		panel.getWidthLabel().setText ("宽度(W):");
		panel.getMineLabel().setText  ("雷数(M):");
		
		panel.getOkButton().setText("确定");
		panel.getCancelButton().setText("取消");
		
		return true;
	}

}
