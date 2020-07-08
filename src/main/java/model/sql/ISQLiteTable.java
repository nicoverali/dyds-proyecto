package model.sql;

import org.intellij.lang.annotations.Language;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public interface ISQLiteTable {
    CachedRowSet getAllWhere(String whereCondition) throws SQLException;

    CachedRowSet getAll() throws SQLException;

    int insert(String[] values) throws SQLException;
}
