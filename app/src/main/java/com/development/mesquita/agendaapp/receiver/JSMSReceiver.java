package com.development.mesquita.agendaapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.dao.JUserDAO;

/**
 * Created by augusto on 24/08/17.
 */

public class JSMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        // Resgata as PDU's da intent.
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        // Resgata formato
        String format = (String) intent.getSerializableExtra("format");

        // Cria SMS de um PDU com um determinado FORMATO.
        SmsMessage smsMessage = SmsMessage.createFromPdu(pdu, format);

        // Pega o telefone de quem enviou o SMS.
        String telefone = smsMessage.getDisplayOriginatingAddress();

        // Verifica se o número do telefone pertence a algum usuário do aplicativo.
        JUserDAO dao = new JUserDAO(context);
        if (dao.isSystemUser(telefone)) {

            // Cria MediaPlayer para manipular sons da aplicação.
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.smw_riding_yoshi);

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

            // Apresenta mensagem.
            Toast.makeText(context, "Chegou menssagem de um usuário de AgendaApp! Telefone: " + telefone, Toast.LENGTH_SHORT).show();
        }

    }
}
