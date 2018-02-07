package fstd.tgsaw.sllot.views;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;

import fstd.tgsaw.sllot.buttons.FitButtons;
import fstd.tgsaw.sllot.game.Resouces;


public class TitleLayer extends CCLayer
{

	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new TitleLayer());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public TitleLayer()
	{
		super();
		
		schedule("getInfo", 1.0f / 10.0f);
	}	
	public void getInfo(float dt){
		unschedule("getInfo");
		createBG();
		createButton();
		createLabel();	
		getTime();
		
	}
	public void getTime(){
		 if(Resouces.allCoin == 0){
			 if(Resouces.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - Resouces.currentTime) / 3600 >= 24){
					 Resouces.allCoin = 250;
					 Resouces.setTimeState = false;
					 Resouces.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(Resouces._getImg("backImages/menu_bg-hd"));
		Resouces.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/zombies","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};	
		FitButtons selectBtn;
		for(int i = 0 ; i < 6 ; i++){
			if (i == 3) {
				selectBtn = FitButtons.button(Resouces._getImg(str[i]), Resouces._getImg(str[i]),this,"startGame",(i+1));
				float fx = 900;
				float fy = 350;
				selectBtn.setAnchorPoint(0, 0);
				selectBtn.setPosition(fx, fy);
				addChild(selectBtn);
			}
		}
		
		CCSprite img_txt = CCSprite.sprite(Resouces._getImg("Buttons/text_box"));
		Resouces.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(Resouces._getX(52), Resouces._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(Resouces._getImg("Buttons/usd3"));
		Resouces.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(Resouces._getX(40), Resouces._getY(564));
		addChild(img_usd);		
		
		FitButtons plus = FitButtons.button(Resouces._getImg("Buttons/plus1"), Resouces._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(Resouces._getX(288), Resouces._getY(597));
		addChild(plus);
		
		FitButtons setting = FitButtons.button(Resouces._getImg("Buttons/setting1"), Resouces._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Resouces._getX(100), Resouces._getY(38));
		addChild(setting);
		
		//FitButtons more_game = FitButtons.button(Resouces._getImg("Buttons/more_game"), Resouces._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(Resouces._getX(824),Resouces._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", Resouces.allCoin), Resouces._getFont("Imagica"), 30);
		Resouces.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(Resouces._getX(160), Resouces._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		Resouces.playEffect(Resouces.click);
		Resouces.titleState = true;
		Resouces.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		Resouces.playEffect(Resouces.click);
		Resouces.GAME_STATE = "title";
		Resouces.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Resouces.playEffect(Resouces.click);
		Resouces.titleState = true;
		Resouces.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		Resouces.playEffect(Resouces.click);
	}
	
	
}