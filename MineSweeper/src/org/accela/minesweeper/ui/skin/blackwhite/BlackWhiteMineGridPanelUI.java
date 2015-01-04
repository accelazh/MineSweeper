package org.accela.minesweeper.ui.skin.blackwhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import org.accela.minesweeper.ui.backpaint.ColorBackpaint;
import org.accela.minesweeper.ui.backpaint.DashBackpaint;
import org.accela.minesweeper.ui.border.AsymmetricLineBorder;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.MineGridPanel.BtnDownState;
import org.accela.minesweeper.view.MineGridPanel.BtnUpState;
import org.accela.minesweeper.view.MineGridPanel.IconState;


public class BlackWhiteMineGridPanelUI extends CompositeProcessor
{
	private Icon flagIcon = Util.createImageIcon("blackwhite/grid/flag.png");
	private Icon questionIcon = Util
			.createImageIcon("blackwhite/grid/question.png");
	private Icon bombIcon = Util.createImageIcon("blackwhite/grid/bomb.png");
	private Icon wrongBombIcon = Util
			.createImageIcon("blackwhite/grid/wrongBomb.png");
	private Icon deadBombIcon = Util
			.createImageIcon("blackwhite/grid/deadBomb.png");

	private Icon[] numberIcons = new Icon[Common.MINE_GRID_MAX_NUMBER
			- Common.MINE_GRID_MIN_NUMBER + 1];

	public BlackWhiteMineGridPanelUI()
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
						.createImageIcon("blackwhite/grid/" + i + ".png");
			}
			panel.setIcon(MineGridPanel.numToIconState(i), numberIcons[i
					- Common.MINE_GRID_MIN_NUMBER]);
		}

		CompoundBorder borderWhenCovered = new CompoundBorder(new LineBorder(
				Color.BLACK, 1), new AsymmetricLineBorder(
				new Insets(1, 1, 1, 1), new Color[] {
						Color.WHITE,
						Color.WHITE,
						Color.BLACK,
						Color.BLACK }));
		((AsymmetricLineBorder) borderWhenCovered.getInsideBorder())
				.setMergeCornerColor(false);
		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, borderWhenCovered);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, borderWhenCovered);

		AsymmetricLineBorder borderWhenUncovered = new AsymmetricLineBorder(
				new Insets(1, 1, 0, 0), new Color[] {
						Color.BLACK,
						Color.BLACK,
						Color.BLACK,
						Color.BLACK });
		borderWhenUncovered.setMergeCornerColor(false);
		panel.setBorderWhenBtnDown(BtnDownState.SAFE, borderWhenUncovered);
		panel.setBorderWhenBtnDown(BtnDownState.ALERT, borderWhenUncovered);
		panel.setBorderWhenBtnDown(BtnDownState.DEAD, borderWhenUncovered);

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, new DashBackpaint(
				Color.BLACK));
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW, new DashBackpaint(
				Color.BLACK));

		panel.setBackpaintWhenBtnDown(BtnDownState.SAFE, new ColorBackpaint(
				Color.WHITE));
		panel.setBackpaintWhenBtnDown(BtnDownState.ALERT, new ColorBackpaint(
				Color.WHITE));
		panel.setBackpaintWhenBtnDown(BtnDownState.DEAD, new ColorBackpaint(
				Color.BLACK));

		panel.setIconOffsetWhenBtnUp(new Point2D.Double(0, 0));
		panel.setIconOffsetWhenBtnDown(new Point2D.Double(0.5, 0.5));
		
		panel.setBackground(Color.WHITE);

		return true;
	}
}
