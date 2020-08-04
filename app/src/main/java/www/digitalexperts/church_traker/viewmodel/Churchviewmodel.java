package www.digitalexperts.church_traker.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import www.digitalexperts.church_traker.Repo.Repo;
import www.digitalexperts.church_traker.models.Church;

public class Churchviewmodel extends ViewModel {
    //Repository
    Repo repo;
    //search
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    //searchresults
    public LiveData<List<Church>> chrslts ;
    public Churchviewmodel(){
        repo =new Repo();
        init();
    }
    private  void init(){
       chrslts=Transformations.switchMap(filterTextAll,input -> {
           if(input == null || input.equals("")) {
               return repo.getchurch("nairobi");
           } else {
               return repo.getchurch(input); }
        });
    }
    public LiveData<Boolean> getstatus(){
        return repo.isexcuted();
    }
    public LiveData<String> getrespo(){ return  repo.getresponse();}

    public LiveData<List<Church>> getChrslts() {
        return chrslts;
    }
}
