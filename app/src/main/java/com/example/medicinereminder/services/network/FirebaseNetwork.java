package com.example.medicinereminder.services.network;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.model.UserDTO;
import com.example.medicinereminder.model.MedicationPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FirebaseNetwork implements NetworkInterface{
    Activity _activity;
    public static FirebaseAuth myAuth;
    private static FirebaseNetwork myFireBase;
    private static FirebaseNetwork trackerFireBase;
    private static FirebaseAuth trackerAuth;

    private NetworkDelegate myDelegate;
    private boolean exist = false;
    private boolean listenToUpdates = false;
    private List<MedicationPOJO> updatedMedicationList;

    private static String userName="";


    private FirebaseNetwork(Activity myActivity) {
        _activity = myActivity;
    }

    private FirebaseNetwork() {

    }

    public static FirebaseNetwork getInstance() {
        if (trackerFireBase == null) {
            trackerAuth = FirebaseAuth.getInstance();
            trackerFireBase = new FirebaseNetwork();
        }
        return myFireBase;
    }


    public static FirebaseNetwork getInstance(Activity myActivity) {
        if (myFireBase == null) {
            myAuth = FirebaseAuth.getInstance();
            myFireBase = new FirebaseNetwork(myActivity);
        }
        return myFireBase;
    }

    @Override
    public void setNetworkDelegate(NetworkDelegate networkDelegate) {
        myDelegate = networkDelegate;
    }

    @Override
    public void isSignedIn() {
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if(currentUser != null){
            myDelegate.onSuccess();
        }
        else
            myDelegate.onFailure("User did not sign in");
    }

    @Override
    public void registerWithEmailAndPass(Activity myActivity, String email, String password, String name) {
        //why the password doesn't exist in the pojo class
        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserDTO user = new UserDTO(name, email);
                            addRegisterInDB(user);
                        }
                        else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegate.onFailure(errorMessage);
                        }
                    }
                });
    }

    @Override
    public void signInWithEmailAndPass(Activity myActivity, String email, String password) {
        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myDelegate.onSuccess();
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegate.onFailure(errorMessage);
                        }
                    }
                });
    }

    @Override
    public void tryLoginGoogle(String email) {
        myAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().getSignInMethods().isEmpty()) {
                                myDelegate.onSuccess(true);
                            } else {
                                myDelegate.onSuccess(false);

                            }
                        } else {
                            handleFireBaseException(task);
                        }
                    }
                });
    }

    @Override
    public void signInUsingGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        myAuth.signInWithCredential(credential)
                .addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userName = getCurrentUser().getDisplayName();
                            String email = getCurrentUser().getEmail();
                            UserDTO user = new UserDTO(userName, email);
                            addUserInDB(user);
                            myDelegate.onSuccess();
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegate.onFailure(errorMessage);
                        }
                    }
                });
    }

    @Override
    public FirebaseUser getCurrentUser() {
        // check if null or not
        if (myAuth == null) {
            // check
            myAuth = FirebaseAuth.getInstance();
        }
        return myAuth.getCurrentUser();
    }

    @Override
    public void addUserInDB(UserDTO user) {
        String uid = user.getEmail().split("\\.")[0];

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean flag = true;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.child("email").getValue().toString();
                    String key = email.split("\\.")[0];
                    if (key.equals(uid)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(uid)
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                myDelegate.onSuccessReturn(user.getUsername());
                            } else {
                                String errorMessage = handleFireBaseException(task);
                                myDelegate.onFailure(errorMessage);
                            }
                        }
                    });
                } else {
                    myDelegate.onSuccess();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void getUserFromRealDB(String email) {
        Log.i("TAG", "getUserFromRealDB: ");
        String uid = email.split("\\.")[0];
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().getValue() != null){
                        userName= task.getResult().getValue().toString();
                    }
                    myDelegate.onSuccessReturn(userName);
                } else {
                    myDelegate.onFailure(task.getException().getMessage());
                    Log.i("TAG", "problem in getting name: " + task.getException().getMessage());

                }
            }
        });
    }

    @Override
    public void sendRequest(RequestDTO requestPojo) {
        String[] uid = requestPojo.getEmail().split("\\.");
        String senderEmail = requestPojo.getMyEmail().split("\\.")[0];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid[0]);
        databaseReference.child("request").child(senderEmail).setValue(requestPojo);
    }

    @Override
    public void loadHelpRequest(String email) {
        List<RequestDTO> requestPojos = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("request");
        //why here didn't use the uid

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestPojos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("myEmail").getValue() != null && Integer.parseInt(String.valueOf(dataSnapshot.child("acceptance").getValue())) == 0) {
                        RequestDTO tracker = new RequestDTO(dataSnapshot.child("name").getValue().toString()
                                , dataSnapshot.child("email").getValue().toString()
                                , dataSnapshot.child("myEmail").getValue().toString()
                                , Integer.parseInt(String.valueOf(dataSnapshot.child("acceptance").getValue()))
                                , dataSnapshot.child("requestID").getValue().toString()
                                );

                        requestPojos.add(tracker);
                    }
                }
                myDelegate.onSuccessRequest(requestPojos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myDelegate.onFailure(error.getMessage());
                Log.i("TAG", "onCancelled: ");
            }
        });
    }

    @Override
    public void onAccept(TrackerDTO trackerDTO, PatientDTO patientDTO) {
        String[] uid = trackerDTO.getPatientEmail().split("\\.");
        String[] myId = patientDTO.getEmail().split(("\\."));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid[0]);
        databaseReference.child("tracker").child(myId[0]).setValue(trackerDTO);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(myId[0]);
        reference.child("request").child(uid[0]).child("acceptance").setValue(1);


        DatabaseReference patientReference = FirebaseDatabase.getInstance().getReference().child("users").child(myId[0]);
        patientReference.child("patient").child(uid[0]).setValue(patientDTO);
    }

    @Override
    public void onReject(String key, String email) {
        String userId = email.split("\\.")[0];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        reference.child("request").child(key).removeValue();
    }

    @Override
    public void loadPatients(String email) {
        List<PatientDTO> patients = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("users").child(email).child("patient");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patients.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("patientEmail").getValue() != null) {
                        PatientDTO patient = new PatientDTO(dataSnapshot.child("email").getValue().toString()
                                , dataSnapshot.child("patientEmail").getValue().toString()
                                , dataSnapshot.child("name").getValue().toString()
                                , Integer.parseInt(dataSnapshot.child("img").getValue().toString())
                                //, Integer.parseInt(dataSnapshot.child("patientId").getValue())
                        );
                        patients.add(patient);
                    }
                }
                myDelegate.onSuccessPatient(patients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myDelegate.onFailure(error.getMessage());
                Log.i("TAG", "onCancelled: ");
            }
        });
    }

    @Override
    public void loadTrackers(String email) {
        List<TrackerDTO> trackers = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("tracker");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackers.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TrackerDTO tracker = new TrackerDTO(dataSnapshot.child("patientEmail").getValue().toString()
                            , dataSnapshot.child("name").getValue().toString()
                            , dataSnapshot.child("email").getValue().toString()
                            , (Integer.parseInt(dataSnapshot.child("img").getValue().toString()))
                            ,dataSnapshot.child("requestID").getValue().toString()
                    );
                    trackers.add(tracker);
                }
                myDelegate.onSuccessTracker(trackers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void loadPatientMedicationList(String email) {
        Long timeNow = Calendar.getInstance().getTimeInMillis();
        String trackerEmail = email.split("\\.")[0];
        Log.i("AAA", "loadPatientMedicationList: " + email);
        List<MedicationPOJO> medicationPOJOS = new ArrayList<>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").child(trackerEmail).child("medications");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.i("AAA", "Eexits: ");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MedicationPOJO medicationPOJO = dataSnapshot.getValue(MedicationPOJO.class);
                        if (timeNow <= medicationPOJO.getEndDate()
                        ) {
                            medicationPOJOS.add(medicationPOJO);
                        }
                    }
                } else {
                    Log.i("AAA", "NotEexits: ");

                }
                // check if contain or not
                Log.i("AAA", "onDataChange: " + medicationPOJOS.size());
                myDelegate.onSuccessReturnMedicationList(medicationPOJOS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myDelegate.onFailure(error.getMessage());
            }
        });
    }

    @Override
    public void addMedicationListViewNetwork(List<MedicationPOJO> medicationPOJOS, String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(email);
        for (MedicationPOJO meds : medicationPOJOS) {
            String key = String.valueOf(meds.getId());
            databaseReference.child("medications").child(key).setValue(meds);
        }
    }

    @Override
    public void UserExistance(String email) {
        String trackerEmail = email.split("\\.")[0];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.child("email").getValue().toString();
                    String key = email.split("\\.")[0];
                    if (key.equals(trackerEmail)) {
                        exist = true;
                        break;
                    }
                }
                myDelegate.isUserExist(exist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void deleteTracker(String takerEmail, String patientEmail) {
        String patientKey = patientEmail.split("\\.")[0];
        String trackerKey = takerEmail.split("\\.")[0];


        DatabaseReference deletTakerRefrenec = FirebaseDatabase.getInstance().getReference().child("users").child(patientKey);
        deletTakerRefrenec.child("tracker").child(trackerKey).removeValue();

        DatabaseReference deletePatientRefrenec = FirebaseDatabase.getInstance().getReference().child("users").child(trackerKey);
        deletePatientRefrenec.child("patient").child(patientKey).removeValue();

        deletePatientRefrenec.child("request").child(patientKey).removeValue();
    }

    @Override
    public void deleteInPatientMedicationList(String email, String medicationID) {
        String uid = email.split("\\.")[0];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        databaseReference.child("medications").child(medicationID).removeValue();
    }

    @Override
    public void updateMedicationToRoomFromFirebase(String email) {
        updatedMedicationList = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("medications");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updatedMedicationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    updatedMedicationList.add(dataSnapshot.getValue(MedicationPOJO.class));
                }
                myDelegate.onUpdateMedicationFromFirebase(updatedMedicationList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void updatePatientMedicationList(String email, MedicationPOJO medicationPOJO) {
        String emailId = email.split("\\.")[0];

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.child(emailId)
                .child("medications").child(String.valueOf(medicationPOJO.getId()))
                .setValue(medicationPOJO);
        // may be check if failed
    }

    private String handleFireBaseException(Task task) {
        String errorMessage = "";

        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            errorMessage = "weak Password";
        } catch (FirebaseAuthUserCollisionException e) {
            errorMessage = "user already exists";
        } catch (FirebaseAuthInvalidUserException e) {
            errorMessage = "problem in e-mail or password";
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        return errorMessage;
    }

    private void addRegisterInDB(UserDTO user) {
        String uid = user.getEmail().split("\\.")[0];

        FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myDelegate.onSuccess();
                } else {
                    String errorMessage = handleFireBaseException(task);
                    myDelegate.onFailure(errorMessage);
                }
            }
        });
    }
}
