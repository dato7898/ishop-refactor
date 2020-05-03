package net.devstudy.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.devstudy.ishop.Constants;
import net.devstudy.ishop.util.WebUtils;

@WebFilter(filterName = "SetCurrentRequestUrlFilter")
public class SetCurrentRequestUrlFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String currentUrl = WebUtils.getCurrentRequestUrl(req);
		req.setAttribute(Constants.CURRENT_REQUEST_URL, currentUrl);
		chain.doFilter(req, resp);
	}

}
