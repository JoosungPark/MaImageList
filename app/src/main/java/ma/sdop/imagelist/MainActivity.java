package ma.sdop.imagelist;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.MalformedURLException;

import ma.sdop.imagelist.dto.instagram.ItemsDto;
import ma.sdop.imagelist.network.GetImageTask;
import ma.sdop.imagelist.network.WebConfig;
import ma.sdop.imagelist.network.WebWrapper;

public class MainActivity extends AppCompatActivity {
    private ItemsDto results = new ItemsDto();

    private GetImageTask getImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getImageTask = new GetImageTask("design", null, onCompletedListener);
        getImageTask.execute();
    }

    private GetImageTask.OnCompletedListener onCompletedListener = new GetImageTask.OnCompletedListener() {
        @Override
        public void onCompleted(boolean isSuccess, ItemsDto result) {
            results = result;
        }
    };
}
