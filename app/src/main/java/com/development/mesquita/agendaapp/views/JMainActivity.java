package com.development.mesquita.agendaapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.adapter.JUserAdapter;
import com.development.mesquita.agendaapp.dao.JUserDAO;
import com.development.mesquita.agendaapp.model.JUser;
import com.development.mesquita.agendaapp.task.JSendUserTask;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;


public class JMainActivity extends AppCompatActivity {

    private ListView lvListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verifica se o app possui as permissões necessárias.
        // Permissão de recebimento de SMS
        ArrayList<String> permissions = new ArrayList<String>();
        if (ActivityCompat.checkSelfPermission(JMainActivity.this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECEIVE_SMS);
        }

        // Permissão para escrita em local externo.
        if (ActivityCompat.checkSelfPermission(JMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // Caso a lista de permissões pendentes possua itens, pede por permissões.
        if (!permissions.isEmpty()) {
            String[] permissionsArray = new String[permissions.size()];
            permissionsArray = permissions.toArray(permissionsArray);
            ActivityCompat.requestPermissions(JMainActivity.this, permissionsArray, 321);
        }

        // Resgata elementos visuais.
        lvListUser = (ListView) findViewById(R.id.lvListAlunos);
        Button btnNovoAluno = (Button) findViewById(R.id.btnMainNovoAluno);

        // Cria botão para levar para tela de formulário.
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToFormAluno = new Intent(JMainActivity.this, JFormUserActivity.class);
                startActivity(intentToFormAluno);
            }
        });

        // Registra listView (lvListAlunos) para um contexto de menu.
        registerForContextMenu(lvListUser);

        // Adiciona evento de click comum.
        lvListUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                JUser user = (JUser) lvListUser.getItemAtPosition(position);
                Intent intentToFormAluno = new Intent(JMainActivity.this, JFormUserActivity.class);
                intentToFormAluno.putExtra("user", user);
                startActivity(intentToFormAluno);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Carrega valores em seus respectivos adapters.
        loadUserList();
    }


    private void loadUserList() {
        // Resgata usuários do banco sem passar critério de busca, então por default, traz todos
        // os usuários.
        JUserDAO alunoDAO = new JUserDAO(this);
        List<JUser> alunoList = alunoDAO.readByCriteria();
        alunoDAO.close();

        // Passa lista de usuários para o Adapter.
        JUserAdapter listUserAdapter = new JUserAdapter(alunoList, this);
        lvListUser.setAdapter(listUserAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        // Identifica qual usuário (item) da lista foi clicado.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final JUser user = (JUser) lvListUser.getItemAtPosition(info.position);

        // Verifica qual é a view (elemento) que está sendo clicado
        // antes de adicionar o menu de contexto.
        if (v.equals(lvListUser)) {

            // ITEM PARA LIGAR
            MenuItem itemMenuCall = menu.add("LIGAR");
            itemMenuCall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (ActivityCompat.checkSelfPermission(JMainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(JMainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                    } else {
                        Intent intentCall = new Intent(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:" + user.getTelefone()));
                        startActivity(intentCall);
                    }
                    return false;
                }
            });


            // ITEM PARA ENVIAR SMS
            MenuItem itemMenuLocalization = menu.add("LOCALIZAÇÃO");
            Intent intentLocalization = new Intent(Intent.ACTION_VIEW);
            intentLocalization.setData(Uri.parse("geo:0,0?q=" + user.getEndereco()));
            itemMenuLocalization.setIntent(intentLocalization);


            // ITEM PARA ENVIAR SMS
            MenuItem itemMenuSendSMS = menu.add("ENVIAR SMS");
            Intent intentToSendSMS = new Intent(Intent.ACTION_VIEW);
            intentToSendSMS.setData(Uri.parse("sms:" + user.getTelefone()));
            itemMenuSendSMS.setIntent(intentToSendSMS);

            // ITEM PARA VISUALIZAR PÁGINA
            // Adicionado item no contextMenu para visualizar página do usuário.
            MenuItem itemMenuVisitSite = menu.add("ACESSAR SITE");
            Intent intentToVisitSite = new Intent(Intent.ACTION_VIEW);

            // Verifica se o endereço do site do usuário já possui o prefixo 'http://', em caso
            // negativo, adiciona.
            if (user.getSite() == null || user.getSite().isEmpty()) {
                user.setSite("http://www.google.com.br");
            } else if (!user.getSite().startsWith("http://")) {
                user.setSite("http://" + user.getSite());
            }

            // Adiciona site como URL de Intent.ACTION.VIEW
            intentToVisitSite.setData(Uri.parse(user.getSite()));

            // Adiciona Intent para visualizar página do usuário ao contextMenu
            itemMenuVisitSite.setIntent(intentToVisitSite);


            // ITEM PARA  DELETAR USUÁRIO
            // Adiciona item no contextMenu para deletar o usuário.
            MenuItem itemMenuDelete = menu.add("DELETAR");
            itemMenuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    JUserDAO alunoDAO = new JUserDAO(JMainActivity.this);
                    alunoDAO.delete(user);
                    alunoDAO.close();

                    loadUserList();
                    Toast.makeText(JMainActivity.this, "Usuário " + user.getNome() + " deletado!", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuUserListSend:
                new JSendUserTask(this).execute();
                break;

            case R.id.menuCourses:
                Intent intentToCourses = new Intent(this, JCourseActivity.class);
                startActivity(intentToCourses);
                break;

            case R.id.menuMap:
                Intent intentToMap = new Intent(this, JMapActivity.class);
                startActivity(intentToMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
