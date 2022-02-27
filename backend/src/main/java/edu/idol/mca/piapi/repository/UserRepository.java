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
	
	public void deleteByLoginName(String loginName);
	
	public User findByLoginName(String loginName);
	
	public User findByLoginNameAndPwd(String loginName, String pwd);

	public List<User> findAllByUserType(String string);

}
