package com.development.mesquita.agendaapp.views;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.dao.JUserDAO;
import com.development.mesquita.agendaapp.helper.JFormUserHelper;
import com.development.mesquita.agendaapp.model.JUser;
import com.development.mesquita.agendaapp.utils.IConstants;

import java.io.File;

public class JFormUserActivity extends AppCompatActivity {

    private JFormUserHelper userHelper;
    private JUser userToEdit;
    private String pictureFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        // Instancia aluno
        userHelper = new JFormUserHelper(this);

        Intent intentReceived = getIntent();
        userToEdit = (JUser) intentReceived.getSerializableExtra("user");
        if (userToEdit != null) {
            setTitle("Editando");
            userHelper.fillForm(userToEdit);
        }

        Button btnFormUserTakePicture = (Button) findViewById(R.id.btnFormUserTakePicture);
        btnFormUserTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intentToTakePicture.resolveActivity(getPackageManager()) != null) {
                    pictureFilePath = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jgp";
                    File pictureFile = new File(pictureFilePath);
                    intentToTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(JFormUserActivity.this, getApplicationContext().getPackageName() + ".com.development.mesquita.agendaapp.provider", pictureFile));
                    startActivityForResult(intentToTakePicture, IConstants.ACTIVITY_RESULT_CAMERA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IConstants.ACTIVITY_RESULT_CAMERA) {
                userHelper.loadUserPicture(pictureFilePath);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.form_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.formUserOk:
                JUser user = userHelper.getUser();
                JUserDAO userDAO = new JUserDAO(this);

                // Se ID == NULL estou criando um novo usuário.
                // Se ID != NULL estou alterando.
                if (user.getId() == null) {
                    userDAO.create(user);
                    Toast.makeText(JFormUserActivity.this, "Pronto! O usuário " + user.getNome() + " foi criado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    userDAO.update(user);
                    Toast.makeText(JFormUserActivity.this, "Pronto! O usuário " + user.getNome() + " foi alterado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                userDAO.close();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
