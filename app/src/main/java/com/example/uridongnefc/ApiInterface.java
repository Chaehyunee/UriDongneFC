package com.example.uridongnefc;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    /*


    @FormUrlEncoded
    @POST("user/register")
    Call<String> registUser(
            @Field("id") String id,
            @Field("pw") String pw,
            @Field("name") String name,
            @Field("phone_number") String phone_number,
            @Field("introduce") String introduce,
            @Field("share_point") Double share_point
    );
*/

    /**회원가입 정보 보내기 profile 사진 존재시 **/
    @Multipart
    @POST("user/register")
    Call<UserVO> registUser(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    /**회원가입 정보 보내기 profile 사진 미존재시 **/
    @Multipart
    @POST("user/register")
    Call<UserVO> registUser_nonProfile(@PartMap Map<String, RequestBody> params);

    /** 로그인 정보 보내기 **/
    @POST("user/login")
    Call<UserVO> login(@Body UserVO user);

    /** 알람 갯수 조회 **/
    @POST("user/alarmCntSelect")
    Call<UserVO> postWriterSelectAlarm(@Body UserVO user);

    /** request 알람 갯수 초기화 **/
    @POST("user/requestAlarmInit")
    Call<UserVO> postWriterInitAlarm(@Body UserVO user);

    /** request 알람 갯수 초기화 **/
    @POST("user/responseAlarmInit")
    Call<UserVO> responseAlarmInit(@Body UserVO user);

    /** userData 조회 **/
    @POST("user/getUserData")
    Call<UserVO> getUserData(@Body UserVO user);

    /** user 프로필 수정 profile 사진 존재시 **/
    @Multipart
    @POST("user/updateUserProfile")
    Call<UserVO> updateUserProfile(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    /** user 프로필 수정 profile 사진 미존재시 **/
    @Multipart
    @POST("user/updateUserProfile")
    Call<UserVO> updateUser_nonProfile(@PartMap Map<String, RequestBody> params);

}
