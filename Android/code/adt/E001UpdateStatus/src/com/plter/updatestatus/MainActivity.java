package com.plter.updatestatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plter.weibo.sdk.extended.CBGetStatusesAPI;
import com.plter.weibo.sdk.extended.PWeibo;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

public class MainActivity extends Activity {

	
	private EditText etInput;
	private Button btnUpdateStatus;
	private PWeibo pWeibo;
	private static final int SUC=1;
	private static final int FAIL=2;
	private static Context __context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		__context = this;
		
		
		pWeibo = new PWeibo(this, Config.APP_KEY, Config.APP_SEC, Config.REDIRECT_URL, Config.SCOPE);
		
		etInput = (EditText) findViewById(R.id.etInput);
		btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
		
		btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pWeibo.getStatusesAPI(new CBGetStatusesAPI() {
					
					@Override
					public void suc(StatusesAPI arg0) {
						
						String content = etInput.getText().toString();
						
						arg0.update(content, "0.0", "0.0", new RequestListener() {
							
							@Override
							public void onIOException(IOException arg0) {
								handler.sendEmptyMessage(FAIL);
							}
							
							@Override
							public void onError(WeiboException arg0) {
								handler.sendEmptyMessage(FAIL);
							}
							
							@Override
							public void onComplete4binary(ByteArrayOutputStream arg0) {
							}
							
							@Override
							public void onComplete(String arg0) {
								handler.sendEmptyMessage(SUC);
							}
						});
					}
					
					@Override
					public void fail() {
						handler.sendEmptyMessage(FAIL);
					}
				});
			}
		});
	}
	
	private static final Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUC:
				Toast.makeText(__context, "成功", Toast.LENGTH_SHORT).show();
				break;
			case FAIL:
				Toast.makeText(__context, "失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
