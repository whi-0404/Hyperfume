package com.Hyperfume.Backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
