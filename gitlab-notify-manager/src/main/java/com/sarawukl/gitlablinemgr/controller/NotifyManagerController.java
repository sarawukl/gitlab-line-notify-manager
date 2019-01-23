package com.sarawukl.gitlablinemgr.controller;

import com.sarawukl.gitlablinemgr.model.Notify;
import com.sarawukl.gitlablinemgr.service.NotifyManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class NotifyManagerController {

    private final NotifyManagerService notifyManagerService;

    public NotifyManagerController(NotifyManagerService notifyManagerService) {
        this.notifyManagerService = notifyManagerService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page) {

        log.debug("Getting Index page");

        String url = request.getRequestURL().toString();
        url = url.endsWith("/") ? url.substring(0, url.lastIndexOf("/")) : url;
        model.addAttribute("url", url);

        int pageSize = 10;
        int currentPage = page.orElse(1);

        Page<Notify> notifyPage = notifyManagerService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("notifyPage", notifyPage);
        model.addAttribute("notifies", notifyPage.getContent());
        int totalPages = notifyPage.getTotalPages();
        if (currentPage > totalPages && notifyPage.getContent().size() > 0) {
            log.debug("Data not found");
            return "404";
        }
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping({"/new"})
    public String newNotify(Model model) {
        log.debug("Getting New Notify Form");
        model.addAttribute("notify", new Notify());
        return "notifyform";
    }

    @GetMapping("edit/{id}")
    public String editNotify(@PathVariable String id, Model model) {
        log.debug("Getting Edit Notify Form");
        Notify notify = notifyManagerService.findById(Long.valueOf(id));
        if (notify == null) {
            log.debug("Cannot Access Notify!");
            return "404";
        }
        model.addAttribute("notify", notify);
        return "notifyform";
    }

    @PostMapping("/notify")
    public String saveOrUpdate(@Valid @ModelAttribute Notify notify, BindingResult bindingResult) {

        log.debug("Post notify");
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "notifyform";
        }

        notifyManagerService.save(notify);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable String id) {

        log.debug("Deleting id: " + id);
        if (!notifyManagerService.deleteById(Long.valueOf(id))) {
            log.debug("Cannot Access Notify!");
            return "404";
        }
        return "redirect:/";
    }
}
