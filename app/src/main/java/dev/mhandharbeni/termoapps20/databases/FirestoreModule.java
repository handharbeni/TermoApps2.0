package dev.mhandharbeni.termoapps20.databases;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import dev.mhandharbeni.termoapps20.utils.Utils;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;

public class FirestoreModule {
    private static final String TAG = FirestoreModule.class.getSimpleName();
    private static FirestoreModule instance = null;
    private FirestoreModuleCallback firestoreModuleCallback;


    FirebaseFirestore firebaseFirestore;

    public FirestoreModule(FirestoreModuleCallback firestoreModuleCallback) {
        this.firestoreModuleCallback = firestoreModuleCallback;
        if (firebaseFirestore == null){
            FirebaseFirestoreSettings firebaseFirestoreSettings =
                    new FirebaseFirestoreSettings.Builder()
                            .setPersistenceEnabled(false)
                            .build();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.setFirestoreSettings(firebaseFirestoreSettings);

            HashMap<String, Object> lastUpdate = new HashMap<>();
            lastUpdate.put("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
            firebaseFirestore.collection(AppConstant.PARENT).document(Utils.getDate()).set(lastUpdate);
        }
    }

    public static FirestoreModule getInstance(FirestoreModuleCallback firestoreModuleCallback){
        if (instance == null){
            instance = new FirestoreModule(firestoreModuleCallback);
        }
        return instance;
    }

    public void writeLogToStore(String parent, String mode, String id, HashMap<String, Object> data){
        firebaseFirestore.collection(parent)
                .document(Utils.getDate())
                .collection(mode)
                .document(id)
                .set(data)
                .addOnSuccessListener(unused -> firestoreModuleCallback.onSuccess())
                .addOnFailureListener(e -> firestoreModuleCallback.onFailure(e))
                .addOnCompleteListener(task -> firestoreModuleCallback.onCompleteVoid(task));
    }

    public Task<DocumentSnapshot> getDataFromStore(String parent, String mode, String id){
        return firebaseFirestore.collection(parent)
                .document(Utils.getDate())
                .collection(mode)
                .document(id)
                .get();
    }

    public Task<QuerySnapshot> getListDataByMode(String parent, String mode){
        return firebaseFirestore.collection(parent).document(Utils.getDate()).collection(mode).get();
    }

    public Task<QuerySnapshot> getListDataByMode(String parent, String date, String mode){
        return firebaseFirestore.collection(parent).document(date).collection(mode).get();
    }

    public Task<QuerySnapshot> getListParent(String parent){
        return firebaseFirestore.collection(parent).get();
    }



    public interface FirestoreModuleCallback{
        void onSuccess();
        void onSuccess(DocumentReference documentReference);
        void onFailure(Exception e);
        void onComplete(Task<DocumentReference> task);
        void onCompleteVoid(Task<Void> task);
    }
}
