package com.ezz.bytourism1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 37492 on 2016/4/4.
 */
public class Personal_centerActivity extends BaseActivity {
    TextView username,userid;
    RelativeLayout re,yep;
    ImageButton buttonpath;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        username = (TextView)findViewById(R.id.nameview);
        userid = (TextView)findViewById(R.id.idview);
        re = (RelativeLayout)findViewById(R.id.relative4);
        yep = (RelativeLayout)findViewById(R.id.yep);
        buttonpath = (ImageButton) findViewById(R.id.buttonpath);
        buttonpath.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(Personal_centerActivity.this,MyCollectActivity.class);
				startActivity(intent);
			}
		});
       updateIn();

    }
    public void signout(View view){
        deletePreference();
        updateIn();
    }
    public void trunToLogin(View view){
        Intent intent = new Intent(Personal_centerActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void turnToRegist(View view){
        Intent intent = new Intent(Personal_centerActivity.this,RegistActivity.class);
        startActivity(intent);
    }
    public void returnHome(View view){
        Intent intent = new Intent(Personal_centerActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        Toast.makeText(Personal_centerActivity.this,"here is intent", Toast.LENGTH_LONG).show();
        updateIn();
    }
    public void updateIn(){
        if(!getPreferenceName().equals("")){
           // Toast.makeText(Personal_centerActivity.this, getResources().getString(R.string.signin)+getPreferenceName(), Toast.LENGTH_LONG).show();
            username.setText(getPreferenceName());
            userid.setText("365354"+getPreferenceId());
            yep.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
        }
        else{
            //Toast.makeText(Personal_centerActivity.this, getResources().getString(R.string.signout)+getPreferenceName(), Toast.LENGTH_LONG).show();
            username.setText(getResources().getString(R.string.signout));
            userid.setText(getResources().getString(R.string.signout));
            yep.setVisibility(View.VISIBLE);
            re.setVisibility(View.GONE);
        }
    }


}
