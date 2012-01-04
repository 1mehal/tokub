package com.hellowebapps;

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
import android.widget.RelativeLayout;

public class SlidingMenu extends RelativeLayout {
	protected boolean menuOut = true;
	final int MENU_DISTANCE = 150;
	protected int menuButtonsNumber;
	final int BASIC_ID_FOR_MENUBUTTONS = 1500;
	protected RelativeLayout _drawenField = null;
	protected ImageView _startButton;
	protected List<ImageView> _menuButtons = null;
	protected int bCount = 0;

	public SlidingMenu(Context context) {
		super(context);
		init();
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected boolean isChild(int id) {
		return findViewById(id) != null;
	}

	protected int countBottomMargin(int buttonNumero) {
		double angle = ((double) buttonNumero / (double) (menuButtonsNumber - 1))
				* (Math.PI / 2);
		int y = (int) (MENU_DISTANCE * Math.sin(angle));
		// Log.i("BottomMargin", "y = " + y);
		return y;
	}

	protected int countLeftMargin(int buttonNumero) {
		double angle = ((double) buttonNumero / (double) (menuButtonsNumber - 1))
				* (Math.PI / 2);
		int x = (int) (MENU_DISTANCE * Math.cos(angle));
		// Log.i("buttonNumero", "buttonNumero = " + buttonNumero);
		// Log.i("menuButtonsNumber", "menuButtonsNumber = " +
		// menuButtonsNumber);
		// Log.i("Angle", "angle = " + angle);
		// Log.i("LeftMargin", "x = " + x);
		return x;
	}

	protected void reinit() {
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

				params.setMargins(countLeftMargin(bCount) + baseMarginWidth, 0, 0,
						countBottomMargin(bCount) + baseMarginHeight);
				v.setLayoutParams(params);
				v.setVisibility(View.GONE);
				this.addView(v, 0);
				bCount++;
			}
		}
		// setAllParams();
	}

	protected void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.slider_bak, null));

		_drawenField = (RelativeLayout) findViewById(R.id.innerRelativeLayout);
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

	protected void addMenuItem(ImageView v) {
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

	protected void animateOut() {
		RotateAnimation rotateMainButton = new RotateAnimation(45f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateMainButton.setStartOffset(0);
		rotateMainButton.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.2));
		_startButton.setAnimation(rotateMainButton);
		
		for (int i = 0; i < menuButtonsNumber; i++) {
			final ImageView animateButton = (ImageView) findViewById(BASIC_ID_FOR_MENUBUTTONS
					+ i);

			AnimationSet animationSet = new AnimationSet(false);

			RotateAnimation r = new RotateAnimation(0f, -2*360f,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			r.setStartOffset(0);
			r.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.2));
			r.setInterpolator(new DecelerateInterpolator());
			animationSet.addAnimation(r);

			TranslateAnimation a = new TranslateAnimation(
					Animation.ABSOLUTE, (float) - countLeftMargin(i),
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) countBottomMargin(i),
					Animation.ABSOLUTE, 0f);
			a.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.15));
			a.setInterpolator(new OvershootInterpolator(3));
			animationSet.addAnimation(a);
			animationSet.setStartOffset((long) (((menuButtonsNumber - i - 1) / (double)menuButtonsNumber) * 150));

			animateButton.startAnimation(animationSet);
			
			animateButton.setVisibility(View.VISIBLE);
		}
		
	}

	protected void animateIn() {
		RotateAnimation rotateMainButton = new RotateAnimation(-45f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateMainButton.setStartOffset(0);
		rotateMainButton.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.2));
		_startButton.setAnimation(rotateMainButton);

		for (int i = 0; i < menuButtonsNumber; i++) {
			final ImageView animateButton = (ImageView) findViewById(BASIC_ID_FOR_MENUBUTTONS
					+ i);

			AnimationSet animationSet = new AnimationSet(false);

			RotateAnimation r = new RotateAnimation(0f, 2*360f,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			r.setStartOffset(0);
			r.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.2));
			r.setInterpolator(new AccelerateInterpolator());
			animationSet.addAnimation(r);
			
		TranslateAnimation a = new TranslateAnimation(
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) -countLeftMargin(i),
					Animation.ABSOLUTE, 0f,
					Animation.ABSOLUTE, (float) countBottomMargin(i));
			a.setDuration((int) (BASIC_ID_FOR_MENUBUTTONS * 0.25 * menuButtonsNumber * 0.15));
			a.setInterpolator(new AnticipateInterpolator(3));
			animationSet.addAnimation(a);
			
			animationSet.setStartOffset((long) ((i / (double)menuButtonsNumber) * 150));
			animateButton.startAnimation(animationSet);
			
			animationSet.setAnimationListener(new AnimationListener() {
				
				public void onAnimationStart(Animation arg0) {}
				
				public void onAnimationRepeat(Animation arg0) {}
				
				public void onAnimationEnd(Animation arg0) {
					animateButton.setVisibility(View.GONE);
				}
			});
		}
	}

	public void useSlidingMenu() {
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
				// SlidingMenu _menu = new SlidingMenu(getApplicationContext());
				// _menu.addMenuItemByNumber(1);
				// setContentView(_menu);
			}

		});
	}
}
