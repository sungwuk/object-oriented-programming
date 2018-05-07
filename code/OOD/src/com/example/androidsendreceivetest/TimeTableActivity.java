package com.example.androidsendreceivetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeTableActivity extends Activity {

	static final int DATE_DIALOG_ID = 0;
	static final int PICK_DATE_REQUEST = 1;

	final Context context = this;
	TimePickerDialog timePickerDialog;

	ArrayList<String> tableData = new ArrayList<String>(45);
	GridView gridView;

	static String[] numbers;
	private String user;
	private boolean timeSet = false;
	private int tableNum;
	private Calendar startTime = new GregorianCalendar();
	private Calendar endTime = new GregorianCalendar();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		Intent intent = getIntent();
		user = intent.getExtras().getString("user");

		gridView = (GridView) findViewById(R.id.gridview);

		this.setTable();
		this.setButton();
	}

	private void setTable() {
		String result = SendByHttp(); // 메시지를 서버에 보냄
		jsonParserList(result); // JSON 데이터 파싱

		numbers = new String[] { " ", "가", "나", "다", "라", "마", "9", tableData.get(0), tableData.get(9),
				tableData.get(18), tableData.get(27), tableData.get(36), "10", tableData.get(1), tableData.get(10),
				tableData.get(19), tableData.get(28), tableData.get(37), "11", tableData.get(2), tableData.get(11),
				tableData.get(20), tableData.get(29), tableData.get(38), "12", tableData.get(3), tableData.get(12),
				tableData.get(21), tableData.get(30), tableData.get(39), "1", tableData.get(4), tableData.get(13),
				tableData.get(22), tableData.get(31), tableData.get(40), "2", tableData.get(5), tableData.get(14),
				tableData.get(23), tableData.get(32), tableData.get(41), "3", tableData.get(6), tableData.get(15),
				tableData.get(24), tableData.get(33), tableData.get(42), "4", tableData.get(7), tableData.get(16),
				tableData.get(25), tableData.get(34), tableData.get(43), "5", tableData.get(8), tableData.get(17),
				tableData.get(26), tableData.get(35), tableData.get(44), "6" };

		CustomAdapter adapter = new CustomAdapter(this, 0, numbers);
		gridView.setAdapter(adapter);
	}

	private class CustomAdapter extends ArrayAdapter<String> {
		// private ArrayList<String> items;
		private String[] items;

		public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.calendar_item, null);
			}

			// ImageView 인스턴스
			ImageView imageView = (ImageView) v.findViewById(R.id.date_icon);

			// 리스트뷰의 아이템에 이미지를 변경한다.
			if ((items[position]).length() == 9) {
				imageView.setImageResource(R.drawable.icon);
			} else if ((items[position]).equals("9")) {
				imageView.setImageResource(R.drawable.nine);
			} else if ((items[position]).equals("10")) {
				imageView.setImageResource(R.drawable.ten);
			} else if ((items[position]).equals("11")) {
				imageView.setImageResource(R.drawable.eleven);
			} else if ((items[position]).equals("12")) {
				imageView.setImageResource(R.drawable.twelve);
			} else if ((items[position]).equals("1")) {
				imageView.setImageResource(R.drawable.one);
			} else if ((items[position]).equals("2")) {
				imageView.setImageResource(R.drawable.two);
			} else if ((items[position]).equals("3")) {
				imageView.setImageResource(R.drawable.three);
			} else if ((items[position]).equals("4")) {
				imageView.setImageResource(R.drawable.four);
			} else if ((items[position]).equals("5")) {
				imageView.setImageResource(R.drawable.five);
			} else if ((items[position]).equals("6")) {
				imageView.setImageResource(R.drawable.six);
			} else if ((items[position]).equals("가")) {
				imageView.setImageResource(R.drawable.a);
			} else if ((items[position]).equals("나")) {
				imageView.setImageResource(R.drawable.b);
			} else if ((items[position]).equals("다")) {
				imageView.setImageResource(R.drawable.c);
			} else if ((items[position]).equals("라")) {
				imageView.setImageResource(R.drawable.d);
			} else if ((items[position]).equals("마")) {
				imageView.setImageResource(R.drawable.e);
			}
			return v;
		}
	}

	private void setButton() {
		Button plus = (Button) findViewById(R.id.btPlus1);
		plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "대여하기", "평가하기" };
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						switch (id) {
						case 0:
							Toast.makeText(getApplicationContext(), items[id] + "를 선택했습니다.", Toast.LENGTH_SHORT).show();
							openTablePicker();
							break;
						case 1:
							Toast.makeText(getApplicationContext(), items[id] + "를 선택했습니다.", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(TimeTableActivity.this, RateActivity.class);
							intent.putExtra("user", user);
							startActivityForResult(intent, PICK_DATE_REQUEST);
							break;
						default:
							break;
						}
						dialog.dismiss();
					}
				});

				// 다이얼로그 생성
				AlertDialog alertDialog = alertDialogBuilder.create();

				// 다이얼로그 보여주기
				alertDialog.show();
			}
		});
	}

	private void openTablePicker() {
		final CharSequence[] items = { "가", "나", "다", "라", "마" };
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		alertDialogBuilder.setTitle("좌석을 선택하세요");
		alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				switch (id) {
				case 0:
					tableNum = 1;
					Toast.makeText(getApplicationContext(), items[id] + "석을 선택했습니다.", Toast.LENGTH_SHORT).show();
					openTimePickerDialog(false);
					break;
				case 1:
					tableNum = 2;
					Toast.makeText(getApplicationContext(), items[id] + "석을 선택했습니다.", Toast.LENGTH_SHORT).show();
					openTimePickerDialog(false);
					break;
				case 2:
					tableNum = 3;
					Toast.makeText(getApplicationContext(), items[id] + "석을 선택했습니다.", Toast.LENGTH_SHORT).show();
					openTimePickerDialog(false);
					break;
				case 3:
					tableNum = 4;
					Toast.makeText(getApplicationContext(), items[id] + "석을 선택했습니다.", Toast.LENGTH_SHORT).show();
					openTimePickerDialog(false);
					break;
				case 4:
					tableNum = 5;
					Toast.makeText(getApplicationContext(), items[id] + "석을 선택했습니다.", Toast.LENGTH_SHORT).show();
					openTimePickerDialog(false);
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

	private void openTimePickerDialog(boolean is24r) {
		Calendar calendar = Calendar.getInstance();

		timePickerDialog = new TimePickerDialog(TimeTableActivity.this, onTimeSetListener,
				calendar.get(Calendar.HOUR_OF_DAY), 0, is24r);

		if (!this.timeSet) {
			timePickerDialog.setTitle("시작 시간 설정");
		} else {
			timePickerDialog.setTitle("종료 시간 설정");
		}
		timePickerDialog.show();
	}

	OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();

			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, 0);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);

			if (calSet.compareTo(calNow) <= 0) {
				// Today Set time passed, count to tomorrow
				calSet.add(Calendar.DATE, 1);
			}
			setAlarm(calSet);
		}
	};

	private void setAlarm(Calendar targetCal) {
		if (!this.timeSet) {
			this.timeSet = true;
			this.startTime = targetCal;
			this.openTimePickerDialog(false);
		} else {
			this.timeSet = false;
			this.endTime = targetCal;
			this.rentTable(startTime, endTime);

			Intent intent = new Intent(this, TimeTableActivity.class);
			intent.putExtra("user", user);
			startActivityForResult(intent, PICK_DATE_REQUEST);
		}
	}

	private boolean rentTable(Calendar start, Calendar end) {
		if ((end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY)) > 4) {
			Toast.makeText(getApplicationContext(), "4시간 이하만 대여 가능합니다. ", Toast.LENGTH_SHORT).show();
			return false;
		} else {

			String URL = "http://52.78.78.71:8080/MyServer/rentTable.jsp";

			List<NameValuePair> timeData = new ArrayList<NameValuePair>();

			timeData.add(new BasicNameValuePair("user", this.user));
			timeData.add(new BasicNameValuePair("tableNum", "" + this.tableNum));
			timeData.add(new BasicNameValuePair("startHour", "" + start.get(Calendar.HOUR_OF_DAY)));
			timeData.add(new BasicNameValuePair("endHour", "" + end.get(Calendar.HOUR_OF_DAY)));

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

	private String SendByHttp() {

		String URL = "http://52.78.78.71:8080/MyServer/timetable.jsp";

		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(URL);

			/* 데이터 보낸 뒤 서버에서 데이터를 받아오는 과정 */
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
			client.getConnectionManager().shutdown(); // 연결 지연 종료
			return "";
		}
	}

	private void jsonParserList(String result) {
		try {
			JSONObject json = new JSONObject(result);
			JSONArray jArr = json.getJSONArray("List");

			for (int i = 0; i < jArr.length(); i++) {
				json = jArr.getJSONObject(i);

				this.tableData.add(json.getString("time9"));
				this.tableData.add(json.getString("time10"));
				this.tableData.add(json.getString("time11"));
				this.tableData.add(json.getString("time12"));
				this.tableData.add(json.getString("time13"));
				this.tableData.add(json.getString("time14"));
				this.tableData.add(json.getString("time15"));
				this.tableData.add(json.getString("time16"));
				this.tableData.add(json.getString("time17"));
			}
		} catch (JSONException e) {
			Toast.makeText(this, "catch", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}
