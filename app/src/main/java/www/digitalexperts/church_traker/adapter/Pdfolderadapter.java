package www.digitalexperts.church_traker.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.BannerAdBinding;
import www.digitalexperts.church_traker.databinding.FolderinfBinding;
import www.digitalexperts.church_traker.models.Folderz;

public class Pdfolderadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Folderz> flist;
    public Context context;
    public String outdata;

    private static final int AD_TYPE = 1;
    private static final int DEFAULT_VIEW_TYPE = 2;


    public Pdfolderadapter(List<Folderz> flist, Context context, String outdata) {
        this.flist = flist;
        this.context = context;
        this.outdata = outdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                FolderinfBinding folderinfBinding = FolderinfBinding.inflate(inflater, parent, false);
                return new folderholder(folderinfBinding);
            case AD_TYPE:
                BannerAdBinding bannerAdBinding = BannerAdBinding.inflate(inflater, parent, false);
                return new adholderfl(bannerAdBinding);
            case DEFAULT_VIEW_TYPE:
                FolderinfBinding folderinfBindingx = FolderinfBinding.inflate(inflater, parent, false);
                return new folderholder(folderinfBindingx);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (!(holder instanceof folderholder)) {
            return;
        }

        folderholder Holder = (folderholder) holder;
        final int itemPosition = position - position / 5;
        final Folderz folder = (Folderz) flist.get(itemPosition);
        Holder.binding.fname.setText(folder.folder);
        Holder.binding.fcount.setText(" (" + folder.count + ")");
        Holder.binding.cvf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tsend", outdata);
                bundle.putString("f", folder.folder);
                Navigation.findNavController(v).navigate(R.id.action_pdfs_to_contents, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (flist != null) {
            int itemCount = flist.size();
            itemCount += itemCount / 5;
            return itemCount;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (position > 1 && position % 5 == 0) {
            return AD_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }


    public static class folderholder extends RecyclerView.ViewHolder {
        FolderinfBinding binding;

        public folderholder(FolderinfBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public static class adholderfl extends RecyclerView.ViewHolder {
        BannerAdBinding binding;
        private AdView mAdView;

        public adholderfl( BannerAdBinding  b) {
            super(b.getRoot());
            binding = b;
            AdRequest adRequest = new AdRequest.Builder().build();
            binding.adView.loadAd(adRequest);
        }
    }

}
