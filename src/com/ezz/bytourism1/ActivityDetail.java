package com.ezz.bytourism1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.ezz.bean.Activities;
import com.ezz.bean.Activitymen;
import com.ezz.bean.Collect;
import com.ezz.bean.Friend;
import com.ezz.bean.User;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetail extends BaseActivity{
	
	private Integer userid ;
	private String username;
	private int activity_id;
	private ImageButton collectButton;
	private int flag;
	private Button back_btn;
	private TextView activity_describe;
	private TextView activity_pneed;
	private TextView activity_address;
	private TextView address_time;
	private ListView friends;
	private Button button1;
	private List<Map<String, Object>> mData;
	private MyButtonAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		flag = 0;
		userid = getPreferenceId();
		username = getPreferenceName();
		mData = new ArrayList<Map<String, Object>>();
		Intent searchIntent = getIntent();
		activity_id = Integer.parseInt(searchIntent.getStringExtra("activity_id"));
		
		Log.i("userid---ActivityDetail--18", userid+"  "+activity_id+" "+username);
		button1 = (Button) findViewById(R.id.button1);
		
		collectButton = (ImageButton) findViewById(R.id.collectButton);
		back_btn = (Button) findViewById(R.id.back_btn);
		activity_describe = (TextView) findViewById(R.id.activity_describe);
		activity_pneed = (TextView) findViewById(R.id.activity_pneed);
		activity_address = (TextView) findViewById(R.id.activity_address);
		address_time = (TextView) findViewById(R.id.address_time);
		friends = (ListView) findViewById(R.id.friends);


		if(Integer.valueOf(userid)==0){
			button1.setEnabled(false);
			collectButton.setEnabled(false);
			Toast.makeText(ActivityDetail.this, "���ȵ�¼��������Щ���ܾͻᱻ����", Toast.LENGTH_SHORT).show();
		}		
	
		
		
		//Back Button
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				ActivityDetail.this.finish();
			}
		});
		// collection judge
		if(userid!=0){
			BmobQuery<Collect> query_collect = new BmobQuery<Collect>();
			query_collect.addWhereEqualTo("userid",userid);
			query_collect.addWhereEqualTo("cid", activity_id);
			query_collect.addWhereEqualTo("type", 4);
			query_collect.findObjects(ActivityDetail.this, new FindListener<Collect>() {		
				@Override
				public void onError(int arg0, String arg1) {
					// TODO �Զ����ɵķ������
					collectButton.setImageResource(R.drawable.collect);
				}
				@Override
				public void onSuccess(List<Collect> arg0) {
					// TODO �Զ����ɵķ������
					Log.i("59", arg0.get(0).toString());
					collectButton.setImageResource(R.drawable.star_on);
					flag = 1;// have been collected
				}
			});
		}else{
			collectButton.setClickable(false);
		}
		//collection 
		collectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if(flag==0){
					Collect collect = new Collect();
					collect.setType(4);
					collect.setCid(activity_id);
					collect.setUserid(userid);
					collect.save(ActivityDetail.this,new SaveListener() {
						@Override
						public void onSuccess() {
						// TODO �Զ����ɵķ������
							Toast.makeText(ActivityDetail.this, "�ղسɹ���", Toast.LENGTH_SHORT).show();
							collectButton.setImageResource(R.drawable.star_on);
							flag = 1;
						}
						@Override
						public void onFailure(int arg0, String arg1) {
						// TODO �Զ����ɵķ������
							Toast.makeText(ActivityDetail.this, "�ղ�ʧ�ܣ�", Toast.LENGTH_SHORT).show();
						}
					});
				}
			else if(flag==1){
				BmobQuery<Collect> query_deleteCollect = new BmobQuery<Collect>();

				query_deleteCollect.addWhereEqualTo("userid",userid);
				query_deleteCollect.addWhereEqualTo("cid", activity_id);
				query_deleteCollect.addWhereEqualTo("type", 4);
				query_deleteCollect.findObjects(ActivityDetail.this,new FindListener<Collect>() {
					@Override
					public void onError(int arg0, String arg1) {
						// TODO �Զ����ɵķ������
						Log.i("fail--104---delete",arg0+arg1);
					}
					@Override
					public void onSuccess(List<Collect> collects) {
						// TODO �Զ����ɵķ������
						for(Collect co:collects){
							
							Collect collect = new Collect();
							collect.setObjectId(co.getObjectId());
							collect.delete(ActivityDetail.this,new DeleteListener() {
								@Override
								public void onSuccess() {
									// TODO �Զ����ɵķ������
									Log.i("success!!!!!!---103", "delete---ok!!!!!");
									Toast.makeText(ActivityDetail.this, "ȡ���ղ�", Toast.LENGTH_SHORT).show();
									collectButton.setImageResource(R.drawable.collect);
								}
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO �Զ����ɵķ������
									Log.i("success!!!!!!---109", "delete---fail!!!!!");
								}
							});
						}
					}
				});
			}
			}
		});
		//end Collection
		adapter = new MyButtonAdapter(this,mData,userid,friends);
		
		friends.setAdapter(adapter);
		
		BmobQuery<Activitymen> query_activitymen = new BmobQuery<Activitymen>();
		query_activitymen.addWhereEqualTo("activityid", activity_id);
		query_activitymen.findObjects(ActivityDetail.this, new FindListener<Activitymen>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO �Զ����ɵķ������
				Log.i("fail---194", arg0+arg1);
			}

			@Override
			public void onSuccess(List<Activitymen> activitymens) {
				// TODO �Զ����ɵķ������
				for(Activitymen activitymen:activitymens){
					Log.i("userid---201--ActivityDetail", activitymen.getUserid()+"");
					BmobQuery<User> query_user = new BmobQuery<User>();
					query_user.addWhereEqualTo("id", activitymen.getUserid());
					query_user.findObjects(ActivityDetail.this, new FindListener<User>() {
						
						@Override
						public void onSuccess(List<User> users) {
							// TODO �Զ����ɵķ������
							for(User user:users){
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("member_name", user.getUsername());
								mData.add(map);
							}
							Message msg = new Message();
							msg.what = 1;
							msg.obj =mData;
							handler.sendMessage(msg);
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							// TODO �Զ����ɵķ������
							Log.i("fail---ActivityDetail--219", arg0+arg1);
						}
					});
				}
			}
			
		});
		

		
		BmobQuery<Activities> query_collect = new BmobQuery<Activities>();
		query_collect.addWhereEqualTo("id", activity_id);
		Log.i("164----ActivityDetail", activity_id+"");
		query_collect.findObjects(ActivityDetail.this, new FindListener<Activities>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO �Զ����ɵķ������
				Log.i("ActivityDetail--161",arg0+arg1);
			}

			@Override
			public void onSuccess(List<Activities> activities) {
				// TODO �Զ����ɵķ������
				for (Activities activity:activities) {
					activity_describe.setText(activity.getDescribe());
					
					activity_address.setText(activity.getAddress());
					address_time.setText(activity.getPtime());
					
				}
			}
			
		});
		if(userid==0){
			button1.setEnabled(false);
		}else{
			BmobQuery<Activitymen> query_activitymen1 = new BmobQuery<Activitymen>();
			query_activitymen1.addWhereEqualTo("userid", userid);
			query_activitymen1.addWhereEqualTo("activityid", activity_id);
			query_activitymen1.findObjects(ActivityDetail.this, new FindListener<Activitymen>() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO �Զ����ɵķ������
					Log.i("fail", arg0+arg1);
				}

				@Override
				public void onSuccess(List<Activitymen> activitymens) {
					// TODO �Զ����ɵķ������
					Log.i("success---226", activitymens.get(0).getUserid()+"");
					button1.setText("�����ɹ�");
					button1.setEnabled(false);
				}
			});
		}
		//����
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				new AlertDialog.Builder(ActivityDetail.this)
				.setTitle("����")
				.setMessage("ȷ��Ҫ������")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO �Զ����ɵķ������
						Activitymen activitymen = new Activitymen();
						activitymen.setUserid(userid);
						activitymen.setActivityid(activity_id);
						activitymen.save(ActivityDetail.this, new SaveListener() {
							
							@Override
							public void onSuccess() {
								// TODO �Զ����ɵķ������
								Toast.makeText(ActivityDetail.this, "�����ɹ���", Toast.LENGTH_SHORT).show();
								Map<String, Object> map = new HashMap<String, Object>();
								button1.setText("�����ɹ���");
								map.put("member_name", username);
								mData.add(map);
								adapter.notifyDataSetChanged();
								activity_pneed.setText(friends.getCount()+"");
								button1.setEnabled(false);
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO �Զ����ɵķ������
								if(userid==0){
									Toast.makeText(ActivityDetail.this, "����ʧ�ܣ����ȵ�¼", Toast.LENGTH_SHORT).show();
									Log.i("ActivityDetail---106---fail", arg0+arg1);
								}
							}
						});
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO �Զ����ɵķ������
						dialog.cancel();
					}
				}).show();
			}
		});

	}
    Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO �Զ����ɵķ������
				switch (msg.what) {
				case 1:
					mData = (List<Map<String,Object>>)msg.obj;
					adapter.notifyDataSetChanged();
					activity_pneed.setText(friends.getCount()+"");
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};


}
