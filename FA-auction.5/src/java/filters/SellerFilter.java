package filters;

import controllers.sesion.LoginLogout;
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


@WebFilter({"/faces/restricted/user/view_my_active_auctions.xhtml", "/faces/restricted/user/view_my_inactive_auctions.xhtml", "/faces/restricted/user/add_auction.xhtml", "/faces/restricted/user/delete_auction.xhtml", "/faces/restricted/user/rate_buyer.xhtml", "/faces/restricted/user/update_auction.xhtml"})
public class SellerFilter implements Filter {

    /**
     * Default constructor.
     */
    public SellerFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LoginLogout user = (LoginLogout) req.getSession().getAttribute("user");

        if (user == null || !user.isLoggedIn() || (user.getAccountype() == 3)) {
            res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
}
