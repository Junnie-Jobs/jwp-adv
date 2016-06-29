package next.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.domain.BoardRepository;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class TrelloController {
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping("/")
    public String boards(Model model) {
		model.addAttribute("boards", boardRepository.findAll());
        return "boards";
    }
	
	@RequestMapping("/home")
    public String home() {
        return "home";
    }
	
	@RequestMapping("/b/{id}")
    public String board(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardRepository.findOne(id));
        return "board";
    }
}
