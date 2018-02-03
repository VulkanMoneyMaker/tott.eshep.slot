package tott.eshep.slot.views;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import tott.eshep.slot.buttons.FitButtons;
import tott.eshep.slot.game.Resouces;


public class Setting extends CCLayer
{
	FitButtons on1;
	FitButtons off1;
	FitButtons on2;
	FitButtons off2;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new Setting());
		return scene;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public Setting()
	{
		super();
		CCSprite im_back = CCSprite.sprite(Resouces._getImg("setting/setting"));
		Resouces.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = FitButtons.button(Resouces._getImg("setting/onBtn"), Resouces._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= FitButtons.button(Resouces._getImg("setting/off"), Resouces._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(Resouces._getX(768), Resouces._getY(332));
		off1.setPosition(Resouces._getX(768), Resouces._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = FitButtons.button(Resouces._getImg("setting/onBtn"), Resouces._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= FitButtons.button(Resouces._getImg("setting/off"), Resouces._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(Resouces._getX(768), Resouces._getY(194));
		off2.setPosition(Resouces._getX(768), Resouces._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		FitButtons back = FitButtons.button(Resouces._getImg("setting/backBtn"), Resouces._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(Resouces._getX(877), Resouces._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(Resouces.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(Resouces.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(Resouces.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			Resouces.bgmState = false;
			Resouces.pauseSound();
			Resouces.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			Resouces.bgmState = true;
			if(Resouces.stopSound){
				Resouces.resumeSound();
				Resouces.stopSound = false;
			}else{
				Resouces.playSound();
			}
		}
		Resouces.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(Resouces.effectState){
			Resouces.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			Resouces.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		Resouces.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		Resouces.playEffect(Resouces.click);
		Resouces.titleState = false;
		if(Resouces.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		Resouces.playEffect(Resouces.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		Resouces.playEffect(Resouces.click);
		getStateBgm();
	}
}