package www.digitalexperts.church_traker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import www.digitalexperts.church_traker.Repo.Repo;
import www.digitalexperts.church_traker.models.Folderz;

public class Pdfviewmodel extends ViewModel {
    //Repository
    Repo repo;
    public LiveData<List<Folderz>> pdfrslts ;

    public Pdfviewmodel() {
        repo =new Repo();
    }
    public LiveData<Boolean> getstatus(){
        return repo.isexcuted();
    }
    public LiveData<String> getrespo(){ return  repo.getresponse();}

    public LiveData<List<Folderz>> getPdfrslts() {
        return repo.getpdf();
    }
}
