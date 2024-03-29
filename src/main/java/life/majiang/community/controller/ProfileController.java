package life.majiang.community.controller;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProfileController
{
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    private String profile(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "2") Integer size,
                           @PathVariable(name = "action") String action,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model) throws IOException
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
        {
            response.sendRedirect("/");
            return null;
        }

        if (action.equals("questions"))
        {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if (action.equals("replies"))
        {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PaginationDTO paginationDTO = questionService.listByUserID(user.getId(), page, size);
        model.addAttribute("paginationDTO", paginationDTO);

        return "profile";
    }
}
