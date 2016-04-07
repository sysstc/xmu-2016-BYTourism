package com.ezz.bytourism1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ezz.bean.City;
import com.ezz.bean.Scenic;
import com.ezz.bean.Scenicroute;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	private TextView textcity;
	private Spinner spinner;
	private List<String>list;

	private ArrayAdapter<String>adapter;
	private String[] citytipStrings = {"北京","上海","广州","深圳","厦门"};
    private ImageView imagefirst;
	private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;
	private String citysid;
	//定义搜索栏
	private AutoCompleteTextView csearch;
	private String citytipname;
	private String cityname;
	private TabHost thHost;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose1);
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
		thHost = (TabHost) findViewById(R.id.tabhost);
		thHost.setup();//initial TabHost
		
		
		thHost.addTab(thHost.newTabSpec("tab1").setIndicator("首页",getResources().getDrawable(R.drawable.home)).setContent(R.id.tab1));
		thHost.addTab(thHost.newTabSpec("tab2").setIndicator("景点",getResources().getDrawable(R.drawable.business)).setContent(R.id.tab2));
		thHost.addTab(thHost.newTabSpec("tab3").setIndicator("个人",getResources().getDrawable(R.drawable.person)).setContent(R.id.tab3));
		thHost.addTab(thHost.newTabSpec("tab4").setIndicator("更多",getResources().getDrawable(R.drawable.person)).setContent(R.id.tab4));
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
	}
    public void turnToPersonal(View view){
        Intent intent = new Intent(MainActivity.this,Personal_centerActivity.class);
        startActivity(intent);
    }
		public void query(){
			//通过搜索栏进行搜索:city表中的cityname-->city表中的id-->scenicroute表中的cityid-->景点id-->scenic表name
			cityname = csearch.getText().toString();
			
			BmobQuery<City> query_city = new BmobQuery<City>();
			query_city.addWhereEqualTo("cityname",cityname);
			query_city.findObjects(MainActivity.this, new FindListener<City>() {

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
		
}
