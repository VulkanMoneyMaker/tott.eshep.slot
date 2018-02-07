package fstd.tgsaw.sllot.game;


import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import fstd.tgsaw.sllot.views.LogoLayer;
import fstd.tgsaw.sllot.views.TitleLayer;
import fstd.tgsaw.sllot.buttons.WinService;

import fstd.tgsaw.sllot.entity.EntityRandom;



public class GameScreen extends Activity {
	private CCGLSurfaceView mGLSurfaceView;
	private boolean startState ;

	//@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
                LayoutParams.FLAG_KEEP_SCREEN_ON);

        mGLSurfaceView = new CCGLSurfaceView(this);        
        if(!startState){
        	CCDirector.sharedDirector().setScreenSize(CCDirector.sharedDirector().winSize().width, 
	        CCDirector.sharedDirector().winSize().height);
	        CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		    CCDirector.sharedDirector().getActivity().setContentView(mGLSurfaceView, createLayoutParams());
		    CCDirector.sharedDirector().attachInView(mGLSurfaceView);       
	        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
	        CCDirector.sharedDirector().setDisplayFPS(false);
	        CCTexture2D.setDefaultAlphaPixelFormat(Config.ARGB_8888);  
//	        getAdmob();
	        
//		    getInterstitialAd();
//		    getVungleAd();
			InitParam();
			CCDirector.sharedDirector().runWithScene( LogoLayer.scene());
			startState = true;
        }
	    
    }
	public void getAdmob(){
//		LinearLayout.LayoutParams adParams = new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth() + getWindowManager().getDefaultDisplay().getWidth() - (int) Resouces._getX(640), getWindowManager().getDefaultDisplay().getHeight()
//                + getWindowManager().getDefaultDisplay().getHeight()
//                - getWindowManager().getDefaultDisplay().getHeight() * 2 - 120);
//
//	    AdView adView = new AdView(GameScreen.this, AdSize.BANNER, getResources().getString(R.string.admob_id));
//	    AdRequest adRequest = new AdRequest();
//		adView.loadAd(adRequest);
//	    CCDirector.sharedDirector().getActivity().addContentView(adView, adParams);
	}
	public void getInterstitialAd(){       	
//		interstitialAd = new InterstitialAd(this, getResources().getString(R.string.admob_id));
//        interstitialAd.setAdListener(this);
//        AdRequest adRequest = new AdRequest();
//        interstitialAd.loadAd(adRequest);
	}
    public void getVungleAd(){
//    	VunglePub.init(this, getResources().getString(R.string.vungle_id));
//    	VunglePub.setEventListener(this);
    }
    
    //@Override 
    public void onStart() {
        super.onStart();       

    }    
   
    @Override
	public void onBackPressed() {
//    	if(!Resouces.titleState)
//    		getInterstitialAd();
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	if(Resouces.titleState){
    					Resouces.titleState = false;
    					CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, TitleLayer.scene()));								
    				}else{
    					Resouces.stopSound();
    					CCDirector.sharedDirector().end();
    			        WinService.releaseScoreManager();
    			        finish();
    				}	
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure? You may lose all your coins.").setPositiveButton("Yes", dialogClickListener)
    	    .setNegativeButton("No", dialogClickListener).show();
	}
    
    
	private void InitParam() { 		
		Resouces.g_Context = this;
		Resouces.curLevel = 1;
		Resouces.curLine = 1;
		Resouces.maxline = 1;
		Resouces.bet = 1;
		
	}	
	@Override public void onPause() {
	      super.onPause();
	      CCDirector.sharedDirector().pause();
	      Resouces.pauseSound();
	        
	 }

	 @Override public void onResume() {
	     super.onResume();
	     CCDirector.sharedDirector().resume();
	     Resouces.resumeSound();
	     review();
	  }

	  @Override public void onDestroy() {
	       super.onDestroy();
	       Resouces.stopSound();
	       CCDirector.sharedDirector().end();
	       WinService.releaseScoreManager();
	  }
   
	
    private LayoutParams createLayoutParams() {
        final DisplayMetrics pDisplayMetrics = new DisplayMetrics();
		CCDirector.sharedDirector().getActivity().getWindowManager().getDefaultDisplay().getMetrics(pDisplayMetrics);
		
		//final float mRatio = (float)Resouces.DEFAULT_W / Resouces.DEFAULT_H;
		final float mRatio = (float) Resouces.DEFAULT_W / Resouces.DEFAULT_H;
		final float realRatio = (float)pDisplayMetrics.widthPixels / pDisplayMetrics.heightPixels;

		final int width;
		final int height;
		if(realRatio < mRatio) {
			width = pDisplayMetrics.widthPixels;
			height = Math.round(width / mRatio);
		} else {
			height = pDisplayMetrics.heightPixels;
			width = Math.round(height * mRatio);
		}

		final LayoutParams layoutParams = new LayoutParams(width, height);

		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}


	/**
	 * Review
	 */
	private void review() {
		int random = EntityRandom.random.nextInt(100);
		if (random < 15) {
			GameScreen.this.showReviewDialog();
		}
	}

    /**
     * Set Review Dialog
     */
    public void showReviewDialog() {
//    	String message = "Do you Like this app? Get FREE 200 coins by giving us 5 STARS review or SHARE this app to your friends!";
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(message)
//            .setPositiveButton("Give Review!", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                	Resouces.allCoin += 200;
//                	Resouces.saveSetting();
//                    EntityActions.giveUsReview(GameScreen.this);
//                }
//            })
//            .setNegativeButton("Share with Friends!", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Resouces.allCoin += 200;
//					Resouces.saveSetting();
//					EntityActions.shareApp(GameScreen.this);
//				}
//            });
//        builder.create().show();
    }	
	
}