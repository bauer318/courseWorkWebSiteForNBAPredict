package nbaprediction.adminmoderator.command;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.adminmoderator.logic.SaveAdminModeratorDataLogic;
import nbaprediction.datalayer.data.Bettor;
import nbaprediction.datalayer.data.User;
import nbaprediction.loginlogout.logic.LoginLogic;
import nbaprediction.users.logic.AddUserLogic;
import nbaprediction.users.logic.EditUserLogic;

public class SaveAdminModeratorDataCommand implements ActionCommand {
	private static final String PARAM_NAME_ADMIN_MODERATOR_ID = "adminModeratorID";
	private static final String PARAM_NAME_LOGIN = "userLogin";
	private static final String PARAM_NAME_PASSWORD = "userPassword";
	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/adminEditUser.jsp";
		String idAdminModeratorStr = request.getParameter(PARAM_NAME_ADMIN_MODERATOR_ID);
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		int idAdminModerator = Integer.parseInt(idAdminModeratorStr);
		if(!AddUserLogic.isLoginExist(login)) {
			SaveAdminModeratorDataLogic.updateUserByID(idAdminModerator, login, password);
		}else {
			String oldLogin = SaveAdminModeratorDataLogic.getLoginByID(idAdminModerator);
			SaveAdminModeratorDataLogic.updateUserByID(idAdminModerator, oldLogin, password);
		}
		List<User> adminModerators = EditUserLogic.getAdminModerators();
		request.setAttribute("adminModerators", adminModerators);
		List<Bettor> bettors = LoginLogic.getAllBettors();
		request.setAttribute("bettors", bettors);
		return page;
	}

}
