package apps.zambrone.com.carpooling.Handlers;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AppHandler extends Application {
    public static final String TAG = AppHandler.class.getSimpleName();
    static AppHandler mInstance;
    private RequestQueue mRequestQueue;
    DataStorage dStorage;
    DatabaseHandler dbHandler;
    SharedPreferences sharedPref;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppHandler getInstance() {
        return mInstance;
    }

    public SharedPreferences getSharedPref() {
        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return sharedPref;
    }

    public DataStorage getDataManager() {
        if (dStorage == null) {
            dStorage = new DataStorage(this);
        }
        return dStorage;
    }

    public DatabaseHandler getDBHandler() {
        if (dbHandler == null) {
            dbHandler = new DatabaseHandler(this);
        }
        return dbHandler;
    }

    public Map<String, String> getAuthorization() {
        if (dStorage == null) {
            getDataManager();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", dStorage.getString("api", "null"));
        return headers;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
