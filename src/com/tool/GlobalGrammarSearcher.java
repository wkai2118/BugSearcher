package com.tool;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.gui.FloatingIcon;

public class GlobalGrammarSearcher implements NativeMouseInputListener
{
	public static GlobalGrammarSearcher Searcher;
	static boolean DblclickFlag = false;
	static boolean TimeFlag = true; // 计时器开关，为了防止第二次点击重新触发计时器
	public static Robot robot;

	public static int PressX;
	public static int PressY;

	public static int ReleaseX;
	public static int ReleaseY;

	public static Timer mTimer = new Timer();

	public GlobalGrammarSearcher()
	{

	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e)
	{
		// 鼠标双击
		if (TimeFlag)
		{
			mTimer.schedule(new ClickTimeDelay(), 239);
			TimeFlag = false;
		}
		if (DblclickFlag)
		{
			FloatingIcon.Icon.setBounds(e.getX() + 10, e.getY() - 50, 38, 38);
			FloatingIcon.Icon.setVisible(true);
		}
		DblclickFlag = true;
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e)
	{
		PressX = e.getX();
		PressY = e.getY();
		if (notInRectangle(PressX, PressY))
		{
			FloatingIcon.Icon.setVisible(false);
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e)
	{
		ReleaseX = e.getX();
		ReleaseY = e.getY();
		if ((ReleaseX != PressX || ReleaseY != e.getY())) // 拖拽检测
		{
			FloatingIcon.Icon.setBounds(e.getX() + 10, e.getY() - 50, 38, 38);
			FloatingIcon.Icon.setVisible(true);
		}

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e)
	{
		// TODO 自动生成的方法存根

	}

	public static void GramSearchInit()
	{
		robot = null;
		try
		{
			robot = new Robot();
		} catch (AWTException e1)
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try
		{
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		FloatingIcon.Icon = new FloatingIcon();
		Searcher = new GlobalGrammarSearcher();
//		GrammarPanel2.GrammarSearch = new GrammarPanel2();

		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
	}

	public static void GramSearchOpen()
	{
		GlobalScreen.addNativeMouseListener(Searcher);
		GlobalScreen.addNativeMouseMotionListener(Searcher);
	}

	public static void GramSearchClose()
	{
		GlobalScreen.removeNativeMouseListener(Searcher);
		GlobalScreen.removeNativeMouseMotionListener(Searcher);
	}

	public boolean notInRectangle(int x, int y)
	{
		if ((x >= ReleaseX + 10 && y <= ReleaseX + 48) && (y >= ReleaseY - 50 && y <= ReleaseY - 12))
		{
			return false;
		}
		return true;
	}

}

class ClickTimeDelay extends TimerTask
{
	@Override
	public void run()
	{
		GlobalGrammarSearcher.DblclickFlag = false;
		GlobalGrammarSearcher.TimeFlag = true;
	}
}
