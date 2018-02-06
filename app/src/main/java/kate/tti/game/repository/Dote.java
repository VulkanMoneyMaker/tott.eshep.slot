package kate.tti.game.repository;


import kate.tti.game.repository.impl.DoteImpl;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Dote {

    @GET("test10")
    Call<DoteImpl> check();

}
