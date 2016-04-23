package com.ezz.bytourism1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.a.a.a.Tempest;
import com.ezz.bean.City;
import com.ezz.bean.Collect;
import com.ezz.bean.Scenic;
import com.ezz.bean.Scenicroute;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AllSightDetail extends BaseActivity implements OnClickListener,OnItemClickListener{
	private Button btn_back;
	private AutoCompleteTextView edit_Search1;
	private List<String> city_list;
 	private ArrayAdapter<String> adapter;
 	private ListView city_listview;
 	public SimpleAdapter sadapter;
 	private List<Map<String,Object>> dataList;
 	private Button find_btn;
 	private String city_name;
 	private TextView route_describe;
 	private int rid;
 	private int time;
 	private ImageButton collectButton;
 	private Integer userid ;
 	private int flag = 0;
 	private List<String> imageUrls;
 	private static final ThreadLocal re = new ThreadLocal();
 	int a = 0;
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		// TODO �Զ����ɵķ������
 		super.onCreate(savedInstanceState);
 		
 		setContentView(R.layout.allsightdetail);
 		Bmob.initialize(AllSightDetail.this, "a1a4ff643e92be99bb8649e33589c596");
 		Intent searchIntent = getIntent();
 		userid = getPreferenceId();
 		rid =0;
 		city_name = searchIntent.getStringExtra("city_name");
 		rid = Integer.parseInt(searchIntent.getStringExtra("rid"));
 		Toast.makeText(AllSightDetail.this, city_name+" "+rid, Toast.LENGTH_SHORT).show();
 		imageUrls = new ArrayList<String>();
 	
 		dataList = new ArrayList<Map<String, Object>>();
 		
 		edit_Search1 = (AutoCompleteTextView) findViewById(R.id.edit_Search1);
 		edit_Search1.setText(city_name);
 		find_btn = (Button) findViewById(R.id.find_btn);
 		btn_back = (Button) findViewById(R.id.btn_back);
 		
 		if(flag==0&&userid>0&&rid>0){
 			BmobQuery<Collect> query_collect = new BmobQuery<Collect>();
 			query_collect.addWhereEqualTo("cid",rid);
 			query_collect.addWhereEqualTo("userid", userid);
 			query_collect.addWhereEqualTo("type", 2);
 			query_collect.findObjects(AllSightDetail.this, new FindListener<Collect>() {
				@Override
				public void onError(int arg0, String arg1) {
					// TODO �Զ����ɵķ������
					Log.i("fail--96",arg0+arg1);
				}

				@Override
				public void onSuccess(List<Collect> collects) {
					// TODO �Զ����ɵķ������
					Log.i("tag",collects.get(0).toString());
					collectButton.setImageResource(R.drawable.star_on);
					flag=2;
				}
			});
 		}
 		
 		collectButton = (ImageButton) findViewById(R.id.collectButton);
 		if(userid<=0){
 			Toast.makeText(AllSightDetail.this, "���ȵ�¼�������޷�ʹ���ղغ����Թ��ܣ�",Toast.LENGTH_SHORT ).show();
 			collectButton.setEnabled(false);
 		}
 		collectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if(flag==0){
				Collect collect =  new Collect();
				collect.setUserid(userid);
				collect.setCid(rid);//�����cid��ʾ���Ǿ���·�ߵ�id
				collect.setType(2);
				collect.save(AllSightDetail.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO �Զ����ɵķ������
						Toast.makeText(AllSightDetail.this, "�ղ�·�߳ɹ�",Toast.LENGTH_SHORT ).show();
						collectButton.setImageResource(R.drawable.star_on);
						flag = 2;
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO �Զ����ɵķ������
						
					}
				});
				}
				if(flag==2){
					BmobQuery<Collect> bmobQuery = new BmobQuery<Collect>();
					bmobQuery.addWhereEqualTo("type",2 );
					bmobQuery.addWhereEqualTo("cid", rid);
					bmobQuery.addWhereEqualTo("userid", userid);
					bmobQuery.findObjects(AllSightDetail.this, new FindListener<Collect>() {
						@Override
						public void onError(int arg0, String arg1) {
							// TODO �Զ����ɵķ������
							Log.i("fail--152",arg0+arg1);
						}
						@Override
						public void onSuccess(List<Collect> collects) {
							// TODO �Զ����ɵķ������
							String objectid = collects.get(0).getObjectId();
							Collect collect = new Collect();
							collect.delete(AllSightDetail.this,objectid,new DeleteListener() {
								@Override
								public void onSuccess() {
									// TODO �Զ����ɵķ������
									Toast.makeText(AllSightDetail.this, "ȡ���ղ�",Toast.LENGTH_SHORT ).show();
									collectButton.setImageResource(R.drawable.collect);
									flag = 0;
								}
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO �Զ����ɵķ������
									Log.i("fail--169",arg0+arg1);
								}
							});
						}
					});
				}
			}
		});
 		
 		
 		city_listview = (ListView) findViewById(R.id.city_listview);
 		city_list = new ArrayList<String>();
 	
 		route_describe = (TextView) findViewById(R.id.route_describe);
 		if(rid>0){
 			BmobQuery<Scenicroute> rdescribe_query = new BmobQuery<Scenicroute>();
 			rdescribe_query.addWhereEqualTo("id", rid);
 			rdescribe_query.findObjects(AllSightDetail.this, new FindListener<Scenicroute>() {
				@Override
				public void onError(int arg0, String arg1) {
					// TODO �Զ����ɵķ������
					Log.i("fail--91", arg0+arg1);
				}
				@Override
				public void onSuccess(List<Scenicroute> scenicroutes) {
					// TODO �Զ����ɵķ������
					route_describe.setText(scenicroutes.get(0).getDescribe());
					collectButton.setVisibility(View.VISIBLE);
				}
			});
 		}else{
 			route_describe.setText("��ǰģʽ����ѡ��·��");
 			collectButton.setVisibility(View.GONE);
 		}
 		/*
 		 * 
 		 * 
 		 * 
 		 * 
 		 * route describe
 		 * 
 		 * 
 		 * 
 		 * 
 		 * 
 		 * 
 		 * */

 		city_list.add("����");
 		city_list.add("�Ϻ�");
 		city_list.add("����");
 		city_list.add("����"); 
 		city_list.add("����");
 		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, city_list);
 		edit_Search1.setAdapter(adapter);
 		
 		find_btn.setOnClickListener(this);
 		
 		btn_back.setOnClickListener(new OnClickListener() {  
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������     
				
				AllSightDetail.this.finish();
			}
		});
 		dataList = new ArrayList<Map<String, Object>>();
		getData();
		sadapter = new MySimpleAdapter(AllSightDetail.this, dataList, R.layout.listview_item,
 				new String[]{"sight_view","sight_name","sight_type","sight_price","sight_avggrade"} ,
 				new int[]{R.id.sight_view,R.id.sight_name,R.id.sight_type,R.id.sight_price,R.id.sight_avggrade});
		
		 city_listview.setAdapter(sadapter);
		 city_listview.setOnItemClickListener(this);
 	}
 	  public boolean onKeyDown(int keyCode, KeyEvent event) {
 	    	// TODO �Զ����ɵķ������
 	    	if(keyCode==KeyEvent.KEYCODE_BACK){
 	    		AllSightDetail.this.finish();
 	 	    	return true;
 	    	}
 	    	return super.onKeyDown(keyCode, event);
 	  }
 	Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO �Զ����ɵķ������
				Object scienc;
				switch (msg.what) {
				case 1:
					 scienc=msg.obj;
					 Map<String, Object> a = (Map<String, Object>)scienc;
					 dataList.add(a);		 
  				     sadapter.notifyDataSetChanged();
  				     
  				     break;
				case 2:
				     scienc=msg.obj;
					 Map<String, Object> b = (Map<String, Object>)scienc;
					 dataList.add(b);
					 sadapter.notifyDataSetChanged();

					break;
				case 3:
					scienc=msg.obj;
					 Map<String, Object> c = (Map<String, Object>)scienc;
					 dataList.add(c);
					 sadapter.notifyDataSetChanged();

				
					break;
				case 4:
					scienc=msg.obj;
					 Map<String, Object> d = (Map<String, Object>)scienc;
					 dataList.add(d);

					 sadapter.notifyDataSetChanged();
					break;
				case 5:
					scienc=msg.obj;
					 Map<String, Object> e = (Map<String, Object>)scienc;
					 dataList.add(e);
					 sadapter.notifyDataSetChanged();
					break;
				case 0:
					 Log.i("Image Src", msg.obj.toString());
					 imageUrls.add(msg.obj.toString());
					break;
				}
				super.handleMessage(msg);
			}
		};
	private List<Map<String, Object>> getData(){
		dataList = new ArrayList<Map<String, Object>>();
		BmobQuery<City> query_city = new BmobQuery<City>();
	
		city_name = edit_Search1.getText().toString();
		
		query_city.addWhereEqualTo("cityname", city_name);
		
		query_city.findObjects(this, new FindListener<City>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO �Զ����ɵķ������
		//		akeText(AllSightDetail.this,"fail136"+ arg0+" "+arg1,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<City> citysList) {
				// TODO �Զ����ɵķ������

				
				Map<String,Object> city_map = new HashMap<String,Object>();
				
				int cityid = citysList.get(0).getId();//city���е�id
				BmobQuery<Scenicroute> query_sroute = new BmobQuery<Scenicroute>();
				query_sroute.addWhereEqualTo("cityid", cityid);
				if(rid>0)
					query_sroute.addWhereEqualTo("id", rid);
		//		Toast.makeText(AllSightDetail.this,cityid+"====>187",Toast.LENGTH_SHORT).show();
				
				query_sroute.findObjects(AllSightDetail.this, new FindListener<Scenicroute>() {
					
					@Override
					public void onSuccess(List<Scenicroute> sroutes) {
						// TODO �Զ����ɵķ������
						int l =sroutes.size();
						for(int i = 0;i<l;i++){//�ҵ���Scenicroute�������·��
							time=0;
							Scenicroute sroute = sroutes.get(0);
							if(sroute.getOne()!=0){
								time++;
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id", sroute.getOne());
								query_scenic.findObjects(AllSightDetail.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO �Զ����ɵķ������
									}
									@Override
									public void onSuccess(List<Scenic> ascenic) {
										// TODO �Զ����ɵķ������
										Map<String,Object> city_map = new HashMap<String,Object>();
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										city_map.put("sight_name",ascenic.get(0).getScenicname());
										city_map.put("sight_type",ascenic.get(0).getScenictype());
										city_map.put("sight_price",ascenic.get(0).getScenicprice());
										city_map.put("sight_avggrade",ascenic.get(0).getAvggrade());
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										
										Message msg = new Message();
										msg.what = 1;
										msg.obj = city_map;
										handler.sendMessage(msg);
										
										Message msg1 = new Message();
										msg1.what = 0;
										msg1.obj = ascenic.get(0).getScenicview();
										handler.sendMessage(msg1);
				//						Toast.makeText(AllSightDetail.this,city_map.get("sight_name")+"",Toast.LENGTH_SHORT).show();
									}
								});
								
							}
							if(sroute.getTwo()!=0){
								time++;
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id", sroute.getTwo());
								query_scenic.findObjects(AllSightDetail.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO �Զ����ɵķ������
										Log.i("fail!!!!!!!!!183", arg0+" "+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> ascenic) {
										// TODO �Զ����ɵķ������
										Map<String,Object> city_map = new HashMap<String,Object>();
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										city_map.put("sight_name",ascenic.get(0).getScenicname());
										city_map.put("sight_type",ascenic.get(0).getScenictype());
										city_map.put("sight_price",ascenic.get(0).getScenicprice());
										city_map.put("sight_avggrade",ascenic.get(0).getAvggrade());
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										
										Message msg = new Message();
										msg.what = 2;
										msg.obj = city_map;
										handler.sendMessage(msg);
										
										
										Message msg1 = new Message();
										msg1.what = 0;
										msg1.obj = ascenic.get(0).getScenicview();
										handler.sendMessage(msg1);
			//							Toast.makeText(AllSightDetail.this,city_map.get("sight_name")+"",Toast.LENGTH_SHORT).show();
										}
								});
								
							}
							if(sroute.getThree()!=0){
								time++;
								BmobQuery< Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id",sroute.getThree());
			//					Toast.makeText(AllSightDetail.this, sroute.getThree()+"", Toast.LENGTH_SHORT).show();
								query_scenic.findObjects(AllSightDetail.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO �Զ����ɵķ������
										Log.i("tag!!!!!!", "209 "+arg0+" "+arg1);
									}
									public void onSuccess(List<Scenic> scenic) {
										// TODO �Զ����ɵķ������
						//				Toast.makeText(AllSightDetail.this, scenic.size()+"", Toast.LENGTH_SHORT).show();
										Map<String,Object> city_map = new HashMap<String,Object>();
										city_map.put("sight_view", scenic.get(0).getScenicview());
										city_map.put("sight_name", scenic.get(0).getScenicname());
										city_map.put("sight_type", scenic.get(0).getScenictype());
										city_map.put("sight_price", scenic.get(0).getScenicprice());
										city_map.put("sight_avggrade", scenic.get(0).getAvggrade());
										city_map.put("sight_view", scenic.get(0).getScenicview());
										
										Message msg = new Message();
										msg.what = 3;
										msg.obj = city_map;
										handler.sendMessage(msg);
										
										Message msg1 = new Message();
										msg1.what = 0;
										msg1.obj = scenic.get(0).getScenicview();
										handler.sendMessage(msg1);
				//						Toast.makeText(AllSightDetail.this,city_map.get("sight_name")+"",Toast.LENGTH_SHORT).show();
										}
								
								});
								
							}
							if(sroute.getFour()!=0){
								time++;
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id", sroute.getFour());
								query_scenic.findObjects(AllSightDetail.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO �Զ����ɵķ������
										Log.i("fail!!!!!!!!!236", arg0+" "+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> ascenic) {
										// TODO �Զ����ɵķ������
										Map<String,Object> city_map = new HashMap<String,Object>();
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										city_map.put("sight_name", ascenic.get(0).getScenicname());
										city_map.put("sight_type", ascenic.get(0).getScenictype());
										city_map.put("sight_price", ascenic.get(0).getScenicprice());
										city_map.put("sight_avggrade", ascenic.get(0).getAvggrade());
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										
										Message msg = new Message();
										msg.what = 4;
										msg.obj = city_map;
										handler.sendMessage(msg);
										
										Message msg1 = new Message();
										msg1.what = 0;
										msg1.obj = ascenic.get(0).getScenicview();
										handler.sendMessage(msg1);
			//							Toast.makeText(AllSightDetail.this,city_map.get("sight_name")+"",Toast.LENGTH_SHORT).show();
										}
								});
								
							}
							if(sroute.getFive()!=0){
								time++;
								BmobQuery<Scenic> query_scenic = new BmobQuery<Scenic>();
								query_scenic.addWhereEqualTo("id", sroute.getFive());
								query_scenic.findObjects(AllSightDetail.this, new FindListener<Scenic>() {
									@Override
									public void onError(int arg0, String arg1) {
										// TODO �Զ����ɵķ������
										Log.i("fail!!!!!!!!!262", arg0+" "+arg1);
									}
									@Override
									public void onSuccess(List<Scenic> ascenic) {
										// TODO �Զ����ɵķ������
										Map<String,Object> city_map = new HashMap<String,Object>();
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										city_map.put("sight_name",ascenic.get(0).getScenicname());
										city_map.put("sight_type",ascenic.get(0).getScenictype());
										city_map.put("sight_price",ascenic.get(0).getScenicprice());
										city_map.put("sight_avggrade",ascenic.get(0).getAvggrade());
										city_map.put("sight_view", ascenic.get(0).getScenicview());
										
										Message msg = new Message();
										msg.what = 5;
										msg.obj = city_map;
										handler.sendMessage(msg);
									
										Message msg1 = new Message();
										msg1.what = 0;
										msg1.obj = ascenic.get(0).getScenicview();
										handler.sendMessage(msg1);
		//								Toast.makeText(AllSightDetail.this,city_map.get("sight_name")+"",Toast.LENGTH_SHORT).show();
									}
								});
								
							}
						
						}
						
					}
					@Override
					public void onError(int arg0, String arg1) {
						// TODO �Զ����ɵķ������
						Log.i("fail!!!!!!!!!284", arg0+" "+arg1);
					}
				});
			}
			
		});		
		
		return dataList;
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		dataList = new ArrayList<Map<String, Object>>();
		route_describe.setText("��ǰģʽ����ѡ��·��");
		collectButton.setVisibility(View.GONE);
		rid = 0;
		getData();
		sadapter = new MySimpleAdapter(AllSightDetail.this, dataList, R.layout.listview_item,
 				new String[]{"sight_view","sight_name","sight_type","sight_price","sight_avggrade","sight_view"} ,
 				new int[]{R.id.sight_view,R.id.sight_name,R.id.sight_type,R.id.sight_price,R.id.sight_avggrade,R.id.sight_view});
		
		 city_listview.setAdapter(sadapter);
		 city_listview.setOnItemClickListener(this);
		 for(int i = 0;i<imageUrls.size();i++){
			Log.i("imagesrc", imageUrls.get(i));
		 }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO �Զ����ɵķ������
		final Map<String, Object> text= (Map<String, Object>)city_listview.getItemAtPosition(position);
		Toast.makeText(this, "position = "+position+" text="+text.get("sight_name")+"",Toast.LENGTH_LONG).show();
		
	
		final String sight_name =  text.get("sight_name").toString();
		
		BmobQuery<Scenic> query_scenicid = new BmobQuery<Scenic>();
		query_scenicid.addWhereEqualTo("scenicname",sight_name);
		Log.i("fail---428",sight_name);
		query_scenicid.findObjects(AllSightDetail.this,new FindListener<Scenic>(){

			@Override
			public void onError(int arg0, String arg1) {
				// TODO �Զ����ɵķ������
				Log.i("fail-433",arg0+arg1);
			}

			@Override
			public void onSuccess(List<Scenic> scenic) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(AllSightDetail.this,SightDetail.class);
				int sight_id = scenic.get(0).getId();
				intent.putExtra("sight_id",String.valueOf(sight_id));
				intent.putExtra("sight_name",sight_name);
				intent.putExtra("sight_type", text.get("sight_type").toString());
				intent.putExtra("sight_price", text.get("sight_price").toString());
				Log.i("tag!!!----445",sight_id+"    "+sight_name);
				startActivity(intent);
			}
			
		});
		
		
		
	}
}

