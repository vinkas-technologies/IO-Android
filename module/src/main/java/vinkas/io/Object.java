package vinkas.io;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import vinkas.io.util.Helper;

/**
 * Created by Vinoth on 3-5-16.
 */
public abstract class Object implements ValueEventListener {

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Helper.onError(firebaseError);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists())
            writeToLocal(dataSnapshot);
        else
            onNonExist();
    }

    public void onNonExist() {

    }

    protected HashMap<String, String> map;
    public abstract void writeToLocal(DataSnapshot dataSnapshot);

    public void writeToRemote() {
        map = new HashMap<String, String>();
        mapData();
        getFirebase().setValue(map);
    }

    public abstract void mapData();

    public abstract void readFromLocal();

    public Object(Database database) {
        setDatabase(database);
    }

    private Database database;
    private Firebase firebase;

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public void readFromRemote() {
        getFirebase().addListenerForSingleValueEvent(this);
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

}
