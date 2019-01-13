package com.ma.se.hospitato;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;



public class DisplayED extends Fragment {

    private Hospital Hospitals;
    private TextView Name;
    private TextView Address;
    private TextView PhoneNumber;
    private HashMap<String, Boolean> Departments;
    private HashMap<String, String> Coordinate;
    private static DisplayED fragment;

    public void setED(Hospital Hospitals) {this.Hospitals=Hospitals;}
    public Hospital getED(){return this.Hospitals;}
    public DisplayED(){}

    public static DisplayED newInstance(Hospital Hospitals){
        Log.d("mostra",Hospitals.getName());
        fragment = new DisplayED();
        Bundle args = new Bundle();
        args.putParcelable("Hospitals", Hospitals);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hospitals = getArguments().getParcelable("Hospitals");
        Log.d("Created", "DisplayED");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_ed,container,false);
        Name=view.findViewById(R.id.ED_name_f);
        Address=view.findViewById(R.id.ED_address_f);
        PhoneNumber=view.findViewById(R.id.PhoneNumber);
        Name.setText(Hospitals.getName());
        Address.setText(Hospitals.getAddress());
        PhoneNumber.setText(Hospitals.getPhoneNumber());
        return view;
    }





}
