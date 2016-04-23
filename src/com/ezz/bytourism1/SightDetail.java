package com.ezz.bytourism1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezz.bean.Activities;
import com.ezz.bean.Collect;
import com.ezz.bean.Scenic;
import com.ezz.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.c.i;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.R.integer;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SightDetail extends BaseActivity implements OnItemClickListener{
	private ImageView sight_view;
	private TextView sight_describe;
	private ListView sight_activities;
	private String sight_name;
	private String sight_price;
	private int sight_id;
	private String sight_type;
	private Button back_btn;
	private TextView city_name;
	private ImageButton collectButton;
	private String mesStr="";
	private String url;
	private Button sight_message;
	private Button launch_activity;
	
	private String sight_idtemp;
	
	private Integer userid ;
	private String username;
	
	private int type;
	private int flag;
	private List<Map<String, Object>> dataList;
	
	private SimpleAdapter simpl_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sight_detail);
		Bmob.initialize(SightDetail.this, "a1a4ff643e92be99bb8649e33589c596");
		Intent searchIntent = getIntent();
		
		userid = getPreferenceId();
		username = getPreferenceName();
		
		flag = 0;
		sight_name = searchIntent.getStringExtra("sight_name");
		sight_type = searchIntent.getStringExtra("sight_type");
		sight_price = searchIntent.getStringExtra("sight_price");
		sight_idtemp = searchIntent.getStringExtra("sight_id");
		sight_view = (ImageView) findViewById(R.id.sight_view);
		sight_describe = (TextView) findViewById(R.id.TextView);
		launch_activity = (Button) findViewById(R.id.launch_activity);
		sight_activities = (ListView) findViewById(R.id.sight_activities);
		
		sight_message = (Button)findViewById(R.id.sight_message);
		sight_message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(SightDetail.this,SightMessage.class);
				intent.putExtra("sight_id",sight_idtemp);
				intent.putExtra("sight_name",sight_name);
				intent.putExtra("sight_type",sight_type);
				intent.putExtra("sight_price", sight_price);
				startActivity(intent);
			}
		});
		back_btn = (Button) findViewById(R.id.back_btn);
		sight_id = Integer.parseInt(sight_idtemp);
		collectButton = (ImageButton) findViewById(R.id.collectButton);
		dataList = new ArrayList<Map<String, Object>>();
		Toast.makeText(this, "name = "+sight_name+"type = "+sight_type+"price = "+sight_price, Toast.LENGTH_SHORT).show();
		showView();
		city_name = (TextView) findViewById(R.id.city_name);
		city_name.setText(sight_name);
		simpl_adapter = new SimpleAdapter(SightDetail.this,dataList, R.layout.sight_item, new String[]{
				"activity_title1","activity_time","activity_place","activity_need","activity_id"
		},new int[]{R.id.activity_title1,R.id.activity_time,R.id.activity_place,R.id.activity_need,R.id.activity_id});
		sight_activities.setAdapter(simpl_adapter);
		
		if(userid!=0){
		BmobQuery<Collect> query_collect = new BmobQuery<Collect>();
		query_collect.addWhereEqualTo("userid", userid);
		query_collect.addWhereEqualTo("cid", sight_id);
		query_collect.addWhereEqualTo("type", 5);
		query_collect.findObjects(SightDetail.this, new FindListener<Collect>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				flag = 0;// the sight isn't collected
				collectButton.setImageResource(R.drawable.collect);
			}
			@Override
			public void onSuccess(List<Collect> arg0) {
				// TODO 自动生成的方法存根
				Log.i("findout",arg0.get(0).getId()+"");
				flag = 1;//the sight is collected
				collectButton.setImageResource(R.drawable.star_on);
			}
		});
	}
		else{
			Toast.makeText(SightDetail.this, "请先登录，否则将无法使用收藏和留言功能！",Toast.LENGTH_SHORT ).show();
			collectButton.setImageResource(R.drawable.collect);
			collectButton.setEnabled(false);
		}
		Log.i("fail---85", flag+"");
		collectButton = (ImageButton) findViewById(R.id.collectButton);
		
			collectButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					if(flag==0){
						Collect collect = new Collect();
						type = 5;
						collect.setUserid(userid);
						collect.setCid(sight_id);
						collect.setType(type);
						collect.save(SightDetail.this,new SaveListener() {
							@Override
							public void onSuccess() {
								// TODO 自动生成的方法存根
								Toast.makeText(SightDetail.this, "收藏成功！", Toast.LENGTH_SHORT).show();
								collectButton.setImageResource(R.drawable.star_on);
								flag = 2;
							}
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO 自动生成的方法存根
								Log.i("115-fail",arg0+arg1);
							}
						});
					}
					else if(flag==1||flag==2){//double click in the same page
						Log.i("fail--116", flag+"");
						BmobQuery<Collect> bmobQuery = new BmobQuery<Collect>();
						bmobQuery.addWhereEqualTo("cid", sight_id);
						bmobQuery.addWhereEqualTo("userid", userid);
						bmobQuery.addWhereEqualTo("type", 5);
						bmobQuery.findObjects(SightDetail.this, new FindListener<Collect>() {
							@Override
							public void onError(int arg0, String arg1) {
								// TODO 自动生成的方法存根
							}
							@Override
							public void onSuccess(List<Collect> collects) {
								// TODO 自动生成的方法存根
								String objectid = collects.get(0).getObjectId();
								Collect collect = new Collect();
								collect.delete(SightDetail.this,objectid,new DeleteListener() {
									@Override
									public void onSuccess() {
										// TODO 自动生成的方法存根
										Toast.makeText(SightDetail.this, "收藏取消！", Toast.LENGTH_SHORT).show();
										collectButton.setImageResource(R.drawable.collect);
										flag = 0;
									}
									
									@Override
									public void onFailure(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail--129",arg0+arg1);
									}
								});
							}
						});
					}
				}
				
			});

		String username = getPreferenceName();
		
		Log.i("tag---65", username+" "+sight_id);
		
		
		back_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		sight_activities.setOnItemClickListener(this);
		launch_activity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.setClass(SightDetail.this, CreateActivity.class);
				intent.putExtra("sight_id", sight_id+"");
				startActivity(intent);
			}
		});
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO 自动生成的方法存根
			Object activity;
			
			switch (msg.what) {
			
			case 1:
					sight_describe = (TextView) findViewById(R.id.sight_describe);
			    	Toast.makeText(SightDetail.this, msg.obj.toString()+"||||||||",Toast.LENGTH_SHORT).show();
					sight_describe.setText(msg.obj.toString());
				break;
			case 2:
				Log.i("SightDetail---249", msg.obj.toString());
				url = msg.obj.toString();
				new Thread(){
					public void run() {
						
						Bitmap bitmap = null;
						try {
							URL myURL = new URL(url);
							HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
							conn.setConnectTimeout(500);
							conn.setDoInput(true);
							conn.setUseCaches(true);
							conn.connect();
							InputStream is = conn.getInputStream();
							bitmap = BitmapFactory.decodeStream(is);
							sight_view.setImageBitmap(bitmap);
							is.close();
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					};
				
				}.start();
				break;
			case 3:
				Log.i("244----------",msg.obj.toString());
				activity = msg.obj;
				Map<String,Object> map = (Map<String,Object>)activity;
				dataList.add(map);
				simpl_adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void showView(){
		BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
		query_scenic.addWhereEqualTo("scenicname",sight_name);
		query_scenic.findObjects(SightDetail.this, new FindListener<Scenic>() {
			@Override
			public void onSuccess(List<Scenic> scenics) {
				// TODO 自动生成的方法存根
				for(Scenic scenic:scenics){
					Message msg = new Message();
					msg.what = 1;
					msg.obj = scenic.getDescribe();
					handler.sendMessage(msg);
		
					
					Message msg1 = new Message();
					msg1.what=2;
					msg1.obj = scenic.getScenicview();
					handler.sendMessage(msg1);
		
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		BmobQuery<Activities> query_activities = new BmobQuery<Activities>();
		query_activities.addWhereEqualTo("scenicid", sight_id);
		Log.i("..............", sight_id+"");
		query_activities.findObjects(SightDetail.this, new FindListener<Activities>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("fail---258", arg0+arg1);
			}

			@Override
			public void onSuccess(List<Activities> activities) {
				// TODO 自动生成的方法存根
				Log.i("264---success", "111111111");
				dataList = new ArrayList<Map<String,Object>>();
				Log.i("1111111",activities.get(0).getDescribe());
				for(Activities activity:activities){
					Map<String, Object> map = new HashMap<String,Object>();
					String date_str = activity.getPtime();
					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date d = null;
				try {
						d = sim.parse(date_str);
						if(d.before(new Date())&&activity.getPstate()==0){
							Log.i("111111111", "目标时间在当前时间之前");
							//activity.setPstate(0);
							activity.setId(activity.getId());
							activity.setTitle(activity.getTitle());
							activity.setDescribe(activity.getDescribe());
							activity.setAddress(activity.getAddress());
							activity.setType(activity.getType());
							activity.setPtime(activity.getPtime());
							activity.setPstate(2);
							activity.setScenicid(activity.getScenicid());
							activity.setPcount(activity.getPcount());
							activity.setPneed_count(activity.getPneed_count());
							activity.update(SightDetail.this,activity.getObjectId(),new UpdateListener() {
								
								@Override
								public void onSuccess() {
									// TODO 自动生成的方法存根
									Log.i("success----356", "更新成功！");
								}
								
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO 自动生成的方法存根
									Log.i("success----356", "更新失败！"+arg0+arg1);									
								}
							});
						}else{
							Log.i("222222222", "目标时间在当前时间之后");
						}
						
					} catch (ParseException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					
					if(activity.getPstate()==0){
						map.put("activity_id", activity.getId()+"");
						map.put("activity_title1", activity.getTitle());
						map.put("activity_time", activity.getPtime());
						map.put("activity_place", activity.getAddress());
						
						map.put("activity_need", activity.getPneed_count()+"");
						//dataList.add(map);
						Message msg = new Message();
						msg.what = 3;
						msg.obj = map;
						handler.sendMessage(msg);
						
					}
				}
				
				
				simpl_adapter = new SimpleAdapter(SightDetail.this,dataList, R.layout.sight_item, new String[]{
						"activity_title1","activity_time","activity_place","activity_need","activity_id"
				},new int[]{R.id.activity_title1,R.id.activity_time,R.id.activity_place,R.id.activity_need,R.id.activity_id});
				sight_activities.setAdapter(simpl_adapter);
				
			}
		});
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		Map<String, Object> map = (Map<String, Object>) sight_activities.getItemAtPosition(position);
		//Log.i("372", map.get("activity_id")+"");
		Intent intent = new Intent(this,ActivityDetail.class);
		intent.putExtra("activity_id",map.get("activity_id")+"");
		startActivity(intent);
	}

	
}
