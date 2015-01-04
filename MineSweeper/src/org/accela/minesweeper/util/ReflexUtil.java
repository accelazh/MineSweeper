package org.accela.minesweeper.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflexUtil
{
	public static String getNameOf(Object obj)
	{
		Method m;
		try
		{
			m = obj.getClass().getMethod("getName", new Class<?>[0]);
			m.setAccessible(true);
			return (String) m.invoke(obj, new Object[0]);
		}
		catch (NoSuchMethodException ex)
		{
			return null;
		}
		catch (SecurityException ex)
		{
			throw ex;
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
			assert (false);
			return null;
		}
		catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
			assert (false);
			return null;
		}
		catch (InvocationTargetException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public static String getSetterName(String prop)
	{
		return insertHeadByWord("set", prop);
	}

	public static String getGetterName(String prop)
	{
		return insertHeadByWord("get", prop);
	}

	private static String insertHeadByWord(String head, String word)
	{
		char[] arr = word.toCharArray();
		if (Character.isLowerCase(arr[0]))
		{
			arr[0] = Character.toUpperCase(arr[0]);
		}

		StringBuffer buf = new StringBuffer(head);
		buf.append(arr);

		return buf.toString();
	}

	public static void setProperty(Object obj, String prop, Object value)
			throws NoSuchMethodException, InvocationTargetException
	{
		boolean succ = false;
		InvocationTargetException lastException = null;

		Method[] methods = obj.getClass().getMethods();
		String setterName = getSetterName(prop);
		for (Method m : methods)
		{
			if (!m.getName().equals(setterName))
			{
				continue;
			}
			Class<?>[] paraTypes = m.getParameterTypes();
			if (paraTypes.length != 1)
			{
				continue;
			}
			if (paraTypes[0].isPrimitive() && null == value)
			{
				continue;
			}
			if (value != null && !paraTypes[0].isPrimitive()
					&& !paraTypes[0].isInstance(value))
			{
				continue;
			}

			m.setAccessible(true);
			try
			{
				m.invoke(obj, value);
				succ = true;
				break;
			}
			catch (IllegalArgumentException ex)
			{
				//do nothing
			}
			catch (IllegalAccessException ex)
			{
				ex.printStackTrace();
				assert (false);
			}
			catch (InvocationTargetException ex)
			{
				lastException = ex;
			}

		}

		if (!succ)
		{
			if (lastException != null)
			{
				throw lastException;
			}
			else
			{
				throw new NoSuchMethodException();
			}
		}

	}
}
