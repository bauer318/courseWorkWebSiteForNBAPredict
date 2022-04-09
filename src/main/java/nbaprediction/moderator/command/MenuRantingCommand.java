package nbaprediction.moderator.command;


import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;

public class MenuRantingCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/ranting.jsp";
		return page;
	}

}
