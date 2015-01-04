package org.accela.minesweeper.ui.skin.classic;

import java.awt.Color;
import java.awt.Insets;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.NumberPanel;


public class ClassicNumberPanelUI extends CompositeProcessor
{
	public ClassicNumberPanelUI()
	{
		super(NumberPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		NumberPanel panel = (NumberPanel) obj;

		panel.setBorder(new AsymmetricLineBorder(
				new Insets(1, 1, 1, 1),
				new Color[] { Color.GRAY, Color.GRAY, Color.WHITE, Color.WHITE }));
		panel.setBackpaint(new ColorBackpaint(Color.BLACK));
		
		return true;
	}

}
