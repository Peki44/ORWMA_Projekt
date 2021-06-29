package com.example.virtualcloset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClosetAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final ArrayList<Closet> closetsList;

    public ClosetAdapter(Context context, int layout, ArrayList<Closet> closetsList) {
        this.context = context;
        this.layout = layout;
        this.closetsList = closetsList;
    }

    @Override
    public int getCount() {
        return closetsList.size();
    }

    @Override
    public Object getItem(int position) {
        return closetsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.notes = row.findViewById(R.id.text_notes);
            holder.imageView = row.findViewById(R.id.img_clothes);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Closet closet = closetsList.get(position);
        holder.notes.setText(closet.getNotes());
        byte[] clothesImage = closet.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(clothesImage, 0, clothesImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView notes;
    }
}
