
package acme.testing.company.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanySessionCreateAdditionalTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/createAdditional-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int index, final String code, final String title, final String abstractt, final String startPeriod, final String endPeriod, final String link, final String confirmed) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.clickOnListingRecord(index);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnButton("Create Additional Session");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirmed", confirmed);

		super.clickOnSubmit("Create");

		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractt", abstractt);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/createAdditional-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int index, final String code, final String title, final String abstractt, final String startPeriod, final String endPeriod, final String link, final String confirmed) {
		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.clickOnListingRecord(index);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnButton("Create Additional Session");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirmed", confirmed);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();

	}
	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/company/practicum-session/createAdditional");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/company/practicum-session/createAdditional");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		super.request("/company/practicum-session/createAdditional");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum-session/createAdditional");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		super.request("/company/practicum-session/createAdditional");
		super.checkPanicExists();
		super.signOut();

	}
}
