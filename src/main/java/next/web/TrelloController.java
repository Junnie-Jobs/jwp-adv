package next.web;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.domain.board.BoardRepository;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class TrelloController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TrelloController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping("/")
    public String boards(@AuthenticationPrincipal Principal principal, Model model) {
		LOGGER.debug("login user : {}", principal);
		if (principal == null) {
			return "home";
		}
		
		model.addAttribute("boards", boardRepository.findAll());
        return "boards";
    }
	
	@RequestMapping("/b/{id}")
    public String board(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardRepository.findOne(id));
        return "board";
    }
}
