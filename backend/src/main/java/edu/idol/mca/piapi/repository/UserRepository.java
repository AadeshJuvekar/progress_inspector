/**
 * 
 */
package edu.idol.mca.piapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.idol.mca.piapi.domain.User;

/**
 *This UserRepository will be responsible for performing all the CRUD operations on User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * This deleteByLoginName method will delete the User by loginName from the database.
	 * @param loginName of User to be deleted.
	 */
	public void deleteByLoginName(String loginName);
	/**
	 * This findByLoginName method will return the User by loginName from the database.
	 * @param loginName of User to be found.
	 * @return User if found otherwise null.
	 */
	public User findByLoginName(String loginName);
	/**
	 * This findByLoginName method will return the User by loginName and password from the database.
	 * @param loginName of User to be found.
	 * @param pwd of User to be found.
	 * @return User if found otherwise null.
	 */
	public User findByLoginNameAndPwd(String loginName, String pwd);
	/**
	 * This findByUserType method will return the User by UserType from the database.
	 * @param UserType of User to be found.
	 * @return User if found otherwise null.
	 */
	public List<User> findByUserType(String userType);

}
