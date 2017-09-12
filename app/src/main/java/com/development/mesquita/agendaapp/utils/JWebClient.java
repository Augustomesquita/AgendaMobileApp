package com.development.mesquita.agendaapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by augusto on 24/08/17.
 */

public class JWebClient {
    public String post(String json) {
        String resposta = JHttpConnections.get("https://reqres.in/api/users?page=2");
        return resposta;
    }
}
