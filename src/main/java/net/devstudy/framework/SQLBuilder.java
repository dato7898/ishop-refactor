package net.devstudy.framework;

public interface SQLBuilder {
	SearchQuery build(Object... builderParams);
}
