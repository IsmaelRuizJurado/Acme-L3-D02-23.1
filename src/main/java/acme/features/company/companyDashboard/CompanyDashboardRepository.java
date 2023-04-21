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
//	Company findCompanyByUserAccountId(int userAccountId);
//
//	@Query("select count(ps) from PracticumSession ps where ps.practicum.company.id = ?1")
//	int findCountSession(int companyId);
//
//	@Query("select avg(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findAverageSessionLength(int companyId);
//
//	@Query("select stddev(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession  ps where ps.practicum.company.id = ?1")
//	double findDeviationSessionLength(int companyId);
//
//	@Query("select min(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findMinimumSessionLength(int companyId);
//
//	@Query("select max(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1")
//	double findMaximumSessionLength(int companyId);
//
//	@Query("select avg((select sum(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1 and ps.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
//	double findAveragePracticaLength(int companyId);
//
//	@Query("select stddev((select sum(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1 and ps.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
//	double findDeviationPracticaLength(int companyId);
//
//	@Query("select min((select sum(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession ps where ps.practicum.company.id = ?1 and ps.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
//	double findMinimumPracticaLength(int companyId);
//
//	@Query("select max((select sum(datediff(ps.endPeriod,ps.startPeriod)) from PracticumSession sp where ps.practicum.company.id = ?1 and ps.practicum.id = p.id)) from Practicum p where p.company.id = ?1")
//	double findMaximumPracticaLength(int companyId);
//
//	@Query("select count(p) from Practicum p where p.company.id = ?1")
//	int findCountPractica(int companyId);
//
//	@Query("SELECT FUNCTION('MONTH', ps.startPeriod), COUNT(sp) FROM PracticumSession ps WHERE ps.practicum.company.id = ?1 GROUP BY FUNCTION('MONTH', ps.startPeriod) ORDER BY COUNT(ps) DESC")
//	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);
//}
