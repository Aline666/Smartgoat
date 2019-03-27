/* 
    Document   : rollenVerw
    Created on : 24.03.2019, 13:22:10
    Author     : a.schaub
*/
package SmartgoatServlet;

import Smartgoat.EJB.RollenBean;
import Smartgoat.jpa.roles;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/app/rollenVerwaltung/"})
public class RollenVerwaltungServlet extends HttpServlet {


    
@EJB
private RollenBean rollenBean;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
            List<roles> groupList;
            groupList = (List<roles>) this.rollenBean.getAllGroups();
            request.setAttribute("groupList", groupList);
            

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/Rollenverwaltung/rollenansicht.jsp").forward(request, response);
    }
}

