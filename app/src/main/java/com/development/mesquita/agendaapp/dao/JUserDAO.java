package com.development.mesquita.agendaapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.development.mesquita.agendaapp.model.JUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by augusto on 18/08/17.
 */

public class JUserDAO extends SQLiteOpenHelper {

    public JUserDAO(Context context) {
        super(context, "agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE users" +
                " (" +
                " id INTEGER PRIMARY KEY," +
                " nome TEXT NOT NULL," +
                " endereco TEXT," +
                " telefone TEXT," +
                " site TEXT," +
                " nota INTEGER," +
                " foto TEXT" +
                " );";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = null;

        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE users ADD foto TEXT";
                db.execSQL(sql);
        }

    }

    public void create(JUser user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues query = getContentValuesUsers(user);

        db.insert("users", null, query);
    }

    public List<JUser> readByCriteria() {
        String sql = "SELECT * FROM users";
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.rawQuery(sql, null);

        List<JUser> userList = new ArrayList<>();


        while (rs.moveToNext()) {
            JUser user = new JUser();
            user.setId(rs.getLong(rs.getColumnIndex("id")));
            user.setNome(rs.getString(rs.getColumnIndex("nome")));
            user.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
            user.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
            user.setSite(rs.getString(rs.getColumnIndex("site")));
            user.setNota(rs.getInt(rs.getColumnIndex("nota")));
            user.setFoto(rs.getString(rs.getColumnIndex("foto")));
            userList.add(user);
        }
        rs.close();

        return userList;
    }

    public void delete(JUser user) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {user.getId().toString()};
        db.delete("users", "id=?", params);
    }

    public void update(JUser user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues query = getContentValuesUsers(user);

        String[] params = {user.getId().toString()};
        db.update("users", query, "id=?", params);
    }

    @NonNull
    private ContentValues getContentValuesUsers(JUser user) {
        ContentValues query = new ContentValues();
        query.put("nome", user.getNome());
        query.put("endereco", user.getEndereco());
        query.put("telefone", user.getTelefone());
        query.put("site", user.getSite());
        query.put("nota", user.getNota());
        query.put("foto", user.getFoto());
        return query;
    }

    public boolean isSystemUser (String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.rawQuery("SELECT * FROM users WHERE telefone = ?", new String[]{telefone});

        int resultado = rs.getCount();
        rs.close();

        return resultado > 0;
    }

}
