package www.digitalexperts.church_traker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.adapter.Churchadapter;
import www.digitalexperts.church_traker.databinding.FragmentHomeBinding;
import www.digitalexperts.church_traker.models.Church;
import www.digitalexperts.church_traker.viewmodel.Churchviewmodel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    FragmentHomeBinding binding;
    Churchviewmodel churchviewmodel;
    Churchadapter churchadapter;
    final String Tag=this.getClass().getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        churchviewmodel = new ViewModelProvider(this).get(Churchviewmodel.class);

        checkprogress();
        checkemptyresults();
        //recycleview
        binding.rvchurches.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rvchurches.setLayoutManager(manager);
        //adapter

        binding.rvchurches.setNestedScrollingEnabled(false);



        //when button is clicked
        binding.srchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = binding.srchquery.getText().toString().trim();
                churchviewmodel.filterTextAll.setValue(query);
            }
        });
        //initialload
        churchviewmodel.filterTextAll.setValue("nairobi");
        churchviewmodel.getChrslts().observe(getViewLifecycleOwner(), new Observer<List<Church>>() {
            @Override
            public void onChanged(List<Church> churches) {
                //Log.d(Tag, String.valueOf(churches));
                churchadapter = new Churchadapter(churches,getContext());
                binding.rvchurches.setAdapter(churchadapter);


            }
        });





    }

    private void checkprogress() {
        churchviewmodel.getstatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
        churchviewmodel.getrespo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.status.setVisibility(View.VISIBLE);
                binding.status.setText(s);
                if(s.contains("Check")){
                    binding.status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            churchviewmodel.filterTextAll.setValue("");
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}