package www.digitalexperts.church_traker.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;



import www.digitalexperts.church_traker.fragments.Radiostream;
import www.digitalexperts.church_traker.fragments.wvinfo;

public class vpageradapter extends FragmentStateAdapter {

    public vpageradapter(@NonNull Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new Radiostream();
            default:
                String c="https://repentanceandholinessinfo.com/livevent.php";
                Bundle bundle=new Bundle();
                bundle.putString("web", c);
                Fragment fragmentmain=new wvinfo();
                fragmentmain.setArguments(bundle);
                return  fragmentmain;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
