package nbaprediction.adminmoderator.command;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.adminmoderator.logic.DeleteAdminModeratorLogic;
import nbaprediction.datalayer.data.Bettor;
import nbaprediction.datalayer.data.User;
import nbaprediction.loginlogout.logic.LoginLogic;
import nbaprediction.users.logic.EditUserLogic;

public class DeleteAdminModeratorCommand implements ActionCommand {
	private static final String PARAM_NAME_ADMIN_MODERATOR_ID = "adminModeratorID";

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/adminEditUser.jsp";
		String idAdminModeratorStr = request.getParameter(PARAM_NAME_ADMIN_MODERATOR_ID);
		int idAdminModerator = Integer.parseInt(idAdminModeratorStr);
		DeleteAdminModeratorLogic.deleteUserByID(idAdminModerator);
		List<User> adminModerators = EditUserLogic.getAdminModerators();
		request.setAttribute("adminModerators", adminModerators);
		List<Bettor> bettors = LoginLogic.getAllBettors();
		request.setAttribute("bettors", bettors);
		return page;
	}

}
