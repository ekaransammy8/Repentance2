package www.digitalexperts.church_traker.fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.FragmentTeachingsBinding;
import www.digitalexperts.church_traker.databinding.FragmentWvinfoBinding;

import static android.content.Context.DOWNLOAD_SERVICE;


public class wvinfo extends Fragment {
    FragmentWvinfoBinding binding;
    private String mypage="";
    private String page="";
    private String dpage="";



    public wvinfo() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWvinfoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String myerrorpage = "file:///android_asset/android/errorpage.html";
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Bundle bundle = getArguments();
        if (bundle != null) {
            page = bundle.getString("web");
            dpage=bundle.getString("dweb");
            if(page==null){
                mypage = dpage;
            }else {
                mypage = page;

            }

        }


        binding.wvvs.getSettings().setJavaScriptEnabled(true);
        binding.wvvs.getSettings().setAllowFileAccess(true);
        binding.wvvs.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        binding.wvvs.clearHistory();
        binding.wvvs.clearCache(false);
        binding.wvvs.requestFocus(View.FOCUS_DOWN);
        binding.wvvs.setFocusable(true);
        binding.wvvs.setFocusableInTouchMode(true);
        binding.wvvs.getSettings().setDomStorageEnabled(true);
        binding.wvvs.getSettings().setDatabaseEnabled(true);
        binding.wvvs.getSettings().setAppCacheEnabled(true);
        binding.wvvs.getSettings().setLoadWithOverviewMode(true);
        binding.wvvs.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        binding.wvvs.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.wvvs.getSettings().setJavaScriptEnabled(true);
        if(page==null){
            String doc="<iframe src='http://docs.google.com/viewer?url=https://repentanceandholinessinfo.com/documents/"+ mypage+"&embedded=true'"+
                    " width='100%' height='100%' style='border: none;'></iframe>";
            binding.wvvs.loadData( doc , "text/html",  "UTF-8");
        }else {
            binding.wvvs.loadUrl(mypage);
        }

        binding.wvvs.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= 19) {
            binding.wvvs.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 19) {
            binding.wvvs.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        binding.wvvs.canGoBack();
        binding.wvvs.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        &&  binding.wvvs.canGoBack()) {
                    binding.wvvs.goBack();
                    return true;
                }
                return false;
            }
        });
        binding.wvvs.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.pgall.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.pgall.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                binding.pgall.setVisibility(View.VISIBLE);
                binding.wvvs.loadUrl(myerrorpage);
                Toast toast = Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG);
                toast.show();
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setMessage("please check your internet connectivity");
                alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                alert.show();

            }
        });

        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(getActivity(), permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(getActivity(), permission_list, 1);
        }
        binding.wvvs.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}