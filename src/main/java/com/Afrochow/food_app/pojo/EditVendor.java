package com.Afrochow.food_app.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditVendor extends EditUser {

    @NotBlank(message = "Business license number is required. If unavailable, use '000000'")
    private String businessLicenseNumber;

    @NotBlank
    private String vendorId;

    @NotBlank(message = "Business name is required")
    private String businessName;

    private String taxId;

    @Schema(description = "Front of ID card", type = "string", format = "binary")
    private MultipartFile idCardFrontFile;

    @Schema(description = "Back of ID card", type = "string", format = "binary")
    private MultipartFile idCardBackFile;

    @Schema(description = "Business logo", type = "string", format = "binary")
    private MultipartFile businessLogoFile;


}