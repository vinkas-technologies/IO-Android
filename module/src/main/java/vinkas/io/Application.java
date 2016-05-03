package vinkas.io;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vinkas.io.model.Account;
import vinkas.io.model.GoogleAccount;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Application extends vinkas.auth.app.Application {

    private Database database;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDatabase(new Database(getApplicationContext(), getString(R.string.vinkas_io)));
    }

    @Override
    public void setGoogleSignInAccount(GoogleSignInAccount googleAccount) {
        getDatabase().setGoogleAccount(new GoogleAccount(getDatabase(), googleAccount));
    }

    @Override
    public void setAndroidAccount(android.accounts.Account androidAccount) {
        getDatabase().setAccount(new Account(getDatabase(), androidAccount));
    }
}
