package khan.shadik.mongoarticle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import khan.shadik.mongoarticle.network.ApiService;

public class AddEmployeeActivity extends AppCompatActivity {

    ApiService apiService;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    FloatingActionButton fabCamera, fabUpload;
    Bitmap mBitmap;
    TextView textView;

    private static EditText registerName;
    private static EditText registercompanyNumber;
    private static EditText registerAddress;
    private static EditText registerPhoneNumber;
    private static EditText registerEmailAddress;
    private static Button registerUpload;
    private static Button submitRegister;
    private static Button cancelRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

//        fabCamera = (FloatingActionButton) findViewById(R.id.fab);
//        fabUpload =  (FloatingActionButton)findViewById(R.id.fabUpload);
//        textView = (TextView) findViewById(R.id.textView);
//        fabCamera.setOnClickListener(this);
//        fabUpload.setOnClickListener(this);
//
//        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mBitmap != null)
//                    multipartImageUploadWithBody();
//                else {
//                    Toast.makeText(getApplicationContext(), "Bitmap is null. Try again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        askPermissions();
//        initRetrofitClient();

    }

//    private void askPermissions() {
//        permissions.add(CAMERA);
//        permissions.add(WRITE_EXTERNAL_STORAGE);
//        permissions.add(READ_EXTERNAL_STORAGE);
//        permissionsToRequest = findUnAskedPermissions(permissions);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//
//            if (permissionsToRequest.size() > 0)
//                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        }
//    }
//
//    private void initRetrofitClient() {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        apiService = new Retrofit.Builder().baseUrl("http://192.168.0.17:4000/").client(client).build().create(ApiService.class);
//    }
//
//
//    public Intent getPickImageChooserIntent() {
//
//        Uri outputFileUri = getCaptureImageOutputUri();
//
//        List<Intent> allIntents = new ArrayList<>();
//        PackageManager packageManager = getPackageManager();
//
//        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for (ResolveInfo res : listCam) {
//            Intent intent = new Intent(captureIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            if (outputFileUri != null) {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            }
//            allIntents.add(intent);
//        }
//
//        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
//        for (ResolveInfo res : listGallery) {
//            Intent intent = new Intent(galleryIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            allIntents.add(intent);
//        }
//
//        Intent mainIntent = allIntents.get(allIntents.size() - 1);
//        for (Intent intent : allIntents) {
//            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
//                mainIntent = intent;
//                break;
//            }
//        }
//        allIntents.remove(mainIntent);
//
//        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
//
//        return chooserIntent;
//    }
//
//
//    private Uri getCaptureImageOutputUri() {
//        Uri outputFileUri = null;
//        File getImage = getExternalFilesDir("");
//        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
//        }
//        return outputFileUri;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//
//        if (resultCode == Activity.RESULT_OK) {
//
//            ImageView imageView = (ImageView) findViewById(R.id.imageView);
//
//            if (requestCode == IMAGE_RESULT) {
//
//
//                String filePath = getImageFilePath(data);
//                if (filePath != null) {
//                    mBitmap = BitmapFactory.decodeFile(filePath);
//                    imageView.setImageBitmap(mBitmap);
//                }
//            }
//
//        }
//
//    }
//
//
//    private String getImageFromFilePath(Intent data) {
//        boolean isCamera = data == null || data.getData() == null;
//
//        if (isCamera) return getCaptureImageOutputUri().getPath();
//        else return getPathFromURI(data.getData());
//
//    }
//
//    public String getImageFilePath(Intent data) {
//        return getImageFromFilePath(data);
//    }
//
//    private String getPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Audio.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putParcelable("pic_uri", picUri);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        picUri = savedInstanceState.getParcelable("pic_uri");
//    }
//
//    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
//        ArrayList<String> result = new ArrayList<String>();
//
//        for (String perm : wanted) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//
//        return result;
//    }
//
//    private boolean hasPermission(String permission) {
//        if (canMakeSmores()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
//            }
//        }
//        return true;
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//    private boolean canMakeSmores() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        switch (requestCode) {
//
//            case ALL_PERMISSIONS_RESULT:
//                for (String perms : permissionsToRequest) {
//                    if (!hasPermission(perms)) {
//                        permissionsRejected.add(perms);
//                    }
//                }
//
//                if (permissionsRejected.size() > 0) {
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                        }
//                                    });
//                            return;
//                        }
//                    }
//
//                }
//
//                break;
//        }
//
//    }
//
//    private void multipartImageUpload() {
//        try {
//            File filesDir = getApplicationContext().getFilesDir();
//            File file = new File(filesDir, "image" + ".png");
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//            byte[] bitmapdata = bos.toByteArray();
//
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//
//            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
////            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");
//            Map<String, Object> jsonParams = new ArrayMap<>();
//            //put something inside the map, could be null
//            jsonParams.put("code", "some code here");
//            jsonParams.put("upload", "fuckkk code here");
//            jsonParams.put("name", "name code here");
//            RequestBody jsonBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
//            Call<ResponseBody> req = apiService.postImage(body, jsonBody);
//
//
//            req.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    if (response.code() == 200) {
//                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//            });
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void multipartImageUploadWithBody() {
//        try {
//            File filesDir = getApplicationContext().getFilesDir();
//            File file = new File(filesDir, "image" + ".png");
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//            byte[] bitmapdata = bos.toByteArray();
//
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//
//
//            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
//            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");
//
//            registerName = (EditText) findViewById(R.id.et_register_name);
//            registerAddress = (EditText) findViewById(R.id.et_register_name);
//            registercompanyNumber = (EditText) findViewById(R.id.et_register_name);
//            registerEmailAddress = (EditText) findViewById(R.id.et_register_name);
//            registerPhoneNumber = (EditText) findViewById(R.id.et_register_name);
//
//            String nameReg = registerName.getText().toString();
//            String companyIDReg = registercompanyNumber.getText().toString();
//            String addressReg = registerAddress.getText().toString();
//            String phoneNumberReg = registerPhoneNumber.getText().toString();
//            String emailAddReg = registerEmailAddress.getText().toString();
//
//            Toast.makeText(AddEmployeeActivity.this, emailAddReg + "___", Toast.LENGTH_SHORT).show();
//
//            Map<String, Object> jsonParams = new ArrayMap<>();
//            jsonParams.put("name", nameReg);
//            jsonParams.put("email", companyIDReg);
//            jsonParams.put("address", addressReg);
//            jsonParams.put("phone_number", phoneNumberReg);
//            jsonParams.put("company_num", emailAddReg);
//
//            RequestBody jsonBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
//            Call<ResponseBody> req = apiService.postImage(body, jsonBody);
//
//
//            req.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    if (response.code() == 200) {
//                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//            });
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.fab:
//                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
//                break;
//
//            case R.id.fabUpload:
//                if (mBitmap != null)
//                    multipartImageUpload();
//                else {
//                    Toast.makeText(getApplicationContext(), "Bitmap is null. Try again", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//
//        }
//    }

}
