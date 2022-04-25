package com.example.medicinereminder.medication_screen.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.medicinereminder.medication_screen.view.MedicationFragmentInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.NetworkValidation;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.repository.RepositoryInterface;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Calendar;
import java.util.List;

public class MedicationFragmentPresenter implements MedicationFragmentPresenterInterface, NetworkDelegate {

    private List<MedicationPOJO> _inActiveMedicines = null;
    private List<MedicationPOJO> _activeMedicines = null;
    private int counter = 0;
    private Context context;
    private RepositoryInterface _repo;
    private MedicationFragmentInterface _medicationFragmentInterface;


    public MedicationFragmentPresenter(Context context, MedicationFragmentInterface medicationFragmentInterface,
                                       LifecycleOwner lifecycleOwner) {
        this._medicationFragmentInterface = medicationFragmentInterface;
        _repo = Repository.getInstance(this, context);
        _repo.setRemoteDelegate(this);
        this.context=context;
        getActiveMedicines(lifecycleOwner);
        getInactiveMedicines(lifecycleOwner);
    }

    public void getActiveMedicines(LifecycleOwner lifecycleOwner){
        _repo.getActiveMedications().observe(lifecycleOwner, medicationPOJOS -> {
            _activeMedicines = medicationPOJOS;
            ifGetAll();
        });
    }

    public void getInactiveMedicines(LifecycleOwner lifecycleOwner){
        _repo.getInactiveMedications().observe(lifecycleOwner, medicationPOJOS -> {
            _inActiveMedicines = medicationPOJOS;
            ifGetAll();
        });
    }

    public void ifGetAll(){
        counter++;
        if(counter == 2)
            _medicationFragmentInterface.showMeds(_activeMedicines.size() != 0 ? true : false,
                    _inActiveMedicines.size() != 0 ? true : false);
    }

    @Override
    public List<MedicationPOJO> getMedicines(int position){
        if(position == 0)
            return _activeMedicines;
        else
            return _inActiveMedicines;
    }

    @Override
    public void deleteMed(MedicationPOJO medication, String email) {
        _repo.deleteMedication(medication);
        if (NetworkValidation.checkShared(context)!=null){
           _repo.deleteInPatientMedicationList(NetworkValidation.checkShared(context),medication.getId());
        }
        _medicationFragmentInterface.refreshRecyclerView();
    }

    @Override
    public void updateMed(MedicationPOJO medication, String email) {
        _repo.updateMedications(medication);
        checkUpdateDatabase(email, medication);
        _medicationFragmentInterface.refreshRecyclerView();
    }

    private void checkDeleteDatabase(String email, String medicationID){
        if(email != null){
            _repo.deleteInPatientMedicationList(email, medicationID);
        }
    }
    private void checkUpdateDatabase(String email, MedicationPOJO medication){
        if(email != null){
            _repo.updatePatientMedicationList(email, medication);
        }
    }

    @Override
    public void onSuccess() {
        _medicationFragmentInterface.refreshRecyclerView();
        Log.i("inside medication","deleted successfully");
    }

    @Override
    public void onFailure(Task<AuthResult> task) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestDTO> requestDTOS) {

    }

    @Override
    public void onSuccessTracker(List<TrackerDTO> trackerDTOS) {

    }

    @Override
    public void onSuccess(boolean response) {

    }

    @Override
    public void onSuccessPatient(List<PatientDTO> patientDTOS) {

    }

    @Override
    public void isUserExist(boolean existance) {

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {
        _medicationFragmentInterface.refreshRecyclerView();
    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }
}
