package org.accela.minesweeper.ui.skin.classic;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.StatusPanel;


public class ClassicStatusPanelUI extends CompositeProcessor
{
	public ClassicStatusPanelUI()
	{
		super(StatusPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		StatusPanel panel = (StatusPanel) obj;

		panel.setBorder(new CompoundBorder(new AsymmetricLineBorder(new Insets(
				2, 2, 2, 2), new Color[] {
				Color.GRAY,
				Color.GRAY,
				Color.WHITE,
				Color.WHITE }), new EmptyBorder(4, 5, 4, 7)));
		
		panel.setBackpaint(null);
		panel.setOpaque(false);
		
		return true;
	}

}
