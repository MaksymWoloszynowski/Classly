package org.classly.schoolstructureservice.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.classly.schoolstructureservice.statistics.dto.StatisticsResponseDTO;
import org.classly.schoolstructureservice.statistics.service.StatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Tag(name = "Statistics", description = "School structure summary statistics")
@SecurityRequirement(name = "cookieAuth")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/")
    @Operation(summary = "Get school statistics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics returned", content = @Content(schema = @Schema(implementation = StatisticsResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Admin role required", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}
