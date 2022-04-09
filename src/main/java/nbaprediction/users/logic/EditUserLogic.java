package nbaprediction.users.logic;

import java.util.ArrayList;
import java.util.List;

import nbaprediction.data.DAOFactory;
import nbaprediction.data.DBType;
import nbaprediction.data.UserDAO;
import nbaprediction.datalayer.data.User;

public class EditUserLogic {
	
	public static List<User> getAdminModerators(){
		List<User> adminModerators = new  ArrayList<User>();
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			adminModerators = userDAO.getAdminModer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminModerators;
	}

}
