package com.agriconnect.agrilink;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.agriconnect.agrilink.fragments.FarmerFragment;
import com.agriconnect.agrilink.fragments.IndustryFragment;
import com.agriconnect.agrilink.fragments.LandlordFragment;
import com.agriconnect.agrilink.fragments.MerchantFragment;

public class ChatViewPagerAdapter extends FragmentStateAdapter {

    public ChatViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ChatViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FarmerFragment();
            case 1:
                return new MerchantFragment();
            case 2:
                return new LandlordFragment();
            case 3:
                return new IndustryFragment();
            default:
                return new FarmerFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}