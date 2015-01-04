package org.accela.minesweeper.util;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Matrix<T> implements Iterable<T>
{
	private Object[][] matrix = null;
	
	private T[] template=null;

	public Matrix(T[] template)
	{
		this.template=template;
		this.matrix=new Object[1][1];
	}

	public int getWidth()
	{
		return this.matrix[0].length;
	}

	public int getHeight()
	{
		return this.matrix.length;
	}

	public Dimension getSize()
	{
		return new Dimension(getWidth(), getHeight());
	}

	public void setSize(int width, int height)
	{
		if (width < 1 || height < 1)
		{
			throw new IllegalArgumentException(
					"width and height should not be less than 1");
		}

		this.matrix = new Object[height][width];
	}

	@SuppressWarnings("unchecked")
	public T get(int x, int y)
	{
		return (T) matrix[y][x];
	}
	
	public T[] getAll()
	{
		if(this.matrix==null)
		{
			return null;
		}
		
		List<T> list=new LinkedList<T>();
		for(int i=0;i<matrix.length;i++)
		{
			for(int j=0;j<matrix[i].length;j++)
			{
				list.add(get(j, i));
			}
		}
		
		return list.toArray(template);
	}
	
	public T set(int x, int y, T obj)
	{
		T old = get(x, y);
		matrix[y][x] = obj;
		return old;
	}

	public Point getPos(T obj)
	{
		for (int y = 0; y < matrix.length; y++)
		{
			for (int x = 0; x < matrix[y].length; x++)
			{
				if (obj.equals(matrix[y][x]))
				{
					return new Point(x, y);
				}
			}
		}

		return null;
	}

	private Point[] getPosAroundRaw(Point pos)
	{
		Point[] posAround = new Point[] {
				new Point(pos.x - 1, pos.y - 1),
				new Point(pos.x - 1, pos.y),
				new Point(pos.x - 1, pos.y + 1),
				new Point(pos.x, pos.y - 1),
				new Point(pos.x, pos.y + 1),
				new Point(pos.x + 1, pos.y - 1),
				new Point(pos.x + 1, pos.y),
				new Point(pos.x + 1, pos.y + 1) };

		return posAround;
	}

	public boolean checkInBound(Point pos)
	{
		Dimension size = getSize();
		assert (size.width == matrix[0].length);
		assert (size.height == matrix.length);

		return (pos.x >= 0 && pos.x < size.width)
				&& (pos.y >= 0 && pos.y < size.height);
	}

	public Point[] getPosAround(Point pos)
	{
		Point[] posAroundRaw = getPosAroundRaw(pos);
		List<Point> posInBound = new LinkedList<Point>();
		for (Point p : posAroundRaw)
		{
			if (checkInBound(p))
			{
				posInBound.add(p);
			}
		}

		return posInBound.toArray(new Point[0]);
	}

	public Point[] getPosAround(T obj)
	{
		return this.getPosAround(this.getPos(obj));
	}

	public T[] getObjAround(Point pos)
	{
		Point[] posAround=getPosAround(pos);
		List<T> list=new LinkedList<T>();
		for(Point p:posAround)
		{
			list.add(this.get(p.x, p.y));
		}
		
		return list.toArray(this.template);
	}

	public T[] getObjAround(T obj)
	{
		return getObjAround(getPos(obj));
	}

	@Override
	public Iterator<T> iterator()
	{
		return Arrays.asList(this.getAll()).iterator();
	}
}
