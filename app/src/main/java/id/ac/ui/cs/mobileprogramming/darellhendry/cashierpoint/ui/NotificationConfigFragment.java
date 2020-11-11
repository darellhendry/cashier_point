package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.receiver.AlarmReceiver;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.service.AlarmService;

public class NotificationConfigFragment extends Fragment {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    RadioGroup radioGroup;
    private final String NOTIFICATION_PERIOD = "id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.ui.NOTIFICATION_PERIOD";

    public NotificationConfigFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_notification_config, container, false);
        radioGroup = root.findViewById(R.id.radio_group_notification);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int notify = sharedPref.getInt(NOTIFICATION_PERIOD, 0);
        switch (notify) {
            case R.id.radio_disabled:
                radioGroup.check(R.id.radio_disabled);
                break;
            case R.id.radio_every_1_hour:
                radioGroup.check(R.id.radio_every_1_hour);
                break;
            case R.id.radio_every_morning:
                radioGroup.check(R.id.radio_every_morning);
                break;
            default:
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_disabled:
                        Intent intent = new Intent(getContext(), AlarmService.class);
                        getContext().stopService(intent);
                        break;
                    case R.id.radio_every_1_hour:
                        Intent one_hour_alarmIntent = new Intent(getContext(), AlarmService.class);
                        one_hour_alarmIntent.putExtra("period", "one_hour");
                        getContext().startService(one_hour_alarmIntent);
                        break;
                    case R.id.radio_every_morning:
                        Intent alarmIntent = new Intent(getContext(), AlarmService.class);
                        alarmIntent.putExtra("period", "every_morning");
                        getContext().startService(alarmIntent);
                        return;
                    default:
                        break;
                }
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(NOTIFICATION_PERIOD, checkedId);
                editor.apply();
            }
        });
        return root;
    }

}