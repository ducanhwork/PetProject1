package dev.anhpd.entity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefeshRequest {
    String token;
}
