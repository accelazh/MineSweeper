package org.accela.minesweeper.model;

public class GameState
{
	public static enum State
	{
		READY, PLAYING, DEAD, WIN,
	}
	
	private State state=null;

	public GameState()
	{
		this.state=State.READY;
	}
	
	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}
	
	
}
