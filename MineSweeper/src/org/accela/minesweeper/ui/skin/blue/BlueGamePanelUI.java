package org.accela.minesweeper.ui.skin.blue;

import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.ui.backpaint.ImageBackpaint;
import org.accela.minesweeper.ui.border.ImageBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.GamePanel;


public class BlueGamePanelUI extends CompositeProcessor
{
	public BlueGamePanelUI()
	{
		super(GamePanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		GamePanel panel = (GamePanel) obj;

		panel.setBorder(new CompoundBorder(new ImageBorder(new Insets(5, 5, 5,
				5), new Image[] {
				Util.createImage("blue/common/borderup/leftTop.png"),
				Util.createImage("blue/common/borderup/leftBottom.png"),
				Util.createImage("blue/common/borderup/rightBottom.png"),
				Util.createImage("blue/common/borderup/rightTop.png") },
				new Image[] {
						Util.createImage("blue/common/borderup/top.png"),
						Util.createImage("blue/common/borderup/left.png"),
						Util.createImage("blue/common/borderup/bottom.png"),
						Util.createImage("blue/common/borderup/right.png") }),
				new EmptyBorder(2, 2, 2, 2)));

		panel.setBackpaint(new ImageBackpaint(Util
				.createImage("blue/common/backpaint/withBorderUp.png")));
		panel.setGap(6);

		return true;
	}

}
