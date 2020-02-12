package com.app.firestore_crud.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.firestore_crud.MainActivity;
import com.app.firestore_crud.R;
import com.app.firestore_crud.models.Event;
import com.app.firestore_crud.utils.AppData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;


public class AddEventFragment extends Fragment {

    private static final String TAG = "AddEventFragment";

    private FirebaseFirestore firestoreDB;
    private boolean isEdit;

    private String docId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event, container, false);
        Button button = view.findViewById(R.id.add_event);

        Event event = AppData.getInstance().getSelectedEvent();
        if (event != null) {
            ((TextView) view.findViewById(R.id.add_tv)).setText("Edit Event");
            ((TextView) view.findViewById(R.id.event_name_a)).setText(event.getName());
            ((TextView) view.findViewById(R.id.event_type_a)).setText(event.getType());
            ((TextView) view.findViewById(R.id.event_place_a)).setText(event.getPlace());
            ((TextView) view.findViewById(R.id.event_start_time_a)).setText(event.getStartTime());
            ((TextView) view.findViewById(R.id.event_end_time_a)).setText(event.getEndTime());

            button.setText("Edit Event");
            isEdit = true;
            docId = event.getId();
        }

        firestoreDB = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    addEvent();
                } else {
                    updateEvent();
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void addEvent() {
        Event event = createEventObj();
        addDocumentToCollection(event);
    }

    private void updateEvent() {
        Event event = createEventObj();
        updateDocumentToCollection(event);
    }

    private Event createEventObj() {
        final Event event = new Event();
        event.setName(((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.event_name_a)).getText().toString());
        event.setPlace(((TextView) getActivity().findViewById(R.id.event_place_a)).getText().toString());
        event.setType(((TextView) getActivity().findViewById(R.id.event_type_a)).getText().toString());
        event.setStartTime(((TextView) getActivity().findViewById(R.id.event_start_time_a)).getText().toString());
        event.setEndTime(((TextView) getActivity().findViewById(R.id.event_end_time_a)).getText().toString());
        return event;
    }

    private void addDocumentToCollection(Event event) {
        firestoreDB.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Event document added - id: "
                                + documentReference.getId());
                        restUi();
                        Toast.makeText(getActivity(),
                                "Event document has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(),
                                "Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateDocumentToCollection(Event event) {
        firestoreDB.collection("events")
                .document(docId)
                .set(event, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Event document updated ");
                        AppData.getInstance().setSelectedEvent(null);
                        Toast.makeText(getActivity(), "Event document has been updated", Toast.LENGTH_SHORT).show();
                        showEventScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(), "Event document could not be added", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void restUi() {
        ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.event_name_a)).setText("");
        ((TextView) getActivity().findViewById(R.id.event_type_a)).setText("");
        ((TextView) getActivity().findViewById(R.id.event_place_a)).setText("");
        ((TextView) getActivity().findViewById(R.id.event_start_time_a)).setText("");
        ((TextView) getActivity().findViewById(R.id.event_end_time_a)).setText("");
    }

    private void showEventScreen() {
        Intent i = new Intent();
        i.setClass(Objects.requireNonNull(getActivity()), MainActivity.class);
        startActivity(i);
    }
}
