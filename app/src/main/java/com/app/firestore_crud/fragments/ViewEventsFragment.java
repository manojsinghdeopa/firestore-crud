package com.app.firestore_crud.fragments;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.firestore_crud.R;
import com.app.firestore_crud.adapters.EventsRecyclerViewAdapter;
import com.app.firestore_crud.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ViewEventsFragment extends Fragment {
    private static final String TAG = "ViewEventsFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView eventsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_events, container, false);

        firestoreDB = FirebaseFirestore.getInstance();

        Button button = view.findViewById(R.id.view_event);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEvents();
            }
        });

        eventsRecyclerView = view.findViewById(R.id.events_lst);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        eventsRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(eventsRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        eventsRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void viewEvents() {
        String eventType = ((TextView) getActivity().findViewById(R.id.event_type_v)).getText().toString();
        getDocumentsFromCollection(eventType);
    }

    private void getDocumentsFromCollection(String eventType) {
        firestoreDB.collection("events")
                .whereEqualTo("type", eventType)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Event> eventList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                Event e = doc.toObject(Event.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            EventsRecyclerViewAdapter recyclerViewAdapter = new EventsRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        firestoreDB.collection("events")
                .whereEqualTo("type", eventType)
                .addSnapshotListener(Objects.requireNonNull(getActivity()), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(Event.class);
                            //do something...
                        }
                    }
                });
    }
}
