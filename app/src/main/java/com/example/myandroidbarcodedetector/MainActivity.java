package com.example.myandroidbarcodedetector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txtView;
    ImageView myImageView;
    BarcodeDetector detector;
    Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = (ImageView) findViewById(R.id.imgview);
        txtView = (TextView) findViewById(R.id.txtContent);
        btn = (Button) findViewById(R.id.button);


        // Load the image
        myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);

        // Setup the barcode detector
        detector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            txtView.setText("Could not set up the detector!");
            return;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates a frame from the bitmap, and passes it to the detector. This returns a SparseArray of barcodes.
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);
                //Decode the Barcode
                Barcode thisCode = barcodes.valueAt(0);
                txtView.setText(thisCode.rawValue);
            }
        });

    }
}
