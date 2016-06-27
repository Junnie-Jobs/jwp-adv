package next.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrelloController {
	@RequestMapping("/")
    public String home() {
        return "index";
    }
	
	@RequestMapping("/boards")
    public String boards() {
        return "boards";
    }
	
	@RequestMapping("/boards/{id}")
    public String board(@PathVariable Long id) {
        return "board";
    }
}
