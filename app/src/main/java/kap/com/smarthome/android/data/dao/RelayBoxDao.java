package kap.com.smarthome.android.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import kap.com.smarthome.android.data.bean.RelayBox;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RELAY_BOX".
*/
public class RelayBoxDao extends AbstractDao<RelayBox, Long> {

    public static final String TABLENAME = "RELAY_BOX";

    /**
     * Properties of entity RelayBox.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property GUID = new Property(1, String.class, "GUID", false, "GUID");
        public final static Property BOX_ID = new Property(2, String.class, "BOX_ID", false, "BOX__ID");
        public final static Property NAME = new Property(3, String.class, "NAME", false, "NAME");
        public final static Property IP = new Property(4, String.class, "IP", false, "IP");
        public final static Property PORT = new Property(5, int.class, "PORT", false, "PORT");
        public final static Property PLATFORM_ADDR = new Property(6, String.class, "PLATFORM_ADDR", false, "PLATFORM__ADDR");
        public final static Property PLATFORM_PORT = new Property(7, int.class, "PLATFORM_PORT", false, "PLATFORM__PORT");
        public final static Property MASK = new Property(8, String.class, "MASK", false, "MASK");
        public final static Property RELAY_ORDER = new Property(9, int.class, "RELAY_ORDER", false, "RELAY__ORDER");
        public final static Property HARDWARE_VERSION = new Property(10, String.class, "HARDWARE_VERSION", false, "HARDWARE__VERSION");
        public final static Property SOFTWARE_VERSION = new Property(11, String.class, "SOFTWARE_VERSION", false, "SOFTWARE__VERSION");
        public final static Property MACHINE_CODE = new Property(12, String.class, "MACHINE_CODE", false, "MACHINE__CODE");
    }


    public RelayBoxDao(DaoConfig config) {
        super(config);
    }
    
    public RelayBoxDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RELAY_BOX\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"GUID\" TEXT," + // 1: GUID
                "\"BOX__ID\" TEXT UNIQUE ," + // 2: BOX_ID
                "\"NAME\" TEXT," + // 3: NAME
                "\"IP\" TEXT," + // 4: IP
                "\"PORT\" INTEGER NOT NULL ," + // 5: PORT
                "\"PLATFORM__ADDR\" TEXT," + // 6: PLATFORM_ADDR
                "\"PLATFORM__PORT\" INTEGER NOT NULL ," + // 7: PLATFORM_PORT
                "\"MASK\" TEXT," + // 8: MASK
                "\"RELAY__ORDER\" INTEGER NOT NULL ," + // 9: RELAY_ORDER
                "\"HARDWARE__VERSION\" TEXT," + // 10: HARDWARE_VERSION
                "\"SOFTWARE__VERSION\" TEXT," + // 11: SOFTWARE_VERSION
                "\"MACHINE__CODE\" TEXT);"); // 12: MACHINE_CODE
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_RELAY_BOX_GUID ON \"RELAY_BOX\"" +
                " (\"GUID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RELAY_BOX\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RelayBox entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String GUID = entity.getGUID();
        if (GUID != null) {
            stmt.bindString(2, GUID);
        }
 
        String BOX_ID = entity.getBOX_ID();
        if (BOX_ID != null) {
            stmt.bindString(3, BOX_ID);
        }
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(4, NAME);
        }
 
        String IP = entity.getIP();
        if (IP != null) {
            stmt.bindString(5, IP);
        }
        stmt.bindLong(6, entity.getPORT());
 
        String PLATFORM_ADDR = entity.getPLATFORM_ADDR();
        if (PLATFORM_ADDR != null) {
            stmt.bindString(7, PLATFORM_ADDR);
        }
        stmt.bindLong(8, entity.getPLATFORM_PORT());
 
        String MASK = entity.getMASK();
        if (MASK != null) {
            stmt.bindString(9, MASK);
        }
        stmt.bindLong(10, entity.getRELAY_ORDER());
 
        String HARDWARE_VERSION = entity.getHARDWARE_VERSION();
        if (HARDWARE_VERSION != null) {
            stmt.bindString(11, HARDWARE_VERSION);
        }
 
        String SOFTWARE_VERSION = entity.getSOFTWARE_VERSION();
        if (SOFTWARE_VERSION != null) {
            stmt.bindString(12, SOFTWARE_VERSION);
        }
 
        String MACHINE_CODE = entity.getMACHINE_CODE();
        if (MACHINE_CODE != null) {
            stmt.bindString(13, MACHINE_CODE);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RelayBox entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String GUID = entity.getGUID();
        if (GUID != null) {
            stmt.bindString(2, GUID);
        }
 
        String BOX_ID = entity.getBOX_ID();
        if (BOX_ID != null) {
            stmt.bindString(3, BOX_ID);
        }
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(4, NAME);
        }
 
        String IP = entity.getIP();
        if (IP != null) {
            stmt.bindString(5, IP);
        }
        stmt.bindLong(6, entity.getPORT());
 
        String PLATFORM_ADDR = entity.getPLATFORM_ADDR();
        if (PLATFORM_ADDR != null) {
            stmt.bindString(7, PLATFORM_ADDR);
        }
        stmt.bindLong(8, entity.getPLATFORM_PORT());
 
        String MASK = entity.getMASK();
        if (MASK != null) {
            stmt.bindString(9, MASK);
        }
        stmt.bindLong(10, entity.getRELAY_ORDER());
 
        String HARDWARE_VERSION = entity.getHARDWARE_VERSION();
        if (HARDWARE_VERSION != null) {
            stmt.bindString(11, HARDWARE_VERSION);
        }
 
        String SOFTWARE_VERSION = entity.getSOFTWARE_VERSION();
        if (SOFTWARE_VERSION != null) {
            stmt.bindString(12, SOFTWARE_VERSION);
        }
 
        String MACHINE_CODE = entity.getMACHINE_CODE();
        if (MACHINE_CODE != null) {
            stmt.bindString(13, MACHINE_CODE);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RelayBox readEntity(Cursor cursor, int offset) {
        RelayBox entity = new RelayBox( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // GUID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // BOX_ID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // NAME
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // IP
            cursor.getInt(offset + 5), // PORT
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // PLATFORM_ADDR
            cursor.getInt(offset + 7), // PLATFORM_PORT
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // MASK
            cursor.getInt(offset + 9), // RELAY_ORDER
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // HARDWARE_VERSION
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // SOFTWARE_VERSION
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // MACHINE_CODE
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RelayBox entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBOX_ID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNAME(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIP(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPORT(cursor.getInt(offset + 5));
        entity.setPLATFORM_ADDR(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPLATFORM_PORT(cursor.getInt(offset + 7));
        entity.setMASK(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setRELAY_ORDER(cursor.getInt(offset + 9));
        entity.setHARDWARE_VERSION(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSOFTWARE_VERSION(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMACHINE_CODE(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RelayBox entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RelayBox entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RelayBox entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
