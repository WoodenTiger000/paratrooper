package com.boluomiyu.ch.menu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.boluomiyu.ch.unit.MainGun;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.view.CDGameButton;
import com.boluomiyu.miyueng.view.IconButton;
import com.boluomiyu.miyueng.view.ImageView;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.PercentImageView;
import com.boluomiyu.miyueng.view.TextView;
import com.boluomiyu.miyueng.view.ToggleButton;
/**
 * 类 BattleMenu
 * 描述：战场操作界面
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-12
 * @version 1.0
 */
public class BattleMenu extends Panel {

	private GameContext context;
	
	private MainGun mainGun;
	
	TextView curtainTextView;
	
	private IconButton iconBtn = new IconButton();
	
	private TextView levelLabel = new TextView("等级：");
	private TextView hpLabel = new TextView("生命：");
	private TextView mpLabel = new TextView("能量：");
	private TextView expLabel = new TextView("经验：");

	private PercentImageView hpPercentView = new PercentImageView();
	private PercentImageView mpPercentView = new PercentImageView();
	private PercentImageView expPercentView = new PercentImageView();
	private TextView levelText = new TextView("1");
	
	private ToggleButton wp1Btn = new ToggleButton();
	private final ToggleButton wp2Btn = new ToggleButton();
	private ToggleButton wp3Btn = new ToggleButton();
	
	private ImageView processMenu = new ImageView();
	private CDGameButton sk1Btn = new CDGameButton();
	private CDGameButton sk2Btn = new CDGameButton();
	private CDGameButton sk3Btn = new CDGameButton();
	
	private ImageView bottomMenu = new ImageView();
	
	
	public BattleMenu() {
		
		this.context = GameContext.context;
		
		// 报幕
		curtainTextView = new TextView("");
		curtainTextView.setTextSize(30);
		this.addView(curtainTextView, 370, 180);
		
		// 触摸事件可以被穿透
		this.isPierced = true;
		// 头像
		iconBtn.height = 67;
		iconBtn.width = 67;
		iconBtn.setNormalBG("images/ui/head_bg.png");
		iconBtn.setIcon("images/ui/headIcon/head_icon_1.png");
		this.addView(iconBtn, 2, 2);
		
		// hp
		hpPercentView.width = 113;
		hpPercentView.height = 10;
		hpPercentView.setPercent(0);
		hpPercentView.setPercentImage("images/ui/hp_value.png");
		hpPercentView.setNormalBG("images/ui/value_bg.png");
		this.addView(hpPercentView, 108, 23);
		
		// mp
		mpPercentView.width = 113;
		mpPercentView.height = 10;
		mpPercentView.setPercent(0);
		mpPercentView.setPercentImage("images/ui/mp_value.png");
		mpPercentView.setNormalBG("images/ui/value_bg.png");
		this.addView(mpPercentView, 108, 38);
		
		// exp
		expPercentView.width = 113;
		expPercentView.height = 10;
		expPercentView.setPercent(0);
		expPercentView.setPercentImage("images/ui/exp_value.png");
		expPercentView.setNormalBG("images/ui/value_bg.png");
		this.addView(expPercentView, 108, 53);
		
		
		// 技能按钮
		wp1Btn.width = 60;
		wp1Btn.height = 50;
		wp1Btn.text = "武器";
		wp1Btn.enable = true;
		wp1Btn.setChoosedBG("images/ui/mainmenu_press_btn.png");
		wp1Btn.setUnchoosedBG("images/ui/head_bg.png");
		this.addView(wp1Btn, 8, 425);
		

		wp2Btn.width = 60;
		wp2Btn.height = 50;
		wp2Btn.text = "武器";
		wp2Btn.enable = true;
		wp2Btn.setChoosedBG("images/ui/mainmenu_press_btn.png");
		wp2Btn.setUnchoosedBG("images/ui/head_bg.png");
		this.addView(wp2Btn, wp1Btn.x + wp1Btn.width + 2, wp1Btn.y);
		

		wp3Btn.width = 60;
		wp3Btn.height = 50;
		wp3Btn.text = "武器";
		wp3Btn.enable = true;
		wp3Btn.setChoosedBG("images/ui/mainmenu_press_btn.png");
		wp3Btn.setUnchoosedBG("images/ui/head_bg.png");
		this.addView(wp3Btn, wp2Btn.x + wp2Btn.width + 2, wp2Btn.y);
		
		// 战斗进度
		processMenu.width = 400;
		processMenu.height = 50;
		processMenu.setNormalBG("images/ui/battle_process_bg.png");
		this.addView(processMenu, wp3Btn.x + wp3Btn.width + 2, wp3Btn.y);
		
		// 技能1
		sk1Btn.width = 80;
		sk1Btn.height = 50;
		sk1Btn.text = "技能1";
		sk1Btn.setCd(1000);
		sk1Btn.setNormalBG("images/ui/head_bg.png");
		this.addView(sk1Btn, processMenu.x + processMenu.width + 2, processMenu.y);

		sk2Btn.width = 80;
		sk2Btn.height = 50;
		sk2Btn.text = "技能2";
		sk2Btn.setCd(2000);
		sk2Btn.setNormalBG("images/ui/head_bg.png");
		this.addView(sk2Btn, sk1Btn.x + sk1Btn.width + 1, sk1Btn.y);

		sk3Btn.width = 80;
		sk3Btn.height = 50;
		sk3Btn.text = "技能3";
		sk3Btn.setCd(3000);
		sk3Btn.setNormalBG("images/ui/head_bg.png");
		this.addView(sk3Btn, sk2Btn.x + sk2Btn.width + 1, sk2Btn.y);
		
		// 底部操作背景
		bottomMenu.width = GameContext.context.width - 2;
		bottomMenu.height = 60;
		bottomMenu.setNormalBG("images/ui/battle_bottom_bg.png");
		this.addView(bottomMenu, 0, 420);
		

		levelLabel.setTextSize(13);
		this.addView(levelLabel, 70, 17);


		levelText.setTextSize(13);
		this.addView(levelText, 110, 17);
		

		hpLabel.setTextSize(13);
		this.addView(hpLabel, 70, levelLabel.y + 15);

		mpLabel.setTextSize(13);
		this.addView(mpLabel, 70, hpLabel.y + + 15);
		
		expLabel.setTextSize(13);
		this.addView(expLabel, 70, mpLabel.y + 15);
		
		// 武器1
		wp1Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				wp2Btn.setChoosed(false);
				wp3Btn.setChoosed(false);
				wp1Btn.setChoosed(true);
			}
		});
		// 武器2
		wp2Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				wp1Btn.setChoosed(false);
				wp3Btn.setChoosed(false);
				wp2Btn.setChoosed(true);
			}
		});
		// 武器3
		wp3Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				wp1Btn.setChoosed(false);
				wp2Btn.setChoosed(false);
				wp3Btn.setChoosed(true);
				
				
			}
		});
		
		
		// 技能1
		sk1Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				if (context.getRaceStatus() == true) {
					if (!sk1Btn.trigger()) {
						//GameClient.context.makeText("技能正在冷却");
					} else {
						//使用技能
						if (mainGun != null) {
							mainGun.thump1();
						}
					}
				}
			}
		});
		// 技能2
		sk2Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				if (context.getRaceStatus() == true) {
					if (sk2Btn.trigger()) {
						//使用技能
						if (mainGun != null) {
							mainGun.thump2();
						}
					}
				}
			}
		});
		// 技能3
		sk3Btn.addEventListener(new EventAdapter(){
			@Override
			public void pressed(MotionEvent e) {
				if (context.getRaceStatus() == true) {
					if (mainGun != null) {
						mainGun.thump3();
					}
				}
			}
		});
		
	}
	@Override
	public void show() {
		super.show();
		curtainTextView.show();
		context.putData("curtain", "");
	}
	@Override
	public void render(Canvas canvas, Paint paint) {
		
		if (curtainTextView.visible == true) {
			String curtain = context.getData("curtain");
			if ("curtain_close".equals(curtain)) {
				curtainTextView.hide();
			} else {
				curtainTextView.setText(curtain);
			}
		}
		
		
		// 绑定显示
		if (mainGun == null) {
			mainGun = context.getSpriteFromScence("maingun");
		}
		if (mainGun != null) {
			this.hpPercentView.setPercent((int)(mainGun.getHP()/mainGun.getMaxHP() * 100));
			this.mpPercentView.setPercent((int)(mainGun.getMP()/mainGun.getMaxMP() * 100));
			this.expPercentView.setPercent((int)(mainGun.getEXP()/mainGun.getMaxEXP() * 100));
		}
		
	}
	
	@Override
	public void reset() {
		this.mainGun = null;
	}
	
}
