package kate.tti.game.views;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import kate.tti.game.buttons.FitButtons;

import kate.tti.game.game.Resouces;

public class CoinBuy extends CCLayer {
	public int coinCount = 0;
	
	private static long lastTime = 0;


	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new CoinBuy());
		return scene;
	}

	/*****************************************************************************************************************************************************************************************************************/
	public CoinBuy() {
		super();
		schedule("getInfo", 1.0f / 10.0f);
	}

	public void getInfo(float dt) {
		unschedule("getInfo");
		CCSprite img_back = CCSprite.sprite(Resouces._getImg("setting/coinSetting"));
		Resouces.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		FitButtons buyBtn = FitButtons.button(Resouces._getImg("setting/buyBtn"),
				Resouces._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(Resouces._getX(717), Resouces._getY(320));
		addChild(buyBtn);

		FitButtons backBtn = FitButtons.button(Resouces._getImg("setting/PlusBack"),
				Resouces._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(Resouces._getX(900), Resouces._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {

//		if (VunglePub.isVideoAvailable(true))
//			VunglePub.displayIncentivizedAdvert(true);

//		admobInterstitial();
		
//		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Resouces.g_Context.getResources().getString(R.string.more_apps_hammyliem)));
//		Resouces.g_Context.startActivity(i);
//
//		long currentTime = System.currentTimeMillis();
//		if ((currentTime - lastTime) > 15 * 60 * 1000) {
//			Resouces.allCoin += 1000;
//			Resouces.saveSetting();
//			lastTime = currentTime;
//		}

	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		Resouces.playEffect(Resouces.click);
		if (Resouces.GAME_STATE.equals("title")) {
			Resouces.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		} else if (Resouces.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
