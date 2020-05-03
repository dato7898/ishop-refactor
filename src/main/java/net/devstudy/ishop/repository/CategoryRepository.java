package net.devstudy.ishop.repository;

import java.util.List;

import net.devstudy.framework.annotation.JDBCRepository;
import net.devstudy.framework.annotation.jdbc.CollectionItem;
import net.devstudy.framework.annotation.jdbc.Select;
import net.devstudy.ishop.entity.Category;

@JDBCRepository
public interface CategoryRepository {
	
	@Select("select * from category order by id")
	@CollectionItem(Category.class)
	List<Category> listAllCategories();
}
