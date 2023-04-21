
package acme.features.authenticated.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


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
		final int id = super.getRequest().getData("id", int.class);
		super.getBuffer().setData(this.repository.findBulletinById(id));
	}

	@Override
	public void unbind(final Bulletin bulletin) {
		assert bulletin != null;
		Tuple tuple;
		tuple = super.unbind(bulletin, "critical", "link", "message", "moment", "title");
		super.getResponse().setData(tuple);
	}

}
