package com.ezz.bytourism1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ezz.bean.City;
import com.ezz.bean.Scenicroute;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.c.i;
import cn.bmob.v3.listener.FindListener;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AllRoutes extends Activity implements OnClickListener{
	
 	private Button btn_back;
	private AutoCompleteTextView edit_Search1;
	private String city_name;
	public int cityid = 0;
	private ListView dataList;
	private ArrayAdapter<String> arrayAdapter;
	private int i = 0;
	private Button find_route_btn;
	private List<String> arr_data;
	private int place = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allroutes);
		Intent searchIntent = getIntent();
 		city_name = searchIntent.getStringExtra("city_name");
 		edit_Search1 = (AutoCompleteTextView) findViewById(R.id.route_search);
 		edit_Search1.setText(city_name);
 		Toast.makeText(this, city_name, Toast.LENGTH_SHORT).show();
 		dataList = (ListView) findViewById(R.id.route_listview);
 		getData();
 		find_route_btn = (Button) findViewById(R.id.find_route_btn);
 		find_route_btn.setOnClickListener(this);
 		btn_back = (Button) findViewById(R.id.btn_route_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
					// TODO 自动生成的方法存根     
					Intent intent = new Intent(AllRoutes.this,MainActivity.class);
					startActivity(intent);
					finish();
			}
		});
		
		
	}
	public void getData(){
		BmobQuery<City> query_city = new BmobQuery<City>();
		city_name = edit_Search1.getText().toString();
		query_city.addWhereEqualTo("cityname", city_name);
		query_city.findObjects(AllRoutes.this, new FindListener<City>() {
			@Override
			public void onSuccess(List<City> citys) {
				// TODO 自动生成的方法存根
				cityid = citys.get(0).getId();
				Log.i("tag", "cityid = "+cityid);
				BmobQuery<Scenicroute> query_route = new BmobQuery<Scenicroute>();
				query_route.addWhereEqualTo("cityid", cityid);
				query_route.findObjects(AllRoutes.this, new FindListener<Scenicroute>() {
					@Override
					public void onError(int arg0, String arg1) {
						// TODO 自动生成的方法存根
						Log.i("fail---55", arg0+arg1);
					}
					@Override
					public void onSuccess(List<Scenicroute> scenicroutes) {
						// TODO 自动生成的方法存根
						i = 0;
						arr_data=new ArrayList<String>();
						 for(Scenicroute scenicroute:scenicroutes){
							 arr_data.add("路线"+(i+1));
							 i++;
						 }
						 Message msg = new Message();
						 msg.what = 1;
						 msg.obj = arr_data;
						 handler.sendMessage(msg);
					}
				});
			}
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("fail----75",arg0+arg1);
			}
		});
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case 1:
				Log.i("fail===111", msg.obj.toString());
				arr_data = (List<String>) msg.obj;
				arrayAdapter = new ArrayAdapter<String>(AllRoutes.this, android.R.layout.simple_list_item_1,arr_data);
				dataList.setAdapter(arrayAdapter);
				dataList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO 自动生成的方法存根
						place = position;
	//					int t = Integer.parseInt(arr_data.get(0).substring(2, arr_data.get(0).length()));
		//				Log.i("131",arr_data.get(0).length());
						BmobQuery<Scenicroute> query_scroute = new BmobQuery<Scenicroute>();
						query_scroute.addWhereEqualTo("cityid", cityid);
						query_scroute.findObjects(AllRoutes.this, new FindListener<Scenicroute>() {
						
							@Override
							public void onError(int arg0, String arg1) {
								// TODO 自动生成的方法存根
								Log.i("fail---135", arg0+arg1);
							}
							@Override
							public void onSuccess(List<Scenicroute> routes) {
								// TODO 自动生成的方法存根
								Log.i("tag---143", routes.get(place).toString());
								int rid =routes.get(place).getId();//get routes id
								Intent intent = new Intent(AllRoutes.this,AllSightDetail.class);
								intent.putExtra("city_name", city_name);
								intent.putExtra("rid", String.valueOf(rid));
								startActivity(intent);
							}
						});
					}
				});
			break;
			}
			super.handleMessage(msg);
		}
	};


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		getData();
	}}
