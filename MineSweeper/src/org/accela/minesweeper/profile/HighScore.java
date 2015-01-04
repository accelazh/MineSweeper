package org.accela.minesweeper.profile;

import java.io.Serializable;

public class HighScore implements Serializable, Comparable<HighScore>
{
	private static final long serialVersionUID = 1L;

	public int score = 0;

	public String name = "";

	public HighScore()
	{
		this(999, "");
	}

	public HighScore(int score, String name)
	{
		this.setScore(score);
		this.setName(name);
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		if (null == name)
		{
			name = "";
		}
		this.name = name;
	}

	@Override
	public int compareTo(HighScore o)
	{
		return score-o.score;
	}
}
