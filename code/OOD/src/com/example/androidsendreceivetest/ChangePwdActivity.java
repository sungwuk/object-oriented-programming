package com.example.androidsendreceivetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePwdActivity extends Activity {

	EditText pwInput1, pwInput2;
	Boolean checkPwd;

	private String user;
	private String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);

		Intent intent = getIntent();
		user = intent.getExtras().getString("user");

		pwInput1 = (EditText) findViewById(R.id.pwinput1);
		pwInput2 = (EditText) findViewById(R.id.pwinput2);
		Button change = (Button) findViewById(R.id.change);

		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if autoLogin unChecked
				String password1 = pwInput1.getText().toString();
				String password2 = pwInput2.getText().toString();

				if (password1.equals(password2)) {
					pwd = password1;
					if (SendByHttp()) {
						Toast.makeText(ChangePwdActivity.this, "비밀번호를 변경하였습니다.", Toast.LENGTH_LONG).show();
						Toast.makeText(ChangePwdActivity.this, "다시 로그인 해주세요.", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(ChangePwdActivity.this, "비밀번호를 변경하는데 실패했습니다.", Toast.LENGTH_LONG).show();
						Toast.makeText(ChangePwdActivity.this, "다시 변경 해주세요.", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(ChangePwdActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
					Toast.makeText(ChangePwdActivity.this, "다시 입력해주세요.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private boolean SendByHttp() {
		String URL = "http://52.78.78.71:8080/MyServer/changePassword.jsp";

		List<NameValuePair> timeData = new ArrayList<NameValuePair>();

		timeData.add(new BasicNameValuePair("user", this.user));
		timeData.add(new BasicNameValuePair("password", this.pwd));

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
			client.getConnectionManager().shutdown(); // 연결 지연 종료
			return false;
		}
	}
}
