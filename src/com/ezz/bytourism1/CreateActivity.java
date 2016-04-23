package com.ezz.bytourism1;

import java.util.Calendar;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;

import com.ezz.bean.Activities;
import com.ezz.bytourism1.R.id;

import android.R.integer;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends BaseActivity{
	private int userid;
	private String username;
	private EditText edit_start_data;
	private ImageButton button_start_data1;
	private EditText edit_start_time1;
	private ImageButton button_start_time1;
	private EditText edit_end_data1;
	private ImageButton button_end_data1;
	private EditText edit_end_time1;
	private ImageButton button_end_time1;
	private EditText edit_address;
	private EditText edit_type;
	private EditText edit_introduce;
	private EditText activity_name1;
	private Button activity_launcher;
	private EditText edit_num1;
	
	private Calendar cal;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private String startDate;
	private String activity_title;
	private String startTime;
	private String startStr;
	private int activity_num;
	private String activityaddress;
	private String activitytype;
	private String activitydescribe;
	private int sight_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		userid = getPreferenceId();
		username = getPreferenceName();
		Intent searchIntent = getIntent();
		sight_id = Integer.valueOf(searchIntent.getStringExtra("sight_id"));
		Log.i("CreateActivity----63", sight_id+"123");
		
		edit_start_data = (EditText) findViewById(R.id.edit_start_data);
		button_start_data1 = (ImageButton) findViewById(R.id.button_start_data1);
		edit_start_time1 = (EditText) findViewById(R.id.edit_start_time1);
		button_start_time1 = (ImageButton) findViewById(R.id.button_start_time1);
		edit_end_data1 = (EditText) findViewById(R.id.edit_end_data1);
		button_end_data1 = (ImageButton) findViewById(R.id.button_end_data1);
		edit_end_time1 = (EditText) findViewById(R.id.edit_end_time1);
		button_end_time1 = (ImageButton) findViewById(R.id.button_end_time1);
		edit_address = (EditText) findViewById(R.id.edit_address);
		edit_type = (EditText) findViewById(R.id.edit_type);
		activity_name1 = (EditText) findViewById(R.id.activity_name1);
		edit_introduce = (EditText) findViewById(R.id.edit_introduce);
		activity_launcher = (Button) findViewById(R.id.activity_launcher);
		edit_num1 = (EditText) findViewById(R.id.edit_num1);
		
		//获取日历对象
		cal =Calendar.getInstance();
		//获取年月日时分秒
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);//获取的是当月的信息
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		if(userid==0){
			activity_launcher.setEnabled(false);
		}
		button_start_data1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				new DatePickerDialog(CreateActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO 自动生成的方法存根
						edit_start_data.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, year, month, day).show();

			}
		});
		button_end_data1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				new DatePickerDialog(CreateActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO 自动生成的方法存根
						edit_end_data1.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, year, month, day).show();
			}
		});
		button_start_time1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				new TimePickerDialog(CreateActivity.this, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO 自动生成的方法存根
						edit_start_time1.setText(hourOfDay+":"+minute);
					}
				}, hour, minute, true).show();
			}
		});
		button_end_time1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				new TimePickerDialog(CreateActivity.this, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO 自动生成的方法存根
						edit_end_time1.setText(hourOfDay+":"+minute);
					}
				}, hour, minute, true).show();
			}
		});
		activity_launcher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startDate = edit_start_data.getText().toString();
				startTime = edit_start_time1.getText().toString();
				startStr = startDate+" "+startTime+":00";
				activity_title = activity_name1.getText().toString();
				if(!edit_num1.getText().toString().equals(""))
					activity_num = Integer.parseInt(edit_num1.getText().toString());
				else{
					activity_num = 0;
				}
				activityaddress = edit_address.getText().toString();
				activitytype = edit_type.getText().toString();
				activitydescribe = edit_introduce.getText().toString();
			if(!edit_num1.getText().toString().equals("")&&!activity_title.equals("")&&!activityaddress.equals("")&&!activitytype.equals("")&&!activitydescribe.equals("")){
				Log.i("162-----CreateActivity", startStr+" "+activity_num+" "+activityaddress+" "+activitytype+" "+activitydescribe);
				Activities activity = new Activities();
				activity.setPtime(startStr);
				activity.setPneed_count(activity_num);
				activity.setAddress(activityaddress);
				activity.setType(activitytype);
				activity.setDescribe(activitydescribe);
				activity.setTitle(activity_title);
				activity.setPstate(0);
				activity.setScenicid(sight_id);
				activity.setPcount(0);
				activity.save(CreateActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO 自动生成的方法存根
						Toast.makeText(CreateActivity.this, "发布活动成功！", Toast.LENGTH_SHORT).show();
						activity_launcher.setText("活动已经成功发布");
						activity_launcher.setEnabled(false);
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO 自动生成的方法存根
						Log.i("fail---192",arg0+arg1);
					}
				});
				}else{
					Toast.makeText(CreateActivity.this, "发布失败，请检查是否填完整了", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
