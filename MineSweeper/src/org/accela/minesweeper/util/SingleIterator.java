package org.accela.minesweeper.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleIterator<T> implements Iterator<T>, Serializable
{
	private static final long serialVersionUID = 1L;

	private T element = null;

	private boolean done = false;

	public SingleIterator(T element)
	{
		this.element = element;
	}

	public T getElement()
	{
		return element;
	}

	@Override
	public boolean hasNext()
	{
		return !done;
	}

	@Override
	public T next()
	{
		if (done)
		{
			throw new NoSuchElementException();
		}
		else
		{
			done = true;
			return element;
		}
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
