package nbaprediction.loginlogout.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.loginlogout.logic.LogoutLogic;

public class LogoutCommand implements ActionCommand {
	private static final int AUTHORIZATION_STATUS = 0;
	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/login.jsp";
		HttpSession session = request.getSession(false);
		String login = (String)session.getAttribute("login");
		LogoutLogic.setAuthorizationStatus(login, AUTHORIZATION_STATUS);
		session.invalidate();
		return page;
	}

}
