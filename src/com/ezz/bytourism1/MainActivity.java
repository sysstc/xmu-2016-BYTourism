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
	private String[] citytipStrings = {"����","�Ϻ�","����","����","����"};
    private ImageView imagefirst;
	private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;
	private String citysid;
	private ImageButton imagebutton1;
	
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "�������� ", "����", "�ҵ�����",
			"Լ¿��", "�ҵĻ", "����·" };
	private int[] mItemImgs = new int[] { R.drawable.person_three,
			R.drawable.seting, R.drawable.friend_2,
			R.drawable.tourism, R.drawable.activity,
			R.drawable.route_normal };

	
	//����������
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
		
		//Բ�β˵�
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

		
		//ʵ����������
		csearch = (AutoCompleteTextView) findViewById(R.id.edit_Search1);
		
		Bmob.initialize(MainActivity.this, "a1a4ff643e92be99bb8649e33589c596");
		//�����б����ÿ�ʼ
		spinner=(Spinner)findViewById(R.id.spinner1);
		list=new ArrayList<String>();
		list.add("����");
		list.add("�Ϻ�");
		list.add("����");
		
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		citytipname=csearch.getText().toString();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,citytipStrings);
		csearch.setAdapter(arrayAdapter);

		
		csearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO �Զ����ɵķ������
				if(actionId==EditorInfo.IME_ACTION_SEND||(event!=null&&event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
					Toast.makeText(MainActivity.this, "׼����ת", Toast.LENGTH_SHORT).show();
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
				// TODO �Զ����ɵķ������
		        Intent intent = new Intent(MainActivity.this,Personal_centerActivity.class);
		        startActivity(intent);
			}
		});
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO �Զ����ɵķ������
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		new AlertDialog.Builder(this)
    		.setTitle("�˳�")
    		.setMessage("ȷ��Ҫ�˳���?")
    		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO �Զ����ɵķ������
					Intent exit = new Intent(Intent.ACTION_MAIN);
					exit.addCategory(Intent.CATEGORY_HOME);
					exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(exit);
					System.exit(0);
				}
			}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO �Զ����ɵķ������
					dialog.cancel();
				}
			}).show();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }


    
		public void query(){
			//ͨ����������������:city���е�cityname-->city���е�id-->scenicroute���е�cityid-->����id-->scenic��name
			cityname = csearch.getText().toString();
			
			BmobQuery<City> query_city = new BmobQuery<City>();
			query_city.addWhereEqualTo("cityname",cityname);
			query_city.findObjects(MainActivity.this, new cn.bmob.v3.listener.FindListener<City>() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO �Զ����ɵķ������
					Toast.makeText(MainActivity.this, "��ֵʧ�ܣ�", Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onSuccess(List<City> fcity) {
					// TODO �Զ����ɵķ������
					int cityid = fcity.get(0).getId();
					Intent intent = new Intent(MainActivity.this,AllSightDetail.class);
					intent.putExtra("city_id", cityid);
					startActivity(intent);
					Toast.makeText(MainActivity.this, "���ݵ�ֵΪ"+cityid, Toast.LENGTH_SHORT).show();
				}
			});
			
		}
		private void initDrawerLayout(){
			drawerLayout = (DrawerLayout) this.findViewById(R.id.drawerLayout);
			drawerLayout.setDrawerListener(new DrawerListener() {
				
				// �����Ǵ򿪲˵���ʱ����ִ��onDrawerStateChanged��Ȼ�󲻶�ִ��onDrawerSlide
				//��������ִ��onDrawerOpened�����ִ��onDrawerStateChanged
				// �����ǹرղ˵���ʱ����ִ��onDrawerStateChanged��Ȼ�󲻶�ִ��onDrawerSlide
				//��������ִ��onDrawerClosed�����ִ��onDrawerStateChanged
				@Override
				public void onDrawerStateChanged(int newState) {
					// TODO �Զ����ɵķ������
					Log.i("leftMenu", "onDrawerStateChanged");
				}
				
				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					// TODO �Զ����ɵķ������
					slideAnim(drawerView, slideOffset);
					Log.i("leftMenu", "onDrawerSlide");
				}
				
				@Override
				public void onDrawerOpened(View arg0) {
					// TODO �Զ����ɵķ������
					Log.i("leftMenu", "onDrawerOpened");
				}
				
				@Override
				public void onDrawerClosed(View arg0) {
					// TODO �Զ����ɵķ������
					Log.i("leftMenu", "onDrawerClosed");
				}
			});
		}
		private void slideAnim(View drawerView, float slideOffset) {
			View contentView = drawerLayout.getChildAt(0);
			// slideOffset��ʾ�˵�������ı������򿪲˵�ʱȡֵΪ0->1,�رղ˵�ʱȡֵΪ1->0
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
