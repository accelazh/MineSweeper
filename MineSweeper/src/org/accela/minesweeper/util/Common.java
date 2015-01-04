package org.accela.minesweeper.util;

import java.awt.Dimension;

public class Common
{
	public static final int MINE_GRID_MIN_NUMBER = 1;	//NOTE，变更是需要与MineGridPanel.IconState中定义的NUM保持一致。
	public static final int MINE_GRID_MAX_NUMBER = 8;	//NOTE，变更是需要与MineGridPanel.IconState中定义的NUM保持一致。

	public static final int MIN_DIGIT=0;
	public static final int MAX_DIGIT=9;
	
	public static final int GUI_STANDARD_INTERVAL=5;
	
	public static final Dimension MIN_MINE_FIELD_SIZE = new Dimension(9, 9);
	public static final Dimension MAX_MINE_FIELD_SIZE = new Dimension(70, 40);
	
	public static int getMinMineCount()
	{
		return Math.min(10, getMaxMineCount(MIN_MINE_FIELD_SIZE));
	}
	
	public static int getMaxMineCount(Dimension fieldSize)
	{
		return (fieldSize.width - 1) * (fieldSize.height - 1);
	}

}
