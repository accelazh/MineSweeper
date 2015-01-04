package org.accela.minesweeper.ui.skin.classic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.SmilePanel;
import org.accela.minesweeper.view.SmilePanel.BtnDownState;
import org.accela.minesweeper.view.SmilePanel.BtnUpState;
import org.accela.minesweeper.view.SmilePanel.IconState;


public class ClassicSmilePanelUI extends CompositeProcessor
{
	public ClassicSmilePanelUI()
	{
		super(SmilePanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		SmilePanel panel = (SmilePanel) obj;

		panel.setPreferredSize(new Dimension(26, 26));

		panel.setIcon(
				IconState.SMILE,
				Util.createImageIcon("classic/smile/smile.png"));
		panel.setIcon(
				IconState.NERVOUS,
				Util.createImageIcon("classic/smile/nervous.png"));
		panel.setIcon(
				IconState.DEAD,
				Util.createImageIcon("classic/smile/dead.png"));
		panel.setIcon(
				IconState.WIN,
				Util.createImageIcon("classic/smile/win.png"));

		Border borderWhenBtnUp = new CompoundBorder(new AsymmetricLineBorder(
				new Insets(1, 1, 1, 1), new Color[] {
						Color.GRAY,
						Color.GRAY,
						Color.GRAY,
						Color.GRAY }), new AsymmetricLineBorder(new Insets(2,
				2, 2, 2), new Color[] {
				Color.WHITE,
				Color.WHITE,
				Color.GRAY,
				Color.GRAY }));
		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, borderWhenBtnUp);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, borderWhenBtnUp);

		panel.setBorderWhenBtnDown(BtnDownState.NORMAL, new CompoundBorder(
				new AsymmetricLineBorder(new Insets(1, 1, 1, 1), new Color[] {
						Color.GRAY,
						Color.GRAY,
						Color.GRAY,
						Color.GRAY }), new AsymmetricLineBorder(new Insets(1,
						1, 0, 0), new Color[] {
						Color.GRAY,
						Color.GRAY,
						Color.GRAY,
						Color.GRAY })));

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, new ColorBackpaint(
				Color.LIGHT_GRAY));
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW, new ColorBackpaint(
				Color.LIGHT_GRAY));

		panel.setBackpaintWhenBtnDown(BtnDownState.NORMAL, new ColorBackpaint(
				Color.LIGHT_GRAY));

		panel.setIconOffsetWhenBtnUp(new Point(1, 1));
		panel.setIconOffsetWhenBtnDown(new Point(2, 2));

		return true;
	}

}
