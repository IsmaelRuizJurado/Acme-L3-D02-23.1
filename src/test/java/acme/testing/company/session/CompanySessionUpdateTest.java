
package acme.testing.company.session;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanySessionUpdateTest extends TestHarness {

	@Autowired
	protected CompanySessionRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int index, final String code, final String title, final String abstractt, final String startPeriod, final String endPeriod, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(index);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractt", abstractt);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int index, final String code, final String title, final String abstractt, final String startPeriod, final String endPeriod, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(index);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();

	}
	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/update-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String id) {

		final String param = "id=" + id;
		final String url = "/company/practicum-session/update";

		super.checkLinkExists("Sign in");
		super.request(url, param);
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		super.request(url, param);
		super.checkPanicExists();
		super.signOut();

	}
}
