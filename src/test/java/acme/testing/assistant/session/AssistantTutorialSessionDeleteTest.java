
package acme.testing.assistant.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.sessions.Session;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordTutorialIndex, final int recordSessionIndex, final String title, final String nextTitle) {
		// HINT: this test logs in as an assistant, lists his or her tutorials, 
		// selects one of their sessions and list them, deletes it, and then checks that 
		// the delete has actually been performed.

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordTutorialIndex);
		super.clickOnButton("List of Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(recordSessionIndex);
		super.clickOnSubmit("Delete");

		super.clickOnMenu("Assistant", "List my tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordTutorialIndex);
		super.clickOnButton("List of Sessions");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordSessionIndex, 0, nextTitle);

		super.checkNotPanicExists();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete a session with a role other than "Assistant",
		// or using an assistant who is not the owner.
		Collection<Session> sessions;
		String param;

		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant1");
		for (final Session session : sessions)
			if (!session.getTutorial().isDraftMode()) {
				param = String.format("id=%d", session.getTutorial().getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/assistant/session/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete a published session that was registered by the principal.
		Collection<Session> sessions;
		String params;

		super.signIn("assistant1", "assistant1");
		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant1");
		for (final Session session : sessions)
			if (!session.getTutorial().isDraftMode()) {
				params = String.format("id=%d", session.getTutorial().getId());
				super.request("/assistant/session/delete", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to delete a session that wasn't registered by the principal,
		// be it published or unpublished.
		Collection<Session> sessions;
		String params;

		super.signIn("assistant2", "assistant2");
		sessions = this.repository.findTutorialSessionsByAssistantUsername("assistant1");
		for (final Session session : sessions)
			if (!session.getTutorial().isDraftMode()) {
				params = String.format("id=%d", session.getTutorial().getId());
				super.request("/assistant/session/delete", params);
			}
		super.signOut();
	}
}
