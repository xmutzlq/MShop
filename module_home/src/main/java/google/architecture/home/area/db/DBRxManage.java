package google.architecture.home.area.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.apkfuns.logutils.LogUtils;
import com.kongzue.dialog.v2.WaitDialog;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import google.architecture.common.widget.address.ISelectAble;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/5/30
 */

/**
 * 用于操作外部数据库DB
 */
public class DBRxManage extends SQLiteOpenHelper implements DBRxAction{

    private SQLiteDatabase mDataBase;
    private Context mContext;

//    private static final String DATABASE_PATH = "/data/data/google.architecture.home/databases/";
    private static final String DATABASE_PATH = "/data/data/com.bop.android.shopping/databases/";
    private static final String DATABASE_NAME = "area.db";
    private static final int DATABASE_VERSION = 1;

    public DBRxManage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public DBRxManage(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    public void createDataBaseToSdCard(IDBResult dbResult) {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            if(dbResult != null) dbResult.onDBResult();
        } else {
            getReadableDatabase();
            copyDataBase(dbResult);
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            LogUtils.tag("zlq").e("数据库不存在！");
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase(IDBResult dbResult) {
        Observable.create(e -> {
            InputStream myInput = null;
            OutputStream myOutput = null;
            try {
                myInput = mContext.getAssets().open(DATABASE_NAME);
                String outFileName = DATABASE_PATH + DATABASE_NAME;
                myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            }catch (Exception ex){
                LogUtils.tag("zlq").e("ex = " + ex.getMessage());
            } finally {
                // Close the streams
                if(myOutput != null) myOutput.flush();
                if(myOutput != null) myOutput.close();
                if(myInput != null) myInput.close();
            }
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                if(dbResult != null) dbResult.onPreDBResult();
            }

            @Override
            public void onNext(Object o) {}

            @Override
            public void onError(Throwable e) {
                WaitDialog.dismiss();
            }

            @Override
            public void onComplete() {
                if(dbResult != null) dbResult.onDBResult();
            }
        });
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public Observable<List<ISelectAble>> getProvinces() {
        return Observable.create((ObservableEmitter<List<ISelectAble>> e) ->{
            List<ISelectAble> provinces = new ArrayList<>();
            String sql = "select * from Province";
            Cursor cursor = getReadableDatabase().rawQuery(sql, null);
            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i< cursor.getCount(); i ++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    ISelectAble able = new SelectAble(id, name, String.valueOf(id));
                    provinces.add(able);
                }
                cursor.close();
            }
            e.onNext(provinces);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ISelectAble>> getCitys(String provinceId) {
        return Observable.create((ObservableEmitter<List<ISelectAble>> e) -> {
            List<ISelectAble> citys = new ArrayList<>();
            String sql = "select * from City where province_id=?";
            Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{provinceId});
            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i< cursor.getCount(); i ++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String province_id = cursor.getString(cursor.getColumnIndex("province_id"));
                    ISelectAble able = new SelectAble(id, name, province_id);
                    citys.add(able);
                }
                cursor.close();
            }
            e.onNext(citys);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ISelectAble>> getCountrys(String cityId) {
        return Observable.create((ObservableEmitter<List<ISelectAble>> e) -> {
            List<ISelectAble> countrys = new ArrayList<>();
            String sql = "select * from County where city_id=?";
            Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{cityId});
            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i< cursor.getCount(); i ++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String city_id = cursor.getString(cursor.getColumnIndex("city_id"));
                    ISelectAble able = new SelectAble(id, name, city_id);
                    countrys.add(able);
                }
                cursor.close();
            }
            e.onNext(countrys);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<ISelectAble>> getStreets(String countryId) {
        return Observable.create((ObservableEmitter<List<ISelectAble>> e) -> {
            List<ISelectAble> streets = new ArrayList<>();
            String sql = "select * from Street where county_id=?";
            Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{countryId});
            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i< cursor.getCount(); i ++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String county_id = cursor.getString(cursor.getColumnIndex("county_id"));
                    ISelectAble able = new SelectAble(id, name, county_id);
                    streets.add(able);
                }
                cursor.close();
            }
            e.onNext(streets);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public interface IDBResult {
        void onPreDBResult();
        void onDBResult();
    }

    static class SelectAble implements ISelectAble {

        int id;
        String name;
        String referenceId;

        public SelectAble(int id, String name, String referenceId) {
            this.id = id;
            this.name = name;
            this.referenceId = referenceId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public Object getArg() {
            return referenceId;
        }
    }
}
