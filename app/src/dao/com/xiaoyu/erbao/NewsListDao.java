package com.xiaoyu.erbao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.xiaoyu.erbao.NewsList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table NEWS_LIST.
*/
public class NewsListDao extends AbstractDao<NewsList, Integer> {

    public static final String TABLENAME = "NEWS_LIST";

    /**
     * Properties of entity NewsList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property NewsId = new Property(0, Integer.class, "newsId", true, "NEWS_ID");
        public final static Property NewsType = new Property(1, Integer.class, "newsType", false, "NEWS_TYPE");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Summary = new Property(3, String.class, "summary", false, "SUMMARY");
        public final static Property ThumPicUrl = new Property(4, String.class, "thumPicUrl", false, "THUM_PIC_URL");
        public final static Property ContentText = new Property(5, String.class, "contentText", false, "CONTENT_TEXT");
        public final static Property ContentUrls = new Property(6, String.class, "contentUrls", false, "CONTENT_URLS");
        public final static Property Date = new Property(7, java.util.Date.class, "date", false, "DATE");
        public final static Property Read = new Property(8, Boolean.class, "read", false, "READ");
        public final static Property Eyes = new Property(9, String.class, "eyes", false, "EYES");
        public final static Property Says = new Property(10, String.class, "says", false, "SAYS");
    };


    public NewsListDao(DaoConfig config) {
        super(config);
    }
    
    public NewsListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'NEWS_LIST' (" + //
                "'NEWS_ID' INTEGER PRIMARY KEY ," + // 0: newsId
                "'NEWS_TYPE' INTEGER," + // 1: newsType
                "'TITLE' TEXT," + // 2: title
                "'SUMMARY' TEXT," + // 3: summary
                "'THUM_PIC_URL' TEXT," + // 4: thumPicUrl
                "'CONTENT_TEXT' TEXT," + // 5: contentText
                "'CONTENT_URLS' TEXT," + // 6: contentUrls
                "'DATE' INTEGER," + // 7: date
                "'READ' INTEGER," + // 8: read
                "'EYES' TEXT," + // 9: eyes
                "'SAYS' TEXT);"); // 10: says
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NEWS_LIST'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NewsList entity) {
        stmt.clearBindings();
 
        Integer newsId = entity.getNewsId();
        if (newsId != null) {
            stmt.bindLong(1, newsId);
        }
 
        Integer newsType = entity.getNewsType();
        if (newsType != null) {
            stmt.bindLong(2, newsType);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(4, summary);
        }
 
        String thumPicUrl = entity.getThumPicUrl();
        if (thumPicUrl != null) {
            stmt.bindString(5, thumPicUrl);
        }
 
        String contentText = entity.getContentText();
        if (contentText != null) {
            stmt.bindString(6, contentText);
        }
 
        String contentUrls = entity.getContentUrls();
        if (contentUrls != null) {
            stmt.bindString(7, contentUrls);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(8, date.getTime());
        }
 
        Boolean read = entity.getRead();
        if (read != null) {
            stmt.bindLong(9, read ? 1l: 0l);
        }
 
        String eyes = entity.getEyes();
        if (eyes != null) {
            stmt.bindString(10, eyes);
        }
 
        String says = entity.getSays();
        if (says != null) {
            stmt.bindString(11, says);
        }
    }

    /** @inheritdoc */
    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public NewsList readEntity(Cursor cursor, int offset) {
        NewsList entity = new NewsList( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // newsId
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // newsType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // summary
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // thumPicUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // contentText
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // contentUrls
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // date
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // read
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // eyes
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // says
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NewsList entity, int offset) {
        entity.setNewsId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setNewsType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSummary(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setThumPicUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContentText(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContentUrls(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setRead(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setEyes(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSays(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Integer updateKeyAfterInsert(NewsList entity, long rowId) {
        return entity.getNewsId();
    }
    
    /** @inheritdoc */
    @Override
    public Integer getKey(NewsList entity) {
        if(entity != null) {
            return entity.getNewsId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
