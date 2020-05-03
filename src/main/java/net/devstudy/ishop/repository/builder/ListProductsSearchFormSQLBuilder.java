package net.devstudy.ishop.repository.builder;

public class ListProductsSearchFormSQLBuilder extends AbstractSearchFormSQLBuilder {

	@Override
	public String getSelectFields() {
		return "p.*, c.name as category, pr.name as producer";
	}
	
	@Override
	public String getLastSqlPart() {
		return " order by p.id limit ? offset ?";
	}

}
