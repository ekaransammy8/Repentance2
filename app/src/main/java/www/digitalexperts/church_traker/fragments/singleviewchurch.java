package www.digitalexperts.church_traker.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import www.digitalexperts.church_traker.databinding.FragmentSingleviewchurchBinding;
import www.digitalexperts.church_traker.models.eandp;
import www.digitalexperts.church_traker.viewmodel.Pastorsviewmodel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link singleviewchurch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class singleviewchurch extends Fragment {
    FragmentSingleviewchurchBinding binding;
    Pastorsviewmodel pastorsviewmodel;
    private  String id;
    private String church;
    private String location;
    private String photo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public singleviewchurch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment singleviewchurch.
     */
    // TODO: Rename and change types and number of parameters
    public static singleviewchurch newInstance(String param1, String param2) {
        singleviewchurch fragment = new singleviewchurch();
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
        binding = FragmentSingleviewchurchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pastorsviewmodel=new ViewModelProvider(this).get(Pastorsviewmodel.class);
            id =getArguments().getString("id");
            church =getArguments().getString("name");
            location =getArguments().getString("location");
            photo=getArguments().getString("photo");
            pastorsviewmodel.filterTextAll.setValue(id);

        checkprogress();
        checkemptyresults();

        Glide.with(binding.chimagez)
                .load("https://repentanceandholinessinfo.com/photos/" + photo)
                .into(binding.chimagez);
        binding.chnamez.setText(church);

        pastorsviewmodel.getPstrslts().observe(getViewLifecycleOwner(), new Observer<List<eandp>>() {
            @Override
            public void onChanged(List<eandp> eandpz) {
                ArrayList<String> title = new ArrayList<String>();
                for (eandp value : eandpz) {
                    title.add(value.event);
                    title.add(value.name);
                    title.add(value.phone);
                }

                binding.chpastorz.setText(title.get(1));
                String phn=title.get(2);
                binding.callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!phn.isEmpty()){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:+254"+phn));
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }
    private void checkprogress() {
        pastorsviewmodel.getstatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.pgbar.setVisibility(View.VISIBLE);

                } else {
                    binding.pgbar.setVisibility(View.GONE);
                    binding.callbtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkemptyresults() {
        pastorsviewmodel.getrespo().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        binding = null;
    }
}