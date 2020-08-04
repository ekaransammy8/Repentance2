package www.digitalexperts.church_traker.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;



import java.io.File;

import www.digitalexperts.church_traker.databinding.ItemAudioBinding;
import www.digitalexperts.church_traker.models.Myaudio;

public class AudioListAdapter extends ListAdapter<Myaudio, RecyclerView.ViewHolder> {
    private Context context;
    audioclickinterface Audioclickinterface;

    public AudioListAdapter(Context context, audioclickinterface audioclickinterface) {
        super(diffCallback);
        this.context = context;
        Audioclickinterface = audioclickinterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        ItemAudioBinding itemAudioBinding=ItemAudioBinding.inflate(inflater,parent, false);
        return new audioHolder(itemAudioBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Myaudio myAudio = getItem(position);
        audioHolder Holder = (audioHolder) holder;
        Holder.binding.audioPlayer.setAudioTarget(Uri.parse(myAudio.path));
        Holder.binding.audioName.setText(myAudio.name);
        Holder.binding.shareaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename=myAudio.name;
                File filelocation = new File(Environment.getExternalStorageDirectory() + File.separator + "recordings/",filename);
                Uri path = FileProvider.getUriForFile(context, "www.digitalexperts.church_traker.fileprovider", filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // set the type to 'email'
                emailIntent .setType("audio/mp3");
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // the attachment
                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
                context.startActivity(Intent.createChooser(emailIntent, "Send To"));


            }
        });
        Holder.binding.deleteaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = myAudio.path;
                File file = new File(uri);
                file.delete();
                Audioclickinterface.onDelete(position);



            }
        });
    }

    public class audioHolder extends RecyclerView.ViewHolder
    {
       ItemAudioBinding binding;
        public audioHolder(@NonNull ItemAudioBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }
    private static DiffUtil.ItemCallback<Myaudio> diffCallback=new DiffUtil.ItemCallback<Myaudio>() {
        @Override
        public boolean areItemsTheSame(@NonNull Myaudio oldItem, @NonNull Myaudio newItem) {
            return  oldItem.path==newItem.path;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Myaudio oldItem, @NonNull Myaudio newItem) {
            return oldItem.equals(newItem);
        }
    };


    public interface audioclickinterface{
        public void onDelete(int position);

    }

}
