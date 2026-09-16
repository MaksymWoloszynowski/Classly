package org.edziennik.schoolstructureservice.profile.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.edziennik.schoolstructureservice.profile.dto.ProfileResponseDTO;
import org.edziennik.schoolstructureservice.profile.service.ProfileService;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User profile", description = "Get user profile")
@SecurityRequirement(name = "cookieAuth")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping("/my-profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(profileService.getProfile(user));
    }
}
