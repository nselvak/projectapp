package vttp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.project.model.Music;
import vttp.project.model.Search;
import vttp.project.model.User;
import vttp.project.service.SearchService;

@Controller
@RequestMapping
public class SearchController {

    @Autowired
    SearchService svc;

    @PostMapping("/process_request")
    public String search( Search search, 
                            Model model, User user) {
        
        List<Music> last = svc.searchMusic(search, user);
        if (last.isEmpty()) {
            return "error";
        }
                        
        model.addAttribute("list", last);
                        
        return "result";
                                
    }
    
}
