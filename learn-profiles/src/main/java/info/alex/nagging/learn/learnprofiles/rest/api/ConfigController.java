package info.alex.nagging.learn.learnprofiles.rest.api;

import info.alex.nagging.learn.learnprofiles.conf.ConfData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private ConfData confData;

    @GetMapping("/config")
    public String getConfig() {
        return confData.toString();
    }
}
