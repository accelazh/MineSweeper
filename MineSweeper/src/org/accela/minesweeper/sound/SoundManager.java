package org.accela.minesweeper.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.accela.minesweeper.util.Util;

public class SoundManager
{
	// 单件模式
	private static SoundManager instance = null;

	public static SoundManager getInstance()
	{
		if (null == instance)
		{
			instance = new SoundManager();
		}

		return instance;
	}

	// =========================================================================

	public static final String TICK = "tick.wav";
	public static final String DEAD = "dead.wav";
	public static final String WIN = "win.wav";

	protected SoundManager()
	{
		// do nothing
	}

	public void playByName(String name)
	{
		try
		{
			Util.createWavSound(name).start();
		} catch (UnsupportedAudioFileException ex)
		{
			ex.printStackTrace();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} catch (LineUnavailableException ex)
		{
			ex.printStackTrace();
		}
	}
}
