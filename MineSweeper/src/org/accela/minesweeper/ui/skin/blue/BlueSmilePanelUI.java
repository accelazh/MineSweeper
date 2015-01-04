package org.accela.minesweeper.ui.skin.blue;

import java.awt.Dimension;
import java.awt.Point;

import org.accela.minesweeper.ui.backpaint.ImageBackpaint;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.SmilePanel;
import org.accela.minesweeper.view.SmilePanel.BtnDownState;
import org.accela.minesweeper.view.SmilePanel.BtnUpState;
import org.accela.minesweeper.view.SmilePanel.IconState;


public class BlueSmilePanelUI extends CompositeProcessor
{
	public BlueSmilePanelUI()
	{
		super(SmilePanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		SmilePanel panel = (SmilePanel) obj;

		panel.setPreferredSize(new Dimension(26, 26));

		panel.setIcon(IconState.SMILE, Util.createImageIcon("blue/smile/smile.png"));
		panel.setIcon(IconState.NERVOUS, Util.createImageIcon("blue/smile/nervous.png"));
		panel.setIcon(IconState.DEAD, Util.createImageIcon("blue/smile/dead.png"));
		panel.setIcon(IconState.WIN, Util.createImageIcon("blue/smile/win.png"));

		panel.setBorderWhenBtnUp(BtnUpState.NORMAL, null);
		panel.setBorderWhenBtnUp(BtnUpState.GLOW, null);

		panel.setBorderWhenBtnDown(BtnDownState.NORMAL, null);

		panel.setBackpaintWhenBtnUp(BtnUpState.NORMAL, new ImageBackpaint(Util
				.createImage("blue/smile/backpaintWhenBtnUp.png")));
		panel.setBackpaintWhenBtnUp(BtnUpState.GLOW, new ImageBackpaint(Util
				.createImage("blue/smile/backpaintWhenBtnUpAndGlow.png")));

		panel.setBackpaintWhenBtnDown(BtnDownState.NORMAL, new ImageBackpaint(Util
				.createImage("blue/smile/backpaintWhenBtnDown.png")));

		panel.setIconOffsetWhenBtnUp(new Point(1, 1));
		panel.setIconOffsetWhenBtnDown(new Point(2, 2));

		return true;
	}

}
