package sanmateo.com.profileapp.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import sanmateo.com.profileapp.R;


/**
 * Created by rsbulanon on 6/28/16.
 */
public class NotificationHelper {

    public static void displayNotification(final int id,final Context context, final String title,
                                           final String content, final Class cls) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.drawable.san_mateo_logo);
            mBuilder.setColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            mBuilder.setSmallIcon(R.drawable.san_mateo_logo);
        }

        mBuilder.setContentTitle(title)
                .setContentText(content);

        if (cls != null) {
            final Intent intent = new Intent(context,cls);
            final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
        }
        final NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }

    public static void displayNotification(final int id,final Context context, final String title,
                                           final String content, final Class cls, final String area) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.san_mateo_logo)
                .setContentTitle(title)
                .setContentText(content);
        if (cls != null) {
            final Intent intent = new Intent(context,cls);
            intent.putExtra("area", area);
            final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
        }
        final NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
