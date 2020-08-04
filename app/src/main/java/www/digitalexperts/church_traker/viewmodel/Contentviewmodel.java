package www.digitalexperts.church_traker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import www.digitalexperts.church_traker.Repo.Repo;
import www.digitalexperts.church_traker.models.Folderz;

public class Contentviewmodel extends ViewModel {
    //Repository
    Repo repo;
    //contents
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    //contentresults
    public LiveData<List<Folderz>> crslts ;

    public Contentviewmodel() {
        repo =new Repo();
        init();
    }
    private  void init(){
        crslts= Transformations.switchMap(filterTextAll, input -> {
            String[] separated = input.split("\\^");
            separated[0] = separated[0].trim(); // this will contain "Fruit"
            separated[1] = separated[1].trim();
            return  repo.getcontents(separated[0] ,separated[1]);
        });
    }
    public LiveData<Boolean> getstatus(){
        return repo.isexcuted();
    }
    public LiveData<String> getrespo(){ return  repo.getresponse();}

    public LiveData<List<Folderz>> getCrslts() {
        return crslts;
    }
}
