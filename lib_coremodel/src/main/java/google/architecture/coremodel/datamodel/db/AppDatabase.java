package google.architecture.coremodel.datamodel.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import google.architecture.coremodel.datamodel.db.converter.RoomDataConverter;
import google.architecture.coremodel.datamodel.db.dao.SearchInfoDao;
import google.architecture.coremodel.datamodel.db.dao.UserInfoDao;
import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.coremodel.datamodel.db.entity.UserInfoEntity;

/**
 * 重点注意:在数据库创建完成后，如果对象中的字段增加了，必须做版本迁移
 * 比如：new UserInfoEntity(UserInfoEntity.ID, userInfo) 参数是一个UserInfo对象当成表字段，如果UserInfo的字段改变了，就是数据表改变，就会触发SQLite数据库schema的改变
 * <br/>异常：Room cannot verify the data integrity.
 * Looks like you’ve changed schema but forgot to update the version number.
 * You can simply fix this by increasing the version number
 * <br/>解决方案：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0728/8278.html
 * @author lq.zeng
 * @date 2018/4/17
 */
@Database(entities = {UserInfoEntity.class, SearchInfoEntity.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "ky-db";

    private static AppDatabase INSTANCE;

    public abstract UserInfoDao userInfoDao();

    public abstract SearchInfoDao searchInfoDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                    INSTANCE.updateDatabaseCreated(context);
                }
            }
        }
        return INSTANCE;
    }

    /**数据库迁移 version需要+1**/
    public static AppDatabase getMigration(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2).build();
                    INSTANCE.updateDatabaseCreated(context);
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE XXX " + " ADD COLUMN XXX TEXT");
        }
    };

    private void updateDatabaseCreated(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true);
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
