package net.devstudy.ishop.repository;

import java.util.List;

import net.devstudy.framework.annotation.JDBCRepository;
import net.devstudy.framework.annotation.jdbc.CollectionItem;
import net.devstudy.framework.annotation.jdbc.Select;
import net.devstudy.ishop.entity.Producer;

@JDBCRepository
public interface ProducerRepository {
	
	@Select("select * from producer order by name")
	@CollectionItem(Producer.class)
	List<Producer> listAllProducer();
}
