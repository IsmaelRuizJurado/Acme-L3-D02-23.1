
package acme.features.assistant.assistantDashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.form.AssistantDashboard;
import acme.form.Stats;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Constants --------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"totalNumberOfTutorial", "sessionLength", "tutorialLength"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantDashboardRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final Assistant assistant;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);

		status = assistant != null && principal.hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Integer assistantId;
		final AssistantDashboard assistantDashboard;
		final Principal principal;
		int userAccountId;
		final Assistant assistant;

		final Stats sessionTime;
		final double averageSessionTime;
		final double deviationSessionTime;
		final double minimumSessionTime;
		final double maximumSessionTime;
		int countSession;

		final Stats tutorialTime;
		final double averageTutorialTime;
		final double deviationTutorialTime;
		final double minimumTutorialTime;
		final double maximumTutorialTime;
		final Integer countTutorial;

		final int totalNumberOfTutorial;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);
		assistantId = assistant.getId();

		averageSessionTime = this.repository.findAverageSessionTime(assistantId);
		deviationSessionTime = this.repository.findDeviationSessionTime(assistantId);
		minimumSessionTime = this.repository.findMinimumSessionTime(assistantId);
		maximumSessionTime = this.repository.findMaximumSessionTime(assistantId);
		countSession = this.repository.findCountSession(assistantId);
		sessionTime = new Stats(countSession, averageSessionTime, maximumSessionTime, minimumSessionTime, deviationSessionTime);

		averageTutorialTime = this.repository.findAverageTutorialTime(assistantId);
		deviationTutorialTime = this.repository.findDeviationTutorialTime(assistantId);
		minimumTutorialTime = this.repository.findMinimumTutorialTime(assistantId);
		maximumTutorialTime = this.repository.findMaximumTutorialTime(assistantId);
		countTutorial = this.repository.findCountTutorial(assistantId);
		tutorialTime = new Stats(countTutorial, averageTutorialTime, maximumTutorialTime, minimumTutorialTime, deviationTutorialTime);

		totalNumberOfTutorial = this.repository.findTotalNumberOfTutorial(assistantId).intValue();

		assistantDashboard = new AssistantDashboard();

		assistantDashboard.setTotalNumberOfTutorial(totalNumberOfTutorial);
		assistantDashboard.setSessionTime(sessionTime);
		assistantDashboard.setTutorialTime(tutorialTime);

		super.getBuffer().setData(assistantDashboard);
	}

	@Override
	public void unbind(final AssistantDashboard assistantDashboard) {
		Tuple tuple;

		tuple = super.unbind(assistantDashboard, AssistantDashboardShowService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
