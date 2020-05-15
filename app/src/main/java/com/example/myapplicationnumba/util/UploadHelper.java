//package com.example.myapplicationnumba.util;
//
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class UploadHelper extends Presenter {
//
//    //public static final String TAG = "UploadHelper";
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    private final OkHttpClient client = new OkHttpClient();
//
//    public String upload(String imageType, String userPhone, File file) throws NetworkException{
//
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
/////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + fileName + "\""),
////                        RequestBody.create(MEDIA_TYPE_PNG, file))
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"imagetype\""),
////                        RequestBody.create(null, imageType))
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"userphone\""),
////                        RequestBody.create(null, userPhone))
//
//                .addFormDataPart("file", "head_image", fileBody)
//                .addFormDataPart("imagetype", imageType)
//                .addFormDataPart("userphone", userPhone)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://xxxxx")
//                .post(requestBody)
//                .build();
//
//        Response response;
//        try {
//            response = client.newCall(request).execute();
//            String jsonString = response.body().string();
//
//            if(!response.isSuccessful()){
//                throw new NetworkException("upload error code "+response);
//            }else{
//                JSONObject jsonObject = new JSONObject(jsonString);
//                int errorCode = jsonObject.getInt("errorCode");
//                if(errorCode == 0){
//                    Log.d(TAG," upload data ="+jsonObject.getString("data"));
//                    return jsonObject.getString("data");
//                }else {
//                    throw new NetworkException("upload error code "+errorCode+",errorInfo="+jsonObject.getString("errorInfo"));
//                }
//            }
//
//        } catch (IOException e) {
//            Log.d(TAG,"upload IOException ",e);
//        }catch (JSONException e){
//            Log.d(TAG,"upload JSONException ",e);
//        }
//        return null;
//    }
