package org.accela.minesweeper.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.UIManager;

import org.accela.minesweeper.res.images.ImageLocator;
import org.accela.minesweeper.res.sound.SoundLocator;
import org.accela.minesweeper.sound.WavSound;

public class Util
{
	public static Rectangle rectShrinkByInsets(Rectangle rect, Insets insets)
	{
		Rectangle newRect = new Rectangle(rect);
		newRect.x += insets.left;
		newRect.y += insets.top;
		newRect.width -= insets.left + insets.right;
		newRect.height -= insets.top + insets.bottom;

		return newRect;
	}

	// digits低位优先
	public static int digitsToNumber(int[] digits)
	{
		int sum = 0;
		final int RADIX = 10;
		int base = 1;

		for (int i = 0; i < digits.length; i++, base *= RADIX)
		{
			sum += digits[i] * base;
		}

		return sum;
	}

	// 返回的digits数组低位优先
	public static int[] numberToDigits(int num)
	{
		if (num < 0)
		{
			throw new IllegalArgumentException("num should not be negative");
		}

		final int RADIX = 10;

		List<Integer> list = new ArrayList<Integer>();
		while (num > 0)
		{
			list.add(num % RADIX);
			num /= RADIX;
		}

		int[] ret = new int[list.size()];
		ListIterator<Integer> itr = list.listIterator();
		while (itr.hasNext())
		{
			int idx = itr.nextIndex();
			ret[idx] = itr.next();
		}

		return ret;
	}

	// 创建一个能够遍历comp和它的所有子组件的Iterator
	// 仅支持swing，不保证支持awt
	public static Iterator<Component> createCompItr(Component comp)
	{
		return new CompItr(comp);
	}

	private static class CompItr implements Iterator<Component>
	{
		private Stack<Component> tasks = new Stack<Component>();

		public CompItr(Component start)
		{
			tasks.push(start);
		}

		@Override
		public boolean hasNext()
		{
			return tasks.size() > 0;
		}

		@Override
		public Component next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}

			Component next = tasks.pop();
			findNewTask(next);

			return next;
		}

		private void findNewTask(Component comp)
		{
			if (!(comp instanceof Container))
			{
				return;
			}

			for (Component c : ((Container) comp).getComponents())
			{
				tasks.push(c);
			}
			if (comp instanceof JMenu)
			{
				tasks.push(((JMenu) comp).getPopupMenu());
			}
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}

	public static void setSwingFont(Font font)
	{
		UIManager.put("Button.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("ColorChooser.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("ComboBoxItem.font", font);
		UIManager.put("InternalFrame.titleFont", font);
		UIManager.put("Label.font", font);
		UIManager.put("List.font", font);
		UIManager.put("MenuBar.font", font);
		UIManager.put("Menu.font", font);
		UIManager.put("MenuItem.font", font);
		UIManager.put("RadioButtonMenuItem.font", font);
		UIManager.put("CheckBoxMenuItem.font", font);
		UIManager.put("PopupMenu.font", font);
		UIManager.put("OptionPane.font", font);
		UIManager.put("OptionPane.messageFont", font);
		UIManager.put("OptionPane.buttonFont", font);
		UIManager.put("Panel.font", font);
		UIManager.put("ProgressBar.font", font);
		UIManager.put("ScrollPane.font", font);
		UIManager.put("Viewport", font);
		UIManager.put("TabbedPane.font", font);
		UIManager.put("TableHeader.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("PasswordFiled.font", font);
		UIManager.put("TextArea.font", font);
		UIManager.put("TextPane.font", font);
		UIManager.put("EditorPane.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("ToolBar.font", font);
		UIManager.put("ToolTip.font", font);
		UIManager.put("Tree.font", font);
		UIManager.put("Table.font", font);
	}

	public static Dimension calLargestDimension(Iterable<Dimension> itr)
	{
		Dimension ret = new Dimension(0, 0);
		for (Dimension d : itr)
		{
			ret.width = Math.max(ret.width, d.width);
			ret.height = Math.max(ret.height, d.height);
		}
		return ret;
	}

	public static Dimension calLargestPreferredSize(Iterable<Component> itr)
	{
		List<Dimension> dims = new LinkedList<Dimension>();
		for (Component c : itr)
		{
			dims.add(c.getPreferredSize());
		}

		return calLargestDimension(dims);
	}

	public static Dimension calLargestPreferredSize(Component... args)
	{
		Component[] comps = args;
		return calLargestPreferredSize(Arrays.asList(comps));
	}

	public static Image createImage(String path)
	{
		URL url = ImageLocator.class.getResource(path);
		Image img = java.awt.Toolkit.getDefaultToolkit().createImage(url);

		return img;
	}

	public static ImageIcon createImageIcon(String path)
	{
		return new ImageIcon(createImage(path));
	}

	public static File createExternalFile(String path)
	{
		File ret= new File(path);
		File parentFile=ret.getParentFile();
		if(parentFile!=null)
		{
			parentFile.mkdirs();
		}
		return ret;
	}

	public static Color mergeColor(Color a, Color b)
	{
		return new Color((a.getRed() + b.getRed()) / 2,
				(a.getGreen() + b.getGreen()) / 2,
				(a.getBlue() + b.getBlue()) / 2, (a.getAlpha() + b.getAlpha())
						/ 2 + Math.abs(b.getAlpha() - a.getAlpha()));
	}

	public static Object[] arrayDim2ToDim1(Object[][] arr)
	{
		List<Object> list = new LinkedList<Object>();
		for (int i = 0; i < arr.length; i++)
		{
			for (int j = 0; j < arr[i].length; j++)
			{
				list.add(arr[i][j]);
			}
		}

		return list.toArray();
	}

	public static boolean isSmallerOrEqualTo(Dimension small, Dimension big)
	{
		return small.width <= big.width && small.height <= big.height;
	}

	public static WavSound createWavSound(String path)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException
	{
		return new WavSound(SoundLocator.class.getResource(path));
	}

	public static boolean isWindows()
	{
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows") && !os.contains("linux")
				&& !os.contains("unix") && !os.contains("bsd")
				&& !os.contains("mac"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
