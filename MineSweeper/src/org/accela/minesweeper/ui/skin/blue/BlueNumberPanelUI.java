package org.accela.minesweeper.ui.skin.blue;

import java.awt.Color;
import java.awt.Insets;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.NumberPanel;


public class BlueNumberPanelUI extends CompositeProcessor
{
	public BlueNumberPanelUI()
	{
		super(NumberPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		NumberPanel panel = (NumberPanel) obj;

		panel.setBorder(new AsymmetricLineBorder(new Insets(2, 2, 2, 2),
				new Color[] {
						new Color(23, 60, 109),
						new Color(23, 60, 109),
						new Color(181, 208, 225),
						new Color(181, 208, 225) }));
		panel.setBackpaint(new ColorBackpaint(new Color(12, 31, 55)));

		return true;
	}

}
