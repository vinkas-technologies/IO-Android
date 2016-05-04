package vinkas.io.model;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vinkas.io.app.Database;

/**
 * Created by Vinoth on 3-5-16.
 */
public class GoogleAccount extends Object {

    private String displayName;
    private String email;
    private String photoUrl;
    private String id;

    @Override
    public void mapData() {
        map.put("DisplayName", getDisplayName());
        map.put("Email", getEmail());
        map.put("PhotoUrl", getPhotoUrl());
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean isValid() {
        return (getDisplayName() != null);
    }

    @Override
    public void onRead(String key, java.lang.Object value) {
        String val = value.toString();
        switch (key) {
            case "DisplayName":
                setDisplayName(val);
                break;
            case "Email":
                setEmail(val);
                break;
            case "PhotoUrl":
                setPhotoUrl(val);
                break;
        }
    }

    public GoogleAccount(Database database, GoogleSignInAccount googleSignInAccount) {
        super(database, childPath(googleSignInAccount.getId()));
        setId(googleSignInAccount.getId());
        setDisplayName(googleSignInAccount.getDisplayName());
        setEmail(googleSignInAccount.getEmail());
        if (googleSignInAccount.getPhotoUrl() != null)
            setPhotoUrl(googleSignInAccount.getPhotoUrl().toString());
        write();
    }

    protected GoogleAccount(Database database, String id) {
        super(database, childPath(id));
        setId(id);
        read();
    }

    protected static String childPath(String id) {
        return "google/accounts/" + id;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
        setFirebase(getDatabase().getFirebase().child("google/accounts/" + getId()));
    }

}
