package Controller;

import Dal.BlogDAO;
import Dal.BlogDetailDAO;
import Dal.CategoryDAO;
import Dal.UserDAO;
import Model.Blog;
import Model.BlogDetail;
import Model.Category;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDAO = new BlogDAO();
        BlogDetailDAO bdDAO = new BlogDetailDAO();
        UserDAO uDAO = new UserDAO();
        CategoryDAO cDAO = new CategoryDAO();

        String blogIdRaw = request.getParameter("blogId");

        Blog blog = bDAO.getBlogByID(Integer.parseInt(blogIdRaw));

        List<BlogDetail> blogContent = bdDAO.getBlogDetailsByBlogId(Integer.parseInt(blogIdRaw));

        StringBuilder combinedContent = new StringBuilder();
        for (BlogDetail detail : blogContent) {
            combinedContent.append(detail.getContent()).append("\n");
        }
        String cc = combinedContent.toString();

        User user = uDAO.getUserById(blog.getAuthorId());

        List<Category> categoryList = cDAO.getAllCategories();
        List<Blog> siderBlogList;
        try {
            siderBlogList = bDAO.getLatestUploadedBlogs().subList(0, 2);
        } catch (IndexOutOfBoundsException ex) {
            siderBlogList = new ArrayList<>();
        }

        if (siderBlogList.isEmpty()) {
            request.setAttribute("siderBlogMessage", "siderBlogEmpty");
        } else {
            request.setAttribute("siderBlogList", siderBlogList);
        }

        request.setAttribute("blog", blog);
        request.setAttribute("blogContent", blogContent);
        request.setAttribute("combinedContent", cc);
        request.setAttribute("user", user);
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("blog_details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        BlogDAO bd = new BlogDAO();

        HttpSession session = request.getSession();

        if ("edit".equals(action)) {
            Blog b = bd.getBlogByID(blogId);
            b.setBlogTitle(request.getParameter("blogTitle").trim());
            b.setBlogThumbnail(request.getParameter("blogThumbnail").trim());
            b.setBriefInfo(request.getParameter("briefInfo").trim());
            b.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            b.setBlogUpdatedDate(new Date());
            b.setStatus(Integer.parseInt(request.getParameter("status")));
            b.setIsFeatured(Integer.parseInt(request.getParameter("is_featured")));

            bd.updateBlog(b);

            String combinedContent = request.getParameter("combinedContent");

            List<BlogDetail> existingDetails = bd.getBlogDetailsByBlogId(blogId);

            String[] contentLines = combinedContent.split("\n");
            for (int i = 0; i < contentLines.length; i++) {
                String content = contentLines[i].trim();
                if (i < existingDetails.size()) {
                    BlogDetail existingDetail = existingDetails.get(i);
                    existingDetail.setContent(content);
                    bd.updateBlogDetail(existingDetail);
                } else {
                    BlogDetail newDetail = new BlogDetail();
                    newDetail.setBlogId(blogId);
                    newDetail.setContent(content);
                    bd.insertBlogDetail(newDetail);
                }
            }

            for (int i = contentLines.length; i < existingDetails.size(); i++) {
                bd.deleteBlogDetail(existingDetails.get(i).getBlogDetailId());
            }

            session.setAttribute("message", "Blog post updated successfully!");
            response.sendRedirect("blog-details?blogId=" + blogId);
        } else if ("delete".equals(action)) {
            bd.deleteBlogDetailsByBlogId(blogId);
            bd.deleteBlog(blogId);
            session.setAttribute("message", "Blog post deleted successfully!");
            response.sendRedirect("blog-list");
        }
    }

}
