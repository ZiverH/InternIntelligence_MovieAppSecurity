package org.app.movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieResponsetDto {

    private Long id;
    private String title;
    private String director;
    private String year;
    private String genre;
    private String imdb;
}
