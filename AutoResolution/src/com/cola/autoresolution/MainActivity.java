package com.cola.autoresolution;

import com.cola.autoresolution.PathView.OnItemClickListener;
import com.cola.autoresolution.util.AssetsUtil;
import com.cola.autoresolution.util.FileUtil;
import com.cola.autoresolution.util.ToastUtil;
import com.cola.autoresolution.util.XmlUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();

        // makeDimens();
    }

    private void setupView() {

        PathView mPathView = (PathView) this.findViewById(R.id.PathView);

        ImageButton startMenu = new ImageButton(this);

        startMenu.setBackgroundResource(R.drawable.ic_launcher);

        mPathView.setStartMenu(startMenu);

        int[] drawableIds = new int[] {

        R.drawable.ic_launcher,

        R.drawable.ic_launcher,

        R.drawable.ic_launcher,

        R.drawable.ic_launcher

        };

        View[] items = new View[drawableIds.length];

        for (int i = 0; i < drawableIds.length; i++) {

            ImageButton button = new ImageButton(this);

            button.setBackgroundResource(drawableIds[i]);

            items[i] = button;

        }

        mPathView.setItems(items);

        mPathView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void makeDimens() {
        AssetsUtil.copyDimenXml(this);

        // createResolution(720, 240);
        createResolution(720, 320);
        createResolution(720, 360);
        createResolution(720, 480);
        createResolution(720, 540);
        createResolution(720, 600);
        createResolution(720, 640);
        createResolution(720, 768);
        createResolution(720, 800);
        createResolution(720, 1080);

        ToastUtil.showShort(this, "create resolution finish");
    }

    private void createResolution(int srcResolution, int destResolution) {
        String dir = FileUtil.AUTORESOLUTION + destResolution + "/";
        FileUtil.createDirectory(dir);
        String path = dir + "dimens.xml";

        FileUtil.delFile(path);
        FileUtil.saveTextFile(path, XmlUtil.buildResourcesInfo(XmlUtil.getResourcesInfo(FileUtil.SRC_XML), destResolution, srcResolution));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
