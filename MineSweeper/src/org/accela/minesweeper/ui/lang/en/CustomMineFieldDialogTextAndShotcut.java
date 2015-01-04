package org.accela.minesweeper.ui.lang.en;

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
		dialog.setTitle("Custom Mine Field");
				
		CustomMineFieldDialog.Panel panel=dialog.getPanel();
		
		panel.getHeightLabel().setText("Height(H):");
		panel.getWidthLabel().setText ("Width(W):");
		panel.getMineLabel().setText  ("Mine(M):");
		
		panel.getOkButton().setText("Ok");
		panel.getCancelButton().setText("Cancel");
		
		return true;
	}

}
