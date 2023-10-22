
package acme.testing.company.session;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanySessionListTest extends TestHarness {

	@Autowired
	protected CompanySessionRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int index, final String code, final String title, final String startPeriod, final String endPeriod, final String additional) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(index);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, startPeriod);
		super.checkColumnHasValue(recordIndex, 3, endPeriod);
		super.checkColumnHasValue(recordIndex, 4, additional);

		super.signOut();
	}
	@Test
	public void test200Negative() {

	}
	@Test
	public void test300Hacking() {
		final List<Practicum> practicums = this.repository.findAllPracticumsByCompany("company1");

		for (final Practicum practicum : practicums) {
			final String param = "practicumId=" + practicum.getId();
			final String url = "/company/practicum-session/list";

			super.checkLinkExists("Sign in");
			super.request(url, param);
			super.checkPanicExists();

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
}
