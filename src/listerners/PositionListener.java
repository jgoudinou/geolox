/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package listerners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 *
 * @author TeepH
 */
public class PositionListener implements LocationListener {
    public PositionListener() {
    }

    public void onLocationChanged(Location arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onProviderEnabled(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onProviderDisabled(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
