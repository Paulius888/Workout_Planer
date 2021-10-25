package com.example.workoutplaner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class RegularExerciseFragment extends Fragment {

    private Exercise exercise;
    private VPAdapter adapter;
    private Button button;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_regular_exercise, container, false);
        exercise = (Exercise) getArguments().getSerializable("exercise");
        TextView exercise1 = rootView.findViewById(R.id.repsSets1);
        addExerciseName(exercise1, exercise.getName());
        button = rootView.findViewById(R.id.button2);
        checkBox1=rootView.findViewById(R.id.checkBox);
        checkBox2=rootView.findViewById(R.id.checkBox2);
        checkBox3=rootView.findViewById(R.id.checkBox3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int totalamount=0;
//                String result = "";
//                result += "Selected Items:";
//                if(checkBox1.isChecked()){
//                    result += "\nch1 done";
//                    totalamount+=1;
//                }
//                if(checkBox2.isChecked()){
//                    result += "\nch2 done";
//                    totalamount+=1;
//                }
//                if(checkBox3.isChecked()){
//                    result += "\nch3 done";
//                    totalamount+=1;
//                }
//                result +=  "\nTotal: "+totalamount+"Rs";
//                //Displaying the message on the toast
//                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                ((ExercisesActivity)getActivity()).setExerciseState(true, exercise.getName());
            }
        });
        return rootView;
    }

    public void addExerciseName(TextView tv, String name){
        tv.setText(name);
    }

}