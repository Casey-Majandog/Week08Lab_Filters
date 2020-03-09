/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import services.UserService;

/**
 *
 * @author 813793
 */
public class AdminFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
            
            final int ADMIN_NUM = 1;
            UserService user = new UserService();
            // this code will execute before HomeServlet and UsersServlet
            HttpServletRequest r = (HttpServletRequest)request;
            HttpSession session = r.getSession();
            String username = (String)session.getAttribute("username");
            int userNum = 0;
            try 
            {
                 userNum = user.get(username).getRole().getRoleid();
            } 
            catch (Exception ex) 
            {
                 Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (userNum == ADMIN_NUM) {
                // if they are admin
                // then allow them to continue on to the servlet
                chain.doFilter(request, response);
            } else {
                // they are not admin
                // send them to the home page
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.sendRedirect("home");
            }
       
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }

    
}
