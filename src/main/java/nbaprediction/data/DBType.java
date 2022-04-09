package nbaprediction.data;

import nbaprediction.datalayer.oracle.OracleDBDAOFactory;

public enum DBType {
	
	ORACLE {
		public DAOFactory getDAOFactory() throws Exception {
			return OracleDBDAOFactory.getInstance();
		}
	};

	public abstract DAOFactory getDAOFactory() throws Exception;

}
