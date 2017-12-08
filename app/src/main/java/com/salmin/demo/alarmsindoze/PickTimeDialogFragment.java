package com.salmin.demo.alarmsindoze;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by aminnella on 12/6/17.
 */

public class PickTimeDialogFragment extends DialogFragment {
    private int timeHour;
    private int timeMinute;

    public interface SetAlarmClick {
        void clickSetAlarm (Bundle bundle);
    }

    public PickTimeDialogFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        timeHour = bundle.getInt(MainActivity.HOUR);
        timeMinute = bundle.getInt(MainActivity.MINUTE);
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TextView textView = getActivity().findViewById(R.id.msg1);
                String alarmTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                textView.setText(alarmTime);
                timeHour = hourOfDay;
                timeMinute = minute;
                Bundle b = new Bundle();
                b.putInt(MainActivity.HOUR, timeHour);
                b.putInt(MainActivity.MINUTE, timeMinute);
                ((SetAlarmClick) getActivity()).clickSetAlarm(b);
            }
        };

        return new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, false);
    }
}
