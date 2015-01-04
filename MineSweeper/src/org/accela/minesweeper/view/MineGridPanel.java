package org.accela.minesweeper.view;

import java.awt.Point;

import org.accela.minesweeper.util.Common;

public class MineGridPanel
		extends
		BasicButtonPanel<MineGridPanel.IconState, MineGridPanel.BtnUpState, MineGridPanel.BtnDownState>
{
	private static final long serialVersionUID = 1L;

	// NOTE，变更是需要与Common中定义的MINE_GRID_MIN/MAX_NUMBER保持一致。
	public static enum IconState
	{
		CLEAR, FLAG, QUESTION, BOMB, WRONG_BOMB, DEAD_BOMB,

		NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8
	}

	public static enum BtnUpState
	{
		NORMAL, GLOW
	}

	// 翻开格子后，如果周围零颗雷，称作safe，周围有雷，称作alert，已经被炸死了，称作dead。分别使用不同的背景予以标识
	public static enum BtnDownState
	{
		SAFE, ALERT, DEAD
	}

	private MineFieldPanel parentField = null;

	public MineGridPanel()
	{
		this(null);
	}

	public MineGridPanel(MineFieldPanel parentField)
	{
		super(IconState.values().length, BtnUpState.values().length,
				BtnDownState.values().length, IconState.CLEAR, BtnState.UP,
				BtnUpState.NORMAL, BtnDownState.SAFE);

		this.setParentField(parentField);
	}

	public MineFieldPanel getParentField()
	{
		return parentField;
	}

	public void setParentField(MineFieldPanel field)
	{
		this.parentField = field;
	}

	public static int iconStateToNum(IconState state)
	{
		int ret = state.ordinal() - IconState.NUM_1.ordinal()
				+ Common.MINE_GRID_MIN_NUMBER;
		if (ret > Common.MINE_GRID_MAX_NUMBER
				|| ret < Common.MINE_GRID_MIN_NUMBER)
		{
			throw new IllegalArgumentException("state is not a num state");
		}

		return ret;
	}

	public static IconState numToIconState(int num)
	{
		if (num > Common.MINE_GRID_MAX_NUMBER
				|| num < Common.MINE_GRID_MIN_NUMBER)
		{
			throw new IllegalArgumentException("num out of bounds");
		}
		
		return IconState.values()[num - Common.MINE_GRID_MIN_NUMBER
				+ IconState.NUM_1.ordinal()];

	}

	public Point getPosition()
	{
		return this.getParentField().getGridPos(this);
	}

}