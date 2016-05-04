package vinkas.io.app;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.AuthData;

import vinkas.util.Helper;

/**
 * Created by Vinoth on 4-5-16.
 */
public abstract class SplashActivity extends vinkas.app.SplashActivity {

    @Override
    public Application getApp() {
        return (Application) super.getApp();
    }

    @Override
    protected void initialize() {
        super.initialize();
        authenticate();
    }

    private static final int REQUEST_CODE_CONNECT = 1001;

    void authenticate() {
        AuthData authData = getApp().getDatabase().getFirebase().getAuth();
        if (authData == null) {
            Intent intent = new Intent(this, ConnectActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CONNECT);
        } else
            getApp().getDatabase().onAuthenticated(authData);
    }

    @Override
    public boolean isReady() {
        if (super.isReady())
            if (getApp().isReady())
                return true;
        return false;
    }

}