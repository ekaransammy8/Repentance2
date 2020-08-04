package www.digitalexperts.church_traker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.adapter.vpageradapter;
import www.digitalexperts.church_traker.databinding.FragmentHomeBinding;
import www.digitalexperts.church_traker.databinding.FragmentLiveBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Live#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Live extends Fragment {
        FragmentLiveBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Live() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Live.
     */
    // TODO: Rename and change types and number of parameters
    public static Live newInstance(String param1, String param2) {
        Live fragment = new Live();
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
        binding = FragmentLiveBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.viewPager.setAdapter(new vpageradapter(getParentFragment()));

        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(binding.tablayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Listen");
                        tab.setIcon(R.drawable.ic_baseline_wifi_tethering_24);
                        break;
                    }
                    case 1:{
                        tab.setText("Live events");
                        tab.setIcon(R.drawable.ic_baseline_slow_motion_video_24);
                        break;
                    }

                }
            }
        });
        tabLayoutMediator.attach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}