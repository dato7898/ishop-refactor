package net.devstudy.ishop.exception;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends AbstractApplicationException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(message, HttpServletResponse.SC_BAD_REQUEST);
	}
}
