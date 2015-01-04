package org.accela.minesweeper.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.accela.minesweeper.res.sound.SoundLocator;

public class WavSound extends Thread
{
	private AudioInputStream sound = null;
	private AudioFormat format = null;
	private DataLine.Info info = null;
	private SourceDataLine auline = null;

	private static final int BUFFER_SIZE = 1024 * 16;

	public WavSound(URL url) throws UnsupportedAudioFileException, IOException,
			LineUnavailableException
	{
		sound = AudioSystem.getAudioInputStream(url);
		format = sound.getFormat();
		info = new DataLine.Info(SourceDataLine.class, format);
		auline = (SourceDataLine) AudioSystem.getLine(info);
		auline.open();
	}

	@Override
	public void run()
	{
		auline.start();

		byte[] audioData = new byte[BUFFER_SIZE];
		int inCount = 0;
		while (inCount != -1)
		{
			try
			{
				inCount = sound.read(audioData, 0, BUFFER_SIZE);
			} catch (IOException ex)
			{
				ex.printStackTrace();
				continue;
			}
			if (inCount > 0)
			{
				auline.write(audioData, 0, inCount);
			}
		}
	}

	public static void main(String[] args)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException, InterruptedException
	{
		while (true)
		{
			WavSound s = new WavSound(
					SoundLocator.class.getResource("tick.wav"));
			s.start();
			Thread.sleep(1000);
		}
	}
}
