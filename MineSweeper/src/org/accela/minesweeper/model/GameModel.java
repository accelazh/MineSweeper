package org.accela.minesweeper.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.accela.minesweeper.model.operation.Operation;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.util.ObjectActionEvent;

public class GameModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String MINE_FIELD_UPDATED_ACTION_COMMAND = "mineFieldUpdated";
	public static final String SMILE_UPDATED_ACTION_COMMAND = "smileUpdated";
	public static final String FLAG_COUNTER_UPDATED_ACTION_COMMAND = "flagCounterUpdated";
	public static final String TIME_COUNTER_UPDATED_ACTION_COMMAND = "timeCounterUpdated";

	public static final String GAME_START_ACTION_COMMAND = "gameStart";
	public static final String GAME_WIN_ACTION_COMMAND = "gameWin";
	public static final String GAME_DEAD_ACTION_COMMAND = "gameDead";

	private EventManager eventManager = new EventManager();

	private GameState gameState = new GameState();

	private boolean questionMarkEnabled = true;

	private Dimension fieldSizeOnStart = Common.MIN_MINE_FIELD_SIZE;

	private int mineCountOnStart = Common.getMinMineCount();

	private MineFieldModel field = new MineFieldModel(gameState);

	private SmileModel smile = new SmileModel();

	private FlagCounterModel flagCounter = new FlagCounterModel();

	private TimeCounterModel timeCounter = new TimeCounterModel();

	public GameModel()
	{
		initEvents();
		reset();
	}

	private void initEvents()
	{
		timeCounter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				eventManager.fireActionEvent(new ObjectActionEvent(
						GameModel.this, ActionEvent.ACTION_PERFORMED,
						TIME_COUNTER_UPDATED_ACTION_COMMAND, timeCounter
								.getCounter()));
			}
		});
		flagCounter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				eventManager.fireActionEvent(new ObjectActionEvent(
						GameModel.this, ActionEvent.ACTION_PERFORMED,
						FLAG_COUNTER_UPDATED_ACTION_COMMAND, flagCounter
								.getFlagCount()));
			}
		});

		smile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals(
						SmileModel.BUTTON_CLICK_ACTION_COMMAND))
				{
					onSmileClickAction();
				}
				else
				{
					eventManager.fireActionEvent(new ObjectActionEvent(
							GameModel.this, ActionEvent.ACTION_PERFORMED,
							SMILE_UPDATED_ACTION_COMMAND, smile));
				}
			}
		});

		field.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals(
						MineFieldModel.FLAG_ON_ACTION_COMMAND))
				{
					ObjectActionEvent oe = (ObjectActionEvent) e;
					onGridFlagOnAction((MineGridModel[]) oe.getObject());
				}
				else if (e.getActionCommand().equals(
						MineFieldModel.FLAG_OFF_ACTION_COMMAND))
				{
					ObjectActionEvent oe = (ObjectActionEvent) e;
					onGridFlagOffAction((MineGridModel[]) oe.getObject());
				}
				else if (e.getActionCommand().equals(
						MineFieldModel.GRID_DOWN_ACTION_COMMAND))
				{
					ObjectActionEvent oe = (ObjectActionEvent) e;
					eventManager.fireActionEvent(new ObjectActionEvent(this,
							ActionEvent.ACTION_PERFORMED,
							MINE_FIELD_UPDATED_ACTION_COMMAND, oe.getObject()));
					onGridDownAction((MineGridModel[]) oe.getObject());
				}
				else if (e.getActionCommand().equals(
						MineFieldModel.GRID_UPDATED_ACTION_COMMAND))
				{
					ObjectActionEvent oe = (ObjectActionEvent) e;
					eventManager.fireActionEvent(new ObjectActionEvent(this,
							ActionEvent.ACTION_PERFORMED,
							MINE_FIELD_UPDATED_ACTION_COMMAND, oe.getObject()));
				}
				else
				{
					assert (false);
				}
			}
		});
	}

	private void onSmileClickAction()
	{
		this.reset();
	}

	private void onGridFlagOnAction(MineGridModel[] gs)
	{
		for (int i = 0; i < gs.length; i++)
		{
			flagCounter.decrFlagCount();
		}

		if (field.isMineAllFlaged() && !field.hasWrongFlag())
		{
			onWin();
		}
	}

	private void onWin()
	{
		this.gameState.setState(GameState.State.WIN);
		timeCounter.stop();
		smile.setState(SmileModel.State.UP_WIN);
		
		eventManager.fireActionEvent(new ObjectActionEvent(
				GameModel.this, ActionEvent.ACTION_PERFORMED,
				SMILE_UPDATED_ACTION_COMMAND, smile));
		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED, GAME_WIN_ACTION_COMMAND,
				timeCounter.getCounter()));
	}

	private void onGridFlagOffAction(MineGridModel[] gs)
	{
		for (int i = 0; i < gs.length; i++)
		{
			flagCounter.incrFlagCount();
		}
	}

	private void onGridDownAction(MineGridModel[] gs)
	{
		if (gameState.getState().equals(GameState.State.READY))
		{
			timeCounter.start();
			gameState.setState(GameState.State.PLAYING);

			eventManager.fireActionEvent(new ObjectActionEvent(this,
					ActionEvent.ACTION_PERFORMED, GAME_START_ACTION_COMMAND,
					null));
		}
		else if (gameState.getState().equals(GameState.State.PLAYING))
		{
			for (MineGridModel g : gs)
			{
				if (g.isMine())
				{
					onDead();
					break;
				}
			}
		}
		else
		{
			assert (false);
		}
	}

	private void onDead()
	{
		this.gameState.setState(GameState.State.WIN);
		timeCounter.stop();
		smile.setState(SmileModel.State.UP_DEAD);

		List<MineGridModel> modifiedList = new LinkedList<MineGridModel>();
		field.uncoverAllBombsAndWrongBombs(modifiedList);

		eventManager.fireActionEvent(new ObjectActionEvent(
				GameModel.this, ActionEvent.ACTION_PERFORMED,
				SMILE_UPDATED_ACTION_COMMAND, smile));
		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				MINE_FIELD_UPDATED_ACTION_COMMAND, modifiedList
						.toArray(new MineGridModel[0])));
		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED, GAME_DEAD_ACTION_COMMAND,
				timeCounter.getCounter()));
	}

	public void pause(boolean pause)
	{
		timeCounter.pause(pause);
	}

	public void onOperation(Operation op)
	{
		smile.onOperation(op);
		field.onOperation(op);
	}

	public int getMaxMineCount()
	{
		return Common.getMaxMineCount(fieldSizeOnStart);
	}

	public void addActionListener(ActionListener l)
	{
		eventManager.addActionListener(l);
	}

	public void removeActionListener(ActionListener l)
	{
		eventManager.removeActionListener(l);
	}

	public void reset()
	{
		gameState.setState(GameState.State.READY);
		field.reset(
				fieldSizeOnStart.width,
				fieldSizeOnStart.height,
				mineCountOnStart);
		smile.reset();
		flagCounter.setFlagCount(mineCountOnStart);
		timeCounter.reset();

		eventManager
				.fireActionEvent(new ObjectActionEvent(GameModel.this,
						ActionEvent.ACTION_PERFORMED,
						FLAG_COUNTER_UPDATED_ACTION_COMMAND, flagCounter
								.getFlagCount()));
		eventManager.fireActionEvent(new ObjectActionEvent(GameModel.this,
				ActionEvent.ACTION_PERFORMED, SMILE_UPDATED_ACTION_COMMAND,
				smile));
		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				TIME_COUNTER_UPDATED_ACTION_COMMAND, timeCounter.getCounter()));
		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				MINE_FIELD_UPDATED_ACTION_COMMAND, field));
	}

	public boolean isQuestionMarkEnabled()
	{
		return questionMarkEnabled;
	}

	public void setQuestionMarkEnabled(boolean questionMarkEnabled)
	{
		this.questionMarkEnabled = questionMarkEnabled;

		eventManager.fireActionEvent(new ObjectActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				MINE_FIELD_UPDATED_ACTION_COMMAND, field));
	}

	public Dimension getFieldSizeOnStart()
	{
		return fieldSizeOnStart;
	}

	public void setFieldSizeOnStart(Dimension fieldSizeOnStart)
	{
		this.fieldSizeOnStart = fieldSizeOnStart;
	}

	public int getMineCountOnStart()
	{
		return this.field.getMineCountOnStart();
	}

	public void setMineCountOnStart(int mineCount)
	{
		this.field.setMineCountOnStart(mineCount);
		this.mineCountOnStart = mineCount;
	}

	public MineFieldModel getField()
	{
		return field;
	}

	public GameState getGameState()
	{
		return gameState;
	}

	public SmileModel getSmile()
	{
		return smile;
	}

	public FlagCounterModel getFlagCounter()
	{
		return flagCounter;
	}

	public TimeCounterModel getTimeCounter()
	{
		return timeCounter;
	}

}
