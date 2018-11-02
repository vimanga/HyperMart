package afinal.hp.madfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import afinal.hp.madfinal.User;
import afinal.hp.madfinal.UserProfile;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + UserProfile.Users.TABLE_NAME + "(" + UserProfile.Users.COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT , " + UserProfile.Users.COL_2 + " TEXT NOT NULL UNIQUE , " + UserProfile.Users.COL_3 + " TEXT , " + UserProfile.Users.COL_4 + " TEXT , " + UserProfile.Users.COL_5 + " TEXT " + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME);

    }

    public boolean addInfo(User u) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProfile.Users.COL_2, u.getUserName());
        contentValues.put(UserProfile.Users.COL_3, u.getDateOfBirth());
        contentValues.put(UserProfile.Users.COL_4, u.getGender());
        contentValues.put(UserProfile.Users.COL_5, u.getPassword());

        long result = sqLiteDatabase.insert(UserProfile.Users.TABLE_NAME, null, contentValues);

        if (result == -1) {

            return false;
        } else {
            return true;

        }
    }

    public Boolean checkValidity(User user) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql = "SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE " + UserProfile.Users.COL_2 + " = '" + user.getUserName() + "' AND " + UserProfile.Users.COL_5 + " = '" + user.getPassword() + "'";

        Cursor res = sqLiteDatabase.rawQuery(sql, null);

        if (res.getCount() > 0) {

            return true;

        } else {

            return false;
        }

    }

    public int getId(String userName){

        int id = 0;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql = "SELECT ID FROM " + UserProfile.Users.TABLE_NAME + " WHERE userName = '" + userName + "'" ;

        Log.d("USERCHECK",sql);

        Cursor result = sqLiteDatabase.rawQuery(sql,null);

        while(result.moveToNext()){

           id = result.getInt(0);

        }

        return  id;

    }



    public Cursor readAllInfor(int id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sql = " SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE ID = ( SELECT ID FROM " + UserProfile.Users.TABLE_NAME + " WHERE " + UserProfile.Users.COL_2 + " = '" + userName + "' )" + "" ;

        String sql = " SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE ID = " +id+ "";
        Log.d("SQL ",sql);
        return sqLiteDatabase.rawQuery(sql, null);


    }

    public Cursor readAllInfor() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql = " SELECT * FROM " + UserProfile.Users.TABLE_NAME ;

        Cursor result = sqLiteDatabase.rawQuery(sql, null);

        return result;


    }

    public boolean updateInfor(User u,int id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(UserProfile.Users.COL_2,u.getUserName());
        contentValues.put(UserProfile.Users.COL_3,u.getDateOfBirth());
        contentValues.put(UserProfile.Users.COL_4,u.getGender());
        contentValues.put(UserProfile.Users.COL_5,u.getPassword());


        int result = sqLiteDatabase.update(UserProfile.Users.TABLE_NAME,contentValues, UserProfile.Users.COL_1+ " = ? ",new String[]{String.valueOf(id)});


        if(result > 0 ){

            return  true;

        }

        else {

            return  false;
        }


    }


    public  int deleteInfo(int id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.delete(UserProfile.Users.TABLE_NAME,"ID = ? ", new String[]{String.valueOf(id)});


    }




}