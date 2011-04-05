/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.geolix;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 *
 * @author TeepH
 */
public class TracePositionService extends IntentService {
  public TracePositionService(){
    super("Service de tracage de position");
  }

  @Override
  protected void onHandleIntent(Intent arg0) {
    // Normally we would do some work here, like download a file.
    // For our sample, we just sleep for 5 seconds.
    Log.e("DEBUG", "Dans la queue");
    long endTime = System.currentTimeMillis() + 5*1000;
    while (System.currentTimeMillis() < endTime) {
      synchronized (this) {
        try {
          Log.e("DEBUG", "Le service tourne !");
          Toast.makeText(this, "Service waiting", Toast.LENGTH_SHORT).show();
          wait(endTime - System.currentTimeMillis());
        } catch (Exception e) {
        }
      }
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Toast.makeText(this, "Track service starting", Toast.LENGTH_SHORT).show();
    super.onStartCommand(intent, flags, startId);
    //handleCommand(intent);
    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    Toast.makeText(this, "Track service stoping", Toast.LENGTH_SHORT).show();
    super.onDestroy();
  }
}
