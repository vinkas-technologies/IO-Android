package vinkas.io.model;

import android.accounts.AccountManager;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Iterator;

import vinkas.io.Database;
import vinkas.io.Object;

/**
 * Created by Vinoth on 3-5-16.
 */
public class GoogleAccount extends Object {

    private GoogleSignInAccount googleSignInAccount;

    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    private void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        this.googleSignInAccount = googleSignInAccount;
        readFromLocal();
    }

    public GoogleAccount(Database database, GoogleSignInAccount googleSignInAccount) {
        super(database);
        setGoogleSignInAccount(googleSignInAccount);
    }

    @Override
    public void mapData() {

    }

    @Override
    public void writeToLocal(DataSnapshot dataSnapshot) {
        AccountManager am = AccountManager.get(getDatabase().getAndroidContext());
        android.accounts.Account account = getDatabase().getAccount().getAndroidAccount();
        Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
        while(it.hasNext()) {
            DataSnapshot data = it.next();
            String key = data.getKey();
            String value = data.getValue().toString();
            switch (key) {
                case "Id":
                    am.setUserData(account, "Id", value);
                    break;
            }
        }
    }

    private String id, token;

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
        setFirebase(getDatabase().getFirebase().child("google/accounts/" + getId()));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        getDatabase().getFirebase().authWithOAuthToken("google", token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void readFromLocal() {
        setId(getGoogleSignInAccount().getId());
        setToken(getGoogleSignInAccount().getIdToken());
    }

}
