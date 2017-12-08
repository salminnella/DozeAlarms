package com.salmin.demo.alarmsindoze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends FragmentActivity implements PickTimeDialogFragment.SetAlarmClick {
    public static final String HOUR = "time_hour";
    public static final String MINUTE = "time_minute";
    public static final String TIME_PICKER = "time_picker";
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    TextView textView1;
    private static TextView textView2;
    private Button button1;
    private Button button2;
    private FloatingActionButton fab;

    public static TextView getTextView2() {
        return textView2;
    }
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setAllClickListeners();
        initAlarmManager();
    }

    private void initViews() {
        textView1 = (TextView)findViewById(R.id.msg1);
        textView1.setText(timeHour + ":" + timeMinute);
        textView2 = (TextView)findViewById(R.id.msg2);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void setAllClickListeners() {
        setClickListeners(button1, null);
        setClickListeners(button2, null);
        setClickListeners(null,fab);
    }

    private void setClickListeners(final Button button, final FloatingActionButton fab) {
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (button.getId()) {
                        case R.id.button1:
                            Log.d("MainActivity", "onClick: was called");
                            textView2.setText("");
                            Bundle bundle = new Bundle();
                            bundle.putInt(HOUR, timeHour);
                            bundle.putInt(MINUTE, timeMinute);
                            PickTimeDialogFragment fragment = new PickTimeDialogFragment();
                            fragment.setArguments(bundle);
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.add(fragment, TIME_PICKER);
                            transaction.commit();
                            break;
                        case R.id.button2:
                            textView2.setText("");
                            cancelAlarm();
                            break;
                        default:
                            break;
                    }
                }
            });
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "replace with an action", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initAlarmManager() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
    }

    @Override
    public void clickSetAlarm(Bundle timeBundle) {
        timeHour = timeBundle.getInt(HOUR);
        timeMinute = timeBundle.getInt(MINUTE);
        textView1.setText(timeHour + ":" + timeMinute);
        Log.d("MainActivity", "clickSetAlarm: " + timeHour + ":" + timeMinute);
        setAlarm();
    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
