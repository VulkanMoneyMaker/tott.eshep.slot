package tott.eshep.slot.repository;


import tott.eshep.slot.repository.impl.DoteImpl;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Dote {

    @GET("test10")
    Call<DoteImpl> check();

}
