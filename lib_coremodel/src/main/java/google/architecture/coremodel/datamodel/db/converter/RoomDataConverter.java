package google.architecture.coremodel.datamodel.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * @author lq.zeng
 * @date 2018/4/17
 */

public class RoomDataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
