package com.example.androidsendreceivetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */

	static final int DATE_DIALOG_ID = 0;
	static final int PICK_DATE_REQUEST = 1;
	final Context context = this;

	private String user;
	boolean selection = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = getIntent();
		user = intent.getExtras().getString("user");

		this.setButton();
	}

	private void setButton() {
		Button plus = (Button) findViewById(R.id.btPlus);
		plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "좌석 조회하기", "비밀번호 변경하기" };
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						switch (id) {
						case 0:
							Toast.makeText(getApplicationContext(), items[id] + "를 선택했습니다.", Toast.LENGTH_SHORT).show();
							Intent timeTable = new Intent(MainActivity.this, TimeTableActivity.class);
							timeTable.putExtra("user", user);
							startActivityForResult(timeTable, PICK_DATE_REQUEST);
							break;
						case 1:
							Toast.makeText(getApplicationContext(), items[id] + "를 선택했습니다.", Toast.LENGTH_SHORT).show();
							Intent changePwd = new Intent(MainActivity.this, ChangePwdActivity.class);
							changePwd.putExtra("user", user);
							startActivityForResult(changePwd, PICK_DATE_REQUEST);
							break;
						default:
							break;
						}
						dialog.dismiss();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}
}