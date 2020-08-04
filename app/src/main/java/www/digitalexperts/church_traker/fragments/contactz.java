package www.digitalexperts.church_traker.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.FragmentContactzBinding;
import www.digitalexperts.church_traker.databinding.FragmentRadiostreamBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link contactz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class contactz extends Fragment {
    FragmentContactzBinding binding;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public contactz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment contactz.
     */
    // TODO: Rename and change types and number of parameters
    public static contactz newInstance(String param1, String param2) {
        contactz fragment = new contactz();
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
        binding = FragmentContactzBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkForPhonePermission();

        binding.p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = binding.p1.getText().toString();
                comunicate("call", ph);
            }
        });
        binding.p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = binding.p2.getText().toString();
                comunicate("call", ph);
            }
        });
        binding.p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = binding.p3.getText().toString();
                comunicate("call", ph);
            }
        });
        binding.p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph =binding.p4.getText().toString();
                comunicate("call", ph);
            }
        });

        binding.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = binding.r1.getText().toString();
                comunicate("call", ph);
            }
        });
       binding.r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph =binding.r2.getText().toString();
                comunicate("call", ph);
            }
        });
        binding.s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph =  binding.s1.getText().toString();
                comunicate("sms", ph);
            }
        });

        binding.e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph =   binding.e1.getText().toString();
                comunicate("email", ph);
            }
        });
       binding.e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = binding.e2.getText().toString();
                comunicate("email", ph);
            }
        });
    }

    private void comunicate(final String type, final String number) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Do you wish to " + type + " this number");
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (type.equals("call")) {
                    String phoneno = "tel:" + number;
                    Intent dailintent = new Intent(Intent.ACTION_CALL);
                    dailintent.setData(Uri.parse(phoneno));
                    try {
                        startActivity(dailintent);

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(),
                                "phone app failed, please try again later.", Toast.LENGTH_SHORT).show();
                    }

                } else if (type.equals("sms")) {
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", "coverse with us");
                    startActivity(intent);
                } else if (type.equals("email")) {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", number, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });
        alert.show();

    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            Toast.makeText(getContext(), "call permission not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }

    }
}