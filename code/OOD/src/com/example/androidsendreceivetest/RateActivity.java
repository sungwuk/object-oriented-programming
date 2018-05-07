package com.example.androidsendreceivetest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateActivity extends Activity {
	static final int DATE_DIALOG_ID = 0;
	static final int PICK_DATE_REQUEST = 1;

	private float rate;
	private String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rate);

		Intent intent = getIntent();
		user = intent.getExtras().getString("user");

		RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);
		rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				rate = rating;
			}
		});

		Button btOk = (Button) findViewById(R.id.btOk);

		btOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rating()) {
					Intent intent = new Intent(RateActivity.this, MainActivity.class);
					intent.putExtra("user", user);
					startActivityForResult(intent, PICK_DATE_REQUEST);
				} else {
					Toast.makeText(getApplicationContext(), "평가할 수 있는 이전 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private boolean rating() {
		String URL = "http://52.78.78.71:8080/MyServer/selectUser.jsp";

		List<NameValuePair> timeData = new ArrayList<NameValuePair>();

		timeData.add(new BasicNameValuePair("user", this.user));
		timeData.add(new BasicNameValuePair("score", "" + this.rate));

		DefaultHttpClient client = new DefaultHttpClient();

		try {
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			HttpPost post = new HttpPost(URL);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(timeData, "UTF-8");
			post.setEntity(entity);
			client.execute(post);

			return true;
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			client.getConnectionManager().shutdown();
			return false;
		}
	}
}
