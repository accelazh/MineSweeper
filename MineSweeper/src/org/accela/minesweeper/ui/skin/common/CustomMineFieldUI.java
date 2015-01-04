package org.accela.minesweeper.ui.skin.common;

import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.CustomMineFieldDialog;


public class CustomMineFieldUI extends CompositeProcessor
{
	public CustomMineFieldUI()
	{
		super(CustomMineFieldDialog.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		CustomMineFieldDialog dialog = (CustomMineFieldDialog) obj;
		CustomMineFieldDialog.Panel panel = dialog.getPanel();

		panel.setBorder(new EmptyBorder(4 * Common.GUI_STANDARD_INTERVAL,
				2 * Common.GUI_STANDARD_INTERVAL,
				4 * Common.GUI_STANDARD_INTERVAL,
				4 * Common.GUI_STANDARD_INTERVAL));

		panel.getHeightLabel().setPreferredSize(null);
		panel.getWidthLabel().setPreferredSize(null);
		panel.getMineLabel().setPreferredSize(null);
		
		Dimension goodLabelSize = Util.calLargestPreferredSize(
				panel.getHeightLabel(),
				panel.getWidthLabel(),
				panel.getMineLabel());

		panel.getHeightLabel().setPreferredSize(goodLabelSize);
		panel.getWidthLabel().setPreferredSize(goodLabelSize);
		panel.getMineLabel().setPreferredSize(goodLabelSize);

		Dimension goodFieldSize = new Dimension((int)(goodLabelSize.width * 1.2),
				goodLabelSize.height);

		panel.getHeightField().setPreferredSize(goodFieldSize);
		panel.getWidthField().setPreferredSize(goodFieldSize);
		panel.getMineField().setPreferredSize(goodFieldSize);

		return false;
	}
}
