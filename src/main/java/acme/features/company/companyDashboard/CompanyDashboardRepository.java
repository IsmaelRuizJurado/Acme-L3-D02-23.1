//
//package acme.features.company.companyDashboard;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import acme.framework.repositories.AbstractRepository;
//import acme.roles.Company;
//
//@Repository
//public interface CompanyDashboardRepository extends AbstractRepository {
//
//	@Query("select c from Company c where c.userAccount.id = ?1")
//	Company findOneCompanyByUserAccountId(int userAccountId);
//
//	@Query("select avg(timediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findAverageSessionLength(int companyId);
//
//	@Query("select stddev(timediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findDeviationSessionLength(int companyId);
//
//	@Query("select min(timediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findMinimumSessionLength(int companyId);
//
//	@Query("select max(timediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findMaximumSessionLength(int companyId);
//
//	@Query("select count(ps) from PracticumSession ps where ps.practicum.company.id = ?1")
//	int findCountSession(int companyId);
//
//	@Query("select avg(sum(timediff(ps.endPeriod,ps.startPeriod))) from PracticumSession ps where ps.practicum.company.id = ?1 group by ps.practicum.id")
//	double findAveragePracticaLength(int companyId);
//
//	@Query("select stddev(sum(timediff(ps.endPeriod,ps.startPeriod))) from PracticumSession ps where ps.practicum.company.id = ?1 group by ps.practicum.id")
//	double findDeviationPracticaLength(int companyId);
//
//	@Query("select max(sum(timediff(ps.endPeriod,ps.startPeriod))) from PracticumSession sp where ps.practicum.company.id = ?1 group by ps.practicum.id")
//	double findMinimumPracticaLength(int companyId);
//
//	@Query("select min(sum(timediff(ps.endPeriod,ps.startPeriod))) from PracticumSession sp where ps.practicum.company.id = ?1 group by ps.practicum.id")
//	double findMaximumPracticaLength(int companyId);
//
//	@Query("select count(p) from Practicum p where p.company.id = ?1")
//	int findCountPractica(int companyId);
//
//	@Query("SELECT FUNCTION('MONTH', ps.startPeriod), COUNT(sp) FROM PracticumSession ps where ps.practicum.company.id = ?1 group by function('MONTH', ps.startPeriod) order by count(ps) desc")
//	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);
//}
