package com.simple.spiderman;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * author : ChenPeng
 * date : 2018/4/20
 * description :
 */
public class CrashActivity extends AppCompatActivity {

    public static final String CRASH_MODEL = "crash_model";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
    private CrashModel model;

    private ViewGroup root;
    private ScrollView scrollView;
    private ViewGroup toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SpiderMan.mThemeId);
        setContentView(R.layout.activity_crash);
        model = getIntent().getParcelableExtra(CRASH_MODEL);
        if (model == null) {
            return;
        }
        Log.e("SpiderMan", Log.getStackTraceString(model.getEx()));

        root = findViewById(R.id.root);
        scrollView = findViewById(R.id.scrollView);
        toolbar = findViewById(R.id.toolbar);
//        TextView tv_packageName = findViewById(R.id.tv_packageName);
        TextView textMessage = findViewById(R.id.textMessage);
        TextView tv_className = findViewById(R.id.tv_className);
        TextView tv_methodName = findViewById(R.id.tv_methodName);
        TextView tv_lineNumber = findViewById(R.id.tv_lineNumber);
        TextView tv_exceptionType = findViewById(R.id.tv_exceptionType);
        TextView tv_fullException = findViewById(R.id.tv_fullException);
        TextView tv_time = findViewById(R.id.tv_time);
        TextView tv_model = findViewById(R.id.tv_model);
        TextView tv_brand = findViewById(R.id.tv_brand);
        TextView tv_version = findViewById(R.id.tv_version);
        TextView tv_more = findViewById(R.id.tv_more);
//
//        tv_packageName.setText(model.getClassName());
        textMessage.setText(model.getExceptionMsg());
        tv_className.setText(model.getFileName());
        tv_methodName.setText(model.getMethodName());
        tv_lineNumber.setText(String.valueOf(model.getLineNumber()));
        tv_exceptionType.setText(model.getExceptionType());
        tv_fullException.setText(model.getFullException());
        tv_time.setText(df.format(model.getTime()));

        tv_model.setText(model.getDevice().getModel());
        tv_brand.setText(model.getDevice().getBrand());
        tv_version.setText(model.getDevice().getVersion());

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    private void showMenu(View v) {
        PopupMenu menu = new PopupMenu(CrashActivity.this, v);
        menu.inflate(R.menu.menu_more);
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_copy_text) {
                    String crashText = getShareText(model);
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm != null) {
                        ClipData mClipData = ClipData.newPlainText("crash", crashText);
                        cm.setPrimaryClip(mClipData);
                        showToast(getString(R.string.simpleCopied));
                    }
                } else if (id == R.id.menu_share_text) {
                    String crashText = getShareText(model);
                    shareText(crashText);
                } else if (id == R.id.menu_share_image) {
//                    if (ContextCompat.checkSelfPermission(CrashActivity.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
//                            ContextCompat.checkSelfPermission(CrashActivity.this,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    } else {
                    shareImage();
//                    }
                }
                return true;
            }
        });
    }


    private String getShareText(CrashModel model) {
        StringBuilder builder = new StringBuilder();

        builder.append(getString(R.string.simpleCrashInfo))
                .append("\n")
                .append(model.getExceptionMsg())
                .append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleClassName))
                .append(model.getFileName()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleFunName)).append(model.getMethodName()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleLineNum)).append(model.getLineNumber()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleExceptionType)).append(model.getExceptionType()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleTime)).append(df.format(model.getTime())).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleModel)).append(model.getDevice().getModel()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleBrand)).append(model.getDevice().getBrand()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleVersion)).append(model.getDevice().getVersion()).append("\n");
        builder.append("\n");//空一行，好看点，(#^.^#)

        builder.append(getString(R.string.simpleAllInfo))
                .append("\n")
                .append(model.getFullException()).append("\n");

        return builder.toString();
    }

    private void shareText(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.simpleCrashInfo));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.simpleShareTo)));
    }

    private static final int REQUEST_CODE = 110;

    private void requestPermission(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        //判断请求码，确定当前申请的权限
//        if (requestCode == REQUEST_CODE) {
//            //判断权限是否申请通过
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //授权成功
//                shareImage();
//            } else {
//                //授权失败
//                showToast(R.string.simplePermissionSd);
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    public Bitmap getBitmapByView(ViewGroup toolbar, ScrollView scrollView) {
        if (toolbar == null || scrollView == null) return null;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int svHeight = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            svHeight += scrollView.getChildAt(i).getHeight();
        }
        int height = svHeight + toolbar.getHeight();
        //
        Bitmap resultBitmap = Bitmap.createBitmap(toolbar.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas rootCanvas = new Canvas(resultBitmap);
        //
        Bitmap toolbarBitmap = Bitmap.createBitmap(toolbar.getWidth(), toolbar.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas toolbarCanvas = new Canvas(toolbarBitmap);
        toolbarCanvas.drawRGB(255, 255, 255);
        toolbar.draw(toolbarCanvas);
        //
        Bitmap svBitmap = Bitmap.createBitmap(toolbar.getWidth(), svHeight,
                Bitmap.Config.ARGB_8888);
        Canvas svCanvas = new Canvas(svBitmap);
        svCanvas.drawRGB(255, 255, 255);
        scrollView.draw(svCanvas);

        rootCanvas.drawBitmap(toolbarBitmap, 0, 0, paint);
        rootCanvas.drawBitmap(svBitmap, 0, toolbar.getHeight(), paint);
        return resultBitmap;
    }

    private File BitmapToFile(Bitmap bitmap) {
        if (bitmap == null) return null;
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .getAbsolutePath();
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = Utils.getCachePath();
        File imageFile = new File(path, "SpiderMan-" + df.format(model.getTime()) + ".png");
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private void shareImage() {
//        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            showToast(R.string.simpleNoSdCard);
//            return;
//        }
        File file = BitmapToFile(getBitmapByView(toolbar, scrollView));
        if (file == null || !file.exists()) {
            showToast(R.string.simpleImageNotExist);
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                    getApplicationContext().getPackageName() + ".spidermanfileprovider", file);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, getString(R.string.simpleShareTo)));
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void showToast(@StringRes int textId) {
        Toast.makeText(getApplicationContext(), textId, Toast.LENGTH_SHORT).show();
    }

}
