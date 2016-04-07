package com.ezz.bytourism1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ezz.bean.Collect;
import com.ezz.bean.Scenic;
import com.ezz.bean.Scenicroute;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * Created by 37492 on 2016/4/7.
 */
public class RoutesCollectActivity extends BaseActivity{
	private ImageButton btn_back_re;
	private ListView routeslist;
	private Integer userid;
	private SimpleAdapter simpleAdapter;
	private List<Map<String,Object>> dataList;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dataList = new ArrayList<Map<String, Object>>();
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			default:
				break;
			}
			}
		};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routescollect);
        
        userid = getPreferenceId();
        btn_back_re = (ImageButton) findViewById(R.id.btn_back_re);
        dataList = new ArrayList<Map<String, Object>>();
        
        if(userid<=0){
        	Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }
        btn_back_re.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(RoutesCollectActivity.this,Personal_centerActivity.class);
				startActivity(intent);
			}
		});
        BmobQuery<Collect> query_city = new BmobQuery<Collect>();
        query_city.addWhereEqualTo("userid",userid );
        query_city.addWhereEqualTo("type", 2);
        query_city.findObjects(RoutesCollectActivity.this, new FindListener<Collect>() {
			@Override
			public void onSuccess(List<Collect> collects) {
				// TODO 自动生成的方法存根
				for(Collect collect:collects){
					int id = collect.getCid();
					BmobQuery<Scenicroute> scenicroute_query = new BmobQuery<Scenicroute>();
					scenicroute_query.addWhereEqualTo("id", id);
					scenicroute_query.findObjects(RoutesCollectActivity.this, new FindListener<Scenicroute>() {
						@Override
						public void onError(int arg0, String arg1) {
							// TODO 自动生成的方法存根
							Log.i("fail---70", arg0+arg1);
						}
						@Override
						public void onSuccess(List<Scenicroute> scenicroutes) {
							// TODO 自动生成的方法存根
							if(scenicroutes.get(0).getOne()!=0){
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",scenicroutes.get(0).getOne() );
								query_scenic.findObjects(RoutesCollectActivity.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail---70", arg0+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> scenics) {
										// TODO 自动生成的方法存根
										Message msg = new Message();
										msg.what = 1;
										msg.obj = scenics.get(0).getScenicname();
										handler.sendMessage(msg);
									}
								});
							}
							if(scenicroutes.get(0).getTwo()!=0){
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",scenicroutes.get(0).getTwo() );
								query_scenic.findObjects(RoutesCollectActivity.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail---70", arg0+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> scenics) {
										// TODO 自动生成的方法存根
										Message msg = new Message();
										msg.what = 2;
										msg.obj = scenics.get(0).getScenicname();
										handler.sendMessage(msg);
									}
								});
							}
							if(scenicroutes.get(0).getThree()!=0){
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",scenicroutes.get(0).getThree() );
								query_scenic.findObjects(RoutesCollectActivity.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail---70", arg0+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> scenics) {
										// TODO 自动生成的方法存根
										Message msg = new Message();
										msg.what = 3;
										msg.obj = scenics.get(0).getScenicname();
										handler.sendMessage(msg);
									}
								});
							}
							if(scenicroutes.get(0).getFour()!=0){
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",scenicroutes.get(0).getFour() );
								query_scenic.findObjects(RoutesCollectActivity.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail---70", arg0+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> scenics) {
										// TODO 自动生成的方法存根
										Message msg = new Message();
										msg.what = 4;
										msg.obj = scenics.get(0).getScenicname();
										handler.sendMessage(msg);
									}
								});
							}
							if(scenicroutes.get(0).getFive()!=0){
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",scenicroutes.get(0).getFive());
								query_scenic.findObjects(RoutesCollectActivity.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO 自动生成的方法存根
										Log.i("fail---70", arg0+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> scenics) {
										// TODO 自动生成的方法存根
										Message msg = new Message();
										msg.what = 5;
										msg.obj = scenics.get(0).getScenicname();
										handler.sendMessage(msg);
									}
								});
							}
						}
						
					});
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				// TODO 自动生成的方法存根
				Log.i("fail!!---60", arg0+arg1);
			}
		});
    }
	
}
