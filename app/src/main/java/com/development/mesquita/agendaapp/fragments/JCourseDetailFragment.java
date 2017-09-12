package com.development.mesquita.agendaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.model.JCourse;
import com.development.mesquita.agendaapp.views.JCourseActivity;

public class JCourseDetailFragment extends Fragment {

    private TextView courseName;
    private TextView courseDate;
    private ListView courseTopics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);

        courseName = (TextView) view.findViewById(R.id.courseDetailName);
        courseDate = (TextView) view.findViewById(R.id.courseDetailDate);
        courseTopics = (ListView) view.findViewById(R.id.courseDetailTopics);

        Bundle bundle = getArguments();
        if (bundle != null) {
            JCourse course = (JCourse) bundle.getSerializable("course");
            fillFields(course);
        }

        ((JCourseActivity) getActivity()).setTitle("Cursos | Programação");


        return view;
    }

    public void fillFields(JCourse course) {
        courseName.setText(course.getName());
        courseDate.setText(course.getDate());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, course.getTopics());
        courseTopics.setAdapter(adapter);
    }

}
