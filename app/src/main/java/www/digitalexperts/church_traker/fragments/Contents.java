package www.digitalexperts.church_traker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.adapter.Listaadapter;
import www.digitalexperts.church_traker.databinding.ContentMainBinding;
import www.digitalexperts.church_traker.databinding.FragmentContentsBinding;
import www.digitalexperts.church_traker.databinding.FragmentHomeBinding;
import www.digitalexperts.church_traker.models.Folderz;
import www.digitalexperts.church_traker.viewmodel.Contentviewmodel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contents extends Fragment {
    FragmentContentsBinding binding;
    Contentviewmodel contentviewmodel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contents() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contents.
     */
    // TODO: Rename and change types and number of parameters
    public static Contents newInstance(String param1, String param2) {
        Contents fragment = new Contents();
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
        binding = FragmentContentsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String x =getArguments().getString("tsend");
        String y =getArguments().getString("f");
        contentviewmodel=new ViewModelProvider(this).get(Contentviewmodel.class);

        checkprogress();
        checkemptyresults();
        //recycleview
        binding.rvcontent.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rvcontent.setLayoutManager(manager);
        //setting details
        contentviewmodel.filterTextAll.setValue(x+"^"+y);
        contentviewmodel.getCrslts().observe(getViewLifecycleOwner(), new Observer<List<Folderz>>() {
            @Override
            public void onChanged(List<Folderz> folderzs) {
                Listaadapter listaadapter=new Listaadapter(folderzs,getContext(),"1");
                binding.rvcontent.setAdapter(listaadapter);
            }
        });


    }
    private void checkprogress() {
        contentviewmodel.getstatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.pgbar.setVisibility(View.VISIBLE);
                } else {
                    binding.pgbar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void checkemptyresults() {
       contentviewmodel.getrespo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.status.setVisibility(View.VISIBLE);
                binding.status.setText(s);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}