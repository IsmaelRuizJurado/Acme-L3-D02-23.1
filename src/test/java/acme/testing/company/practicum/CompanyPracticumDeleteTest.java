
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String nextCode) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkColumnHasValue(recordIndex, 0, nextCode);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}
	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String id) {

		final String param = "id=" + id;
		final String url = "/company/practicum/delete";

		super.checkLinkExists("Sign in");
		super.request(url, param);
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
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
