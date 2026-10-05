package org.classly.schoolstructureservice.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.classly.schoolstructureservice.profile.dto.ProfileResponseDTO;
import org.classly.schoolstructureservice.profile.service.ProfileService;
import org.classly.security.AuthenticatedUser;
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
    @Operation(summary = "Get the current user profile")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile returned", content = @Content(schema = @Schema(implementation = ProfileResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
        @ApiResponse(responseCode = "404", description = "Profile data not found", content = @Content)
    })
    public ResponseEntity<ProfileResponseDTO> getProfile(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(profileService.getProfile(user));
    }
}
