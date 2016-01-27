package slickdesigns.slickdesginsapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import slickdesigns.slickdesginsapp.MainActivity;
import slickdesigns.slickdesginsapp.R;

public class ContactUsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ContactUsFragment() { }

    public static ContactUsFragment newInstance(int sectionNumber) {
        ContactUsFragment newFragment = new ContactUsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
