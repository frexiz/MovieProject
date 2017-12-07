$(document).ready(function () {
    var page;

    var url = window.location;

    //get first page of movies
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/rest/favourites?page=0",
        success: function (data) {
            page = data;
            console.log(page);
             setContentToMovieContainerFromResponse(page);
             setCurrentPage();
        },
        error: function (e) {
            console.log(e.message);
        }
    });

    //initFunctions
    function setContentToMovieContainerFromResponse(movies2) {
        console.log(movies2);
        clearMovieContainer();
        // clearMovieContainer();
        var movies = movies2.content;
        var $div = $('div[id^="movie"]:last');

        var num = parseInt($div.prop("id").match(/\d+/g)) + 1;

        movies.forEach(function (movie) {
          $div = $('div[id^="movie"]:last');

            // var num = parseInt($div.prop("id").match(/\d+/g)) + 1;
            num++;
            var $klon = $div.clone().prop('id', 'movie' + num);
            if (movie.poster != null) {
                if (movie.poster.length > 10) {
                    $klon.find('.favourites__image').prop('src', movie.poster);
                }
            }
            // $klon.find('#info').text(movie.description);
            $klon.find('.cinema-title').text(movie.name);

           var htmlMovieInfo = '/movie/infoById/'+ movie.id;
            $klon.find('.cinema__images').attr("href", htmlMovieInfo);

            $klon.find('.cinema-rating').text(movie.rating);


            // var length_categories = movie.categories.length;
            //
            // for (var i = 0; i < length_categories; i++) {
            //     // console.log(movie.categories[i]);
            //     $klon.find('.movie__categories').append("<a href=\"#\">" + movie.categories[i].name + "</a>");
            //
            // }
            // var length_actors = movie.actors.length;
            //
            // for (var i = 0; i < length_actors; i++) {
            //     // console.log(movie.categories[i]);
            //     $klon.find('.movie__actors').append("<a href=\"#\">" + movie.actors[i].fullName + "<span></span>" + "</a>");
            //
            // }


            var tmpName = movie.name.replace(/ /g, "+");
            // $klon.find('.movieName').find('a').attr("href", url+ "/movie/info/title=" + tmpName);

            // text('<a th:href="@{/movie/info/{title}(title = ' + movie.name +')}">' + movie.name + '</a>');
            // $klon.find('.movie-add').attr("href", url+ "movie/edit/" + tmpName);
            var tmpForFav = 'javascript: hello(' + movie.id + ');';
            $klon.find('.movie-add').attr("href", tmpForFav);
            //TODO to add color if added or not


// EDIT!!!
            var tmpForEdit = 'javascript: edit(' + movie.id + ');';
            $klon.find('.movie-EDIT').attr("href", tmpForEdit);


            var tmpForDelete = 'javascript: deleteMovie(' + movie.id + ');';
            $klon.find('.movie-delete').attr("href", tmpForDelete);

            // $klon.find('.movieName').find('a').attr("href", url+ "/movie/info/title=" + tmpName);
            $klon.find('#year').text(movie.year);
            $klon.find('#studio').text(movie.studio);
            $klon.find('#language').text(movie.language);
            $klon.find('#duration').text(movie.duration + ' min');
            $klon.find('#director').text(movie.director);
            $klon.find('#rating').text(movie.rating);
            if (movie.actors != null) {
                var actrs = movie.actors;
                var strActrsName = '';
                for (var i = 0; i < actrs.length; i++) {
                    if (i != actrs.length - 1) {
                        strActrsName += actrs[i].fullName + ", ";
                    } else {
                        strActrsName += actrs[i].fullName;

                    }
                }
                $klon.find('#actors').text(strActrsName);

            }

            if (movie.categories != null) {
                var ctgrs = movie.categories;
                var strCatgs = '';
                for (var i = 0; i < ctgrs.length; i++) {
                    if (ctgrs.length > 1) {
                        if (i != ctgrs.length - 1) {
                            strCatgs += ctgrs[i].name + ", ";
                        } else {
                            strCatgs += ctgrs[i].name;

                        }
                    } else {
                        strCatgs = ctgrs[0].name;
                    }

                }
                $klon.find('#categories').text(strCatgs);

            }


            var $moviesContainre = $('#favourites-container');
            $moviesContainre.append($klon);

        });

    }




    function clearMovieContainer() {
        $("#movie-container").html("");
    }


    //functions for paginations
    function setCurrentPage() {
        clearPaginationLiDisabledClasses();
        window.scrollTo(0, 0);
        if (page.number == 0) {
            $('#bottom-previous-page').addClass('disabled');
        }
        if (page.number == (page.totalPages - 1)) {
            $('#bottom-next-page').addClass('disabled');
        }
        if (page.number > 0 && page.number < page.totalPages - 1) {
            clearPaginationLiDisabledClasses();
        }
    }

    $("#bottom-previous-page").click(function () {
        if (!page.first) {
            var pageNumber = Number(page.number - 1);
            var tempUrl = url + "rest/favourites?page=" + pageNumber;
            $.ajax({
                type: "GET",
                url: tempUrl,
                success: function (data) {
                    page = data;
                    setContentToMovieContainerFromResponse(page);
                    setCurrentPage();
                },
                error: function (e) {
                    console.log(e.message);
                }
            });
        }
    });
    //
    // $("#bottom-first-page").click(function () {
    //     if (!page.first) {
    //         var tempUrl = url + "rest/favourites?page=" + pageNumber;
    //         $.ajax({
    //             type: "GET",
    //             url: tempUrl,
    //             success: function (data) {
    //                 page = data;
    //                 setContentToMovieContainerFromResponse(page);
    //                 setCurrentPage();
    //             },
    //             error: function (e) {
    //                 console.log(e.message);
    //             }
    //         });
    //     }
    // });

    $("#bottom-next-page").click(function () {
        if (!page.last) {
            var pageNumber = Number(page.number + 1);
            var tempUrl = url + "rest/favourites?page=" + pageNumber;
            $.ajax({
                type: "GET",
                url: tempUrl,
                success: function (data) {
                    page = data;
                    setContentToMovieContainerFromResponse(page);
                    setCurrentPage();
                },
                error: function (e) {
                    console.log(e.message);
                }
            });
        }
    });

    // $("#bottom-last-page").click(function () {
    //     if (!page.last) {
    //         var pageNumber = Number(page.totalPages - 1);
    //         var tempUrl = url + "rest/favourites?page=" + pageNumber;
    //         $.ajax({
    //             type: "GET",
    //             url: tempUrl,
    //             success: function (data) {
    //                 page = data;
    //                 setContentToMovieContainerFromResponse(page);
    //                 setCurrentPage();
    //             },
    //             error: function (e) {
    //                 console.log(e.message);
    //             }
    //         });
    //     }
    // });

    function clearPaginationLiDisabledClasses() {
        $('#bottom-previous-page').removeClass();
        $('#bottom-next-page').removeClass();
    }
});

