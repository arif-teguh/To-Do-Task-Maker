package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.opengl;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.R;

public class PermissionRequiredActivity extends AppCompatActivity {

    private OGLView oglView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oglView = (OGLView) findViewById(R.id.oglView);
        System.loadLibrary("hello-jni");
        String test = stringFromJNI(20);
        TextView main_text =  (TextView)findViewById(R.id.main_text);
        main_text.setText(test);
    }

    public native String stringFromJNI(int x);

    @Override
    protected void onPause() {
        super.onPause();
        oglView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        oglView.onResume();
    }
}
