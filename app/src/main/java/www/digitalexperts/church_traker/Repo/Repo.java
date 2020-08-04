package www.digitalexperts.church_traker.Repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kosalgeek.android.json.JsonConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import www.digitalexperts.church_traker.models.Church;
import www.digitalexperts.church_traker.models.eandp;
import www.digitalexperts.church_traker.models.Folderz;
import www.digitalexperts.church_traker.network.ApiClient;
import www.digitalexperts.church_traker.network.ApiInterface;

public class Repo {
    private ArrayList<Church> clist = new ArrayList<>();
    //getting churches
    private MutableLiveData<List<Church>> churchmutable;
    //getting pastors
    private MutableLiveData<List<eandp>> pastorsmutable;
    //getting main folders
    private MutableLiveData<List<Folderz>> mainfoldersmutable;
    //getting pdf
    private MutableLiveData<List<Folderz>> pdffoldersmutable;
    //getting contents
    private MutableLiveData<List<Folderz>> contentfoldersmutable;

    private static ApiInterface apiInterface;
    private MutableLiveData<Boolean> progressbarObservable = new MutableLiveData<Boolean>();
    private MutableLiveData<String> statusObservable = new MutableLiveData<String>();
    final String Tag=this.getClass().getName();

    public Repo() {
    }

    public MutableLiveData<List<Church>> getchurch(String c) {
        if (churchmutable == null) {
            churchmutable = new MutableLiveData<List<Church>>();
        }
        progressbarObservable.setValue(true);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody>call = apiInterface.searchresults(c);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressbarObservable.setValue(false);


                try {
                   String k=response.body().string();
                    Log.d(Tag,k);

                    if(k.contains("none")){
                        statusObservable.setValue("No results Found for "+ c+"\n ");

                    }else{
                        statusObservable.setValue("");
                        ArrayList<Church> clist= new JsonConverter<Church>().toArrayList(k, Church.class);
                        Log.d(Tag, String.valueOf(clist));
                        churchmutable.setValue(clist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbarObservable.setValue(false);
                if (t instanceof HttpException) {
                    // We had non-2XX http error
                    statusObservable.setValue("Something  went wrong please try again later ");
                } else if (t instanceof IOException) {
                    // A network or conversion error happened
                    statusObservable.setValue("Check your internet connection \n click to retry");
                } else {
                    statusObservable.setValue("Something  went wrong please try again later ");
                }
            }
        });
        return churchmutable;
    }

    public MutableLiveData<List<eandp>> getpastors(String c) {
        if(pastorsmutable==null){
            pastorsmutable=new MutableLiveData<List<eandp>>();
        }
        progressbarObservable.setValue(true);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody>call = apiInterface.getingpastors(c);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressbarObservable.setValue(false);
                try {
                    String k=response.body().string();
                    Log.d(Tag,k);

                        statusObservable.setValue("");
                        ArrayList<eandp> navdetails = new JsonConverter<eandp>().toArrayList(k, eandp.class);
                        Log.d(Tag, String.valueOf( navdetails));
                        pastorsmutable.setValue(navdetails);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbarObservable.setValue(false);
                if (t instanceof HttpException) {
                    // We had non-2XX http error
                    statusObservable.setValue("Something  went wrong please try again later ");
                } else if (t instanceof IOException) {
                    // A network or conversion error happened
                    statusObservable.setValue("Check your internet connection \n click to retry");
                } else {
                    statusObservable.setValue("Something  went wrong please try again later ");
                }
            }
        });
        return pastorsmutable;
    }
    public MutableLiveData<List<Folderz>> getmainfolders() {
        if(mainfoldersmutable==null){
            mainfoldersmutable=new MutableLiveData<List<Folderz>>();
        }
        progressbarObservable.setValue(true);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody>call = apiInterface.getingteachings("teachings");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressbarObservable.setValue(false);
                try {
                    String k=response.body().string();
                    Log.d(Tag,k);

                    statusObservable.setValue("");
                    ArrayList<Folderz> flist= new JsonConverter<Folderz>().toArrayList(k, Folderz.class);
                    Log.d(Tag, String.valueOf( flist));
                    mainfoldersmutable.setValue(flist);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbarObservable.setValue(false);
                if (t instanceof HttpException) {
                    // We had non-2XX http error
                    statusObservable.setValue("Something  went wrong please try again later ");
                } else if (t instanceof IOException) {
                    // A network or conversion error happened
                    statusObservable.setValue("Check your internet connection \n click to retry");
                } else {
                    statusObservable.setValue("Something  went wrong please try again later ");
                }
            }
        });
        return mainfoldersmutable;
    }
    public MutableLiveData<List<Folderz>> getpdf() {
        if(pdffoldersmutable==null){
            pdffoldersmutable=new MutableLiveData<List<Folderz>>();
        }
        progressbarObservable.setValue(true);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody>call = apiInterface.getingteachings("magazines");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressbarObservable.setValue(false);
                try {
                    String k=response.body().string();
                    Log.d(Tag,k);

                    statusObservable.setValue("");
                    ArrayList<Folderz> flist= new JsonConverter<Folderz>().toArrayList(k, Folderz.class);
                    Log.d(Tag, String.valueOf( flist));
                    pdffoldersmutable.setValue(flist);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbarObservable.setValue(false);
                if (t instanceof HttpException) {
                    // We had non-2XX http error
                    statusObservable.setValue("Something  went wrong please try again later ");
                } else if (t instanceof IOException) {
                    // A network or conversion error happened
                    statusObservable.setValue("Check your internet connection \n click to retry");
                } else {
                    statusObservable.setValue("Something  went wrong please try again later ");
                }
            }
        });
        return pdffoldersmutable;
    }
    public MutableLiveData<List<Folderz>> getcontents(String x ,String y) {
        if(contentfoldersmutable==null){
            contentfoldersmutable=new MutableLiveData<List<Folderz>>();
        }
        progressbarObservable.setValue(true);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody>call = apiInterface.getfoldercontents(x, y);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressbarObservable.setValue(false);
                try {
                    String k=response.body().string();
                    Log.d(Tag,k);

                    statusObservable.setValue("");
                    ArrayList<Folderz> flist= new JsonConverter<Folderz>().toArrayList(k, Folderz.class);
                    Log.d(Tag, String.valueOf( flist));
                    contentfoldersmutable.setValue(flist);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbarObservable.setValue(false);
                if (t instanceof HttpException) {
                    // We had non-2XX http error
                    statusObservable.setValue("Something  went wrong please try again later ");
                } else if (t instanceof IOException) {
                    // A network or conversion error happened
                    statusObservable.setValue("Check your internet connection \n click to retry");
                } else {
                    statusObservable.setValue("Something  went wrong please try again later ");
                }
            }
        });
        return contentfoldersmutable;
    }

    public MutableLiveData<Boolean> isexcuted() {
        return progressbarObservable;
    }
    public MutableLiveData<String> getresponse() {
        return statusObservable;
    }
}
