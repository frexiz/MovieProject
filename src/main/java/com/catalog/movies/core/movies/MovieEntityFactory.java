package com.catalog.movies.core.movies;

public interface MovieEntityFactory {
    static Movie transferDTOtoEntity(final MovieDTO movieDTO) {
        return new Movie(movieDTO.getName(), movieDTO.getYear(), movieDTO.getStudio(),
                movieDTO.getDescription(), movieDTO.getLanguage(), movieDTO.getDirector(), movieDTO.getDuration(), movieDTO.getPoster(),
                movieDTO.getRating(), movieDTO.getTrailer_url());
    }
}
