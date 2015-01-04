package org.accela.minesweeper.view;

public class SmilePanel
		extends
		BasicButtonPanel<SmilePanel.IconState, SmilePanel.BtnUpState, SmilePanel.BtnDownState>
{
	private static final long serialVersionUID = 1L;

	public static enum IconState
	{
		SMILE, NERVOUS, DEAD, WIN
	}

	public static enum BtnUpState
	{
		NORMAL, GLOW
	}

	public static enum BtnDownState
	{
		NORMAL
	}

	public SmilePanel()
	{
		super(IconState.values().length, BtnUpState.values().length,
				BtnDownState.values().length, IconState.SMILE, BtnState.UP,
				BtnUpState.NORMAL, BtnDownState.NORMAL);
	}
}
