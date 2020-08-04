package www.digitalexperts.church_traker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.List;

import www.digitalexperts.church_traker.R;
import www.digitalexperts.church_traker.databinding.ChurchinfBinding;
import www.digitalexperts.church_traker.databinding.ListinfBinding;
import www.digitalexperts.church_traker.databinding.TemplatefileBinding;
import www.digitalexperts.church_traker.models.Folderz;

public class Listaadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Folderz> xlist;
    private Context context;
    private String status;


    private static final int AD_TYPE = 1;
    private static final int DEFAULT_VIEW_TYPE = 2;

    public Listaadapter(List<Folderz> xlist, Context context, String status) {
        this.xlist = xlist;
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == AD_TYPE) {
            TemplatefileBinding templatefileBinding = TemplatefileBinding.inflate(inflater, parent, false);
            return new Churchadapter.adholderc(templatefileBinding);
        } else {
            ListinfBinding listinfBinding=ListinfBinding.inflate(inflater, parent, false);
            return new listholder(listinfBinding);
        }

    }

    @Override
    public void  onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)  {
        if (!(holder instanceof listholder)) {
            return;
        }

        listholder Holder = (listholder) holder;
        final int itemPosition = position - position / 4 ;
        final Folderz folder=(Folderz) xlist.get(itemPosition );



        Holder.binding.ltitle.setText(folder.title);

            if(folder.type.equals("doc")|| folder.type.equals("docx") ){
                Holder.binding.ivl.setImageResource(R.drawable.wordicon);
            }else if(folder.type.equals("pdf")){
                Holder.binding.ivl.setImageResource(R.drawable.pdficon);
            }else if (folder.type.equals("ppt")||folder.type.equals("pptx") ){
                Holder.binding.ivl.setImageResource(R.drawable.ppticon);
            }else if(folder.type.equals("jpg")) {
                Holder.binding.ivl.setImageResource(R.drawable.pdfimg);
            }else{
                Holder.binding.ivl.setImageResource(R.drawable.readicon);
            }

        Holder.binding.cvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("dweb", folder.document);
                Navigation.findNavController(v).navigate(R.id.action_contents_to_wvinfo, bundle);

            }
        });



    }

    @Override
    public int getItemCount() {
        if(xlist!=null){
            int itemCount = xlist.size();
            itemCount += itemCount / 4 ;
            return itemCount;
        }
        return 0;
    }
    @Override
    public int getItemViewType(int position) {

        if (position>1 && position % 4 == 0) {
            return AD_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }

    public static  class listholder extends RecyclerView.ViewHolder {
      ListinfBinding binding;
        public listholder(@NonNull ListinfBinding b) {
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
}
