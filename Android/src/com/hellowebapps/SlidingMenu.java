package com.hellowebapps;

import java.util.ArrayList;
import java.util.List;

import com.hellowebapps.easyxmpp.R;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SlidingMenu extends RelativeLayout {
	protected RelativeLayout _drawenField = null;
	protected ImageView _startButton;
	protected List<ImageView> _menuButtons = null;

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
		
	protected boolean checkChild(int id){
		return findViewById(id) != null;
	}
	
	protected void reinit(){
		for(ImageView v : _menuButtons){
			if(!checkChild(v.getId())){
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(55, 55, 55, 55);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, v.getId());
					v.setLayoutParams(params);
					this.addView(v);
				}
			
		}
			//setAllParams();
	}
	
	protected void init(){
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.addView(inflater.inflate(R.layout.slider_bak, null));
		
		_drawenField = (RelativeLayout)findViewById(R.id.innerRelativeLayout);
		/*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
              RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
 		 params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, this._startButton.getId());
		 this.addView(_drawenField, params);*/
		_startButton = (ImageView)findViewById(R.id.mainButton);
		
		// _startButton = (ImageView)findViewById(R.id.mainButton);
		//_drawenField.addView(_startButton);
		//this.addView(_drawenField);
		}
		
	public void addMenuItem(ImageView v){
		if(_menuButtons == null){
			_menuButtons = new ArrayList<ImageView>();
		}
	
		_menuButtons.add(v);
		reinit();
	}
	public void addMenuItemByNumber(int number){
		Resources res = getResources();
		TypedArray immages = res.obtainTypedArray(R.array.buttonImmages);
		for (int i = 0; i < number; i++){
			ImageView button = new ImageView(getContext());
			button.setImageDrawable(immages.getDrawable(i));
			button.setId(4000+i);
			addMenuItem(button);
		}
		
	}
	
	public void useSlidingMenu(){
	    _startButton.setOnClickListener(new OnClickListener() {
				public void onClick(View updatedView) {
					
					Toast.makeText(getContext(), "ja prazyjy", 4000).show();
					//SlidingMenu _menu = new SlidingMenu(getApplicationContext());
					//_menu.addMenuItemByNumber(1);
					//setContentView(_menu);
				}
				
			});
	}
}
