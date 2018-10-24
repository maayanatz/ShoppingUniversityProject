import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "CustomerAuthFilter")
public class CustomerFilter implements Filter {

	public CustomerFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);
			
			if (ses != null && ses.getAttribute("currentEmail") != null)
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + "/faces/login-customer.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}