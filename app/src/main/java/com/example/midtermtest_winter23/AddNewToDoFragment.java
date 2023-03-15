package com.example.midtermtest_winter23;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddNewToDoFragment extends DialogFragment {

    interface FragmentClickListener { // callback interface
        void fragmentSavedATask(Task newTask);
        void fragmentCanceled();
    }
    FragmentClickListener listener;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return new AlertDialog.Builder(requireContext())
//                .setMessage("THis is my first dialog fragment")
//                .setPositiveButton("OK", (dialog, which) -> {} )
//                .create();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_add_new_to_do,container);
        EditText taskText = v.findViewById(R.id.task_id);
        Switch isurgent = v.findViewById(R.id.isargent_switch);
        DatePicker cal = v.findViewById(R.id.datapicker);
        Button save = v.findViewById(R.id.save_id);
        Button cancel = v.findViewById(R.id.cancel_id);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskDate = cal.getDayOfMonth()+"/"+cal.getMonth()+"/"+cal.getYear();
                if (!taskText.getText().toString().isEmpty()) {
                    boolean isa =  isurgent.isChecked()? true: false;
                    Task newTodo = new Task(taskText.getText().toString(), taskDate, isa);
                    listener.fragmentSavedATask(newTodo);
                    dismiss();
                }
                }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fragmentCanceled();
                dismiss();
            }
        });
        return v;
    }
}
