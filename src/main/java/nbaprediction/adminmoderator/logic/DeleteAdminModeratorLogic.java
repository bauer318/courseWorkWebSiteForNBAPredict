package nbaprediction.adminmoderator.logic;

import nbaprediction.data.DAOFactory;
import nbaprediction.data.DBType;
import nbaprediction.data.UserDAO;

public class DeleteAdminModeratorLogic {
	public static void deleteUserByID(int id) {
		try (DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE)) {
			UserDAO userDAO = factory.getUserDAO();
			if(id>0) {
				userDAO.deleteUserByID(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
