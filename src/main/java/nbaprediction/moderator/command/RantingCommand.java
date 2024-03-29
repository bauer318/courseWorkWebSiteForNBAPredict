package nbaprediction.moderator.command;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.datalayer.data.DefensiveRanting;
import nbaprediction.datalayer.data.OffensiveRanting;
import nbaprediction.datalayer.data.Ranting;
import nbaprediction.datetime.DateTimeWorker;
import nbaprediction.moderator.logic.RantingLogic;

public class RantingCommand implements ActionCommand {
	private static final String PARAM_NAME_RANTING_DATE = "rantingDate";
	private static final String PARAM_NAME_RANTING_ADD_NEW_RANTING = "addNewRanting";

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String page = "/jsp/commands/ranting.jsp";
		String dateStr = request.getParameter(PARAM_NAME_RANTING_DATE);
		int addNewRanting = Integer.parseInt(request.getParameter(PARAM_NAME_RANTING_ADD_NEW_RANTING));
		Date date = DateTimeWorker.stringToDate(dateStr);
		if (addNewRanting == 1) {
			// We take the list of the last rows in the Ranting table in general
			List<Ranting> rantings = RantingLogic.getAllRantings();
			RantingLogic.setRantings(rantings);
			Map<Integer, Double> mapFullOffensiveAverageTeams = RantingLogic.getFullOffensiveAverageTeams();
			Map<Integer, Double> mapFullDefensiveAverageTeams = RantingLogic.getFullDefensiveAverageTeams();
			RantingLogic.insertNewOffensiveRanting(mapFullOffensiveAverageTeams, date);
			RantingLogic.insertNewDefensiveRanting(mapFullDefensiveAverageTeams, date);
		}
		session.setAttribute("currentRantingDate", date);
		List<OffensiveRanting> offensiveRantings = RantingLogic.getOffensiveRantingByDate(date);
		Map<Integer, Integer> mapOffensiveRanting = RantingLogic.getMapOffensiveRanking(offensiveRantings);
		List<DefensiveRanting> defensiveRantings = RantingLogic.getDefensiveRantingByDate(date);
		Map<Integer, Integer> mapDefensiveRantings = RantingLogic.getMapDefensiveRanking(defensiveRantings);
		session.setAttribute("offensiveRantings", offensiveRantings);
		session.setAttribute("mapOffensiveRanting", mapOffensiveRanting);
		session.setAttribute("defensiveRantings", defensiveRantings);
		session.setAttribute("mapDefensiveRantings", mapDefensiveRantings);
		return page;
	}

}
