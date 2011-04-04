/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.geolix;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 *
 * @author TeepH
 */
public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        TextView tv = new TextView(this);
        tv.setText("Hello, Android");
        setContentView(tv);
    }

}
