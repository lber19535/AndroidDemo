package com.example.bill.utils.torrentkitty;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.example.bill.App;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bill_lv on 2015/12/17.
 */
public class TorrentKittyViewModel {

    private TorrentKittyModel kittyModel;

    private static final String TEST_MODEL = "test";

    public TorrentKittyViewModel() {
        kittyModel = new TorrentKittyModel();
    }

    public Observable<MagnetContent> searchKeyWord(String key) {
        if (key.isEmpty()) {
            return Observable.just(new MagnetContent("empty", "empty"));
        } else if (key.equals(TEST_MODEL)) {
            return Observable.range(0, 10).map(new Func1<Integer, MagnetContent>() {
                @Override
                public MagnetContent call(Integer integer) {
                    return new MagnetContent(integer.toString(), integer.toString());
                }
            });
        } else {
            return kittyModel.requestTorrent(key);
        }
    }

    public void copyToClipboard(Iterable<String> contents) {
        StringBuffer sb = new StringBuffer();
        for (String content : contents) {
            sb.append(content);
            sb.append("\n");
        }
        copyToClipboard(sb.toString());
    }


    public void copyToClipboard(String content) {
        ClipboardManager clipboard = (ClipboardManager)
                App.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("torrent",content);
        clipboard.setPrimaryClip(data);
    }
}
