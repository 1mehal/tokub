package com.hellowebapps.easyxmpp;

import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {

	private double tension = 4;
	
	public SpringInterpolator() {}
	
	public SpringInterpolator(double tension) {
		this.tension = tension;
	}

	public float getInterpolation(float t) {
		return (float) (1 - Math.exp(-tension*t)*Math.cos(tension * Math.PI * t));
	}

}
