package com.development.mesquita.agendaapp.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.Toast;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.dao.JUserDAO;
import com.development.mesquita.agendaapp.model.JUser;
import com.development.mesquita.agendaapp.utils.JWebClient;
import com.development.mesquita.agendaapp.views.JMainActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by augusto on 24/08/17.
 */

public class JSendUserTask extends AsyncTask<Object, Object, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public JSendUserTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] params) {
        JUserDAO userDAO = new JUserDAO(context);
        List<JUser> userList = userDAO.readByCriteria();

        JWebClient webClient = new JWebClient();
        String response = webClient.post(new Gson().toJson(userList));

        // Não funciona aqui, pois um treadh secundária não pode  manipular elementos da view.
        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

        // Cria MediaPlayer para manipular sons da aplicação.
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.smw_coin);

        // Instancia um gerenciador de audio do dispositivo.
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Verifica se o volume do dispositivo está setado para silencioso usando o gerenciador.
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                mediaPlayer.setVolume(0, 0);
                break;
        }

        // Inicia o som
        mediaPlayer.start();
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde", "Enviando nota para o servidor...", true, true);
    }
}
