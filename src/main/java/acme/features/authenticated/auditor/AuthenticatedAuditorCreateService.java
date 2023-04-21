
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository	repository;

	private final String[]						properties	= {
		"professionalId", "firm", "certifications", "link"
	};


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal;
		int userAccountId;
		UserAccount userAccount;
		final Auditor auditor = new Auditor();
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findUserById(userAccountId);
		auditor.setUserAccount(userAccount);
		super.getBuffer().setData(auditor);
	}

	@Override
	public void bind(final Auditor auditor) {
		assert auditor != null;
		super.bind(auditor, this.properties);
	}

	@Override
	public void validate(final Auditor auditor) {
		assert auditor != null;
	}

	@Override
	public void perform(final Auditor auditor) {
		this.repository.save(auditor);
	}

	@Override
	public void unbind(final Auditor auditor) {
		assert auditor != null;
		Tuple tuple;
		tuple = super.unbind(auditor, this.properties);
		super.getResponse().setData(tuple);
	}
}
