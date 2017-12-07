$(document).ready(function () {
    var page;
    var category = "";
    var actors = "";
    var flagSearch = "";
    var url = window.location;

    //get first page of movies
    $.ajax({
        type: "GET",
        url: url + "/rest/movies?page=0&categoryName=" + category + "&actorName=" + actors,
        success: function (data) {
            var textSearch = $("#input-search").val();
            $("#actors-search").css('color', 'black');
            $("#category-search").css('color', 'black');
            flagSearch="";
            page = data;
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
                    $klon.find('.movie__image').prop('src', movie.poster);
                }
            }
            $klon.find('#info').text(movie.description);
            $klon.find('.movie__title').text(movie.name);
            $klon.find('.movie__year').text(movie.year);
            $klon.find('.movie__time').text(movie.duration);
            $klon.find('.movie__rating').text(movie.rating);
            $klon.find('.movie__studio').text(movie.studio);
            $klon.find('.movie__director').text(movie.director);
            $klon.find('.movie__language').text(movie.language);


            var length_categories = movie.categories.length;

            for (var i = 0; i < length_categories; i++) {
                // console.log(movie.categories[i]);
                var categoryId = movie.categories[i].id;
                var categoryNameClass = movie.categories[i].name;

                $klon.find('.movie__categories').append("<a id='"+ categoryNameClass +"' class='lala'>" + movie.categories[i].name + "</a>");
                               //$('#').attr('href', burl);

            }
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

//INFO
            var tmpForInfo = 'javascript: info(' + movie.id + ');';
            $klon.find('.movie__title').attr("href", tmpForInfo);

//DELETE
            var tmpForDelete = 'javascript: deleteMovie(' + movie.id + ');';
            $klon.find('.movie-delete').attr("href", tmpForDelete);

//MODAL TRAILER
            var uTrailer = 'https://www.youtube.com/watch?v=' + movie.trailer_url ;
            $klon.find('.trailer-sample-test').attr("href", uTrailer);

            $('.trailer-sample-test').magnificPopup({
                disableOn: 700,
                type: 'iframe',
                mainClass: 'mfp-fade',
                removalDelay: 160,
                preloader: false,

                fixedContentPos: false
            });

            //show hide content
            $('.trailer-btn').click(function (e) {
                e.preventDefault();

                $(this).hide();
                $(this).parent().addClass('trailer-block--short').find('.hidden-content').slideDown(500);
            })

            ///////////////////////////////////////////////////////////////////////////////






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


            var $moviesContainre = $('#movie-container');
            $moviesContainre.append($klon);

        });

    }


    function clearMovieContainer() {
        $("#movie-container").html("");
    }




    // $("#movie-container").on('click', '.movie__title', function () {
    //     event.preventDefault();
    //     console.log("ASDASDASDASd");
    //     console.log($(this).html());
    // });


    //SEARCH BAR
    $("#category-search").on('click', function () {
        event.preventDefault();
        console.log("category");
        flagSearch = "category";
        $("#category-search").css('color', 'red');
        $("#actors-search").css('color', 'black');



    });



    $("#actors-search").on('click', function () {
        event.preventDefault();
        $("#actors-search").css('color', 'red');
        $("#category-search").css('color', 'black');

        console.log("actors");
        flagSearch = "actor"
    });

    $("#do-search").on('click', function () {
        event.preventDefault();
        var textSearch = $("#input-search").val();
        console.log(textSearch);
        if (flagSearch == 'category') {
            category = textSearch;
        } else if (flagSearch == 'actor') {
            actors = textSearch;
        }
        $.ajax({
            type: "GET",
            url: url + "/rest/movies?page=0&categoryName=" + category + "&actorName=" + actors,
            success: function (data) {
                page = data;
                setContentToMovieContainerFromResponse(page);
                setCurrentPage();
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });

    $("#do-clear").on('click', function () {
        event.preventDefault();
        $("#input-search").val("");
        $("#category-search").css('color', 'black');
        $("#actors-search").css('color', 'black');
        category = "";
        actors = "";
        flagSearch = "";
        $.ajax({
            type: "GET",
            url: url + "/rest/movies?page=0&categoryName=" + category + "&actorName=" + actors,
            success: function (data) {
                page = data;
                setContentToMovieContainerFromResponse(page);
                setCurrentPage();
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });

    //functions for paginations
    function setCurrentPage() {
        clearPaginationLiDisabledClasses();
        window.scrollTo(0, 0);
        if (page.number == 0) {
            $('#top-first-page').addClass('disabled');
            $('#bottom-first-page').addClass('disabled');
            $('#top-previous-page').addClass('disabled');
            $('#bottom-previous-page').addClass('disabled');
        }
        if (page.number == (page.totalPages - 1)) {
            $('#top-last-page').addClass('disabled');
            $('#bottom-last-page').addClass('disabled');
            $('#top-next-page').addClass('disabled');
            $('#bottom-next-page').addClass('disabled');
        }
        if (page.number > 0 && page.number < page.totalPages - 1) {
            clearPaginationLiDisabledClasses();
        }
        $("#top-current-page").html(page.number + 1);
        $("#bottom-current-page").html(page.number + 1);
    }

    $("#top-previous-page, #bottom-previous-page").click(function () {
        if (!page.first) {
            var pageNumber = Number(page.number - 1);
            var tempUrl = url + "rest/movies?page=" + pageNumber + "&categoryName=" + category + "&actorName=" + actors;
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


    $("#top-first-page, #bottom-first-page").click(function () {
        if (!page.first) {
            var tempUrl = url + "rest/movies?page=0&categoryName=" + category + "&actorName=" + actors;
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

    $("#top-next-page, #bottom-next-page").click(function () {
        if (!page.last) {
            var pageNumber = Number(page.number + 1);
            var tempUrl = url + "rest/movies?page=" + pageNumber + "&categoryName=" + category + "&actorName=" + actors;
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

    $("#top-last-page, #bottom-last-page").click(function () {
        if (!page.last) {
            var pageNumber = Number(page.totalPages - 1);
            var tempUrl = url + "rest/movies?page=" + pageNumber + "&categoryName=" + category + "&actorName=" + actors;
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

    function clearPaginationLiDisabledClasses() {
        $('#top-first-page').removeClass();
        $('#bottom-first-page').removeClass();
        $('#top-previous-page').removeClass();
        $('#bottom-previous-page').removeClass();
        $('#top-last-page').removeClass();
        $('#bottom-last-page').removeClass();
        $('#top-next-page').removeClass();
        $('#bottom-next-page').removeClass();
    }


    //functions for modal
    $(".modal-backdrop, #modalId .close, #modalId .btn").click(function () {
        $("#modalId iframe").attr("src", jQuery("#modalId iframe").attr("src"));
        $('#modalId').fadeOut();
    });


    $('#movie-container').on("click", '.info-modal', function (event) {
        event.preventDefault();


        var tmpName = $(this).html().replace(/ /g, "+");
        // getMovie();
        // Open Bootstrap Modal
        openModel();
        // Get data from Server
        ajaxGet(tmpName);

    });

    // $('#movie-container').on("click", '.movie-edit', function (event) {
    //     event.preventDefault();
    //     console.log("Pesho");
    //
    // });

    // Open Bootstrap Modal
    function openModel() {
        console.log("lainaaaa");
        $("#modalId").modal();

    }

    // DO GET
    function ajaxGet(title) {
        $.ajax({
            type: "GET",
            url: url + "rest/info/" + title,
            success: function (data) {
                // fill data to Modal Body
                console.log(data);
                fillData(data);
            },
            error: function (e) {
                fillData(null);
            }
        });
    }


    function fillData(movie) {
        if (movie != null) {
            $(".modal-title").text(movie.name);
            $(".modal-body #greetingId").text("Trailer");
            var $frame = $("#iframe1");
            var uTrailer = 'https://www.youtube.com/embed/' + movie.trailer_url + '?rel=0';
            $frame.attr("src", uTrailer);

            // https://www.youtube.com/embed/AntcyqJ6brc?rel=0

        } else {
            $(".modal-title").text('Error 404');

            $(".modal-body #greetingId").text("Can Not Get Data from Server!");
        }
    }

})

