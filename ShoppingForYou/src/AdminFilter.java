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

@WebFilter(filterName = "AdminAuthFilter")
public class AdminFilter implements Filter {

	public AdminFilter() {
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

			String reqURI = reqt.getRequestURI();
			if (reqURI.indexOf("/login-admin.xhtml") >= 0
					|| (ses != null && ses.getAttribute("currentEmail") != null)
					|| reqURI.indexOf("/publicArea/") >= 0
					|| reqURI.contains("javax.faces.resource"))
				{
				chain.doFilter(request, response);
			
				System.out.println("====================================================================");
				System.out.println(chain.toString());
				System.out.println(reqt.getContextPath());
				System.out.println(ses.getAttribute("currentEmail"));
				System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
				}
			else {
				System.out.println("====================================================================");
				System.out.println(reqt.getContextPath());
				System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
				resp.sendRedirect(reqt.getContextPath() + "/publicArea/login-admin.xhtml");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}