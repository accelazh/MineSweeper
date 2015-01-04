package org.accela.minesweeper.ui.skin.classic;

import java.awt.Color;
import java.awt.Insets;

import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.MineFieldPanel;


public class ClassicMineFieldPanelUI extends CompositeProcessor
{
	public ClassicMineFieldPanelUI()
	{
		super(MineFieldPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		MineFieldPanel panel = (MineFieldPanel) obj;

		panel.setBorder(new AsymmetricLineBorder(
				new Insets(3, 3, 3, 3),
				new Color[] { Color.GRAY, Color.GRAY, Color.WHITE, Color.WHITE }));

		return false;
	}
}
