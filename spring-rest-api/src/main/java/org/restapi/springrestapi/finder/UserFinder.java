package org.restapi.springrestapi.finder;

import org.restapi.springrestapi.model.User;

public interface UserFinder {
	/**
	 * 사용자의 데이터베이스 id로 사용자를 조회합니다.
	 * @param id User 엔티티의 데이터베이스 id
	 * @return 조회된 사용자 엔티티를 반환합니다.
	 */
	User findById(Long id);
}
