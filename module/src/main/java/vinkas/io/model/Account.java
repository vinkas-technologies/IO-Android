package vinkas.io.model;

import android.accounts.AccountManager;

import com.firebase.client.DataSnapshot;

import java.util.Iterator;

import vinkas.io.Database;
import vinkas.io.Object;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Account extends Object {

    private android.accounts.Account androidAccount;
    private String id, token, name, email;

    public Account(Database database, android.accounts.Account androidAccount) {
        super(database);
        setAndroidAccount(androidAccount);
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    private void setId(String id) {
        if(this.id != id) {
            this.id = id;
            setFirebase(getDatabase().getFirebase().child("accounts/" + getId()));
        }
    }

    protected void setToken(String token) {
        if(this.token != token) {
            this.token = token;
            getDatabase().onTokenChange(getToken());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public android.accounts.Account getAndroidAccount() {
        return androidAccount;
    }

    private void setAndroidAccount(android.accounts.Account androidAccount) {
        this.androidAccount = androidAccount;
        readFromLocal();
        readFromRemote();
    }

    public void readFromLocal() {
        AccountManager am = AccountManager.get(getDatabase().getAndroidContext());
        android.accounts.Account account = getAndroidAccount();
        setEmail(account.name);
        setId(am.getUserData(account, "Id"));
        setToken(am.getUserData(account, "Token"));
        setName(am.getUserData(account, "Name"));
    }

    @Override
    public void writeToLocal(DataSnapshot dataSnapshot) {
        AccountManager am = AccountManager.get(getDatabase().getAndroidContext());
        android.accounts.Account account = getAndroidAccount();
        Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
        while(it.hasNext()) {
            DataSnapshot data = it.next();
            String key = data.getKey();
            String value = data.getValue().toString();
            switch (key) {
                case "Name":
                    am.setUserData(account, "Name", value);
                    break;
            }
        }
    }

    @Override
    public void mapData() {
        map.put("Name", getName());
        map.put("Email", getEmail());
    }

    @Override
    public void onNonExist() {
        super.onNonExist();
        writeToRemote();
    }

}