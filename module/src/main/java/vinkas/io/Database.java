package vinkas.io;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vinkas.io.model.Account;
import vinkas.io.model.GoogleAccount;
import vinkas.io.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Database implements Firebase.AuthResultHandler, Firebase.AuthStateListener {

    public void onTokenChange(String token) {
        AuthData authData = getFirebase().getAuth();
        if (authData != null && authData.getToken() == token)
            onAuthStateChanged(authData);
        else
            authWithCustomToken(token);
    }

    private static final String HTTPS = "https://";
    private Account account;

    public Account getAccount() {
        return account;
    }

    protected void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void onAuthenticated(AuthData authData) {
        // onAuthStateChanged
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
    }

    @Override
    public void onAuthStateChanged(AuthData authData) {
        if (authData != null) {
            authenticated = true;
        } else {
            authenticated = false;
        }
    }

    private Firebase firebase;
    private String url;
    private Context androidContext;
    private Boolean authenticated = false;
    private GoogleAccount googleAccount;

    public GoogleAccount getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(GoogleAccount googleAccount) {
        this.googleAccount = googleAccount;
    }

    public Boolean isReady() {
        if (isAuthenticated())
            return true;
        return false;
    }

    public void authWithCustomToken(String token) {
        onAuthStateChanged(null);
        getFirebase().authWithCustomToken(token, this);
    }

    public Boolean isAuthenticated() {
        return authenticated;
    }

    public Context getAndroidContext() {
        return androidContext;
    }

    public void setAndroidContext(Context androidContext) {
        this.androidContext = androidContext;
        Firebase.setAndroidContext(androidContext);
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setFirebase(new Firebase(getUrl()));
    }

    public Database(Context context, String host) {
        setAndroidContext(context);
        setUrl(HTTPS + host);
    }

}