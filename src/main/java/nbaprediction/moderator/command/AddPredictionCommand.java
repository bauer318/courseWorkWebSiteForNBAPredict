package nbaprediction.moderator.command;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.datalayer.data.QtTeam;
import nbaprediction.datalayer.data.Result;
import nbaprediction.datalayer.data.XY;
import nbaprediction.moderator.logic.AddPredictionLogic;
import nbaprediction.moderator.logic.RantingLogic;

public class AddPredictionCommand implements ActionCommand {
	private static final String PARAM_NAME_SAVE_RESULT = "saveResult";
	private static final String PARAM_NAME_SELECTED_INDEX = "selectedIndex";

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/addPronostic.jsp";
		HttpSession session = request.getSession(false);
		int saveResult = Integer.parseInt(request.getParameter(PARAM_NAME_SAVE_RESULT));
		/*
		 * Selected date is the date selected in the Add match's menu or the current
		 * date
		 */
		Date date = (Date) session.getAttribute("selectedDate");

		int selectedIndex = Integer.parseInt(request.getParameter(PARAM_NAME_SELECTED_INDEX));
		@SuppressWarnings("unchecked")
		List<QtTeam> listGuestTeam = (List<QtTeam>) session.getAttribute("listGuestTeam");
		@SuppressWarnings("unchecked")
		List<QtTeam> listHomeTeam = (List<QtTeam>) session.getAttribute("listHomeTeam");

		@SuppressWarnings("unchecked")
		Map<Integer, Integer> mapOffensiveRanting = (Map<Integer, Integer>) session.getAttribute("mapOffensiveRanting");
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> mapDefensiveRantings = (Map<Integer, Integer>) session
				.getAttribute("mapDefensiveRantings");
		int homeTeamId = listHomeTeam.get(selectedIndex).getIdTeam();
		int guestTeamId = listGuestTeam.get(selectedIndex).getIdTeam();
		int idMatch = listGuestTeam.get(selectedIndex).getIdMatch();
		int qt11 = listGuestTeam.get(selectedIndex).getQt1();
		int qt12 = listGuestTeam.get(selectedIndex).getQt2();
		int qt13 = listGuestTeam.get(selectedIndex).getQt3();
		int qt21 = listHomeTeam.get(selectedIndex).getQt1();
		int qt22 = listHomeTeam.get(selectedIndex).getQt2();
		int qt23 = listHomeTeam.get(selectedIndex).getQt3();
		if (mapOffensiveRanting != null && mapDefensiveRantings != null) {
			int guestTeamOffensive = mapOffensiveRanting.get(guestTeamId);
			int guestTeamDefensive = mapDefensiveRantings.get(guestTeamId);
			int homeTeamOffensive = mapOffensiveRanting.get(homeTeamId);
			int homeTeamDefensive = mapDefensiveRantings.get(homeTeamId);

			/*
			 * Guest Team Result
			 */
			double average = AddPredictionLogic.getTeamAvg(qt11, qt12, qt13);
			double total = AddPredictionLogic.getTotalPointsMatch(qt11, qt12, qt13, qt21, qt22, qt23);
			total += average;
			// InterestedMatch
			int numberMatchWihtCondition = AddPredictionLogic.countTeamMatchWihtQt4BiggestThanAVGBeforeDate(guestTeamId,
					average, date);
			// TotalMatch
			int numberTotalTeamMatch = RantingLogic.getLastRantingByTeamIdBeforeDate(guestTeamId, date)
					.getMatchNumber();
			double averagePercent = AddPredictionLogic.getAveragePercent(numberTotalTeamMatch,
					numberMatchWihtCondition);
			short regime = AddPredictionLogic.getRegime(averagePercent);
			if (averagePercent <= 35.0) {
				List<List<QtTeam>> listQtTeam = AddPredictionLogic.getListQtTeamByTeamAVG(guestTeamId, average);
				AddPredictionLogic.addListQtTeams(guestTeamId, listQtTeam);
				List<List<Result>> listResult = AddPredictionLogic.getListResultTeamByTeamAVG(guestTeamId, average);
				AddPredictionLogic.addListResults(guestTeamId, listResult);
				session.setAttribute("mapListGuestTeamByAVG", AddPredictionLogic.getMapListQtTeams());
				session.setAttribute("mapListGuestTeamResultByAVG", AddPredictionLogic.getMapListResults());
			}
			short offensiveRegime = AddPredictionLogic.getOffensiveDefensiveRegime(guestTeamOffensive);
			short deffensiveRegime = AddPredictionLogic.getOffensiveDefensiveRegime(homeTeamDefensive);
			short teamOffensive = (short) guestTeamOffensive;
			short opponentDefensive = (short) homeTeamDefensive;
			double averageDifferenceGuest = AddPredictionLogic.getDifference(qt11, qt12, qt13, qt21, qt22, qt23);
			double averageDifferenceHome = averageDifferenceGuest * (-1.0);
			int numberMatchWithMostQt4 = AddPredictionLogic.countTeamMatchWithMostPointQt4BeforeDate(guestTeamId, date);
			double mostQt4Percent = AddPredictionLogic.getAveragePercent(numberTotalTeamMatch, numberMatchWithMostQt4);
			double qt13Average = AddPredictionLogic.getQt13Average(guestTeamId, date);
			String decision = "n";
			int matchQt4 = 0;
			XY xy = AddPredictionLogic.getXY(guestTeamId, average);
			AddPredictionLogic.addXY(guestTeamId, xy);
			List<Result> prevResultsByRegime = AddPredictionLogic.getListPrevResultByRegime(regime, offensiveRegime,
					deffensiveRegime);
			AddPredictionLogic.addPrevResult(guestTeamId, prevResultsByRegime);
			List<Result> allPrevResultByTeamStatut = AddPredictionLogic
					.getListAllResultByClubStatut(averageDifferenceGuest, prevResultsByRegime);
			double superiorProbabilty = AddPredictionLogic.getSuperiorProbability(averageDifferenceGuest, xy,
					allPrevResultByTeamStatut, averagePercent);
			AddPredictionLogic.addDecision(guestTeamId, superiorProbabilty);
			short observation = AddPredictionLogic.getObservation(superiorProbabilty);
			Result guestResult = new Result(idMatch, guestTeamId, average, regime, offensiveRegime, deffensiveRegime,
					teamOffensive, opponentDefensive, averageDifferenceGuest, averagePercent, mostQt4Percent,
					qt13Average, decision, matchQt4, observation, numberTotalTeamMatch, numberMatchWihtCondition);
			AddPredictionLogic.addResult(guestTeamId, guestResult);
			/* Save team result */
			if (saveResult == 1) {
				if (AddPredictionLogic.isNotExistingResult(idMatch, guestTeamId)) {
					AddPredictionLogic.insertNewResult(idMatch, guestTeamId, average, regime, offensiveRegime,
							deffensiveRegime, teamOffensive, opponentDefensive, averageDifferenceGuest, averagePercent,
							mostQt4Percent, qt13Average, decision, matchQt4, observation, numberTotalTeamMatch,
							numberMatchWihtCondition);
				} else {
					AddPredictionLogic.updateResultByTeamMatch(idMatch, guestTeamId, average, regime, offensiveRegime,
							deffensiveRegime, teamOffensive, opponentDefensive, averageDifferenceGuest, averagePercent,
							mostQt4Percent, qt13Average, decision, matchQt4, observation, numberTotalTeamMatch,
							numberMatchWihtCondition);
				}
			}
			AddPredictionLogic.addConfirmsText(guestTeamId, average, qt11,qt12,qt13);
			/*
			 * Home Team Result
			 */
			average = AddPredictionLogic.getTeamAvg(qt21, qt22, qt23);
			total += average;
			AddPredictionLogic.addTotalPoints(idMatch, total);
			session.setAttribute("mapTotalPointsMatch", AddPredictionLogic.getMapTotalPointsMatch());
			numberMatchWihtCondition = AddPredictionLogic.countTeamMatchWihtQt4BiggestThanAVGBeforeDate(homeTeamId,
					average, date);
			numberTotalTeamMatch = RantingLogic.getLastRantingByTeamIdBeforeDate(homeTeamId, date).getMatchNumber();
			averagePercent = AddPredictionLogic.getAveragePercent(numberTotalTeamMatch, numberMatchWihtCondition);
			regime = AddPredictionLogic.getRegime(averagePercent);
			if (averagePercent <= 35.0) {
				List<List<QtTeam>> listQtTeam = AddPredictionLogic.getListQtTeamByTeamAVG(homeTeamId, average);
				AddPredictionLogic.addListQtTeams(homeTeamId, listQtTeam);
				List<List<Result>> listResult = AddPredictionLogic.getListResultTeamByTeamAVG(homeTeamId, average);
				AddPredictionLogic.addListResults(homeTeamId, listResult);
				session.setAttribute("mapListHomeTeamByAVG", AddPredictionLogic.getMapListQtTeams());
				session.setAttribute("mapListHomeTeamResultByAVG", AddPredictionLogic.getMapListResults());
			}
			offensiveRegime = AddPredictionLogic.getOffensiveDefensiveRegime(homeTeamOffensive);
			deffensiveRegime = AddPredictionLogic.getOffensiveDefensiveRegime(guestTeamDefensive);
			teamOffensive = (short) homeTeamOffensive;
			opponentDefensive = (short) guestTeamDefensive;
			numberMatchWithMostQt4 = AddPredictionLogic.countTeamMatchWithMostPointQt4BeforeDate(homeTeamId, date);
			mostQt4Percent = AddPredictionLogic.getAveragePercent(numberTotalTeamMatch, numberMatchWithMostQt4);
			qt13Average = AddPredictionLogic.getQt13Average(homeTeamId, date);
			decision = "n";
			matchQt4 = 0;
			xy = AddPredictionLogic.getXY(homeTeamId, average);
			AddPredictionLogic.addXY(homeTeamId, xy);
			prevResultsByRegime = AddPredictionLogic.getListPrevResultByRegime(regime, offensiveRegime,
					deffensiveRegime);
			allPrevResultByTeamStatut = AddPredictionLogic.getListAllResultByClubStatut(averageDifferenceHome,
					prevResultsByRegime);
			superiorProbabilty = AddPredictionLogic.getSuperiorProbability(averageDifferenceHome, xy,
					allPrevResultByTeamStatut, averagePercent);
			observation = AddPredictionLogic.getObservation(superiorProbabilty);
			AddPredictionLogic.addDecision(homeTeamId, superiorProbabilty);
			AddPredictionLogic.addPrevResult(homeTeamId, prevResultsByRegime);
			Result homeResult = new Result(idMatch, homeTeamId, average, regime, offensiveRegime, deffensiveRegime,
					teamOffensive, opponentDefensive, averageDifferenceHome, averagePercent, mostQt4Percent,
					qt13Average, decision, matchQt4, observation, numberTotalTeamMatch, numberMatchWihtCondition);
			if (saveResult == 1) {
				if (AddPredictionLogic.isNotExistingResult(idMatch, homeTeamId)) {
					AddPredictionLogic.insertNewResult(idMatch, homeTeamId, average, regime, offensiveRegime,
							deffensiveRegime, teamOffensive, opponentDefensive, averageDifferenceHome, averagePercent,
							mostQt4Percent, qt13Average, decision, matchQt4, observation, numberTotalTeamMatch,
							numberMatchWihtCondition);
				} else {
					AddPredictionLogic.updateResultByTeamMatch(idMatch, homeTeamId, average, regime, offensiveRegime,
							deffensiveRegime, teamOffensive, opponentDefensive, averageDifferenceHome, averagePercent,
							mostQt4Percent, qt13Average, decision, matchQt4, observation, numberTotalTeamMatch,
							numberMatchWihtCondition);
				}
			}
			AddPredictionLogic.addConfirmsText(homeTeamId, average, qt21,qt22,qt23);
			AddPredictionLogic.addResult(homeTeamId, homeResult);
			session.setAttribute("mapResult", AddPredictionLogic.getMapResults());
			session.setAttribute("mapPrevResult", AddPredictionLogic.getMapPrevResultByRegime());
			session.setAttribute("mapXY", AddPredictionLogic.getMapXY());
			session.setAttribute("mapDecision", AddPredictionLogic.getMapDecisions());
			session.setAttribute("mapConfirmsText", AddPredictionLogic.getMapConfirmsText());
		} else {
			request.setAttribute("RantingNotDefined", "RantingNotDefined");
		}
		return page;
	}

}
