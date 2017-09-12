package com.development.mesquita.agendaapp.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.fragments.JCourseDetailFragment;
import com.development.mesquita.agendaapp.fragments.JCourseFragment;
import com.development.mesquita.agendaapp.model.JCourse;

public class JCourseActivity extends AppCompatActivity {

    private JCourse course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder_course);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentCourseHolderMainFrame, new JCourseFragment());

        // Faz replaces para modo paisagem
        if (isLandscapeMode()) {
            replaceForLandscape(fragmentTransaction, savedInstanceState);
        }

        fragmentTransaction.commit();
    }

    private boolean isLandscapeMode() {
        return getResources().getBoolean(R.bool.landscapeMode);
    }

    private void replaceForLandscape(FragmentTransaction fragmentTransaction, Bundle bundle) {

        // Instanciando fragmento de detalhes do curso.
        JCourseDetailFragment courseDetailFragment = new JCourseDetailFragment();

        // Verificamos se o bundle possui o serializavel 'course'. Observe que o serializável course
        // só existirá caso o usuário tenha rotacionado a tela de 'portrait' para 'landscape'.
        if (bundle.getSerializable("course") != null) {
            // Recupera bundle
            courseDetailFragment.setArguments(bundle);

            // Remove BackStack
            getSupportFragmentManager().popBackStack();
        }

        // Substitui frame pelo fragment.
        fragmentTransaction.replace(R.id.fragmentCourseHolderSecondFrame, courseDetailFragment);
    }


    public void selectCourse(JCourse course) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!isLandscapeMode()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            JCourseDetailFragment courseDetailFragment = new JCourseDetailFragment();

            // Prepara argumentos para serem passados para o fragment a ser aberto.
            // Funciona como o putExtra do Intent (ao abrir uma nova activity).
            Bundle args = new Bundle();
            args.putSerializable("course", course);
            courseDetailFragment.setArguments(args);

            // Substitui frame pelo fragment.
            fragmentTransaction.replace(R.id.fragmentCourseHolderMainFrame, courseDetailFragment);

            // Adiciona transaction no backStack para que sua navegação seja como uma Acitivity.
            // Em outras palavras, permitirá o usuário utilizar o botão voltar para retornar para
            // a transaction anterior.
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else {
            JCourseDetailFragment courseDetailFragment = (JCourseDetailFragment) fragmentManager.findFragmentById(R.id.fragmentCourseHolderSecondFrame);
            courseDetailFragment.fillFields(course);
        }
        this.course = course;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Passando os dados pelo bundle, caso estejamos mudando a orientação da tela para Landscape
        if (isLandscapeMode()) {
            if (this.course != null) outState.putSerializable("course", this.course);
        }
        super.onSaveInstanceState(outState);
    }
}
