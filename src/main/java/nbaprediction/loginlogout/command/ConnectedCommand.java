package nbaprediction.loginlogout.command;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;

public class ConnectedCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/login.jsp";
		return page;
	}

}
