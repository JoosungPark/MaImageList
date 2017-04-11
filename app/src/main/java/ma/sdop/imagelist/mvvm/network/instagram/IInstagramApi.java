package ma.sdop.imagelist.mvvm.network.instagram;

import java.util.List;

import io.reactivex.Observable;
import ma.sdop.imagelist.common.web.dto.json.gson.ItemsDto;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by parkjoosung on 2017. 4. 10..
 */

public interface IInstagramApi {
    @GET("https://www.instagram.com/{userId}/media/?max_id={maxId}")
    Observable<List<ItemsDto>> getImages(@Path("userId") String userId, @Path("maxId") String maxId);
}
