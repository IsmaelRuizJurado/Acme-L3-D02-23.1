
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.sessions.Session;
import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantTutorialSessionTestRepository repository;


	// Test methods -----------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordTutorialIndex, final int recordSessionIndex, final String title, final String abstractt, final String type, final String startPeriod, final String finishPeriod, final String link) {
		// HINT: this test authenticates as an assistant, list his or her tutorials, navigates
		// to their tutorialSession, and checks that they have the expected data.
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordTutorialIndex);
		super.clickOnButton("List of Sessions");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("finishPeriod", finishPeriod);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.clickOnListingRecord(recordSessionIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractt", abstractt);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("finishPeriod", finishPeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final int sessionRecordIndex, final String title, final String abstractt, final String type, final String startPeriod, final String finishPeriod, final String link) {
		// HINT: this test attempts to create sessions using wrong data.
		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("List of Sessions");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("finishPeriod", finishPeriod);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a session for a tutorial as a principal without 
		// the "Assistant" role.
		Collection<Session> sessions;
		String param;

		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant1");
		for (final Session session : sessions) {
			param = String.format("masterId=%d", session.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a session for a published tutorial created by 
		// the principal.
		Collection<Session> sessions;
		String param;

		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant1");
		for (final Session session : sessions)
			if (!session.getTutorial().isDraftMode()) {
				param = String.format("masterId=%d", session.getId());
				super.request("/assistant/session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create sessions for tutorials that weren't created 
		// by the principal.

		Collection<Session> sessions;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant2");
		for (final Session session : sessions) {
			param = String.format("masterId=%d", session.getId());
			super.request("/assistant/session/create", param);
			super.checkPanicExists();
		}
	}

}
