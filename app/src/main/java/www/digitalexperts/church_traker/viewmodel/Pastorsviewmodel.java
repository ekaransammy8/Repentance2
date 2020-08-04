package www.digitalexperts.church_traker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import www.digitalexperts.church_traker.Repo.Repo;
import www.digitalexperts.church_traker.models.eandp;

public class Pastorsviewmodel extends ViewModel {
    //Repository
    Repo repo;
    //getpastors results
    LiveData<List<eandp>> pstrslts;
    //search with id
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    public Pastorsviewmodel() {
        repo =new Repo();
        init();
    }
    private  void init(){
        pstrslts= Transformations.switchMap(filterTextAll, input -> {
                return repo.getpastors(input);
        });
    }


    public LiveData<Boolean> getstatus(){
        return repo.isexcuted();
    }
    public LiveData<String> getrespo(){ return  repo.getresponse();}

    public LiveData<List<eandp>> getPstrslts() {
        return pstrslts;
    }
}
