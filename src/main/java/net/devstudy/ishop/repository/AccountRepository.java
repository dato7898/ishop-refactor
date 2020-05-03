package net.devstudy.ishop.repository;

import net.devstudy.framework.annotation.JDBCRepository;
import net.devstudy.framework.annotation.jdbc.Insert;
import net.devstudy.framework.annotation.jdbc.Select;
import net.devstudy.ishop.entity.Account;

@JDBCRepository
public interface AccountRepository {
	
	@Select("select * from account where email=?")
	Account findByEmail(String email);
	
	@Insert
	void create(Account account);
}
