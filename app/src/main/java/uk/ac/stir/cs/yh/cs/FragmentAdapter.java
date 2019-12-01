package uk.ac.stir.cs.yh.cs;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class FragmentAdapter extends FragmentStatePagerAdapter {

    private final int numberOfFragments = 2;

    FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int fragmentIndex) {
        switch (fragmentIndex) {
            case 0:
                return new PickerFragment();
            case 1:
                return new ConversionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfFragments;
    }
}