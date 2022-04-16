/**
 * 
 */
package edu.idol.mca.piapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.idol.mca.piapi.domain.Remark;

/**
 *This RemarkRepository will be responsible for performing all the CRUD operations on Remark
 */
@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {

	Remark findByRemarkIdentifier(String remarkIdentifier);

}
