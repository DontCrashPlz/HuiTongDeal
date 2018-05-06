package com.zheng.zchlibrary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库助手类
 * Created by Zheng on 2016/9/6.
 */
public class MYSQLiteOpenHelper extends SQLiteOpenHelper {
    //数据库的名称
    private static final String DB_NAME = "yicr_soa";

    // 数据库版本为1
    private static final int DB_VERSION = 2;

    /**
     * 数据库表名,联系人信息表
     */
    public static final String DB_TABLE_CONTACTS_INFO = "t_contacts_info";

    /**
     * 联系人信息表结构
     */
    public interface TableContactsInfo {
        /**
         * 联系人主键
         */
        String ID = "_id";
        /**
         * 联系人姓名
         */
        String EMPNAME = "_empname";
        /**
         * 联系人移动电话
         */
        String MOBILENO = "_mobileno";
        /**
         * 联系人固定电话
         */
        String OTEL = "_otel";
        /**
         * 联系人邮箱
         */
        String OEMAIL = "_oemail";
        /**
         * 联系人性别
         */
        String SEX = "_sex";
        /**
         * 联系人所属部门id
         */
        String ORGID = "_orgid";
        /**
         * 联系人所属部门
         */
        String ORGNAME = "_orgname";
        /**
         * 联系人职务
         */
        String POSINAME = "_posiname";
        /**
         * 联系人id
         */
        String EMPID = "_empid";
        /**
         * 联系人头像url
         */
        String HEADIMG = "_headimg";

    }

    // 创建联系人信息表SQL语句
    private static final String SQL_CREATE_TABLE_CONTACTS_INFO =
            "CREATE TABLE '" + DB_TABLE_CONTACTS_INFO + "' ( '"
                    + TableContactsInfo.ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
                    + TableContactsInfo.EMPNAME + "' TEXT NOT NULL, '"
                    + TableContactsInfo.MOBILENO + "' TEXT, '"
                    + TableContactsInfo.OTEL + "' TEXT, '"
                    + TableContactsInfo.OEMAIL + "' TEXT, '"
                    + TableContactsInfo.SEX + "' TEXT  NOT NULL, '"
                    + TableContactsInfo.ORGID + "' INTEGER  NOT NULL, '"
                    + TableContactsInfo.ORGNAME + "' TEXT  NOT NULL, '"
                    + TableContactsInfo.POSINAME + "' TEXT  NOT NULL, '"
                    + TableContactsInfo.EMPID + "' INTEGER  NOT NULL, '"
                    + TableContactsInfo.HEADIMG + "' TEXT);";

    /**
     * 数据库表名,附件信息表
     */
    public static final String DB_TABLE_FILES_INFO = "t_files_info";


    /**
     * 附件信息表结构
     */
    public interface TableFilesInfo {
        /**
         * 附件主键
         */
        String ID = "_id";
        /**
         * 附件名
         */
        String FILENAME = "_filename";
        /**
         * 附件上传时间
         */
        String UPLOADTIME = "_uploadtime";
        /**
         * 附件下载地址
         */
        String DOWNLOADURL = "_downloadurl";
        /**
         * 附件大小
         */
        String FILESIZE = "_filesize";
        /**
         * 附件ID
         */
        String FILEID = "_fileid";

    }

    // 创建附件信息表SQL语句
    private static final String SQL_CREATE_TABLE_FILES_INFO =
            "CREATE TABLE '" + DB_TABLE_FILES_INFO + "' ( '"
                    + TableFilesInfo.ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
                    + TableFilesInfo.FILENAME + "' TEXT NOT NULL, '"
                    + TableFilesInfo.UPLOADTIME + "' TEXT NOT NULL, '"
                    + TableFilesInfo.DOWNLOADURL + "' TEXT NOT NULL, '"
                    + TableFilesInfo.FILESIZE + "' TEXT NOT NULL, '"
                    + TableFilesInfo.FILEID + "' TEXT NOT NULL);";


    /**
     * 数据库表名,部门信息表
     */
    public static final String DB_TABLE_DEPARTMENT_INFO = "t_department_info";

    /**
     * 部门信息表结构
     */
    public interface TableDepartmentInfo {
        /**
         * 部门主键
         */
        String ID = "_id";
        /**
         * 部门ID
         */
        String ORGID = "_orgid";
        /**
         * 部门名称
         */
        String ORGNAME = "_orgname";
        /**
         * 父部门ID
         */
        String PARENTORGID = "_parentorgid";
        /**
         * 部门人数
         */
        String CHILDRENNUM = "_childrennum";

    }

    // 创建联系人信息表SQL语句
    private static final String SQL_CREATE_TABLE_DEPARTMENT_INFO =
            "CREATE TABLE '" + DB_TABLE_DEPARTMENT_INFO + "' ( '"
                    + TableDepartmentInfo.ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
                    + TableDepartmentInfo.ORGID + "' INTEGER  NOT NULL, '"
                    + TableDepartmentInfo.ORGNAME + "' TEXT  NOT NULL, '"
                    + TableDepartmentInfo.CHILDRENNUM + "' INTEGER  NOT NULL, '"
                    + TableDepartmentInfo.PARENTORGID + "' INTEGER  NOT NULL);";


    public MYSQLiteOpenHelper(Context context){
        this(context, DB_NAME, null, DB_VERSION);
    }

    public MYSQLiteOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 创建数据库时调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建联系人信息表
        db.execSQL(SQL_CREATE_TABLE_CONTACTS_INFO);
        // 创建附件信息表
        db.execSQL(SQL_CREATE_TABLE_FILES_INFO);
        // 创建附件信息表
        db.execSQL(SQL_CREATE_TABLE_DEPARTMENT_INFO);
    }

    /**
     * 更新数据库时使用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 创建附件信息表
        db.execSQL(SQL_CREATE_TABLE_DEPARTMENT_INFO);
    }

}
