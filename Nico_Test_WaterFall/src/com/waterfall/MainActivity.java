package com.waterfall;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.waterfall.bean.ItemView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

@TargetApi(11)
public class MainActivity extends Activity implements OnTouchListener {

	private int count = 3;
	private int itemWidth;

	private int picCount = 51;

	LinearLayout layout;
	LinearLayout l1;
	LinearLayout l2;
	LinearLayout l3;
	LinearLayout pbl;
	ScrollView sc;
	private List<ItemView> list1, list2, list3;
	RelativeLayout rl;
	List<String> fileNames = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		initView();



		handler.sendEmptyMessage(1);

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	//生成文件名列表
	public void setList() {
		AssetManager am = getAssets();
		try {
			String[] str = am.list("image");
			for (String s : str) {
				fileNames.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//获取随机数
	public int getRandomHeight() {
		Random rd = new Random();
		int height = rd.nextInt(30);
		return height;
	}



	//初始化一些view
	public void initView() {
		
		Display display = this.getWindowManager().getDefaultDisplay();
		rl = new RelativeLayout(this);
		int width = display.getWidth();
//		int height = display.getHeight();
		itemWidth = width / count;
		//三排 存放 view的列表
		list1 = new ArrayList<ItemView>();
		list2 = new ArrayList<ItemView>();
		list3 = new ArrayList<ItemView>();
		sc = new ScrollView(this);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(lp1);

		rl.setLayoutParams(params);
		//等待进度条
		ProgressBar pb = new ProgressBar(this, null,
				android.R.attr.progressBarStyleSmall);
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 50);

		lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);

		//三排列表
		l1 = new LinearLayout(this);
		l2 = new LinearLayout(this);
		l3 = new LinearLayout(this);
		pbl = new LinearLayout(this);
		pbl.setGravity(Gravity.CENTER);
		pbl.addView(pb);
		pbl.setLayoutParams(lp2);
		rl.addView(pbl);
		pbl.setVisibility(View.GONE);
		
		setItemSize(l1);
		setItemSize(l2);
		setItemSize(l3);

		insertImageView(picCount);

		layout.addView(l1);
		layout.addView(l2);
		layout.addView(l3);
		
		sc.addView(layout);
		sc.setOnTouchListener(this);

		rl.addView(sc);
		this.setContentView(rl);
	}

	public void insertImageView(int picCount) {
		for (int n = 0; n < picCount; n++) {
			int num = (n + 3) % 3;
			switch (num) {
			case 0:
				ItemView itv1 = new ItemView(this);
				itv1.setViewMsg(n, R.drawable.ic_launcher);
				l1.addView(itv1);
				list1.add(itv1);
				break;
			case 1:
				ItemView itv2 = new ItemView(this);
				itv2.setViewMsg(n, R.drawable.ic_launcher);
				l2.addView(itv2);
				list2.add(itv2);
				break;
			case 2:
				ItemView itv3 = new ItemView(this);
				itv3.setViewMsg(n, R.drawable.ic_launcher);
				l3.addView(itv3);
				list3.add(itv3);
				break;
			}

		}
		setList();
	}

	public void setItemSize(LinearLayout layout) {
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams(itemWidth,
				LinearLayout.LayoutParams.FILL_PARENT));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {

		case MotionEvent.ACTION_DOWN:
			Log.v("caodongquan", "ontouch on down");
			break;
		case MotionEvent.ACTION_MOVE:
			if (sc.getScrollY() > 50) {

				Log.v("caodongquan", fileNames.size() + "  before  ");
				if (fileNames.size() > 0) {
					pbl.setVisibility(View.VISIBLE);
				}
				handler.sendEmptyMessage(1);
			}

			// sc.getScrollY();
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return false;
	}

	int targetNum = 0;
	int lstnum1 = 0;
	int lstnum2 = 0;
	int lstnum3 = 0;
	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			if (msg.what == 1) {
				if (fileNames.size() > 9) {
					targetNum = 9;
				} else {
					targetNum = fileNames.size();
				}
				for (int i = 0; i < targetNum; i++) {

					if (fileNames.size() <= i) {
						pbl.setVisibility(View.GONE);
						break;
					}
					String name = fileNames.get(i);
					Bitmap bmp = getBitmap(name);
					BitmapDrawable bd = new BitmapDrawable(bmp);
					int num = i % 3;
					switch (num) {
					case 0:
						if (list1.size() > lstnum1) {
							list1.get(lstnum1).setBackgroundDrawable(bd);
							lstnum1 += 1;
						}
						break;
					case 1:
						if (list2.size() > lstnum2) {
							list2.get(lstnum2).setBackgroundDrawable(bd);
							lstnum2 += 1;
						}
						break;
					case 2:
						if (list3.size() > lstnum3) {
							list3.get(lstnum3).setBackgroundDrawable(bd);
							lstnum3 += 1;
						}
						break;
					}

					fileNames.remove(i);
					pbl.setVisibility(View.GONE);
				}
			}

		}

	};

	//返回bitmap
	public Bitmap getBitmap(String name) {
		Bitmap image = null;
		try {
			InputStream is = getAssets().open("image/" + name);
			image = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}