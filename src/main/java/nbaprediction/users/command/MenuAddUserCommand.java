package nbaprediction.users.command;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.datalayer.data.User;
import nbaprediction.loginlogout.logic.LoginLogic;

public class MenuAddUserCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/adminIndex.jsp";
		List<User> users = LoginLogic.getAllUsers();
		request.setAttribute("users", users);
		return page;
	}

}
