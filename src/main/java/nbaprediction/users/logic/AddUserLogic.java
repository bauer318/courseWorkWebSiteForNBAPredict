package nbaprediction.users.logic;

import nbaprediction.data.DAOFactory;
import nbaprediction.data.DBType;
import nbaprediction.data.UserDAO;
import nbaprediction.datalayer.data.User;

public class AddUserLogic {

	public static boolean isLoginExist(String login) {
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			User user = userDAO.getUserByLogin(login);
			return (user != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void addAdminModerator(String login, String password, int groupId) {
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			if(!(login.isEmpty()||password.isEmpty()||groupId<=0)) {
				userDAO.addAdminModerator(login, password, groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void addBettor(String firstname, String lastname, String email) {
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			if(!(firstname.isEmpty()||lastname.isEmpty()||email.isEmpty())) {
				userDAO.addBettor(firstname, lastname, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
