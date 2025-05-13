package com.Hyperfume.Backend.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerfumeImageRequest {
    int perfumeId;

    MultipartFile imageFile;

    List<MultipartFile> imageFiles;
}
