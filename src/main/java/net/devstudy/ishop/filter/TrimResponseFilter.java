package net.devstudy.ishop.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter(filterName = "TrimResponseFilter")
public class TrimResponseFilter extends AbstractFilter {
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		TrimResponse trimResponse = new TrimResponse(response);
		chain.doFilter(request, trimResponse);
		trimResponse.complete();
	}

	private static class TrimResponse extends HttpServletResponseWrapper {
		private TrimProxyWriter trimProxyWriter;

		public TrimResponse(HttpServletResponse response) throws IOException {
			super(response);
			trimProxyWriter = new TrimProxyWriter(response.getWriter());
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					trimProxyWriter.write(b);
				}

				@Override
				public void setWriteListener(WriteListener writeListener) {
				}

				@Override
				public boolean isReady() {
					return false;
				}
			};
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(trimProxyWriter);
		}

		private void complete() throws IOException {
			setContentLength(trimProxyWriter.getLength());
			trimProxyWriter.complete();
		}
	}

	private static class TrimProxyWriter extends Writer {
		private Writer wr;
		private int length;

		private TrimProxyWriter(Writer wr) {
			super();
			this.wr = wr;
			this.length = 0;
		}

		@Override
		public void write(int c) throws IOException {
			processChar((char) c);
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			for (int i = off; i < len; i++) {
				processChar(cbuf[i]);
			}
		}

		@Override
		public void write(String str, int off, int len) throws IOException {
			for (int i = off; i < len; i++) {
				processChar(str.charAt(i));
			}
		}

		private void processChar(char c) throws IOException {
			if (c != '\n' && c != '\t' && c != '\r') {
				wr.write(c);
				length++;
			}
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}

		private int getLength() {
			return length;
		}

		private void complete() throws IOException {
			wr.flush();
			wr.close();
		}
	}
}
