package com.app.demo.shibie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.tensorflow.lite.Interpreter;
import com.app.demo.R;
import com.app.demo.activitys.DetailActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ShibieActivity extends AppCompatActivity {
    private Interpreter tflite;
    private TextView resultTextView;
    private ImageView imageView;
    private Button pickImageButton;
    private ProgressDialog progressDialog;
    private static final int INPUT_SIZE = 224; // 模型輸入尺寸
    private static final int NUM_CLASSES = 9; // 類別數量
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST = 101;
    private int selectedPosi; // 儲存當前辨識結果的 posi

    // 用於儲存辨識結果和 posi
    private static class RecognitionResult {
        String result;
        int posi;

        RecognitionResult(String result, int posi) {
            this.result = result;
            this.posi = posi;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shibie);

        resultTextView = findViewById(R.id.result_text);
        imageView = findViewById(R.id.image_view);
        pickImageButton = findViewById(R.id.pick_image_button);

        // 初始化進度對話框
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("載入模型中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // 載入 TFLite 模型（異步執行）
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tflite = new Interpreter(loadModelFile());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            resultTextView.setText("模型載入失敗：" + e.getMessage());
                        }
                    });
                }
            }
        }).start();

        // 設置按鈕點擊事件
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ShibieActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShibieActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE);
                } else {
                    pickImage();
                }
            }
        });

        // 讓辨識結果可點擊，跳轉到 DetailActivity
        resultTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosi >= 0) {
                    Toast.makeText(ShibieActivity.this, "Selected posi: " + selectedPosi, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShibieActivity.this, DetailActivity.class);
                    intent.putExtra("posi", selectedPosi);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "需要儲存權限以選擇圖片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            progressDialog.setMessage("辨識中...");
            progressDialog.show();
            final Uri imageUri = data.getData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        final RecognitionResult recognitionResult = runInference(bitmap);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                imageView.setImageBitmap(bitmap);
                                selectedPosi = recognitionResult.posi;
                                resultTextView.setText(recognitionResult.result);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                resultTextView.setText("圖片處理失敗：" + e.getMessage());
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("danshui_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private RecognitionResult runInference(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        float[][][][] input = new float[1][INPUT_SIZE][INPUT_SIZE][3];

        for (int i = 0; i < INPUT_SIZE; i++) {
            for (int j = 0; j < INPUT_SIZE; j++) {
                int pixel = resizedBitmap.getPixel(i, j);
                input[0][i][j][0] = ((pixel >> 16) & 0xFF) / 255.0f; // R
                input[0][i][j][1] = ((pixel >> 8) & 0xFF) / 255.0f;  // G
                input[0][i][j][2] = (pixel & 0xFF) / 255.0f;         // B
            }
        }

        float[][] output = new float[1][NUM_CLASSES];
        tflite.run(input, output);

        int maxIndex = 0;
        float maxProb = output[0][0];
        for (int i = 1; i < output[0].length; i++) {
            if (output[0][i] > maxProb) {
                maxProb = output[0][i];
                maxIndex = i;
            }
        }

        String[] labels;
        try {
            labels = loadLabels();
        } catch (IOException e) {
            e.printStackTrace();
            return new RecognitionResult("無法載入標籤：" + e.getMessage(), -1);
        }

        // 直接使用 maxIndex 作為 posi
        return new RecognitionResult(labels[maxIndex] + " (信心度: " + (maxProb * 100) + "%)", maxIndex);
    }

    private String[] loadLabels() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
        List<String> labels = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            labels.add(line);
        }
        reader.close();
        return labels.toArray(new String[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }
}