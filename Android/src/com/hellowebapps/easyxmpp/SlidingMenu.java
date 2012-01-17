package com.hellowebapps.easyxmpp;

import java.util.ArrayList;
import java.util.List;

import com.hellowebapps.easyxmpp.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class SlidingMenu extends RelativeLayout {
	final public int LEFT_BOTTOM = 0;
	final public int LEFT_TOP = 1;
	final public int RIGHT_TOP = 2;
	final public int RIGHT_BOTTOM = 3;

	final int BASIC_ID_FOR_MENUBUTTONS = 1500;
	final int BASIC_TIMING = 1500;
	
	private boolean menuOut = true;
	private int MENU_DISTANCE = 150;
	private int menuButtonsNumber;

	private int timming = 1500;
	private ImageView _startButton;
	private List<ImageView> _menuButtons = null;
	private int bCount = 0;

	private int menuPosition = LEFT_BOTTOM;

	public SlidingMenu(Context context) {
		super(context);
		init();
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		
		super(context, setDefaults(attrs));
		RelativeLayout.LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		this.setLayoutParams(params);
		init();
	}

	private static AttributeSet setDefaults(AttributeSet attrs) {
		new CombinedAttributeSet(attrs);
		return attrs;
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		RelativeLayout.LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
//		this.setLayoutParams(params);
		init();
	}

	protected boolean isChild(int id) {
		return findViewById(id) != null;
	}

	private int countVerticalMargin(int buttonNumero) {
		double angle = ((double) buttonNumero / (double) (menuButtonsNumber - 1))
				* (Math.PI / 2);
		int y = (int) Math.abs(MENU_DISTANCE * Math.sin(angle));
		Log.i("y", "y=" + y);
		return y;
	}

	private int countHorisontalMargin(int buttonNumero) {
		double angle = ((double) buttonNumero / (double) (menuButtonsNumber - 1))
				* (Math.PI / 2);
		int x = (int) Math.abs(MENU_DISTANCE * Math.cos(angle));
		
		Log.i("x", "x=" + x);
		return x;
	}

	private void reinit() {
		for (ImageView v : _menuButtons) {
			if (!isChild(v.getId())) {
				v.setScaleType(ScaleType.CENTER_INSIDE);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, v.getId());
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, v.getId());
				int baseMarginHeight = (_startButton.getDrawable().getIntrinsicHeight() - v.getDrawable().getIntrinsicHeight()) / 2;
				int baseMarginWidth = (_startButton.getDrawable().getIntrinsicWidth() - v.getDrawable().getIntrinsicWidth()) / 2;

				params.setMargins(countHorisontalMargin(bCount) + baseMarginWidth, 0, 0,
						countVerticalMargin(bCount) + baseMarginHeight);
				v.setLayoutParams(params);
				v.setVisibility(View.GONE);
				this.addView(v, 0);
				bCount++;
			}
		}
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.slider_bak, null));

		//_drawenField = (RelativeLayout) findViewById(R.id.innerRelativeLayout);
		/*
		 * RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		 * RelativeLayout.LayoutParams.FILL_PARENT,
		 * RelativeLayout.LayoutParams.FILL_PARENT);
		 * params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
		 * this._startButton.getId()); this.addView(_drawenField, params);
		 */
		_startButton = (ImageView) findViewById(R.id.mainButton);

		// _startButton = (ImageView)findViewById(R.id.mainButton);
		// _drawenField.addView(_startButton);
		// this.addView(_drawenField);
	}

	private void addMenuItem(ImageView v) {
		if (_menuButtons == null) {
			_menuButtons = new ArrayList<ImageView>();
		}

		_menuButtons.add(v);
		reinit();
	}

	public void addItemsToMenu(int number) {
		menuButtonsNumber = number;
		Resources res = getResources();
		TypedArray immages = res.obtainTypedArray(R.array.buttonImmages);
		for (int i = 0; i < number; i++) {
			ImageView button = new ImageView(getContext());
			button.setImageDrawable(immages.getDrawable(i));
			button.setId(BASIC_ID_FOR_MENUBUTTONS + i);
			addMenuItem(button);
		}

	}

	private AnimationSet countAnimationToSlideOut (int buttonID){
		AnimationSet animationSet = new AnimationSet(false);
		
		RotateAnimation r = new RotateAnimation(0f, -2*360f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		r.setStartOffset(0);
		r.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.2));
		r.setInterpolator(new DecelerateInterpolator());
		animationSet.addAnimation(r);

		int numero = buttonID - BASIC_ID_FOR_MENUBUTTONS;
		
		TranslateAnimation a = new TranslateAnimation(Animation.ABSOLUTE, 0f,
													  Animation.ABSOLUTE, 0f,
													  Animation.ABSOLUTE, 0f,
													  Animation.ABSOLUTE, 0f);
		switch (menuPosition){
		case LEFT_BOTTOM:
			a = new TranslateAnimation( Animation.ABSOLUTE, (float) - countHorisontalMargin(numero),
										Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) countVerticalMargin(numero),
										Animation.ABSOLUTE, 0f);
			break;
		case LEFT_TOP:
			a = new TranslateAnimation( Animation.ABSOLUTE, (float) - countHorisontalMargin(numero),
										Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) - countVerticalMargin(numero),
										Animation.ABSOLUTE, 0f);
			break;
		case RIGHT_TOP:
			a = new TranslateAnimation( Animation.ABSOLUTE, (float) countHorisontalMargin(numero),
										Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) - countVerticalMargin(numero),
										Animation.ABSOLUTE, 0f);
			break;
		case RIGHT_BOTTOM:
			a = new TranslateAnimation( Animation.ABSOLUTE, (float) countHorisontalMargin(numero),
										Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) countVerticalMargin(numero),
										Animation.ABSOLUTE, 0f);	
			break;
			
		}

		a.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.15));
		a.setInterpolator(new OvershootInterpolator(3));
		animationSet.addAnimation(a);
		animationSet.setStartOffset((long) (((menuButtonsNumber - numero - 1) / (double)menuButtonsNumber) * MENU_DISTANCE));

		return animationSet;
	}
	
	private AnimationSet countAnimationToSlideIn (int buttonID){
		AnimationSet animationSet = new AnimationSet(false);
	
		RotateAnimation r = new RotateAnimation(0f, 2*360f,				
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		r.setStartOffset(0);
		r.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.2));
		r.setInterpolator(new AccelerateInterpolator());
		animationSet.addAnimation(r);
	
		int numero = buttonID - BASIC_ID_FOR_MENUBUTTONS;
		TranslateAnimation a = new TranslateAnimation(
				Animation.ABSOLUTE, 0f,
				Animation.ABSOLUTE, 0f,
				Animation.ABSOLUTE, 0f,
				Animation.ABSOLUTE, 0f);
		
		switch (menuPosition){
		case LEFT_BOTTOM:
			a = new TranslateAnimation( Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) -countHorisontalMargin(numero),
										Animation.ABSOLUTE, 0f,
										Animation.ABSOLUTE, (float) countVerticalMargin(numero));
			break;
		case LEFT_TOP:
			a = new TranslateAnimation( Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) - countHorisontalMargin(numero),
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) - countVerticalMargin(numero));
			break;
		case RIGHT_TOP:
			a = new TranslateAnimation( Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) countHorisontalMargin(numero),
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) - countVerticalMargin(numero));
			break;
		case RIGHT_BOTTOM:
			a = new TranslateAnimation( Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) countHorisontalMargin(numero),
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) countVerticalMargin(numero));
			break;
		}
		a.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.15));
		a.setInterpolator(new AnticipateInterpolator(3));
		animationSet.addAnimation(a);

		animationSet.setStartOffset((long) ((numero / (double)menuButtonsNumber) * 150));
		return animationSet;
	}
	
	private void animateOut() {
		RotateAnimation rotateMainButton = new RotateAnimation(45f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateMainButton.setStartOffset(0);
		rotateMainButton.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.2));
		_startButton.setAnimation(rotateMainButton);
		
		for (int i = 0; i < menuButtonsNumber; i++) {
			final ImageView animateButton = (ImageView) findViewById(BASIC_ID_FOR_MENUBUTTONS
					+ i);
			AnimationSet animationSet = countAnimationToSlideOut(animateButton.getId());
			animateButton.startAnimation(animationSet);
			animateButton.setVisibility(View.VISIBLE);
		}
	}

	private void animateIn() {
		RotateAnimation rotateMainButton = new RotateAnimation(-45f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateMainButton.setStartOffset(0);
		rotateMainButton.setDuration((int) (timming * 0.25 * menuButtonsNumber * 0.2));
		_startButton.setAnimation(rotateMainButton);

		for (int i = 0; i < menuButtonsNumber; i++) {
			final ImageView animateButton = (ImageView) findViewById(BASIC_ID_FOR_MENUBUTTONS
					+ i);

			AnimationSet animationSet = countAnimationToSlideIn(animateButton.getId());

			animationSet.setAnimationListener(new AnimationListener() {
					public void onAnimationStart(Animation arg0) {}
					public void onAnimationRepeat(Animation arg0) {}
					public void onAnimationEnd(Animation arg0) {
							animateButton.setVisibility(View.GONE);
					}
			});
			animateButton.startAnimation(animationSet);
		}
	}

	public void showSlidingMenu() {
		_startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View updatedView) {
				if (menuOut) {
					animateOut();
					_startButton.setImageResource(R.drawable.main_button_to_close);
				} else {
					animateIn();
					_startButton.setImageResource(R.drawable.main_button_to_open);
				}
				menuOut = !menuOut;
			}

		});
	}
	
	public void setAcceleration (float persantage){
		timming = (int) persantage * BASIC_TIMING / 100;
	}
	
	private RelativeLayout.LayoutParams createParams (){
		int position = menuPosition;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		int buttonID = _startButton.getId();
		switch (position){
		case LEFT_BOTTOM:
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, buttonID);
			break;
		case LEFT_TOP:
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, buttonID);
			break;
		case RIGHT_TOP:
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, buttonID);
			break;
		case RIGHT_BOTTOM:
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, buttonID);
			break;
		default: return null;
		}
		return params;
	}
	
	private RelativeLayout.LayoutParams createParams(int buttonID){
		int position = menuPosition;
		if (buttonID < BASIC_ID_FOR_MENUBUTTONS) return null;
		ImageView button = (ImageView)findViewById(buttonID); 
		int baseMarginHeight = (_startButton.getDrawable().getIntrinsicHeight() - button.getDrawable().getIntrinsicHeight()) / 2;
		int baseMarginWidth = (_startButton.getDrawable().getIntrinsicWidth() - button.getDrawable().getIntrinsicWidth()) / 2;

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		switch (position){
		case LEFT_BOTTOM:
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, buttonID);
			params.setMargins(
					countHorisontalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginWidth, 0, 0,
					countVerticalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginHeight);

			break;
		case LEFT_TOP:
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, buttonID);
			params.setMargins(
					countHorisontalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginWidth, 
					countVerticalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginHeight, 0, 0);
			break;
		case RIGHT_TOP:
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, buttonID);
			params.setMargins(0,
					countVerticalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginHeight,
					countHorisontalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginWidth, 0);
			break;
		case RIGHT_BOTTOM:
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, buttonID);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, buttonID);
			params.setMargins(0, 0,
					countHorisontalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginWidth,
					countVerticalMargin(BASIC_ID_FOR_MENUBUTTONS-buttonID) + baseMarginHeight);
			break;
		default: return null;
		}
		return params;
	}
	
	public void setMenuPosition(int position){
		menuPosition = position;
		_startButton.setLayoutParams(createParams());
		ImageView button = null;
		int id = BASIC_ID_FOR_MENUBUTTONS;
		for (int butNum = 0; butNum < menuButtonsNumber; butNum++){
			id = BASIC_ID_FOR_MENUBUTTONS + butNum;
			button = (ImageView) findViewById(id);
			button.setLayoutParams(createParams(id));
		}
	}

	public void setOnClickListener (int buttonNumero, OnClickListener listener){
		ImageView button = (ImageView) findViewById(BASIC_ID_FOR_MENUBUTTONS + buttonNumero);
		button.setOnClickListener(listener);
	}
}
