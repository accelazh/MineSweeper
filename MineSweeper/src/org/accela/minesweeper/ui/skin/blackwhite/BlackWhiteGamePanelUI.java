package org.accela.minesweeper.ui.skin.blackwhite;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.GamePanel;


public class BlackWhiteGamePanelUI extends CompositeProcessor
{
	public BlackWhiteGamePanelUI()
	{
		super(GamePanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		GamePanel panel = (GamePanel) obj;

		panel.setBorder(new CompoundBorder(new AsymmetricLineBorder(new Insets(
				3, 3, 0, 0), new Color[] {
				Color.WHITE,
				Color.WHITE,
				Color.GRAY,
				Color.GRAY }), new EmptyBorder(6,6,5,5))); 

		panel.setBackpaint(new ColorBackpaint(Color.LIGHT_GRAY));
		panel.setGap(6);

		return true;
	}

}
