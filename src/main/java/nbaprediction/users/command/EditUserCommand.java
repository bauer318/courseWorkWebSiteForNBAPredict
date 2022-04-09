package nbaprediction.users.command;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.datalayer.data.Bettor;
import nbaprediction.datalayer.data.User;
import nbaprediction.loginlogout.logic.LoginLogic;
import nbaprediction.users.logic.EditUserLogic;

public class EditUserCommand implements ActionCommand{

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/adminEditUser.jsp";
		List<User> adminModerators = EditUserLogic.getAdminModerators();
		request.setAttribute("adminModerators", adminModerators);
		List<Bettor> bettors = LoginLogic.getAllBettors();
		request.setAttribute("bettors", bettors);
		return page;
	}

}
