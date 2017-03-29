package br.com.banconeon;

import java.util.List;

import br.com.banconeon.model.TransferHistory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("/GenerateToken")
    Call<ResponseBody> generateToken(@Query("nome") String nome, @Query("email") String email);

    @FormUrlEncoded
    @POST("/SendMoney")
    Call<Boolean> sendMoney(@Field("ClienteId") String ClienteId, @Field("token") String token, @Field("valor") Double valor);

    @GET("/GetTransfers")
    Call<List<TransferHistory>> getTransfers(@Query("token") String token);

}
