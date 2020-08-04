package www.digitalexperts.church_traker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import www.digitalexperts.church_traker.Repo.Repo;
import www.digitalexperts.church_traker.models.Folderz;

public class Teachingsviewmodel extends ViewModel {
    //Repository
    Repo repo;
    public LiveData<List<Folderz>>mrslts ;

    public Teachingsviewmodel() {
        repo =new Repo();
    }
    public LiveData<Boolean> getstatus(){
        return repo.isexcuted();
    }
    public LiveData<String> getrespo(){ return  repo.getresponse();}

    public LiveData<List<Folderz>> getMrslts() {
        return repo.getmainfolders();
    }
}
