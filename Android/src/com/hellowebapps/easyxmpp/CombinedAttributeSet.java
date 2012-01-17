package com.hellowebapps.easyxmpp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.AttributeSet;

public class CombinedAttributeSet {
	private HashMap <String, Object> attributes = new HashMap<String, Object>();
	private List <Object> atributeArr = new ArrayList<Object>();
	
	public CombinedAttributeSet(AttributeSet... attributeSets) {
		for(AttributeSet set : attributeSets) {
			for (int i = 0; i < set.getAttributeCount(); i ++){
				String key = set.getAttributeName(i);
				String key1 = set.getAttributeName(i);
			}
		}
	}
	
	private static String createKey(String nameSpace, String attributeName){
		return nameSpace + '|' + attributeName;
	}
}
