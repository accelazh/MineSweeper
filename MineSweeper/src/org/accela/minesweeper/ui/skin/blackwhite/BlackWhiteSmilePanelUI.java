package org.accela.minesweeper.ui.skin.blackwhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import org.accela.minesweeper.ui.backpaint.DashBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.SmilePanel;
import org.accela.minesweeper.view.SmilePanel.BtnDownState;
import org.accela.minesweeper.view.SmilePanel.BtnUpState;
import org.accela.minesweeper.view.SmilePanel.IconState;


public class BlackWhiteSmilePanelUI extends CompositeProcessor
{
	public BlackWhiteSmilePanelUI()
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
				Util.createImageIcon("blackwhite/smile/smile.png"));
		panel.setIcon(
				IconState.NERVOUS,
				Util.createImageIcon("blackwhite/smile/nervous.png"));
		panel.setIcon(
				IconState.DEAD,
				Util.createImageIcon("blackwhite/smile/dead.png"));
		panel.setIcon(
				IconState.WIN,
				Util.createImageIcon("blackwhite/smile/win.png"));

		Border borderWhenBtnUp = new CompoundBorder(new AsymmetricLineBorder(
				new Insets(1, 1, 1, 1), new Color[] {
						Color.BLACK,
						Color.BLACK,
						Color.BLACK,
						Color.BLACK }), new AsymmetricLineBorder(new Insets(2,
				2, 2, 2), new Color[] {
				Color.WHITE,
				Color.WHITE,
				Color.BLACK,
				Color.BLACK }));
		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, borderWhenBtnUp);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, borderWhenBtnUp);

		panel.setBorderWhenBtnDown(BtnDownState.NORMAL, new CompoundBorder(
				new AsymmetricLineBorder(new Insets(1, 1, 1, 1), new Color[] {
						Color.BLACK,
						Color.BLACK,
						Color.BLACK,
						Color.BLACK }), new AsymmetricLineBorder(new Insets(1,
						1, 0, 0), new Color[] {
						Color.BLACK,
						Color.BLACK,
						Color.BLACK,
						Color.BLACK })));

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, new DashBackpaint(
				Color.BLACK));
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW, new DashBackpaint(
				Color.BLACK));

		panel.setBackpaintWhenBtnDown(BtnDownState.NORMAL, new DashBackpaint(
				Color.BLACK));

		panel.setIconOffsetWhenBtnUp(new Point(1, 1));
		panel.setIconOffsetWhenBtnDown(new Point(2, 2));

		panel.setBackground(Color.WHITE);
		
		return true;
	}

}
