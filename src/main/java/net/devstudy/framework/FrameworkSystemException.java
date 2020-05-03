package net.devstudy.framework;

public class FrameworkSystemException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	public FrameworkSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FrameworkSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkSystemException(String message) {
		super(message);
	}

	public FrameworkSystemException(Throwable cause) {
		super(cause);
	}
	
}
