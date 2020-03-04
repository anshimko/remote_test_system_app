package by.htp.ts.dao.connection_pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.ts.dao.DAOException;

public final class CloseConnectionBlockFinally {
	
	public final static void close (Connection con, PreparedStatement pst, ResultSet rs) throws DAOException {
		
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		try {
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
	}

}
