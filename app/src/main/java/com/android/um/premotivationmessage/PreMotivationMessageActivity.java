package com.android.um.premotivationmessage;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.motivation_messages.MotivationMessagesActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreMotivationMessageActivity extends BaseActivity implements PreMotivationMessageContract.View {
  PreMotivationMessageContract.Presenter mPresenter;
  public String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
  final int UPLOAD_FILE_REQUEST = 1020;
  int PERMISSION_REQUEST_CODE = 100;
  @BindView(R.id.image_view)
  ImageView imageView;
  @BindView(R.id.next_btn)
  Button nextBtn;
  @BindView(R.id.skip_btn)
  TextView skipBtn;

  @BindView(R.id.rl_upload)
  RelativeLayout rl_upload;

  String imageUrl="";
  boolean storagePermissionGranted = false;
  boolean imageUploaded = false;
  String name="";
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    PresenterInjector.injectPreMotivationMessagesPresenter(this);
    setLocale(mPresenter.getLanguage(), R.layout.pre_motivationmessage_activity);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
    mPresenter.start(getIntent().getExtras());
    requestPermission();


  }

  @Override
  public void getMotivatorName(String name) {
    this.name=name;
  }

  @Override
  public void setPresenter(PreMotivationMessageContract.Presenter presenter) {
    this.mPresenter = presenter;
  }

  @Override
  public Context getContext() {
    return this;
  }

  void goToMotivationMessages() {
    Intent intent = new Intent(this, MotivationMessagesActivity.class);
    intent.putExtra("MOTIVATOR_NAME",name);
    mPresenter.saveImageUrl(imageUrl);
    //intent.putExtra("MOTIVATOR_IMAGE",imageUrl);
    startActivity(intent);
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  public void uploadFile() {
    Intent intent = new Intent();
    intent.setType("*/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
    startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), UPLOAD_FILE_REQUEST);
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
  }

  @Override
  public void setLocale(String localeName, int layout) {
    super.setLocale(localeName, layout);
  }

  @Override
  public void showMessage(Context context, String message) {
    super.showMessage(context, message);
  }

  private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
    new AlertDialog.Builder(PreMotivationMessageActivity.this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 0) {
        storagePermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


        if (storagePermissionGranted) {

        } else {
          for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              if (shouldShowRequestPermissionRationale(permission)) {
                showMessageOKCancel("You need to allow access to both the permissions",
                        new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                              requestPermissions(new String[]{Manifest.permission.CAMERA},
                                      PERMISSION_REQUEST_CODE);
                            }
                          }
                        });
                return;
              } else
                storagePermissionGranted = true;

            }

          }
        }
      }
    }
  }

  public String getMimeType(Uri uri) {
    String mimeType = null;
    if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
      ContentResolver cr = this.getContentResolver();
      mimeType = cr.getType(uri);
    } else {
      String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
              .toString());
      mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
              fileExtension.toLowerCase());
    }
    return mimeType;
  }

  public Bitmap getImageBitmapByPath(String path) {
    try {
      FileInputStream fileInputStream = new FileInputStream(new File(path)); // 2nd line
      Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
      fileInputStream.close();
      return bitmap;
    } catch (IOException e) {
      showMessage(this, "Image Not Found");
      return null;
    }


  }

  public Bitmap getImageBitmapByUri(Uri path) {
    try {
      Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);

      return bitmap;
    } catch (IOException e) {
      showMessage(this, "Image Not Found");
      return null;
    }


  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == UPLOAD_FILE_REQUEST && resultCode == RESULT_OK) {
      if (data != null) {
        Uri selectedFile = data.getData();
        String fileType = getMimeType(selectedFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap=null;


        if (fileType.equals(this.getResources().getString(R.string.image_type_jpeg)) || fileType.equals(this.getResources().getString(R.string.image_type_png))) {
          if (getImageBitmapByUri(selectedFile) != null) {
            bitmap=getImageBitmapByUri(selectedFile);
            imageView.setImageBitmap(bitmap);
            nextBtn.setText(getResources().getString(R.string.next_btn));
            imageUploaded = true;
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
          } else if (getImageBitmapByPath(selectedFile.getPath()) != null) {
            bitmap=getImageBitmapByPath(selectedFile.getPath());
            imageView.setImageBitmap(bitmap);
            nextBtn.setText(getResources().getString(R.string.next_btn));
            imageUploaded = true;
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
          }

          if (bitmap!=null)
          {
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            imageUrl= Base64.encodeToString(byteArray, Base64.DEFAULT);
            // imageUrl=selectedFile.toString();
          }
        }




      }

    }
  }

  @OnClick({R.id.next_btn, R.id.skip_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.next_btn:

        if (storagePermissionGranted) {
          if (imageUploaded)
            goToMotivationMessages();
          else
            uploadFile();
        } else
          requestPermission();

        break;
      case R.id.skip_btn:
        goToMotivationMessages();
        break;
    }
  }
}