package com.example.virtualcloset;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Frag3 extends Fragment {
    GridView gridView;
    ArrayList<Closet> list;
    ClosetAdapter adapter = null;
    private SQLiteHelper sqLiteHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2_layout, container, false);
        gridView = view.findViewById(R.id.grid_view);
        list = new ArrayList<>();
        adapter = new ClosetAdapter(getContext(), R.layout.recycler_layout, list);
        gridView.setAdapter(adapter);
        sqLiteHelper = new SQLiteHelper(getActivity(), "ClosetDB.sqLite", null, 1);
        getClothes();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence[] item = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Choose an action");
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Cursor c = sqLiteHelper.getData("SELECT id FROM CLOSET WHERE type = 'Jeans'");
                        ArrayList<Integer> arrID = new ArrayList<Integer>();
                        while (c.moveToNext()) {
                            arrID.add(c.getInt(0));
                        }
                        showDialogDelete(arrID.get(position));

                    }
                });
                dialog.show();
                return true;
            }
        });
        return view;
    }

    private void getClothes() {
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM CLOSET");
        list.clear();
        while (cursor.moveToNext()) {
            if (cursor.getString(3).equals("Jeans")) {
                int id = cursor.getInt(0);
                String notes = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                list.add(new Closet(notes, image, id));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateList();
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void showDialogDelete(final int idCloset) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());
        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure you want to delete this?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity(), "ClosetDB.sqLite", null, 1);
                sqLiteHelper.deleteData(idCloset);
                Toast.makeText(getContext(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                updateList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateList() {
        sqLiteHelper = new SQLiteHelper(getActivity(), "ClosetDB.sqLite", null, 1);
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM CLOSET");
        list.clear();
        while (cursor.moveToNext()) {
            if (cursor.getString(3).equals("Jeans")) {
                int id = cursor.getInt(0);
                String notes = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                list.add(new Closet(notes, image, id));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
