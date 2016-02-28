package com.example.srinivas.cameramodule;

        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;

        import java.io.InputStream;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    private Button buttonGetImage;
    private ImageView imageView;
    private final String imageURL = "192.168.1.123/cestaImage/getImage.php?id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGetImage = (Button) findViewById(R.id.buttonGetImage);
        imageView = (ImageView) findViewById(R.id.imageViewShow);

        buttonGetImage.setOnClickListener(this);
    }


    private void getImage() {
        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void,Bitmap>{


            ImageView bmImage;
            ProgressDialog loading;

            public GetImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                bmImage.setImageBitmap(bitmap);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewActivity.this,"Downloading Image","Please wait...",true,true);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = imageURL+ strings[0];
                Bitmap mIcon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                return mIcon;
            }
        }

        GetImage gi = new GetImage(imageView);
        gi.execute(id);
    }

    @Override
    public void onClick(View v) {
        getImage();
    }
}
