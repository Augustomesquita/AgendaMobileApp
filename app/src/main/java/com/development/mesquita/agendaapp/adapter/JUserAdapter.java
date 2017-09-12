package com.development.mesquita.agendaapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.model.JUser;

import java.util.List;

/**
 * Created by augusto on 24/08/17.
 */

public class JUserAdapter extends BaseAdapter {

    private final List<JUser> userList;
    private final Context context;

    public JUserAdapter(List<JUser> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JUser user = userList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_user, parent, false);
        }

        TextView userNome = (TextView) convertView.findViewById(R.id.customListUserNome);
        userNome.setText(user.getNome());

        TextView userTelefone = (TextView) convertView.findViewById(R.id.customListUserTelefone);
        userTelefone.setText(user.getTelefone());

        TextView userEndereco = (TextView) convertView.findViewById(R.id.customListUserEndereco);
        if (userEndereco != null) userEndereco.setText(user.getEndereco());

        TextView userSite = (TextView) convertView.findViewById(R.id.customListUserSite);
        if (userSite != null) userSite.setText(user.getSite());

        ImageView userFoto = (ImageView) convertView.findViewById(R.id.customListUserFoto);
        String pictureFilePath = user.getFoto();
        if (pictureFilePath != null) {
            Bitmap userPictureBitmap = BitmapFactory.decodeFile(pictureFilePath);
            Bitmap userPictureBitmapSmaller = Bitmap.createScaledBitmap(userPictureBitmap, 300, 300, true);

            userFoto.setImageBitmap(userPictureBitmapSmaller);
            userFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return convertView;
    }
}
