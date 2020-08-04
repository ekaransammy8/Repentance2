package www.digitalexperts.church_traker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;


import java.util.ArrayList;
import java.util.List;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.ChurchinfBinding;
import www.digitalexperts.church_traker.databinding.TemplatefileBinding;
import www.digitalexperts.church_traker.models.Church;


public class Churchadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List churchlist;
    private Context context;

    private static final int AD_TYPE = 1;
    private static final int DEFAULT_VIEW_TYPE = 2;

    public Churchadapter(List churchlist, Context context) {
        this.churchlist = churchlist;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == AD_TYPE) {
            TemplatefileBinding templatefileBinding = TemplatefileBinding.inflate(inflater, parent, false);
            return new adholderc(templatefileBinding);
        } else {
            ChurchinfBinding churchinfBinding= ChurchinfBinding.inflate(inflater, parent, false);
            return new churchHolder(churchinfBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){
        if (!(holder instanceof churchHolder)) {
            return;
        }
            final churchHolder Holder = (churchHolder) holder;
            final int itemPosition =position - position / 3;
            final Church church = (Church)churchlist.get(itemPosition);
            Glide.with(Holder.binding.ivchurch)
                    .load("https://repentanceandholinessinfo.com/photos/" + church.photo)
                    .into(Holder.binding.ivchurch);

            Holder.binding.tvcname.setText("Church: " + church.name);
            Holder.binding.tvlocation.setText("Location: " + church.location);
            Holder.binding.ivcv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(church.id));
                    bundle.putString("name", church.name);
                    bundle.putString("location", church.location);
                    bundle.putString("photo", church.photo);
                    Navigation.findNavController(v).navigate(R.id.action_nav_home_to_singleviewchurch, bundle);

                }
            });

    }

  @Override
    public int getItemCount() {

//        int count = super.getItemCount();
        //int count = getCurrentList().size();
            if(churchlist!=null){
                int itemCount = churchlist.size();
                itemCount += itemCount / 3;
                return itemCount;
            }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (position>1 && position % 3 == 0) {
            return AD_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }

    public static class churchHolder extends RecyclerView.ViewHolder {
        ChurchinfBinding binding;

        public churchHolder(@NonNull ChurchinfBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public static class adholderc extends RecyclerView.ViewHolder {
        TemplatefileBinding binding;

        public adholderc(@NonNull final TemplatefileBinding b) {
            super(b.getRoot());
            binding = b;
            AdRequest adRequest = new AdRequest.Builder().build();
            AdLoader adLoader = new AdLoader.Builder(binding.getRoot().getContext(), "ca-app-pub-4814079884774543/2277771600")
                    .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.WHITE)).build();
                            binding.myTemplatebc.setStyles(styles);
                            binding.myTemplatebc.setNativeAd(unifiedNativeAd);
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Handle the failure by logging, altering the UI, and so on.
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            binding.myTemplatebc.setVisibility(View.VISIBLE);
                        }
                    })
                    .build();
            if (adRequest != null && binding.myTemplatebc != null) {
                adLoader.loadAd(adRequest);
            }

        }

    }

    private static DiffUtil.ItemCallback<Church> Diff_callback = new DiffUtil.ItemCallback<Church>() {
        @Override
        public boolean areItemsTheSame(@NonNull Church oldItem, @NonNull Church newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Church oldItem, @NonNull Church newItem) {
            return oldItem.equals(newItem);
        }
    };
}
