package com.development.mesquita.agendaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.model.JCourse;
import com.development.mesquita.agendaapp.views.JCourseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by augusto on 25/08/17.
 */

public class JCourseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        List<String> topicsAndroid = Arrays.asList("Activies", "Usando Java 8", "Fragments", "Integração com serviços nativos", "Rastreando com Stetho");
        JCourse informationAndroid = new JCourse("Android", "01/03/2017", topicsAndroid);

        List<String> topicsSpring = Arrays.asList("O que é Spring", "Entendendo o MVC", "Primeiro WebApp com Spring", "Internacionalização");
        JCourse informationSpring = new JCourse("Spring MVC", "07/04/2017", topicsSpring);

        List<String> topicsUnity = Arrays.asList("Introdução", "Conceito e estrutura básica de um jogo", "Conceito de layers e sua conexões", "Utilizando fisica nos jogos");
        JCourse informationUnity = new JCourse("Unity 2D", "03/05/2017", topicsUnity);

        List<String> topicsWicket = Arrays.asList("O que é Wicket", "Entendendo o fluxo do framework", "O poder do web component", "AJAX para já!");
        JCourse informationWicket = new JCourse("Wicket", "05/06/2017", topicsWicket);

        List<JCourse> coursesList = Arrays.asList(informationAndroid, informationSpring, informationUnity, informationWicket);

        ArrayAdapter<JCourse> adapter = new ArrayAdapter<JCourse>(getContext(), android.R.layout.simple_list_item_1, coursesList);

        ListView lvCoursesList = (ListView) view.findViewById(R.id.courseList);
        lvCoursesList.setAdapter(adapter);
        lvCoursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JCourse course = (JCourse) parent.getItemAtPosition(position);

                JCourseActivity courseActivity = (JCourseActivity) getActivity();
                courseActivity.selectCourse(course);
            }
        });

        ((JCourseActivity) getActivity()).setTitle("Cursos");

        return view;
    }
}
