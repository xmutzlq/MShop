package google.architecture.coremodel.datamodel.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */
@Entity(tableName = "search_info_entity")
public class SearchInfoEntity {
    @NonNull
    @PrimaryKey
    public String keyWord;

    @Ignore
    public SearchInfoEntity() {}

    public SearchInfoEntity(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public boolean equals(Object obj) {
        return (((SearchInfoEntity)obj).keyWord).equalsIgnoreCase(keyWord);
    }
}
