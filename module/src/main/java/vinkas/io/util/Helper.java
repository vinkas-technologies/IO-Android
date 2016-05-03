package vinkas.io.util;

import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;

/**
 * Created by Vinoth on 3-5-16.
 */
public class Helper {

    public static void onError(FirebaseError error) {
        onFirebaseException(error.toException());
    }

    public static void onFirebaseException(FirebaseException firebaseException) {
        onException(firebaseException);
    }

    public static void onException(Exception exception) {
        vinkas.util.Helper.onException(exception);
    }

}
