package xyz.qakashi.qreceipt.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.UserService;
import xyz.qakashi.qreceipt.web.dto.user.ProfileViewDto;

import java.security.Principal;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.PRIVATE_API_ENDPOINT;

@RestController
@RequestMapping(PRIVATE_API_ENDPOINT + "/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/getMyProfile")
    public ResponseEntity<ProfileViewDto> getMyProfile(@ApiIgnore @Autowired Principal principal) {
        return ResponseEntity.ok(userService.getProfileByEmail(principal.getName()));
    }

    @PostMapping("/assignPicture/{uuid}")
    public ResponseEntity assignProfilePicture(
            @ApiIgnore @Autowired Principal principal,
            @PathVariable("uuid") UUID pictureUuid
    ) {
        userService.assignProfilePicture(principal.getName(), pictureUuid);
        return ResponseEntity.ok().build();
    }
}
