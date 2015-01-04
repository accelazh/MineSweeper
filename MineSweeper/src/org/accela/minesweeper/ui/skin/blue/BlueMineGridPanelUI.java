package org.accela.minesweeper.ui.skin.blue;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import javax.swing.Icon;

import org.accela.minesweeper.ui.backpaint.Backpaint;
import org.accela.minesweeper.ui.backpaint.ImageBackpaint;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.MineGridPanel.BtnDownState;
import org.accela.minesweeper.view.MineGridPanel.BtnUpState;
import org.accela.minesweeper.view.MineGridPanel.IconState;

public class BlueMineGridPanelUI extends CompositeProcessor
{
	public BlueMineGridPanelUI()
	{
		super(MineGridPanel.class, null);
	}

	private Icon flagIcon = Util.createImageIcon("blue/grid/flag.png");

	private Icon questionIcon = Util.createImageIcon("blue/grid/question.png");

	private Icon bombIcon = Util.createImageIcon("blue/grid/bomb.png");

	private Icon wrongBombIcon = Util
			.createImageIcon("blue/grid/wrongBomb.png");

	private Icon deadBombIcon = bombIcon;

	private Icon[] numberIcons = new Icon[Common.MINE_GRID_MAX_NUMBER
			- Common.MINE_GRID_MIN_NUMBER + 1];

	private Backpaint backpaintWhenCovered = new ImageBackpaint(
			Util.createImage("blue/grid/backpaintWhenCovered.png"));
	private Backpaint backpaintWhenCoveredAndGlow = new ImageBackpaint(
			Util.createImage("blue/grid/backpaintWhenCoveredAndGlow.png"));

	private Backpaint backpaintWhenUncoveredAndSafe = new ImageBackpaint(
			Util.createImage("blue/grid/backpaintWhenUncoveredAndSafe.png"));
	private Backpaint backpaintWhenUncoveredAndAlert = new ImageBackpaint(
			Util.createImage("blue/grid/backpaintWhenUncoveredAndAlert.png"));
	private Backpaint backpaintWhenUncoveredAndDead = new ImageBackpaint(
			Util.createImage("blue/grid/backpaintWhenUncoveredAndDead.png"));

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
			int iconIdx = i - Common.MINE_GRID_MIN_NUMBER;
			if (numberIcons[iconIdx] == null)
			{
				numberIcons[iconIdx] = Util.createImageIcon("blue/grid/" + i
						+ ".png");
			}

			panel.setIcon(MineGridPanel.numToIconState(i), numberIcons[iconIdx]);
		}

		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, null);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, null);

		panel.setBorderWhenBtnDown(BtnDownState.SAFE, null);
		panel.setBorderWhenBtnDown(BtnDownState.ALERT, null);
		panel.setBorderWhenBtnDown(BtnDownState.DEAD, null);

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, backpaintWhenCovered);
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW,
				backpaintWhenCoveredAndGlow);

		panel.setBackpaintWhenBtnDown(BtnDownState.SAFE,
				backpaintWhenUncoveredAndSafe);
		panel.setBackpaintWhenBtnDown(BtnDownState.ALERT,
				backpaintWhenUncoveredAndAlert);
		panel.setBackpaintWhenBtnDown(BtnDownState.DEAD,
				backpaintWhenUncoveredAndDead);

		panel.setIconOffsetWhenBtnUp(new Point2D.Double(0, 0));
		panel.setIconOffsetWhenBtnDown(new Point2D.Double(0.5, 0.5));

		return true;
	}

}
