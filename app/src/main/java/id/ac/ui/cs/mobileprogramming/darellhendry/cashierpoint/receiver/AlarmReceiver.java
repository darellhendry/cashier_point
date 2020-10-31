package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.Receipt;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model.repository.ReceiptRepository;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private ReceiptRepository repository;


    @Override
    public void onReceive(final Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        queryDatabase(context);
    }

    private void queryDatabase(final Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/receipt");

        long date = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultdate = new Date(date);
        Query query = ref.orderByChild("date").startAt(sdf.format(resultdate));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                int sum = 0;
                for (DataSnapshot snapshot: datasnapshot.getChildren()) {
                    Receipt value = snapshot.getValue(Receipt.class);
                    sum += value.getPrice();
                    Log.d("test", value.toString());
                }
                deliverNotification(context, sum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("test", "Failed to read value.", error.toException());
            }
        });
    }

    private void deliverNotification(Context context, int sum) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_summary_icon_foreground)
                .setContentTitle("Receipt Report")
                .setContentText("Yesterday sales: Rp. " + sum)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
