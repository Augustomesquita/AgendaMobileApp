package com.development.mesquita.agendaapp.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.development.mesquita.agendaapp.views.JFormUserActivity;
import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.model.JUser;

/**
 * Created by augusto on 18/08/17.
 */

public class JFormUserHelper {

    private JUser user;

    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText site;
    private final RatingBar nota;
    private final ImageView foto;

    public JFormUserHelper(JFormUserActivity activity) {
        user = new JUser();
        nome = (EditText) activity.findViewById(R.id.formUserNome);
        endereco = (EditText) activity.findViewById(R.id.formUserEndereco);
        telefone = (EditText) activity.findViewById(R.id.formUserTelefone);
        site = (EditText) activity.findViewById(R.id.formUserSite);
        nota = (RatingBar) activity.findViewById(R.id.formUserNota);
        foto = (ImageView) activity.findViewById(R.id.formUserPicture);
    }

    public JUser getUser() {
        user.setNome(nome.getText().toString());
        user.setEndereco(endereco.getText().toString());
        user.setTelefone(telefone.getText().toString());
        user.setSite(site.getText().toString());
        user.setNota(nota.getProgress());
        user.setFoto((String) foto.getTag());
        return user;
    }

    public void fillForm(JUser userToEdit) {
        nome.setText(userToEdit.getNome());
        endereco.setText(userToEdit.getEndereco());
        telefone.setText(userToEdit.getTelefone());
        site.setText(userToEdit.getSite());
        nota.setProgress(userToEdit.getNota());
        loadUserPicture(userToEdit.getFoto());
        this.user = userToEdit;
    }

    public void loadUserPicture(String pictureFilePath) {
        if (pictureFilePath != null) {
            Bitmap userPictureBitmap = BitmapFactory.decodeFile(pictureFilePath);
            Bitmap userPictureBitmapSmaller = Bitmap.createScaledBitmap(userPictureBitmap, 300, 300, true);

            foto.setImageBitmap(userPictureBitmapSmaller);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(pictureFilePath);
        }
    }

}
