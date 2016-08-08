package next.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

@RestController
public class ApiTrelloController {
	@RequestMapping("/servers/info")
	public Map<String, Object> info(HttpServletRequest request) {
		Map<String, Object> infos = Maps.newHashMap();
		infos.put("ServerName", request.getLocalName());
		infos.put("Port", request.getLocalPort());
		return infos;
	}
}
