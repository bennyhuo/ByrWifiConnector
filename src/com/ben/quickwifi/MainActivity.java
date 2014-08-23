package com.ben.quickwifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ben.update.UpdateManager;

public class MainActivity extends Activity {
	private EditText mUserName;
	private EditText mUserPwd;
	private Button mSubmit;
	private ToggleButton mLockDisconn;
	private ToggleButton mWeakDisconn;
	private ToggleButton mNotable;
	private ToggleButton mAutologin;
	private RadioGroup type;
	// private Button mReset;
	private SharedPreferences sp;

	private WifiManager wm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);

		sp = getSharedPreferences("qw", Context.MODE_PRIVATE);

		if (sp.getBoolean("FirstFlag", true)) {
			Editor editor = sp.edit();
			editor.putBoolean("FirstFlag", false);
			editor.commit();

			popAboutDialog();
		} else {
			// �������񣬲���������
		}

		initWifi();

		this.mUserName = (EditText) this.findViewById(R.id.username);
		this.mUserPwd = (EditText) this.findViewById(R.id.userpwd);

		this.mUserName.setText(this.sp.getString("mUserName", ""));
		this.mUserPwd.setText(this.sp.getString("mUserPwd", ""));

		this.mSubmit = (Button) this.findViewById(R.id.submit);
		this.mSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Editor editorInfo = sp.edit();
				editorInfo.putString("mUserName", mUserName.getText().toString().trim());
				editorInfo.putString("mUserPwd", mUserPwd.getText().toString().trim());
				editorInfo.putBoolean("autologin", mAutologin.isChecked());
				editorInfo.putBoolean("lockdisconn", mLockDisconn.isChecked());
				editorInfo.putBoolean("weakdisconn", mWeakDisconn.isChecked());
				editorInfo.putBoolean("notable", mNotable.isChecked());
				editorInfo.commit();

				Intent it = new Intent(MainActivity.this, QuickWifiService.class);
				// stopService(it);
				startService(it);
				System.out.println("������������������");
				finish();
			}
		});
		// this.mReset = (Button) this.findViewById(R.id.reset);
		// this.mReset.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// mUserName.setText("");
		// mUserPwd.setText("");
		// }
		// });
		type = (RadioGroup) findViewById(R.id.type);
		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int type = -1;
				switch (checkedId) {
				case R.id.wiredin:
					type = 1;
					break;
				case R.id.wireless:
					type=0;
					break;
				case R.id.none:
					
					break;
				default:
				}
				sp.edit().putInt("type", type).commit();
			}
		});
		this.mAutologin = (ToggleButton) this.findViewById(R.id.autologin);
		this.mLockDisconn = (ToggleButton) this.findViewById(R.id.autolockdisconn);
		this.mWeakDisconn = (ToggleButton) this.findViewById(R.id.autoweakdisconn);
		this.mNotable = (ToggleButton) this.findViewById(R.id.notablebutton);
		this.mAutologin.setChecked(sp.getBoolean("autologin", false));
		this.mLockDisconn.setChecked(sp.getBoolean("lockdisconn", false));
		this.mWeakDisconn.setChecked(sp.getBoolean("weakdisconn", false));
		this.mNotable.setChecked(sp.getBoolean("notable", true));
	}

	private void popAboutDialog() {
		String tips = "��ӭʹ��BYR�������֣�\n1.�����û��������룬�����¼��������������\n2.������Զ������豸��WIFI״̬��һ�����ֽ�����������ͻ���ݽ�����������Ƴ��Ե�¼����У԰����\n3.��Ӧ��ּ����ͬѧ����ȥ�ֶ���ҳ��֤���鷳������֤����ȡ������κ���Ϣ��������������㣬����¡�\nPowered By WCSN����";
		AlertDialog ad = new AlertDialog.Builder(this).setTitle("ʹ��˵��").setIcon(R.drawable.ic_launcher).setMessage(tips)
				.setPositiveButton("��������", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setNegativeButton("�˳�����", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).create();
		ad.show();
	}

	private void initWifi() {
		this.wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if (!wm.isWifiEnabled()) {
			AlertDialog ad = new AlertDialog.Builder(this).setTitle("WIFIû�д򿪣�").setIcon(R.drawable.ic_launcher)
					.setPositiveButton("��WIFI", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							wm.setWifiEnabled(true);
						}
					}).setNegativeButton("�˳�����", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					}).create();

			ad.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			popAboutDialog();
			break;

		case R.id.update:
			final UpdateManager manager = new UpdateManager(MainActivity.this);
			// ����������
			manager.checkUpdate();
			Toast.makeText(this, "���ڼ����£����Ժ򡭡�", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onMenuItemSelected(featureId, item);

	}

}
