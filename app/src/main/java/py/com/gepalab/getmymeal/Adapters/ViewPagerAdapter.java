package py.com.gepalab.getmymeal.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import py.com.gepalab.getmymeal.Fragments.CardFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 3;
    private String paramIn = "";
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String param) {
        super(fragmentActivity);
        paramIn = param;
    }

    @NonNull @Override public Fragment createFragment(int position) {
        return CardFragment.newInstance(position, paramIn);
    }

    @Override public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}