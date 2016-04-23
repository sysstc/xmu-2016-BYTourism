package com.ezz.bytourism1;

import java.util.List;
import java.util.Map;

import com.ezz.bean.Friend;
import com.ezz.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyButtonAdapter extends BaseAdapter  {
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mData;
	private Context context;
	private int userid;
	private ListView friendlist;
	private ViewHolder holder;

	
	public MyButtonAdapter(Context context,List<Map<String, Object>> mData,int userid,ListView friendlist){
		this.mInflater = LayoutInflater.from(context);
		this.mData = mData;
		this.userid = userid;
		this.context = context;
		this.friendlist = friendlist;
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		holder = null;
		if(convertView ==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_select, null);
			holder.member_name =(TextView)convertView.findViewById(R.id.member_name);
			holder.member_add = (ImageButton) convertView.findViewById(R.id.member_add);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.member_name.setText((String)mData.get(position).get("member_name"));
		holder.member_add.setTag(position);
		
		
		Log.i("75---MyButtonAdapter---", position+"");
		
		BmobQuery<User> query_user1 = new BmobQuery<User>();
		query_user1.addWhereEqualTo("username",(String)mData.get(position).get("member_name"));
		query_user1.findObjects(context, new FindListener<User>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("83----MyButtonAdapter---fail", arg0+arg1);
			}
			@Override
			public void onSuccess(List<User> users) {
				// TODO 自动生成的方法存根
				int friend_id = users.get(0).getId();
				BmobQuery<Friend> query_friend = new BmobQuery<Friend>();
				query_friend.addWhereEqualTo("oneid", userid);
				query_friend.addWhereEqualTo("twoid", friend_id);
				Log.i("userid-----91----MyButtonAdapter",userid+"");
				Log.i("friendid-----92----MyButtonAdapter", friend_id+"");
				query_friend.findObjects(context, new FindListener<Friend>() {
					@Override
					public void onError(int arg0, String arg1) {
						// TODO 自动生成的方法存根
						Log.i("96---MyButtonAdapter---fail", arg1+arg0);
					}
					@Override
					public void onSuccess(List<Friend> friends) {
						// TODO 自动生成的方法存根
						Log.i("success---101", friends.get(0).getTwoid()+"");
						final int tag = (Integer)holder.member_add.getTag();
						View view = friendlist.getChildAt(position);
						ImageButton imagebtn1 = (ImageButton) view.findViewById(R.id.member_add);
						imagebtn1.setImageResource(R.drawable.accept);
						imagebtn1.setEnabled(false);
						
					}
					
				});
			}
		});
		
		
		
		holder.member_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				
				// TODO 自动生成的方法存根
				Log.i("MyButtonAdapter---62",(String)mData.get(position).get("member_name"));
				BmobQuery<User> query_user = new BmobQuery<User>();
				query_user.addWhereEqualTo("username",(String)mData.get(position).get("member_name") );
				query_user.findObjects(context, new FindListener<User>() {
					
					@Override
					public void onSuccess(List<User> users) {
						// TODO 自动生成的方法存根
						for(User user:users){
							
							int friendid = user.getId();
							final Friend friend = new Friend();
							friend.setOneid(userid);
							friend.setTwoid(friendid);
							if(userid!=friendid&&userid!=0){
								new AlertDialog.Builder(context)
								.setTitle("添加好友")
								.setMessage("是否要添加为好友？")
								.setPositiveButton("添加",new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO 自动生成的方法存根
										friend.save(context,new SaveListener() {
											@Override
											public void onSuccess() {
												// TODO 自动生成的方法存根
												Toast.makeText(context, "添加好友成功！", Toast.LENGTH_SHORT).show();
												//holder.member_add.setImageResource(R.drawable.accept);
												Log.i("155---success",position+"");
												final int tag = (Integer)v.getTag();
											
												if(tag==position){
													((ImageButton)v).setImageResource(R.drawable.accept);
													((ImageButton)v).setEnabled(false);
												}
											}
											
											@Override
											public void onFailure(int arg0, String arg1) {
												// TODO 自动生成的方法存根
												Log.i("fail---107", arg0+arg1);
												
											}
										});
									}
								}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO 自动生成的方法存根
										dialog.cancel();
									}
								}).show();
								
							}else{
								Toast.makeText(context, "添加好友失败，请检查是否登录，或者是否添加自己为好友", Toast.LENGTH_SHORT).show();
							}
						}
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO 自动生成的方法存根
						Log.i("fail---84", arg0+arg1);
					}
				});
			}
		});
		return convertView;
	}
	public final class ViewHolder{
		public TextView member_name;
		public ImageButton member_add;
	}
	public void add_friend(int position){
		
	}
}
