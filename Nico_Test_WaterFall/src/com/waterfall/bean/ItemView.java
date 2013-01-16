package com.waterfall.bean;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ItemView extends LinearLayout {

	public int viewTag;
	public int sourceId;

	public ItemView(Context context) {
		super(context);
		this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	public void setViewMsg(int tag, int sId) {
		viewTag = tag;
		sourceId = sId;
		this.setBackgroundResource(sId);
	}

	public Integer getTag() {
		return viewTag;
	}

}
