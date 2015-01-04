package org.accela.minesweeper.ui.skin.blue;

import java.awt.Image;
import java.awt.Insets;

import org.accela.minesweeper.ui.border.ImageBorder;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.MineFieldPanel;


public class BlueMineFieldPanelUI extends CompositeProcessor
{
	public BlueMineFieldPanelUI()
	{
		super(MineFieldPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		MineFieldPanel panel = (MineFieldPanel) obj;

		panel.setBorder(new ImageBorder(new Insets(5, 5, 5, 5), new Image[] {
				Util.createImage("blue/common/borderDown/leftTop.png"),
				Util.createImage("blue/common/borderDown/leftBottom.png"),
				Util.createImage("blue/common/borderDown/rightBottom.png"),
				Util.createImage("blue/common/borderDown/rightTop.png") },
				new Image[] {
						Util.createImage("blue/common/borderDown/top.png"),
						Util.createImage("blue/common/borderDown/left.png"),
						Util.createImage("blue/common/borderDown/bottom.png"),
						Util.createImage("blue/common/borderDown/right.png") }));

		return false;
	}
}
