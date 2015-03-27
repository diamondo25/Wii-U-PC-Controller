package com.johnfreed.wiiupccontroller;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class PCController {
	private Robot robot;
	private int autoDelay = 40;
	private boolean waitForIdle = true;
	
	public PCController() throws AWTException {
		robot = new Robot();
		robot.setAutoDelay(autoDelay);
		robot.setAutoWaitForIdle(waitForIdle);
	}
	
	public Point GetMousePosition() {
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	public void MoveMouseToPosition(int x, int y) {
		robot.mouseMove(x, y);
	}
	
	public void LeftClick() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
	}
	
	public void RightClick() {
		robot.mousePress(InputEvent.BUTTON2_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON2_MASK);
		robot.delay(200);
	}
	
	public void TypeKey(int i) {
		robot.delay(50);
		robot.keyPress(i);
		robot.keyRelease(i);
	}
	
	public void TypeString(String text) {
		byte[] bytes = text.getBytes();
		
		robot.delay(500);
		for (byte b : bytes) {
			int code = b;
			
			if (code > 96 && code < 123) code = code - 32;
			robot.delay(50);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}
	
	public void SetAutoDelay(int value) {
		this.autoDelay = value;
		robot.setAutoDelay(this.autoDelay);
	}
	
	public int GetAutoDelay() {
		return this.autoDelay;
	}
	
	public void SetAutoWaitForIdle(boolean value) {
		this.waitForIdle = value;
		robot.setAutoWaitForIdle(this.waitForIdle);
	}
	
	public boolean GetAutoWaitForIdle() {
		return this.waitForIdle;
	}
}
