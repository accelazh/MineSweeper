package org.accela.minesweeper.ui.skin.blue;

import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.accela.minesweeper.ui.backpaint.ImageBackpaint;
import org.accela.minesweeper.ui.border.ImageBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.StatusPanel;


public class BlueStatusPanelUI extends CompositeProcessor
{
	public BlueStatusPanelUI()
	{
		super(StatusPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		StatusPanel panel = (StatusPanel) obj;

		panel.setBorder(new CompoundBorder(new ImageBorder(new Insets(5, 5, 5, 5), new Image[] {
			Util.createImage("blue/common/borderDown/leftTop.png"),
			Util.createImage("blue/common/borderDown/leftBottom.png"),
			Util.createImage("blue/common/borderDown/rightBottom.png"),
			Util.createImage("blue/common/borderDown/rightTop.png") },
			new Image[] {
					Util.createImage("blue/common/borderDown/top.png"),
					Util.createImage("blue/common/borderDown/left.png"),
					Util.createImage("blue/common/borderDown/bottom.png"),
					Util.createImage("blue/common/borderDown/right.png") }), new EmptyBorder(2, 2, 2, 2)));
		
		panel.setBackpaint(new ImageBackpaint(Util.createImage("blue/status/backpaint.png")));
		
		return true;
	}

}
