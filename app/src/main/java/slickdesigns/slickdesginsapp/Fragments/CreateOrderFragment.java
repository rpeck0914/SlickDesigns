package slickdesigns.slickdesginsapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import slickdesigns.slickdesginsapp.MainActivity;
import slickdesigns.slickdesginsapp.R;

public class CreateOrderFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private EditText mEnteredName, mEnteredEmail, mEnteredPhoneNumber, mEnteredQty, mEnteredAdditionalComments;
    private Spinner mTypeSpinner, mTypeColorSpinner;
    private String[] mSpinnerTypes = new String[] {"Select A Type", "T-Shirt", "Long-Sleeve", "Sweatshirt"};
    private String[] mSpinnerTypeColor = new String[] {"Select A Color", "Black", "White", "Blue", "Green", "Red"};
    private String mSelectedType, mSelectedTypeColor;
    private ImageView mAddImageButton;
    private Button mSubmitOrderButton;

    public CreateOrderFragment() { }

    public static CreateOrderFragment newInstance(int sectionNumber) {
        CreateOrderFragment newFragment = new CreateOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_order, container, false);

        mEnteredName = (EditText) v.findViewById(R.id.entered_name);
        mEnteredEmail = (EditText) v.findViewById(R.id.entered_email);
        mEnteredPhoneNumber = (EditText) v.findViewById(R.id.entered_phone_number);
        mEnteredQty = (EditText) v.findViewById(R.id.entered_qty);
        mEnteredAdditionalComments = (EditText) v.findViewById(R.id.entered_additional_comments);

        mTypeSpinner = (Spinner) v.findViewById(R.id.type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mSpinnerTypes);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedType = mTypeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTypeColorSpinner = (Spinner) v.findViewById(R.id.type_color_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mSpinnerTypeColor);
        mTypeColorSpinner.setAdapter(adapter1);
        mTypeColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedTypeColor = mTypeColorSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAddImageButton = (ImageView) v.findViewById(R.id.insert_picture_button);
        mSubmitOrderButton = (Button) v.findViewById(R.id.submit_order_button);


        mAddImageButton.setOnClickListener(this);
        mSubmitOrderButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_picture_button:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 111);
                break;

            case R.id.submit_order_button:
                submitOrder();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void submitOrder() {
        //TODO Needs to send out an email and store the order on the database online
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri targetUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            try {
                Cursor cursor = getActivity().getContentResolver().query(targetUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                mAddImageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
