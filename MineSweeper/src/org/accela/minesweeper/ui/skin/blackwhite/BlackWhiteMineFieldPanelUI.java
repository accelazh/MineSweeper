package org.accela.minesweeper.ui.skin.blackwhite;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.ui.border.EnhancedLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.MineFieldPanel;


public class BlackWhiteMineFieldPanelUI extends CompositeProcessor
{
	public BlackWhiteMineFieldPanelUI()
	{
		super(MineFieldPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		MineFieldPanel panel = (MineFieldPanel) obj;

		Border b1 = new AsymmetricLineBorder(new Insets(2, 2, 2, 2),
				new Color[] {
						Color.BLACK,
						Color.BLACK,
						Color.WHITE,
						Color.WHITE });
		Border b2 = new EnhancedLineBorder(new Insets(0, 0, 1, 1), Color.WHITE);
		Border b3 = new EnhancedLineBorder(new Insets(0, 0, 1, 1), Color.BLACK);

		panel.setBorder(new CompoundBorder(b1, new CompoundBorder(b2, b3)));

		return false;
	}
}
