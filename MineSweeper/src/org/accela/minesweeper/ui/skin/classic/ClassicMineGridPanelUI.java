package org.accela.minesweeper.ui.skin.classic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.border.Border;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.MineGridPanel.BtnDownState;
import org.accela.minesweeper.view.MineGridPanel.BtnUpState;
import org.accela.minesweeper.view.MineGridPanel.IconState;


public class ClassicMineGridPanelUI extends CompositeProcessor
{
	private Icon flagIcon = Util.createImageIcon("classic/grid/flag.png");
	private Icon questionIcon = Util
			.createImageIcon("classic/grid/question.png");
	private Icon bombIcon = Util.createImageIcon("classic/grid/bomb.png");
	private Icon wrongBombIcon = Util
			.createImageIcon("classic/grid/wrongBomb.png");
	private Icon deadBombIcon = bombIcon;

	private Icon[] numberIcons = new Icon[Common.MINE_GRID_MAX_NUMBER
			- Common.MINE_GRID_MIN_NUMBER + 1];

	public ClassicMineGridPanelUI()
	{
		super(MineGridPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		MineGridPanel panel = (MineGridPanel) obj;

		panel.setPreferredSize(new Dimension(16, 16));

		panel.setIcon(IconState.FLAG, flagIcon);
		panel.setIcon(IconState.QUESTION, questionIcon);
		panel.setIcon(IconState.BOMB, bombIcon);
		panel.setIcon(IconState.WRONG_BOMB, wrongBombIcon);
		panel.setIcon(IconState.DEAD_BOMB, deadBombIcon);

		for (int i = Common.MINE_GRID_MIN_NUMBER; i <= Common.MINE_GRID_MAX_NUMBER; i++)
		{
			if (numberIcons[i - Common.MINE_GRID_MIN_NUMBER] == null)
			{
				numberIcons[i - Common.MINE_GRID_MIN_NUMBER] = Util
						.createImageIcon("classic/grid/" + i + ".png");
			}
			panel.setIcon(MineGridPanel.numToIconState(i), numberIcons[i
					- Common.MINE_GRID_MIN_NUMBER]);
		}

		Border borderWhenCovered = new AsymmetricLineBorder(new Insets(2, 2, 2,
				2), new Color[] {
				Color.WHITE,
				Color.WHITE,
				Color.GRAY,
				Color.GRAY });
		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, borderWhenCovered);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, borderWhenCovered);

		Border borderWhenUncovered = new AsymmetricLineBorder(new Insets(1, 1,
				0, 0), new Color[] {
				Color.GRAY,
				Color.GRAY,
				Color.GRAY,
				Color.GRAY });
		panel.setBorderWhenBtnDown(BtnDownState.SAFE, borderWhenUncovered);
		panel.setBorderWhenBtnDown(BtnDownState.ALERT, borderWhenUncovered);
		panel.setBorderWhenBtnDown(BtnDownState.DEAD, borderWhenUncovered);

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, new ColorBackpaint(
				Color.LIGHT_GRAY));
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW, new ColorBackpaint(
				Color.LIGHT_GRAY));

		panel.setBackpaintWhenBtnDown(BtnDownState.SAFE, new ColorBackpaint(
				Color.LIGHT_GRAY));
		panel.setBackpaintWhenBtnDown(BtnDownState.ALERT, new ColorBackpaint(
				Color.LIGHT_GRAY));
		panel.setBackpaintWhenBtnDown(BtnDownState.DEAD, new ColorBackpaint(
				Color.RED));

		panel.setIconOffsetWhenBtnUp(new Point2D.Double(0, 0));
		panel.setIconOffsetWhenBtnDown(new Point2D.Double(0.5, 0.5));

		return true;
	}
}
