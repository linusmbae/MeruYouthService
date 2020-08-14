package org.meruyouthservice.services;

import org.meruyouthservice.models.Token;
import org.meruyouthservice.models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MysApi {
    @POST("users/login")
    Call<Users> login(@Body Users users);


}
