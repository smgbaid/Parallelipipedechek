package com.parallepipedechek.bb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Info extends AppCompatActivity {

    private ImageView bar_code;
    private Button create_button;
    public Bitmap bitmap;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bar_code = findViewById(R.id.bar_code);
        create_button = findViewById(R.id.create_button);

//        create_button.setVisibility(View.VISIBLE);

        bottomNavigationView.setSelectedItemId(R.id.info);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext()
                                , Map.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.info:
                        return true;
                }
                return false;
            }
        });

        (findViewById(R.id.btnWebHelp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://sp.uraic.ru/index.php/sprosi-bibliografa/")));
            }
        });

        (findViewById(R.id.insta)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/belinka.ekb/")));
            }
        });

        (findViewById(R.id.vk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/club6745014")));
            }
        });

        (findViewById(R.id.facebook)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/ye.book/?fref=ts")));
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, BarActivity.class);
                startActivity(intent);
            }
        });


        File filepath = Environment.getExternalStorageDirectory();
        File file = new File(filepath.getAbsolutePath() + "/BBcash/LibraryCard.jpg");

        if (file.exists()) {
            loadCode();
            create_button.setVisibility(View.INVISIBLE);
        } else {
            create_button.setVisibility(View.VISIBLE);
            createCode();
            saveCode();
        }


    }

    private void createCode() {
        try {
            barcode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    public void barcode() throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(getIntent().getExtras().getString("code"), BarcodeFormat.CODE_39, 1000, 300, null);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        bitmap = barcodeEncoder.createBitmap(bitMatrix);
        bar_code.setImageBitmap(bitmap);
    }

    public void saveCode() {
        try {
            if (bitmap != null) {
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/BBcash/");
                dir.mkdir();
                File file = new File(dir, "LibraryCard.jpg");
                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Toast.makeText(getApplicationContext(), "Library Card Save", Toast.LENGTH_SHORT).show();
                outputStream.flush();
                outputStream.close();

                create_button.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCode() {
        try {
            File filepath = Environment.getExternalStorageDirectory();
            bitmap = BitmapFactory.decodeFile(filepath.getAbsolutePath() + "/BBcash/LibraryCard.jpg");
            bar_code.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "Library Card Load", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
