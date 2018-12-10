package google.architecture.coremodel.datamodel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import io.reactivex.Flowable;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */
@Dao
public interface SearchInfoDao {
    @Query("SELECT * FROM search_info_entity")
    Flowable<List<SearchInfoEntity>> loadSearchInfoEntity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSearchInfoEntity(SearchInfoEntity userInfoEntity);

    @Delete
    public int deleteSearchInfoEntitys(List<SearchInfoEntity> searchInfoEntities);

    @Delete
    public int deleteSearchInfoEntity(SearchInfoEntity searchInfoEntities);
}
