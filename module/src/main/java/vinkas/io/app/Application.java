package vinkas.io.app;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vinkas.io.R;
import vinkas.io.model.Account;
import vinkas.io.model.GoogleAccount;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Application extends vinkas.app.Application {

    private Database database;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public boolean isReady() {
        if(getDatabase().isReady())
            return true;
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDatabase(new Database(getApplicationContext(), getResources().getBoolean(R.bool.persistence_enabled), getString(R.string.firebase_io_hostname)));
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleAccount) {
        getDatabase().setGoogleAccount(new GoogleAccount(getDatabase(), googleAccount));
    }

}