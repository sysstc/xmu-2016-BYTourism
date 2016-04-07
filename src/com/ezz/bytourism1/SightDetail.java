package com.ezz.bytourism1;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezz.bean.Collect;
import com.ezz.bean.Scenic;
import com.ezz.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SightDetail extends BaseActivity{
	private ImageView sight_view;
	private TextView sight_describe;
	private EditText sight_my_message;
	private ListView sight_all_message;
	private String sight_name;
	private String sight_price;
	private int sight_id;
	private String sight_type;
	private Button back_btn;
	private Button sendMessage;
	private TextView city_name;
	private ImageButton collectButton;
	private TextView all_message;
	private String mesStr="";
	
	private Integer userid ;
	private int type;
	private int flag;
	private List<Map<String,Object>> dataList;
	
	private SimpleAdapter simp_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sight_detail);
		Bmob.initialize(SightDetail.this, "a1a4ff643e92be99bb8649e33589c596");
		Intent searchIntent = getIntent();
		userid = getPreferenceId();
		flag = 0;
		sight_name = searchIntent.getStringExtra("sight_name");
		sight_type = searchIntent.getStringExtra("sight_type");
		sight_price = searchIntent.getStringExtra("sight_price");
		String sight_idtemp = searchIntent.getStringExtra("sight_id");
		sight_view = (ImageView) findViewById(R.id.sight_view);
		sight_describe = (TextView) findViewById(R.id.TextView);
		sight_my_message = (EditText) findViewById(R.id.sight_my_message);
		sight_all_message = (ListView) findViewById(R.id.sight_all_message);
		
		back_btn = (Button) findViewById(R.id.back_btn);
		sight_id = Integer.parseInt(sight_idtemp);
		collectButton = (ImageButton) findViewById(R.id.collectButton);
		sendMessage = (Button) findViewById(R.id.sendMessage);
		
		Toast.makeText(this, "name = "+sight_name+"type = "+sight_type+"price = "+sight_price, Toast.LENGTH_SHORT).show();
		showView();
		city_name = (TextView) findViewById(R.id.city_name);
		city_name.setText(sight_name);
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
			sendMessage.setEnabled(false);
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

		/*
		if(flag==2){
			
		}*/
/*collect -------------------------------*/
		String username = getPreferenceName();
		
		Log.i("tag---65", username+" "+sight_id);
		
		
		back_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		
	}
	
	public void submit(View view){
		com.ezz.bean.Message userMessage = new com.ezz.bean.Message();
		userMessage.setUserid(userid);
		userMessage.setScenicid(sight_id);
		sight_my_message = (EditText) findViewById(R.id.sight_my_message);
		final String myMeg = sight_my_message.getText().toString();
		userMessage.setContent(myMeg);
		//SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		
			Log.i("187---message", userid+" "+sight_id+" "+myMeg.toString());
		userMessage.save(SightDetail.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO 自动生成的方法存根
				Toast.makeText(SightDetail.this, "发表成功", Toast.LENGTH_SHORT).show();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date mytime = new Date(System.currentTimeMillis());
				String timeStr = format.format(mytime);
				
				sight_my_message.setText("");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("luserid", userid);
				map.put("lusercontent", myMeg);
				map.put("lusertime", timeStr);
				dataList.add(map);
				simp_adapter.notifyDataSetChanged();
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("fail--203", arg0+arg1);
				Toast.makeText(SightDetail.this, "发表失败", Toast.LENGTH_SHORT).show();	
			}
		});
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case 1:
					sight_describe = (TextView) findViewById(R.id.sight_describe);
			    	Toast.makeText(SightDetail.this, msg.obj.toString()+"||||||||",Toast.LENGTH_SHORT).show();
					sight_describe.setText(msg.obj.toString());
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
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				
			}
		});
		BmobQuery<com.ezz.bean.Message> query_message = new BmobQuery<com.ezz.bean.Message>();
		query_message.addWhereEqualTo("scenicid", sight_id);
		query_message.findObjects(SightDetail.this, new FindListener<com.ezz.bean.Message>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("fail--250", arg0+arg1);
			}
			@Override
			public void onSuccess(List<com.ezz.bean.Message> messages) {
				// TODO 自动生成的方法存根
				dataList = new ArrayList<Map<String, Object>>();
				for(com.ezz.bean.Message message:messages){
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("luserid", message.getUserid());
					map.put("lusercontent", message.getContent());
					map.put("lusertime", message.getCreatedAt());
					dataList.add(map);
				}
				/*sight_all_message.setText(mesStr);*/
				simp_adapter = new SimpleAdapter(SightDetail.this, dataList, R.layout.itemlist, new String[]{
						"luserid","lusercontent","lusertime"},new int[]{R.id.luserid,R.id.lusercontent,R.id.lusertime});
				sight_all_message.setAdapter(simp_adapter);
			}
			
		});
		
	}
}
