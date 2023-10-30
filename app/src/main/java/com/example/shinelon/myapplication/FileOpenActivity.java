package com.example.shinelon.myapplication;

import android.os.Environment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

public class FileOpenActivity extends FragmentActivity {

    private TbsReaderView tbsReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_open);
        RelativeLayout root = findViewById(R.id.root_rl);
        tbsReaderView = new TbsReaderView(this,readerCallback);
        root.addView(tbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        openFile();
    }

    private void openFile() {
        String url = Environment.getExternalStorageDirectory().getPath()+"/XueXiPingTai/001.pdf";
        File file = new File(url);
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", url);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = tbsReaderView.preOpen(parseFormat(parseName(url)), false);
        if (result) {
            tbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }

    TbsReaderView.ReaderCallback readerCallback = new TbsReaderView.ReaderCallback() {
        @Override
        public void onCallBackAction(Integer integer, Object o, Object o1) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tbsReaderView!=null){
            tbsReaderView.onStop();
        }
    }
}
