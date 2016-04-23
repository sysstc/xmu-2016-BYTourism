package com.ezz.bytourism1;

import java.util.ArrayList;
import java.util.List;


import com.nineoldandroids.view.ViewHelper;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;

import com.ezz.bean.City;
import com.zhy.view.CircleMenuLayout;
import com.zhy.view.CircleMenuLayout.OnMenuItemClickListener;

public class MainActivity extends Activity {
	private TextView textcity;
	private Spinner spinner;
	
	private List<String>list;

	private ArrayAdapter<String>adapter;
	private String[] citytipStrings = {"北京","上海","广州","深圳","厦门"};
    private ImageView imagefirst;
	private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;
	private String citysid;
	private ImageButton imagebutton1;
	
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "个人中心 ", "设置", "我的朋友",
			"约驴友", "我的活动", "找线路" };
	private int[] mItemImgs = new int[] { R.drawable.person_three,
			R.drawable.seting, R.drawable.friend_2,
			R.drawable.tourism, R.drawable.activity,
			R.drawable.route_normal };

	
	//定义搜索栏
	private AutoCompleteTextView csearch;
	private String citytipname;
	private String cityname;

	//LeftMenu
	
	private DrawerLayout drawerLayout;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose1);
		getActionBar().hide();
		initDrawerLayout();
		
		//圆形菜单
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
				Intent intent = null;
				switch (pos) {
				case 0:
					intent= new Intent(MainActivity.this,Personal_centerActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this,Personal_centerActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this,Personal_centerActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(MainActivity.this,Personal_centerActivity.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(MainActivity.this,Personal_centerActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(MainActivity.this,AllRoutes.class);
					startActivity(intent);
					break;
				default:
					break;
				}
				Toast.makeText(MainActivity.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();
				Log.i("MainActivity----89----success", pos+" "+mItemTexts[pos]);

			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(MainActivity.this,
						"you can do something just like ccb  ",
						Toast.LENGTH_SHORT).show();
				
			}
		});

		
		//实例化搜索栏
		csearch = (AutoCompleteTextView) findViewById(R.id.edit_Search1);
		
		Bmob.initialize(MainActivity.this, "a1a4ff643e92be99bb8649e33589c596");
		//下拉列表设置开始
		spinner=(Spinner)findViewById(R.id.spinner1);
		list=new ArrayList<String>();
		list.add("北京");
		list.add("上海");
		list.add("厦门");
		
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		citytipname=csearch.getText().toString();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,citytipStrings);
		csearch.setAdapter(arrayAdapter);

		
		csearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO 自动生成的方法存根
				if(actionId==EditorInfo.IME_ACTION_SEND||(event!=null&&event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
					Toast.makeText(MainActivity.this, "准备跳转", Toast.LENGTH_SHORT).show();
					cityname = csearch.getText().toString();
					Intent intent = new Intent(MainActivity.this,AllRoutes.class);
					intent.putExtra("city_name", cityname);
					startActivity(intent);
					//query();
					return true;
				}
				return false;
			}
			
		});
		imagebutton1 = (ImageButton) findViewById(R.id.imagebutton1);
		imagebutton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
		        Intent intent = new Intent(MainActivity.this,Personal_centerActivity.class);
		        startActivity(intent);
			}
		});
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO 自动生成的方法存根
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		new AlertDialog.Builder(this)
    		.setTitle("退出")
    		.setMessage("确定要退出吗?")
    		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					Intent exit = new Intent(Intent.ACTION_MAIN);
					exit.addCategory(Intent.CATEGORY_HOME);
					exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(exit);
					System.exit(0);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自动生成的方法存根
					dialog.cancel();
				}
			}).show();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }


    
		public void query(){
			//通过搜索栏进行搜索:city表中的cityname-->city表中的id-->scenicroute表中的cityid-->景点id-->scenic表name
			cityname = csearch.getText().toString();
			
			BmobQuery<City> query_city = new BmobQuery<City>();
			query_city.addWhereEqualTo("cityname",cityname);
			query_city.findObjects(MainActivity.this, new cn.bmob.v3.listener.FindListener<City>() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO 自动生成的方法存根
					Toast.makeText(MainActivity.this, "传值失败！", Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onSuccess(List<City> fcity) {
					// TODO 自动生成的方法存根
					int cityid = fcity.get(0).getId();
					Intent intent = new Intent(MainActivity.this,AllSightDetail.class);
					intent.putExtra("city_id", cityid);
					startActivity(intent);
					Toast.makeText(MainActivity.this, "传递的值为"+cityid, Toast.LENGTH_SHORT).show();
				}
			});
			
		}
		private void initDrawerLayout(){
			drawerLayout = (DrawerLayout) this.findViewById(R.id.drawerLayout);
			drawerLayout.setDrawerListener(new DrawerListener() {
				
				// 当我们打开菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide
				//第三步会执行onDrawerOpened，最后执行onDrawerStateChanged
				// 当我们关闭菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide
				//第三步会执行onDrawerClosed，最后执行onDrawerStateChanged
				@Override
				public void onDrawerStateChanged(int newState) {
					// TODO 自动生成的方法存根
					Log.i("leftMenu", "onDrawerStateChanged");
				}
				
				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					// TODO 自动生成的方法存根
					slideAnim(drawerView, slideOffset);
					Log.i("leftMenu", "onDrawerSlide");
				}
				
				@Override
				public void onDrawerOpened(View arg0) {
					// TODO 自动生成的方法存根
					Log.i("leftMenu", "onDrawerOpened");
				}
				
				@Override
				public void onDrawerClosed(View arg0) {
					// TODO 自动生成的方法存根
					Log.i("leftMenu", "onDrawerClosed");
				}
			});
		}
		private void slideAnim(View drawerView, float slideOffset) {
			View contentView = drawerLayout.getChildAt(0);
			// slideOffset表示菜单项滑出来的比例，打开菜单时取值为0->1,关闭菜单时取值为1->0
			float scale = 1 - slideOffset;
			float rightScale = 0.8f + scale * 0.2f;
			float leftScale = 1 - 0.3f * scale;

			ViewHelper.setScaleX(drawerView, leftScale);
			ViewHelper.setScaleY(drawerView, leftScale);
			ViewHelper.setAlpha(drawerView, 0.6f + 0.4f * (1 - scale));
			ViewHelper.setTranslationX(contentView, drawerView.getMeasuredWidth()
					* (1 - scale));
			ViewHelper.setPivotX(contentView, 0);
			ViewHelper.setPivotY(contentView, contentView.getMeasuredHeight() / 2);
			contentView.invalidate();
			ViewHelper.setScaleX(contentView, rightScale);
			ViewHelper.setScaleY(contentView, rightScale);
		}
}
