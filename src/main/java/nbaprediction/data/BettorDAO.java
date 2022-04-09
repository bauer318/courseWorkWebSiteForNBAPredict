package nbaprediction.data;

import java.sql.SQLException;
import java.util.List;

import nbaprediction.datalayer.data.Bettor;

public interface BettorDAO {
	
	List<Bettor> getAllBettors() throws SQLException;

}
