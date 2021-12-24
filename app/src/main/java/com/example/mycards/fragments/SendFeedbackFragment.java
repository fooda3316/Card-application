package com.example.mycards.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.mycards.social.SocialPresenter;
import com.fooda.mycards.R;
import com.example.mycards.email.GMailSender;
import com.example.mycards.utilits.SharingConnection;

public class SendFeedbackFragment extends Fragment {

    private RadioButton feedback,complaint;
    private EditText editText;
    private Button send,wathts;
    private SocialPresenter presenter;
    private SharingConnection sharingConnection;

    public SendFeedbackFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter=new SocialPresenter();
        feedback=view.findViewById(R.id.rbFeedback);
        complaint=view.findViewById(R.id.rbComplaint);
        complaint=view.findViewById(R.id.rbComplaint);
        editText=view.findViewById(R.id.etDescription);
        send=view.findViewById(R.id.btnSend);
        wathts=view.findViewById(R.id.btnWhatsApp);
        sharingConnection=new SharingConnection(getContext());
        wathts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharingConnection.goTo(SocialPresenter.getWhatsApp());

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (editText.getText().toString().isEmpty()){
            editText.setError("لا يمكن ترك هذا الحقل فارغاً");
        }
        else {
            String title="";
            if(complaint.isChecked()){
                title="رسالة تزمر من تطبيق سودا كارد";
            }
            else {
                title="إقتراح من تطبيق سودا كارد";
            }
            new GMailSender(getActivity()).sendMessage("www.ahmeddmx9996799@gmail.com",title,editText.getText().toString().trim());
            editText.setText("");
        }

    }
});

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().recreate();
    }
}