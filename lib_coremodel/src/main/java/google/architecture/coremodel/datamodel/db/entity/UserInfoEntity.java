package google.architecture.coremodel.datamodel.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import google.architecture.coremodel.data.S_UserInfo;

/**
 * @author lq.zeng
 * @date 2018/4/17
 */
@Entity(tableName = "user_info_entity")
public class UserInfoEntity {
    public static final String ID = "user_info_entity";

    @NonNull
    @PrimaryKey
    public String id;
    @Embedded(prefix = "user_info")
    public S_UserInfo userInfo;

    @Ignore
    public UserInfoEntity() {

    }

    public UserInfoEntity(@NonNull String id, S_UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
