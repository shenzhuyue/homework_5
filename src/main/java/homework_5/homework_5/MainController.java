package homework_5.homework_5;


import homework_5.homework_5.Dao.stackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    private stackRepository StackRepository;

    @Autowired
    public void setStackRepository(stackRepository StackRepository){
        this.StackRepository = StackRepository;
    }

    private List<stack> userlist = new ArrayList<stack>();
    private List<stack> stackList = new ArrayList<>();
    private int id = 0;

    @RequestMapping({"/", "/login"})
    public String Login(Model model) {
        return "login";
    }

    @PostMapping(value = "/login/flag")
    public String Login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("admin") String admin,
                        @RequestParam("password") String password) throws IOException {
        if (admin.equals("Erii") && password.equals("2018211523")) {
            HttpSession session = request.getSession();
            session.setAttribute("user", "2018211523");
            return "redirect:/list";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/list")
    public ModelAndView Index(Model model) {
        stackList = StackRepository.findAll();
        model.addAttribute("user", stackList);
        ModelAndView modelAndView = new ModelAndView("list", "userModel", model);
        return modelAndView;
    }

    @GetMapping(value = "/list/update/{current_id}")
    public ModelAndView update(@PathVariable("current_id") int id, Model model) {
        /*
        Iterator<stack> iter = userlist.iterator();
        while (iter.hasNext()) {
            stack temp = iter.next();
            if (temp.getCurrent_id() == id) {
                model.addAttribute("user", temp);
                break;
            }
        }*/
        stack update_stack = StackRepository.findById(id).orElse(null);
        model.addAttribute("user",update_stack);
        ModelAndView modelAndView = new ModelAndView("update", "userModel", model);
        return modelAndView;
    }

    @PostMapping(value = "/list/del/{current_id}")
    public String delete(@PathVariable("current_id") int id) {
        /*
        Iterator<stack> iter = userlist.iterator();
        while (iter.hasNext()) {
            stack temp = iter.next();
            if (temp.getCurrent_id() == id) {
                iter.remove();
                break;
            }
        }*/
        StackRepository.deleteById(id);
        return "redirect:/list";
    }

    @RequestMapping("/add")
    public ModelAndView Add(Model model) {
        model.addAttribute("user", new stack());
        id += 1;
        ModelAndView modelAndView = new ModelAndView("add", "userModel", model);
        return modelAndView;
    }

    @PostMapping(value = "/add/post")
    public String add(stack temp) {
        userlist.add(temp);
        StackRepository.save(temp);
        return "redirect:/list";
    }

    @PostMapping(value = "/update/post/{current_id}")
    public String updatePost(@PathVariable("current_id") int id, stack contact, Model model) {
        int tempid = contact.getCurrent_id();
        /*Iterator<stack> iter = userlist.iterator();
        while (iter.hasNext()) {
            stack temp = iter.next();
            if (temp.getCurrent_id() == tempid) {
                Collections.replaceAll(userlist, temp, contact);
                break;
            }
        }*/
        StackRepository.save(contact);
        return "redirect:/list";
    }

    @RequestMapping("/update/{current_id}")
    public String Update(@PathVariable("current_id") int id, Model model) {
        return "update";
    }

    @RequestMapping("/list/update/{current_id}")
    public String ListUpdate(@PathVariable("current_id") int id, Model model) {
        return "update";
    }

    @RequestMapping(value = "/add/phone")
    public @ResponseBody
    void phone(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ADD_PHONE = request.getParameter("inputPhone");
        System.out.println("phone" + ADD_PHONE);
        Iterator<stack> iter = userlist.iterator();
        int flag = 1;
        while (iter.hasNext()) {
            stack temp = iter.next();
            if (temp.getPhone().equals(ADD_PHONE)) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("0");
                flag = 0;
                System.out.println("重复");
                break;
            }
        }
        ;
        if (flag == 1) {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("1");
        }
    }
}