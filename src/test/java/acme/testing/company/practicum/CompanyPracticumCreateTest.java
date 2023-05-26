
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class CompanyPracticumCreateTest extends TestHarness {

	// Test data --------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int practicumRecordIndex, final String course, final String code, final String title, final String abstractt, final String goals, final String estimatedTime) {
		// HINT: this test authenticates as a company and then lists his or her
		// HINT: practicums, creates a new one, and check that it's been created properly.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.sortListing(2, "desc");
		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, estimatedTime);
		super.checkColumnHasValue(practicumRecordIndex, 2, title);
		super.checkColumnHasValue(practicumRecordIndex, 3, "No");
		super.clickOnListingRecord(practicumRecordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractt", abstractt);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);

		super.clickOnButton("Practicum sessions");

		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest

	@CsvFileSource(resources = "/company/practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final String course, final String code, final String title, final String abstractt, final String goals, final String estimatedTime) {
		// HINT: this test attempts to create practicum with incorrect data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractt", abstractt);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {
		// HINT: this test tries to create a practicum using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();
	}

}
