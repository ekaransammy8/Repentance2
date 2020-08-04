package www.digitalexperts.church_traker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.adapter.AudioListAdapter;
import www.digitalexperts.church_traker.databinding.FragmentFilezBinding;
import www.digitalexperts.church_traker.models.Myaudio;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link filez#newInstance} factory method to
 * create an instance of this fragment.
 */
public class filez extends Fragment implements  AudioListAdapter.audioclickinterface{
    FragmentFilezBinding binding;
    private String filename;
    ArrayList<Myaudio> audioArrayList = new ArrayList<>();
    AudioListAdapter audioListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public filez() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment filez.
     */
    // TODO: Rename and change types and number of parameters
    public static filez newInstance(String param1, String param2) {
        filez fragment = new filez();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFilezBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        binding.rvfiles.setLayoutManager(manager);
        audioListAdapter=new AudioListAdapter(getContext(),this);
        binding.rvfiles.setAdapter(audioListAdapter);
        Loadsongs();
        if(audioArrayList.size()==0){
            binding.txtError.setVisibility(View.VISIBLE);
            binding.txtError.setText("You have no music \n please download music \n to view here");
        }else {
            binding.txtError.setVisibility(View.GONE);
        }






    }
    private void Loadsongs(){
        filename= Environment.getExternalStorageDirectory() + File.separator + "recordings/";
        File externalStorageDirectory = new File (filename);
        File folder = new File(externalStorageDirectory.getAbsolutePath() );
        /* File file[] = folder.listFiles();*/
        File[] file = folder.listFiles();
        Arrays.sort( file, new Comparator()
        {
            public int compare(Object o1, Object o2) {

                if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                    return -1;
                } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }

        });

        if (file.length != 0) {
            for (int i = 0; i < file.length; i++) {
                //here populate your list
               /* Log.d(Tag,file[i].getAbsolutePath());
                Log.d(Tag,file[i].getName());*/
                audioArrayList.add(new Myaudio(file[i].getName(),file[i].getAbsolutePath()));
                audioListAdapter.submitList(audioArrayList);

            }
        } else {
            Toast.makeText(getContext(), "No Music downloaded yet", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onDelete(int position) {
        audioArrayList.remove(position);
        audioListAdapter.notifyItemRemoved(position);
        audioListAdapter.submitList(audioArrayList);
    }
}