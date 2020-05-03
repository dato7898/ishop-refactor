package net.devstudy.ishop.repository.builder;

public class CountProductsSearchFormSQLBuilder extends AbstractSearchFormSQLBuilder {

	@Override
	public String getSelectFields() {
		return "count(*)";
	}

}
