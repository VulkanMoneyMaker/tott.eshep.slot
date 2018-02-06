package kate.tti.game.views;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import kate.tti.game.buttons.FitButtons;
import kate.tti.game.game.Resouces;

public class PayTable extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new PayTable());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public PayTable()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(Resouces._getImg(String.format("backImages/pay_table%d-hd", Resouces.curLevel)));
		Resouces.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		FitButtons retu = FitButtons.button(Resouces._getImg("Buttons/return"), Resouces._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(Resouces._getX(889), Resouces._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		Resouces.playEffect(Resouces.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}