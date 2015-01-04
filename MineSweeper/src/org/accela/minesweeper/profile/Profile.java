package org.accela.minesweeper.profile;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.accela.minesweeper.ui.lang.chs.ChsUI;
import org.accela.minesweeper.ui.skin.classic.ClassicUI;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.Util;

public class Profile implements Serializable
{
	public static final String DATA_FILE = "profile.dat";

	private static final long serialVersionUID = 1L;

	private static Profile instance = null;

	// 单件模式
	public static Profile getInstance()
	{
		if (null == instance)
		{
			instance = readInstance();
		}

		return instance;
	}

	private static void saveInstance(Profile profile)
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream(
					Util.createExternalFile(DATA_FILE)));
			out.writeObject(profile);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	private static Profile readInstance()
	{
		Profile profile = null;
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream(
					Util.createExternalFile(DATA_FILE)));
			profile = (Profile) in.readObject();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (ClassCastException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		if (null == profile)
		{
			profile = new Profile();
			profile.save();
		}

		return profile;
	}

	// ==================================================================

	private Dimension fieldSize = null;
	private int mineCount = 0;

	public static final int HIGH_SCORE_TYPE_COUNT = 3;
	private HighScore[] highScore = new HighScore[HIGH_SCORE_TYPE_COUNT];

	public static enum Difficulty
	{
		BEGINNER, INTERMEDIATE, EXPERT, CUSTOM,
	}

	private Difficulty difficulty = null;

	private boolean questionMarkEnabled = false;

	private String skinName = null;

	private String langName = null;

	private boolean soundEnabled = false;

	protected Profile()
	{
		reset();
	}

	public void reset()
	{
		this.setFieldSize(null);
		this.resetHighScore();
		this.setMineCount(-1);
		this.setDifficulty(null);
		this.setQuestionMarkEnabled(true);
		this.setSkinName(null);
		this.setLangName(null);
		this.setSoundEnabled(false);
	}

	public void resetHighScore()
	{
		highScore[Difficulty.BEGINNER.ordinal()] = new HighScore(999, "unnamed");
		highScore[Difficulty.INTERMEDIATE.ordinal()] = new HighScore(999,
				"unnamed");
		highScore[Difficulty.EXPERT.ordinal()] = new HighScore(999, "unnamed");
		
		save();
	}

	private void save()
	{
		saveInstance(this);
	}

	public String getSkinName()
	{
		return skinName;
	}

	public void setSkinName(String skinName)
	{
		this.skinName = skinName;
		if (this.skinName == null)
		{
			this.skinName = new ClassicUI().getName();
		}
		save();
	}

	public int getMineCount()
	{
		return mineCount;
	}

	public void setMineCount(int mineCount)
	{
		this.mineCount = mineCount;
		this.mineCount = Math.max(this.mineCount, Common.getMinMineCount());
		save();
	}

	public Dimension getFieldSize()
	{
		return fieldSize;
	}

	public void setFieldSize(Dimension fieldSize)
	{
		this.fieldSize = fieldSize;
		if (this.fieldSize == null)
		{
			this.fieldSize = new Dimension(Common.MIN_MINE_FIELD_SIZE);
		}
		save();
	}

	public Difficulty getDifficulty()
	{
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty)
	{
		this.difficulty = difficulty;
		if (this.difficulty == null)
		{
			this.difficulty = Difficulty.BEGINNER;
		}
		save();
	}

	public boolean isQuestionMarkEnabled()
	{
		return questionMarkEnabled;
	}

	public void setQuestionMarkEnabled(boolean questionMarkEnabled)
	{
		this.questionMarkEnabled = questionMarkEnabled;
		save();
	}

	public boolean isSoundEnabled()
	{
		return soundEnabled;
	}

	public void setSoundEnabled(boolean soundEnabled)
	{
		this.soundEnabled = soundEnabled;
		save();
	}

	public boolean isHighScore(Difficulty dif, int score)
	{
		if (dif == Difficulty.CUSTOM)
		{
			return false;
		}
		return highScore[dif.ordinal()].getScore() >= score;
	}

	public void setHighScore(Difficulty dif, HighScore s)
	{
		if (null == s)
		{
			return;
		}
		if (dif == Difficulty.CUSTOM)
		{
			return;
		}

		highScore[dif.ordinal()] = s;
		save();
	}

	public HighScore getHighScore(Difficulty dif)
	{
		return highScore[dif.ordinal()];
	}

	public String getLangName()
	{
		return langName;
	}

	public void setLangName(String langName)
	{
		this.langName = langName;
		if (null == this.langName)
		{
			this.langName = new ChsUI().getName();
		}
		save();
	}

}
