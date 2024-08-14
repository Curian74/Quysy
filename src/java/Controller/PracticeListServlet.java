package Controller;

import Dal.QuizRecordDAO;
import Dal.RegistrationDAO;
import Model.QuizRecord;
import Model.Registration;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class PracticeListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initiate DAOs
        QuizRecordDAO qrDAO = new QuizRecordDAO();
        RegistrationDAO rDAO = new RegistrationDAO();

        // Initiate current session
        HttpSession session = request.getSession();

        // Get current user
        User u = (User) session.getAttribute("account");

        // Get subject filter
        String subjectIdRaw = request.getParameter("subjectId");
        int subjectId;
        try {
            subjectId = Integer.parseInt(subjectIdRaw);
        } catch (NumberFormatException e) {
            subjectId = 0;
        }

        // Get practices list
        List<QuizRecord> practiceList = qrDAO.getPracticesList(u, subjectId);
        
        // Get page number and page size (number of practices in a page)
        String pageRaw = request.getParameter("page");
        String pageSizeRaw = request.getParameter("pageSize");

        // Check if user is coming from pages else then practices_list.jsp
        if (pageRaw == null) {
            pageRaw = "1";
        }
        if (pageSizeRaw == null) {
            pageSizeRaw = "6";
        }

        // Get number of pages for the nav bar
        int numberOfPages = getNumberOfPages(practiceList, Integer.parseInt(pageSizeRaw));
        
        // Call getPracticesByPage method to get current page practices list
        practiceList = getPracticesByPage(practiceList, Integer.parseInt(pageRaw), Integer.parseInt(pageSizeRaw));
        
        // Check if list is empty or not
        if (practiceList.isEmpty()) {
            request.setAttribute("practiceListMessage", "practiceListEmpty");
        } else {
            request.setAttribute("practiceList", practiceList);
        }

        // Get all registered subjects for filtering
        List<Registration> subjectList = rDAO.getUserPracticesSubjects(u);

        // Set attributes
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("page", pageRaw);

        // Forward to the practices list page
        request.getRequestDispatcher("practices_list.jsp").forward(request, response);
    }
    
    // Function to get practices by page
    public List<QuizRecord> getPracticesByPage(List<QuizRecord> list, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(page * pageSize, list.size());
        if (fromIndex >= list.size() || fromIndex < 0) {
            return new ArrayList<>();
        }
        return list.subList(fromIndex, toIndex);
    }

    // Function to get number of pages
    public int getNumberOfPages(List<QuizRecord> list, int pageSize) {
        int totalItems = list.size();
        int fullPages = totalItems / pageSize;
        int remainder = totalItems % pageSize;

        return remainder == 0 ? fullPages : fullPages + 1;
    }
    
}
