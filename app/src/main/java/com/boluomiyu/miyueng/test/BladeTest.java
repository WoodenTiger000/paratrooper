package com.boluomiyu.miyueng.test;

import android.app.Activity;
/**
 * 锟斤拷锟斤拷锟斤拷
 * @author 锟斤拷锟藉虎
 *
 */
public class BladeTest extends Activity{

	/** 锟斤拷锟絏位锟斤拷 */
	public static int mouseX = 0;
	/** 锟斤拷锟結位锟斤拷 */
	public static int mouseY = 0;
	/*
	public BladeTest() {
		
		BladeTest mainFrame = new BladeTest();
				
			final MainCanvas panel = new MainCanvas(this);
			final TextView mouseLocation = new Label();
			panel.add(mouseLocation);
			panel.setBackground(new Color(0x000000));
			
			Dimension labelSize = new Dimension(500, 20);
			mouseLocation.setMinimumSize(labelSize);
			mouseLocation.setPreferredSize(labelSize);
			mainFrame.add(panel);
			
			final Blade blade = Blade.getInstance();
			
			mainFrame.setVisible(true);
			mainFrame.setSize(500, 400);
			mainFrame.setBounds(400, 200, 500, 400);
			
			Timer timer = new Timer();
			
			panel.getGraphics().setColor(new Color(0x00000));
			final Graphics2D pg = (Graphics2D) panel.getGraphics(); 
			
			final Image dbImage = mainFrame.createImage(500, 400);
			final Graphics2D g = (Graphics2D) dbImage.getGraphics();
			
			
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					//huabia
					
					g.clearRect(0, 0, 500, 400);
					g.setColor(Color.black);
					g.fillRect(0, 0, 500, 400);
					//mouseLocation.setText("锟斤拷锟斤拷锟疥：x=" + mouseX + "  y=" + mouseY);
					mouseLocation.setText("锟叫�" + blade.getPointList());
					blade.render(mouseX, mouseY, g);
					
					pg.drawImage(dbImage, 0, 0, 500, 400, null);
				}
				
			}, new Date(), 30);
			
			panel.addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					blade.setAllowPush(true);
					//mouseLocation.setText("锟斤拷锟斤拷锟疥：x=" + e.getX() + "  y=" + e.getY());
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					blade.setAllowPush(false);
				}
			});
			panel.addMouseMotionListener(new MouseMotionAdapter(){
	
				@Override
				public void mouseMoved(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
				@Override
				public void mouseDragged(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
				
			});
			
			mainFrame.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					System.exit(0);
				}
				
			});
		}
		public void render(Graphics2D g) {
			
		}
	}
	
	
	public static void main(String[] args) {
	
	}
	*/
}
