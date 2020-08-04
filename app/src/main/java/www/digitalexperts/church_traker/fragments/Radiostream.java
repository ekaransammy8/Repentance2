package www.digitalexperts.church_traker.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.luseen.spacenavigation.SpaceItem;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.FragmentRadiostreamBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Radiostream#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Radiostream extends Fragment {
    FragmentRadiostreamBinding binding;
    private Handler handler;
    private boolean isPlaying = false;

    private static final String TAG = "Radiostuff";
    private SimpleExoPlayer exoPlayer;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private MediaRecorder recorder = null;


    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};

    final int BITS_PER_SAMPLE = 16;       // 16-bit data
    final int NUMBER_CHANNELS = 1;        // Mono
    final int COMPRESSION_AMOUNT = 8;

    private String filenamea;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Radiostream() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Radiostream.
     */
    // TODO: Rename and change types and number of parameters
    public static Radiostream newInstance(String param1, String param2) {
        Radiostream fragment = new Radiostream();
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
        binding = FragmentRadiostreamBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url = "https://s3.radio.co/s97f38db97/listen";
        prepareExoPlayerFromURL(Uri.parse(url));

        fileName = Environment.getExternalStorageDirectory() + File.separator + "recordings/";

        //Create  folder if it does not exist
        File exportDir = new File(fileName);

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }


        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);



        binding.playnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File externalStorageDirectory = new File(fileName);
                File folder = new File(externalStorageDirectory.getAbsolutePath());
                File file[] = folder.listFiles();
                if (file.length != 0) {
                    Navigation.findNavController(v).navigate(R.id.action_live_to_filez);

                } else {
                    //no file available
                    Toast.makeText(getContext(), "You have no recordings currently", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int sec = c.get(Calendar.MILLISECOND);
                int date = c.get(Calendar.DAY_OF_MONTH);
                int m = c.get(Calendar.MONTH);
                int y = c.get(Calendar.YEAR);
                String dates = date + "_" + (m + 1) + "_" + y + "_" + sec;
                File file = new File(fileName, dates + ".m4a");
                filenamea = file.getAbsolutePath();
                binding.txtrecord.setText("recording...");
                startRecording();
                binding.spt.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "recording has started: for the best experience please remove your headphones", Toast.LENGTH_LONG).show();
            }
        });

        binding.spt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtrecord.setText("record");
                stopRecording();
                Toast.makeText(getContext(), "recording has stopped", Toast.LENGTH_SHORT).show();
                binding.spt.setVisibility(View.GONE);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adViewrc.loadAd(adRequest);
    }

    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.i(TAG, "onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(TAG, "onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG, "onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(TAG, "onPlayerStateChanged: playWhenReady = " + String.valueOf(playWhenReady)
                    + " playbackState = " + playbackState);
            switch (playbackState) {
                case ExoPlayer.STATE_ENDED:
                    Log.i(TAG, "Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
                    exoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
                    binding.pgbar.setVisibility(View.GONE);
                    binding.rec.setVisibility(View.VISIBLE);
                    binding.txtrecord.setVisibility(View.VISIBLE);
                    Log.i(TAG, "ExoPlayer ready! pos: " + exoPlayer.getCurrentPosition()
                            + " max: " + stringForTime((int) exoPlayer.getDuration()));
                    setProgress();
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    Log.i(TAG, "Playback buffering!");
                    binding.pgbar.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_IDLE:
                    Log.i(TAG, "ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG, "onPlaybackError: " + error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.i(TAG, "onPositionDiscontinuity");
        }
    };
    private void startRecording() {

        final int uncompressedBitRate = 8000 * BITS_PER_SAMPLE * NUMBER_CHANNELS;
        final int encodedBitRate = uncompressedBitRate  / COMPRESSION_AMOUNT;

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP );
        recorder.setAudioSamplingRate(8000);
        recorder.setAudioEncodingBitRate(encodedBitRate);
        recorder.setOutputFile( filenamea);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();
    }
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        /*if (!permissionToRecordAccepted ) finish();*/

    }
    private void prepareExoPlayerFromURL(Uri uri) {

        TrackSelector trackSelector = new DefaultTrackSelector();

        LoadControl loadControl = new DefaultLoadControl();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "wandi"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
        exoPlayer.addListener(eventListener);
        exoPlayer.prepare(audioSource);
        initMediaControls();
    }

    private void initMediaControls() {
        initPlayButton();
        initSeekBar();
    }

    private void initPlayButton() {
        binding.btnPlay.requestFocus();
        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayPause(!isPlaying);
            }
        });
    }

    /**
     * Starts or stops playback. Also takes care of the Play/Pause button toggling
     *
     * @param play True if playback should be started
     */
    private void setPlayPause(boolean play) {
        isPlaying = play;
        exoPlayer.setPlayWhenReady(play);
        if (!isPlaying) {
            binding.btnPlay.setImageResource(android.R.drawable.ic_media_play);
        } else {
            setProgress();
            binding.btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        }
    }


    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private void setProgress() {
        binding.mediacontrollerProgress.setProgress(0);
        binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
        binding.timeCurrent.setText(stringForTime((int) exoPlayer.getCurrentPosition()));
        binding.playerEndTime.setText(stringForTime((int) exoPlayer.getDuration()));

        if (handler == null) handler = new Handler();
        //Make sure you update Seekbar on UI thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && isPlaying) {
                    binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
                    int mCurrentPosition = (int) exoPlayer.getCurrentPosition() / 1000;
                    binding.mediacontrollerProgress.setProgress(mCurrentPosition);
                    binding.timeCurrent.setText(stringForTime((int) exoPlayer.getCurrentPosition()));
                    binding.playerEndTime.setText(stringForTime((int) exoPlayer.getDuration()));

                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void initSeekBar() {
        binding.mediacontrollerProgress.requestFocus();

        binding.mediacontrollerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }

                exoPlayer.seekTo(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.mediacontrollerProgress.setMax(0);
        binding.mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        exoPlayer.release();
        exoPlayer = null;

    }
}