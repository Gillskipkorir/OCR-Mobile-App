package com.kip.gillz.amigo.Myread;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.UserSessionManager;
import com.kip.gillz.amigo.utils.Tools;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

public class Myreadings extends Fragment {
    UserSessionManager session;
    String Username;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Myreadings() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Myreadings newInstance(String param1, String param2) {
        Myreadings fragment = new Myreadings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view =inflater.inflate(R.layout.fragment_myreadings, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        Username = user.get(UserSessionManager.KEY_ADMIN_USERNAME);
         final TextView tvto,tvfrom;
         Button from,btto,btnview;

        from=  view.findViewById(R.id.datefrom);
        tvfrom=  view.findViewById(R.id.from);
        btto= view.findViewById(R.id.dateto);
        tvto=view.findViewById(R.id.to);
        btnview=view.findViewById(R.id.bt_accept);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cur_calender = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long date_ship_millis = calendar.getTimeInMillis();

                                tvfrom.setText(Tools.getFormattedDateSimple(date_ship_millis));
                            }
                        },
                        cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH)


                );
                //set dark light
                datePicker.setThemeDark(true);
                datePicker.setTitle("Date From");
                datePicker.setCancelColor(getResources().getColor(R.color.red_400));
                datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setMaxDate(cur_calender);
                cur_calender.add(Calendar.DAY_OF_MONTH,-30);
                datePicker.setMinDate(cur_calender);

                datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");            }
        });

        btto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cur_calender = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long date_ship_millis = calendar.getTimeInMillis();
                                tvto.setText(Tools.getFormattedDateSimple(date_ship_millis));
                            }
                        },
                        cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH)

                );

                //set dark light
                datePicker.setThemeDark(true);
                datePicker.setTitle("Date To");
                datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setMaxDate(cur_calender);
                cur_calender.add(Calendar.DAY_OF_MONTH,-30);
                datePicker.setMinDate(cur_calender);
                datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");            }
        });

        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), tvfrom.getText() +" "+ tvto.getText() , Toast.LENGTH_LONG).show();

                if (tvfrom.getText().equals("-"))
                {
                    Toast.makeText(getActivity(), " Choose Start Date " , Toast.LENGTH_LONG).show();
                }

                else if (tvto.getText().equals("-"))
                {
                    Toast.makeText(getActivity(), " Choose Last Date " , Toast.LENGTH_LONG).show();
                }

                else {
            String df =tvfrom.getText().toString();
            String dt = tvto.getText().toString();
               Myreadbacktask aaa =new Myreadbacktask(getActivity());
                aaa.execute(Username,df,dt);

            }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
