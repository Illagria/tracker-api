package us.rise8.tracker.api.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import us.rise8.tracker.api.init.dto.InfoDTO;
import us.rise8.tracker.config.CustomProperty;

@RestController
@RequestMapping("/init")
@Api(tags = "FrontEnd initialization")
public class InitController {

    private final CustomProperty property;

    @Autowired
    public InitController(CustomProperty property) {
        this.property = property;
    }

    @ApiOperation(value = "Classification context info",
            notes = "Returns classification context")
    @GetMapping
    public InfoDTO getInfo() {
        return new InfoDTO(property.getClassification(), property.getCaveat());
    }

}
