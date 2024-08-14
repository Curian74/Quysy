package Controller;

import Dal.BlogDAO;
import Dal.CategoryDAO;
import Model.Blog;
import Model.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BlogListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Initiate DAOs
        BlogDAO bDAO = new BlogDAO();
        CategoryDAO cDAO = new CategoryDAO();

        // Get categories
        List<Category> categoryList = cDAO.getAllCategories();

        // Get page number and page size (number of blogs in a page)
        String pageRaw = request.getParameter("page");
        String pageSizeRaw = request.getParameter("pageSize");

        // Check if user is coming from pages else then blog_list.jsp
        if (pageRaw == null) {
            pageRaw = "1";
        }
        if (pageSizeRaw == null) {
            pageSizeRaw = "12";
        }

        // Get category ID to sort the page by category
        String categoryIdRaw = request.getParameter("categoryId");
        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdRaw);
        } catch (NumberFormatException e) {
            categoryId = 0;
        }

        // Get search term
        String searchTermRaw = request.getParameter("searchTerm");

        // Get status
        String statusParam = request.getParameter("status");
        int status = (statusParam != null && statusParam.equals("inactive")) ? 0 : 1;

        // Check to see if the list need to be sorted by category and/or to be searched
        List<Blog> mainBlogList = bDAO.getBlogsByFilterAndStatus(searchTermRaw, categoryId, status);

        // Get number of pages for the nav bar
        int numberOfPages = getNumberOfPages(mainBlogList, Integer.parseInt(pageSizeRaw));

        // Call getBlogsByPage method to get the main blogs list
        mainBlogList = getBlogsByPage(mainBlogList, Integer.parseInt(pageRaw), Integer.parseInt(pageSizeRaw));

        // Get the latest blogs but only three blogs for display
        List<Blog> siderBlogList;
        try {
            siderBlogList = bDAO.getLatestUploadedBlogs().subList(0, 2);
        } catch (IndexOutOfBoundsException ex) {
            siderBlogList = new ArrayList<>();
        }

        // Set attributes
        if (mainBlogList.isEmpty()) {
            request.setAttribute("mainBlogMessage", "mainBlogEmpty");
        } else {
            request.setAttribute("mainBlogList", mainBlogList);
        }

        if (siderBlogList.isEmpty()) {
            request.setAttribute("siderBlogMessage", "siderBlogEmpty");
        } else {
            request.setAttribute("siderBlogList", siderBlogList);
        }

        if (categoryId != 0) {
            request.setAttribute("categoryId", categoryIdRaw);
        }

        if (searchTermRaw != null && !searchTermRaw.isEmpty()) {
            request.setAttribute("searchTerm", searchTermRaw);
        }

        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("page", pageRaw);
        request.setAttribute("status", statusParam);

        // Forward to the blog list page
        request.getRequestDispatcher("blog_list.jsp").forward(request, response);
    }

    // Function to get blogs by page
    public List<Blog> getBlogsByPage(List<Blog> list, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(page * pageSize, list.size());
        if (fromIndex >= list.size() || fromIndex < 0) {
            return new ArrayList<>();
        }
        return list.subList(fromIndex, toIndex);
    }

    // Function to get number of pages
    public int getNumberOfPages(List<Blog> list, int pageSize) {
        int totalItems = list.size();
        int fullPages = totalItems / pageSize;
        int remainder = totalItems % pageSize;

        return remainder == 0 ? fullPages : fullPages + 1;
    }
}
