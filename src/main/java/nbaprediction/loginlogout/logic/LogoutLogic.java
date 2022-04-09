package nbaprediction.loginlogout.logic;

import nbaprediction.data.DAOFactory;
import nbaprediction.data.DBType;
import nbaprediction.data.UserDAO;

public class LogoutLogic {
	
	public static void setAuthorizationStatus(String login, int status) {
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			userDAO.setAuthorizationStatusByLogin(login, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
