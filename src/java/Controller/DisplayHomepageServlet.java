package Controller;

import Dal.BlogDAO;
import Dal.SliderDAO;
import Dal.SubjectDAO;
import Model.Blog;
import Model.Slider;
import Model.Subject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DisplayHomepageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initiate DAOs
        SubjectDAO sDAO = new SubjectDAO();
        BlogDAO bDAO = new BlogDAO();
        SliderDAO slDAO = new SliderDAO();

        // Get active sliders
        List<Slider> sliderList = slDAO.getAllActiveSliders();

        // Get featured subjects
        List<Subject> subjectList = sDAO.getFeatureSubjects();

        // Get latest blogs but only three blogs for display
        List<Blog> siderBlogList;
        try {
            siderBlogList = bDAO.getLatestUploadedBlogs().subList(0, 3);
        } catch (IndexOutOfBoundsException e) {
            siderBlogList = new ArrayList<>();
        }

        // Get top blogs but only six blogs for display
        List<Blog> mainBlogList;
        try {
            mainBlogList = bDAO.getTopBlogs().subList(0, 5);
        } catch (IndexOutOfBoundsException e) {
            mainBlogList = new ArrayList<>();
        }

        // Get featured blogs
        List<Blog> featuredBlogList = bDAO.getFeaturedBlogs();

        // Set attributes
        if (siderBlogList.isEmpty()) {
            request.setAttribute("siderBlogMessage", "siderBlogEmpty");
        } else {
            request.setAttribute("siderBlogList", siderBlogList);
        }

        if (mainBlogList.isEmpty()) {
            request.setAttribute("mainBlogMessage", "mainBlogEmpty");
        } else {
            request.setAttribute("mainBlogList", mainBlogList);
        }

        if (featuredBlogList.isEmpty()) {
            request.setAttribute("featuredBlogMessage", "featuredBlogEmpty");
        } else {
            request.setAttribute("featuredBlogList", featuredBlogList);
        }

        request.setAttribute("sliderList", sliderList);
        request.setAttribute("featuredSubjectList", subjectList);

        // Forward to homepage
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
