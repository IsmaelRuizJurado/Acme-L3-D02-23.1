
package acme.features.authenticated.lecturer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface AuthenticationLecturerRepository extends AbstractRepository {

	@Query("select user from UserAccount user where user.id = :id")
	UserAccount getUserAccountById(int id);

	@Query("select lecturer from Lecturer lecturer where lecturer.userAccount.id = :id")
	Lecturer getLecturerByUserAccount(int id);
}
