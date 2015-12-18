package com.example.bill.utils.torrentkitty;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bill.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bill_lv on 2015/12/17.
 */
public class ActivityTorrentKitty extends AppCompatActivity {

    @Bind(R.id.torrent_list)
    ListView listView;

    @Bind(R.id.search_key)
    EditText searchEt;

    @Bind(R.id.search)
    Button searchBtn;

    private TorrentKittyViewModel kittyViewModel;
    private TorrentListAdapter adapter;

    private List<MagnetContent> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrent_kitty);

        ButterKnife.bind(this);

        adapter = new TorrentListAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setMultiChoiceModeListener(new MultipleSelectListener());

        kittyViewModel = new TorrentKittyViewModel();

    }

    @OnItemClick(R.id.torrent_list)
    public void onTorrentItemClick(int position) {
        final MagnetContent magnet = list.get(position);

        new AlertDialog.Builder(this)
                .setTitle(magnet.getName())
                .setMessage(magnet.getMagnetUrl())
                .setPositiveButton("COPY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kittyViewModel.copyToClipboard(magnet.getMagnetUrl());
                        Toast.makeText(ActivityTorrentKitty.this, "Magnet link is already copy to clipboard !", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create().show();
    }

    @OnClick(R.id.search)
    public void onSearchClick() {

        searchBtn.setClickable(false);
        list.clear();

        kittyViewModel
                .searchKeyWord(searchEt.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MagnetContent>() {
                    @Override
                    public void onCompleted() {
                        searchBtn.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        searchBtn.setClickable(true);
                        list.clear();
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(MagnetContent magnetContent) {
                        list.add(magnetContent);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    class MultipleSelectListener implements ListView.MultiChoiceModeListener {

        MenuItem selectCountMenuItem = null;
        int count = 0;
        HashSet<String> clipSet = new HashSet<>();

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if (checked) {
                count++;
                clipSet.add(list.get(position).getMagnetUrl());
            } else {
                count--;
                clipSet.remove(list.get(position).getMagnetUrl());
            }
            selectCountMenuItem.setTitle("" + count);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.multiple_select_action_torrent, menu);
            selectCountMenuItem = menu.findItem(R.id.action_number);
            // reset counter
            count = 0;
            // rest clip set
            clipSet.clear();

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_copy:
                    kittyViewModel.copyToClipboard(clipSet);
                    break;
                default:
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
