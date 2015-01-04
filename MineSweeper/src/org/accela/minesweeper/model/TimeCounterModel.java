package org.accela.minesweeper.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

import org.accela.minesweeper.util.EventManager;
import org.accela.minesweeper.util.ObjectActionEvent;

public class TimeCounterModel
{
	protected static final String TIME_COUNTER_INCREMENT_ACTION_COMMAND = "timerCounterIncrement";

	private Timer timer = null;

	private AtomicInteger counter = new AtomicInteger();

	private EventManager eventManager = new EventManager();
	
	private boolean pause=false;

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
		if(timer!=null)
		{
			timer.cancel();
		}
		this.counter.set(0);
	}

	public int getCounter()
	{
		return counter.get();
	}

	public void setCounter(int counter)
	{
		this.counter.set(counter);
	}

	public void start()
	{
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if(pause)
				{
					return;
				}
				
				counter.incrementAndGet();
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						eventManager.fireActionEvent(new ObjectActionEvent(
								TimeCounterModel.this,
								ActionEvent.ACTION_PERFORMED,
								TIME_COUNTER_INCREMENT_ACTION_COMMAND, counter.get()));
					}
				});
			}

		}, 1000, 1000);
	}

	public void stop()
	{
		if (timer == null)
		{
			return;
		}

		timer.cancel();
	}

	public void resetAndStart()
	{
		this.reset();
		this.start();
	}
	
	public void pause(boolean pause)
	{
		this.pause=pause;
	}

}
