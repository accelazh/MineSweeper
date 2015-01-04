package org.accela.minesweeper.view;

import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.border.Border;

import org.accela.minesweeper.ui.backpaint.Backpaint;


public class BasicButtonPanel<IconState extends Enum<IconState>, BtnUpState extends Enum<BtnUpState>, BtnDownState extends Enum<BtnDownState>>
		extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	public static final String ICON_STATE_CHANGED_PROPERTY = "iconState";

	public static final String BTN_STATE_CHANGED_PROPERTY = "btnState";
	public static final String BTN_UP_STATE_CHANGED_PROPERTY = "btnUpState";
	public static final String BTN_DOWN_STATE_CHANGED_PROPERTY = "btnDownState";

	private static final String[] BTN_SUB_STATE_CHANGED_PROPERTY = new String[] {
			BTN_UP_STATE_CHANGED_PROPERTY,
			BTN_DOWN_STATE_CHANGED_PROPERTY };

	public static enum BtnState
	{
		UP, DOWN
	}

	private Icon[] icons = null;

	private Border[][] borders = new Border[BtnState.values().length][];

	private Backpaint[][] backpaints = new Backpaint[BtnState.values().length][];

	private IconState iconState;

	private BtnState btnState;
	private Enum<?>[] btnSubStates = new Enum<?>[BtnState.values().length];
	
	private Point2D[] iconOffsets=new Point2D[BtnState.values().length];

	public BasicButtonPanel(int iconStateCount, int btnUpStateCount,
			int btnDownStateCount, IconState initIconState,
			BtnState initBtnState, BtnUpState initBtnUpState,
			BtnDownState initBtnDownState)
	{
		this.icons = new Icon[iconStateCount];

		this.borders[BtnState.UP.ordinal()] = new Border[btnUpStateCount];
		this.backpaints[BtnState.UP.ordinal()] = new Backpaint[btnUpStateCount];

		this.borders[BtnState.DOWN.ordinal()] = new Border[btnDownStateCount];
		this.backpaints[BtnState.DOWN.ordinal()] = new Backpaint[btnDownStateCount];

		if (initIconState == null || initBtnState == null
				|| initBtnUpState == null || initBtnDownState == null)
		{
			throw new IllegalArgumentException("init states should not be null");
		}

		this.iconState = initIconState;

		this.btnState = initBtnState;
		this.btnSubStates[BtnState.UP.ordinal()] = initBtnUpState;
		this.btnSubStates[BtnState.DOWN.ordinal()] = initBtnDownState;
		
		this.syncCompWithIconState();
		this.syncCompWithBtnState();
	}

	private void syncCompWithIconState()
	{
		this.setIcon(this.icons[this.iconState.ordinal()]);
	}

	private void syncCompWithBtnState()
	{
		this.setBorder(this.borders[this.btnState.ordinal()][getBtnSubState(
				this.btnState).ordinal()]);
		this.setBackpaint(this.backpaints[this.btnState.ordinal()][getBtnSubState(
				this.btnState).ordinal()]);
		this.setIconOffset(this.iconOffsets[this.btnState.ordinal()]);
	}

	// =================================================================

	public IconState getIconState()
	{
		return this.iconState;
	}

	public void setIconState(IconState state)
	{
		IconState oldState = this.iconState;
		this.iconState = state;
		this.syncCompWithIconState();

		this.firePropertyChange(
				ICON_STATE_CHANGED_PROPERTY,
				oldState,
				this.iconState);
	}

	public BtnState getBtnState()
	{
		return this.btnState;
	}

	public void setBtnState(BtnState state)
	{
		BtnState oldValue = this.btnState;
		this.btnState = state;
		this.syncCompWithBtnState();

		this.firePropertyChange(
				BTN_STATE_CHANGED_PROPERTY,
				oldValue,
				this.btnState);
	}

	private Enum<?> getBtnSubState(BtnState btnState)
	{
		return this.btnSubStates[btnState.ordinal()];
	}

	private void setBtnSubState(BtnState btnState, Enum<?> btnSubState)
	{
		Enum<?> old = this.btnSubStates[btnState.ordinal()];
		this.btnSubStates[btnState.ordinal()] = btnSubState;
		this.syncCompWithBtnState();

		this.firePropertyChange(
				BTN_SUB_STATE_CHANGED_PROPERTY[btnState.ordinal()],
				old,
				this.btnSubStates[btnState.ordinal()]);
	}

	public Icon getIcon(IconState state)
	{
		return this.icons[state.ordinal()];
	}

	public void setIcon(IconState state, Icon icon)
	{
		this.icons[state.ordinal()] = icon;
		this.syncCompWithIconState();
	}

	private Border getBorder(BtnState btnState, Enum<?> btnSubState)
	{
		return this.borders[btnState.ordinal()][btnSubState.ordinal()];
	}

	private void setBorder(BtnState btnState, Enum<?> btnSubState, Border border)
	{
		this.borders[btnState.ordinal()][btnSubState.ordinal()] = border;
		this.syncCompWithBtnState();
	}

	private Backpaint getBackpaint(BtnState btnState, Enum<?> btnSubState)
	{
		return this.backpaints[btnState.ordinal()][btnSubState.ordinal()];
	}

	private void setBackpaint(BtnState btnState, Enum<?> btnSubState,
			Backpaint backpaints)
	{
		this.backpaints[btnState.ordinal()][btnSubState.ordinal()] = backpaints;
		this.syncCompWithBtnState();
	}
	
	private Point2D getIconOffset(BtnState state)
	{
		return this.iconOffsets[state.ordinal()];
	}
	
	private void setIconOffset(BtnState state, Point2D offset)
	{
		this.iconOffsets[state.ordinal()]=offset;
		this.syncCompWithBtnState();
	}

	// =================================================================

	@SuppressWarnings("unchecked")
	public BtnUpState getBtnUpState()
	{
		return (BtnUpState) this.getBtnSubState(BtnState.UP);
	}

	@SuppressWarnings("unchecked")
	public BtnDownState getBtnDownState()
	{
		return (BtnDownState) this.getBtnSubState(BtnState.DOWN);
	}

	public void setBtnUpState(BtnUpState state)
	{
		this.setBtnSubState(BtnState.UP, state);
	}

	public void setBtnDownState(BtnDownState state)
	{
		this.setBtnSubState(BtnState.DOWN, state);
	}

	public Border getBorderWhenBtnUp(BtnUpState state)
	{
		return this.getBorder(BtnState.UP, state);
	}

	public Border getBorderWhenBtnDown(BtnDownState state)
	{
		return this.getBorder(BtnState.DOWN, state);
	}

	public void setBorderWhenBtnUp(BtnUpState state, Border border)
	{
		this.setBorder(BtnState.UP, state, border);
	}

	public void setBorderWhenBtnDown(BtnDownState state, Border border)
	{
		this.setBorder(BtnState.DOWN, state, border);
	}

	public Backpaint getBackpaintWhenBtnUp(BtnUpState state)
	{
		return this.getBackpaint(BtnState.UP, state);
	}

	public Backpaint getBackpaintWhenBtnDown(BtnDownState state)
	{
		return this.getBackpaint(BtnState.DOWN, state);
	}

	public void setBackpaintWhenBtnUp(BtnUpState state, Backpaint backpaint)
	{
		this.setBackpaint(BtnState.UP, state, backpaint);
	}

	public void setBackpaintWhenBtnDown(BtnDownState state, Backpaint backpaint)
	{
		this.setBackpaint(BtnState.DOWN, state, backpaint);
	}
	
	public Point2D getIconOffsetWhenBtnUp(BtnUpState state)
	{
		return this.getIconOffset(BtnState.UP);
	}

	public Point2D getIconOffsetWhenBtnDown(BtnDownState state)
	{
		return this.getIconOffset(BtnState.DOWN);
	}

	public void setIconOffsetWhenBtnUp(Point2D iconOffset)
	{
		this.setIconOffset(BtnState.UP, iconOffset);
	}

	public void setIconOffsetWhenBtnDown(Point2D iconOffset)
	{
		this.setIconOffset(BtnState.DOWN, iconOffset);
	}
	
}
