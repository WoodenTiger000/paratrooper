package com.boluomiyu.miyueng.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.view.MotionEvent;

import com.boluomiyu.miyueng.exception.EventException;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.view.GameView;
/**
 * 类 EventCenter
 * 描述：用来解决线程安全问题，多线程情况下，
 * 如touch事件，具体方案：首先创建1000个事件载体，每个载体负责装载一个MotionEvent
 * 当触摸线程发送事件时，先发送给EventHanlderCenter存起来，主线程在处理的时候，
 * 再从中拿出事件进行处理，处理完成后，将EventHanlder复位，等待装载下一个Event
 * 这样就不会有List操作，也就不会出线程安全问题
 * 
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class EventHandlerCenter {

	private static EventHandlerCenter eventCenter;
	
	private List<SpriteCreateEventHandler> createEventList;
	
	private List<TouchEventHandler> touchEventList;
	
	private List<GameViewShowEventHandler> gameViewShowEventList;
	
	private List<GameViewHideEventHandler> gameViewHideEventList;
	
	private List<GameViewClearEventHandler> gameViewClearEventList;
	/**
	 * 事件中心，初始化事件集合
	 */
	private EventHandlerCenter() {
		//初始化事件集合
		createEventList = new ArrayList<SpriteCreateEventHandler>();
		for (int i=0; i<100; i++) {
			SpriteCreateEventHandler createEvent = new SpriteCreateEventHandler(null);
			createEventList.add(createEvent);
		}
		
		touchEventList = new ArrayList<TouchEventHandler>();
		for (int i=0; i<100; i++) {
			TouchEventHandler touchEvent = new TouchEventHandler(null);
			touchEventList.add(touchEvent);
		}
		
		gameViewShowEventList = new ArrayList<GameViewShowEventHandler>();
		gameViewHideEventList = new ArrayList<GameViewHideEventHandler>();
		gameViewClearEventList = new ArrayList<GameViewClearEventHandler>();
		
		for (int i=0; i<10; i++) {
			GameViewShowEventHandler sHandler = new GameViewShowEventHandler(null);
			gameViewShowEventList.add(sHandler);
			
			GameViewHideEventHandler hHandler = new GameViewHideEventHandler(null);
			gameViewHideEventList.add(hHandler);
			
			GameViewClearEventHandler cHandler = new GameViewClearEventHandler();
			gameViewClearEventList.add(cHandler);
		}
		
		
	}
	
	public static EventHandlerCenter getInstance() {
		if (eventCenter == null) {
			eventCenter = new EventHandlerCenter();
			return eventCenter;
		} else {
			return eventCenter;
		}
	}
	
	/** 推入一个创建事件 */
	public void pushCreate(Sprite sprite) {
		try {
			// 查找event
			SpriteCreateEventHandler event = this.getEmptSpriteCreateEvent();
			event.setSprite(sprite);
		} catch (EventException e) {
			e.printStackTrace();
		}
	}
	
	/** 推入触屏事件 */
	public void pushTouchEvent(MotionEvent e) {
		try {
			TouchEventHandler touchEventHandler = this.getEmptTouchEventHandler();
			touchEventHandler.setEvent(e);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
	}
	
	/** 推入显示窗口 
	 * @throws EventException 
	 * */
	public void pushShowGameViewEvent(Class<? extends GameView> menuClass) throws EventException {
		GameViewShowEventHandler handlerEvent = null;
		for (GameViewShowEventHandler e : gameViewShowEventList) {
			if (e.getMenuClass() == null) {
				handlerEvent = e;
				break;
			}
		}
		
		if (handlerEvent == null) {
			throw new EventException("事件已用完");
		} else {
			handlerEvent.setMenuClass(menuClass);
		}
	}
	
	/** 推入隐藏窗口 
	 * @throws EventException */
	public void pushHideGameViewEvent(Class<? extends GameView> menuClass) throws EventException {
		GameViewHideEventHandler handlerEvent = null;
		for (GameViewHideEventHandler e : gameViewHideEventList) {
			if (e.getMenuClass() == null) {
				handlerEvent = e;
				break;
			}
		}
		
		if (handlerEvent == null) {
			throw new EventException("事件已用完");
		} else {
			handlerEvent.setMenuClass(menuClass);
		}
	}
	
	/** 推入清除所有显示窗口事件 
	 * @throws EventException */
	public void pushClearGameViewEvent() throws EventException {
		GameViewClearEventHandler handlerEvent = null;
		for (GameViewClearEventHandler e : gameViewClearEventList) {
			if (e.getObject() == null) {
				handlerEvent = e;
				break;
			}
		}
		
		if (handlerEvent == null) {
			throw new EventException("事件已用完");
		} else {
			handlerEvent.setObject(new Object());
		}
	}

	/** 从事件池中获取一个空载事件 */
	private SpriteCreateEventHandler getEmptSpriteCreateEvent() throws EventException {
		SpriteCreateEventHandler createEvent = null;
		for (SpriteCreateEventHandler e : createEventList) {
			if (e.getSprite() == null) {
				createEvent = (SpriteCreateEventHandler) e;
			}
		}
		
		if (createEvent == null) {
			throw new EventException("事件已用完");
		} else {
			return createEvent;
		}
	}
	
	/** 获取空载触摸事件 
	 * @throws EventException */
	private TouchEventHandler getEmptTouchEventHandler() throws EventException {
		TouchEventHandler touchEvent = null;
		for (TouchEventHandler e : touchEventList) {
			if (e.getEvent() == null) {
				touchEvent = (TouchEventHandler) e;
			}
		}
		
		if (touchEvent == null) {
			throw new EventException("Touch事件已用完");
		} else {
			return touchEvent;
		}
	}
	
	/** 执行 */
	public void execute() {
		
		// 精灵创建事件
		Iterator<SpriteCreateEventHandler> it = createEventList.iterator();
		SpriteCreateEventHandler event = null;
		while (it.hasNext()) {
			event = it.next();
			if (event.getSprite() != null) {
				event.execute();
				event.setSprite(null);//执行完成，卸载sprite
			}
		}
		
		// 触屏事件
		TouchEventHandler touchEvent = null;
		Iterator<TouchEventHandler> it2 = touchEventList.iterator();
		while (it2.hasNext()) {
			touchEvent = it2.next();
			touchEvent.execute();
			touchEvent.setEvent(null);//执行完成，卸载事件
		}
		
		// 首先处理隐藏事件，否则有可能显示失效
		for (int i=0; i<10; i++) {

			GameViewHideEventHandler he = gameViewHideEventList.get(i);
			he.execute();
			he.setMenuClass(null);
			
			GameViewClearEventHandler ce = gameViewClearEventList.get(i);
			ce.execute();
			ce.setObject(null);
		}
		
		// 再处理显示事件
		for (int i=0; i<10; i++) {
			GameViewShowEventHandler se = gameViewShowEventList.get(i);
			if (se != null) {
				se.execute();
				se.setMenuClass(null);
			}
			
			
		}
		
	}




	
	
}
