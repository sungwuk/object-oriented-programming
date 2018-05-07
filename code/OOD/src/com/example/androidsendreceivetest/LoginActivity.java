package com.example.androidsendreceivetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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

public class LoginActivity extends Activity {
	EditText idInput, passwordInput;
	Boolean loginChecked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		idInput = (EditText) findViewById(R.id.idinput);
		passwordInput = (EditText) findViewById(R.id.pwinput);
		Button btlogin = (Button) findViewById(R.id.btlogin);

		btlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if autoLogin unChecked
				String id = idInput.getText().toString();
				String password = passwordInput.getText().toString();
				Boolean validation = loginValidation(id, password);

				if (validation) {
					Toast.makeText(LoginActivity.this, "�α��� �Ͽ����ϴ�.", Toast.LENGTH_LONG).show();
					// save id, password to Database

					// goto mainActivity
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					intent.putExtra("user", id);
					startActivity(intent);
				} else {
					Toast.makeText(LoginActivity.this, "���̵� ��й�ȣ�� �ٽ� Ȯ�����ּ���.", Toast.LENGTH_LONG).show();
					// goto LoginActivity
				}
			}
		});
	}

	private boolean loginValidation(String id, String password) {
		String result = SendByHttp(); // �޽����� ������ ����
		if (checkLogin(result, id, password)) { // JSON ������ �Ľ�
			return true;
		} else {
			return false;
		}
	}

	private String SendByHttp() {
		String URL = "http://52.78.78.71:8080/MyServer/login.jsp";

		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(URL);

			/* ������ ���� �� �������� �����͸� �޾ƿ��� ���� */
			HttpResponse response = client.execute(post);
			BufferedReader bufreader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), "utf-8"));

			String line = null;
			String result = "";

			while ((line = bufreader.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			client.getConnectionManager().shutdown(); // ���� ���� ����
			return "";
		}
	}

	private boolean checkLogin(String result, String id, String pwd) {
		boolean check = false;

		try {
			JSONObject json = new JSONObject(result);
			JSONArray jArr = json.getJSONArray("List");

			for (int i = 0; i < jArr.length(); i++) {
				json = jArr.getJSONObject(i);

				if ((json.getString("studentId")).equals(id) && (json.getString("password")).equals(pwd)) {
					check = true;
				}
			}
		} catch (JSONException e) {
			Toast.makeText(this, "catch", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return check;
	}
}
