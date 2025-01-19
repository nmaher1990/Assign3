package com.example.sdaassign32022;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/*
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 */
public class OrderTshirt extends Fragment {


    public OrderTshirt() {
        // Required empty public constructor
    }

    //class wide variables
    private String mPhotoPath;
    private View mEditCollect; //editCollect tag
    private Spinner mSpinner;
    private EditText mCustomerName;
    private EditText meditDelivery;
    private ImageView mCameraImage;

    private Boolean mDeliveryOption; //TRUE = delivery, FALSE = collect

    //static keys
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "OrderTshirt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        mCustomerName = root.findViewById(R.id.editCustomer);
        meditDelivery = root.findViewById(R.id.editDeliver);
        mEditCollect = root.findViewById(R.id.editCollect); //editCollect tag
        meditDelivery.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditDelivery.setRawInputType(InputType.TYPE_CLASS_TEXT);

        mCameraImage = root.findViewById(R.id.imageView);
        Button mSendButton = root.findViewById(R.id.sendButton);

        //set a listener on the the camera image
        mCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });

        //set a listener to start the email intent.
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });


        //initialise spinner using the integer array
        mSpinner = root.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);
        mSpinner.setEnabled(true);


        //mSpinner visibility
        meditDelivery.addTextChangedListener(new android.text.TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //methods required fro android.text.TextWatcher
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Hide the spinner when delivery address is not empty
                if (s.toString().trim().isEmpty()) {
                    mSpinner.setVisibility(View.VISIBLE);
                    mEditCollect.setVisibility(View.VISIBLE);
                    mDeliveryOption = Boolean.FALSE;
                } else {
                    mSpinner.setVisibility(View.GONE);
                    mEditCollect.setVisibility(View.GONE);
                    mDeliveryOption = Boolean.TRUE;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //methods required fro android.text.TextWatcher
            }


        });

        //meditDelivery visibility
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = mSpinner.getSelectedItem().toString();

                // If the selected item is not N/A, hide meditDelivery
                if (!selectedItem.equals("N/A")) {
                    meditDelivery.setVisibility(View.GONE);
                    mDeliveryOption = Boolean.FALSE;
                } else {
                    meditDelivery.setVisibility(View.VISIBLE);
                    mDeliveryOption = Boolean.TRUE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case where no item is selected
            }
        });

        return root;
    }


    /**Take a photo note the view is being passed so we can get context because it is a fragment.
    *update this to save the image so it can be sent via email
     */
    private void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mCameraImage.setImageBitmap(photo);

        }
    }

    /*
     * Returns the Email Body Message, update this to handle either collection or delivery
     */
    private String createOrderSummary(View v) {
        String orderMessage = "";
        String deliveryInstruction = meditDelivery.getText().toString();
        String customerName = getString(R.string.customer_name) + " " + mCustomerName.getText().toString();

        orderMessage += customerName + "\n" + "\n" + getString(R.string.order_message_1);
        orderMessage += "\n" + "Deliver my order to the following address: ";
        orderMessage += "\n" + deliveryInstruction;
        orderMessage += "\n" + getString(R.string.order_message_collect) + mSpinner.getSelectedItem().toString() + "days";
        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();

        return orderMessage;
    }

    /**
     * Send email to my-tshirt@sda.ie
     * Does not allow email without a value for name and delivery option
     *
     * @param v The view that triggers this method.
     * @author Nick Maher
     * @version 1.0
     */
    private void sendEmail(View v) {
        //check that Name is not empty, and ask do they want to continue
        String customerName = mCustomerName.getText().toString();
        if (mCustomerName == null || customerName.equals("")) {
            Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();

        } else {
            String editDelivery = meditDelivery.getText().toString();
            String spinner = mSpinner.getSelectedItem().toString();
            if ((meditDelivery == null || editDelivery.equals("")) && spinner.equals("N/A")) {
                Toast.makeText(getContext(), "Please select delivery or collection", Toast.LENGTH_SHORT).show();
            }
            else{
                //send email button onClick
                String CustomerName = mCustomerName.getText().toString();

                String mEmailAddress = getString(R.string.to_email);
                String mEmailSubject = getString(R.string.email_subject);
                String mEmailBody1 = getString(R.string.order_message_1);
                String mEmailBody2 = "";
                String mEmailBody3 = "";
                String mEmailBody4 = getString(R.string.order_message_end);
                String mEmailBody5 = mCustomerName.getText().toString();

                if(mDeliveryOption == true){
                    mEmailBody2 = getString(R.string.order_message_deliver);
                    mEmailBody3 = meditDelivery.getText().toString();
                }
                else{
                    String SpinnerValue = mSpinner.getSelectedItem().toString();
                    mEmailBody2 = getString(R.string.order_message_collect, SpinnerValue);
                }
                String emailBody = mEmailBody1 + "\n" + "\n" + mEmailBody2 + "\n" + mEmailBody3 + "\n" + "\n"  + mEmailBody4 + "\n" + mEmailBody5;



                Intent emailAppIntent = new Intent(Intent.ACTION_SEND);
                emailAppIntent.setType("text/plain");
                emailAppIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mEmailAddress});
                emailAppIntent.putExtra(Intent.EXTRA_SUBJECT, mEmailSubject);
                emailAppIntent.putExtra(Intent.EXTRA_TEXT,emailBody);
                //emailAppIntent.putExtra(Intent.EXTRA_STREAM, (Parcelable) mCameraImage);

                startActivity(Intent.createChooser(emailAppIntent, "Send mail"));

                Log.w("SDA-Assign02-2024", "Email details sent");
                }
            }

        }
    }
