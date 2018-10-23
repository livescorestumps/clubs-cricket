package com.game.clubs.stumps.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Praneeth on 10/11/2018.
 */

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> userlivedata;
    private MutableLiveData<FirebaseUser> registerlivedata;
    private MutableLiveData<Boolean> checkIfUserLiveData;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
        userlivedata = new MutableLiveData<>();
        registerlivedata = new MutableLiveData<>();
        checkIfUserLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<FirebaseUser> getUserlivedata() {
        return userlivedata;
    }

    public MutableLiveData<FirebaseUser> getRegisterlivedata() {
        return registerlivedata;
    }

    public void getCurrentUser() {
        if (mAuth != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                userlivedata.postValue(user);
            }
        }
    }

    public void authenticate(String email, String password) {
        if (mAuth == null) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        userlivedata.postValue(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        userlivedata.postValue(null);
                    }
                });
    }

    public void signup(String email, String password) {
        if (mAuth == null) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        userlivedata.postValue(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        userlivedata.postValue(null);
                    }
                });
    }

    public void checkIfUserExits(@NonNull String email) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Players")
                .whereEqualTo("emailId", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        checkIfUserLiveData.postValue(task.getResult().size() > 0);
                    } else {
                        checkIfUserLiveData.postValue(false);
                    }
                });
    }

    public MutableLiveData<Boolean> getCheckIfUserLiveData() {
        return checkIfUserLiveData;
    }
}
