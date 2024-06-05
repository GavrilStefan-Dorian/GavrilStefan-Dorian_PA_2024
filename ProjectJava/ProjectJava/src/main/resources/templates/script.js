    $(document).ready(function() {
        // GET author by ID
        $('#getForm').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            var id = $('#getForm input[name=id]').val();

            $.ajax({
                type: 'GET',
                url: "http://localhost:8080/authors/" + id,
                success: function(response) {
                    $('.get-result').empty(); // Clear previous result
                    $('.get-result').append("Retrieved author: " + JSON.stringify(response));
                },
                error: function() {
                    $('.get-result').empty();
                    $('.get-result').append("Error retrieving author.");
                }
            });
        });

        // GET all authors
        $('#getAllForm').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'GET',
                url: "http://localhost:8080/authors",
                success: function(response) {
                    $('.get-all-result').empty(); // Clear previous result
                    $('.get-all-result').append("Retrieved authors: " + JSON.stringify(response));
                },
                error: function() {
                    $('.get-all-result').empty();
                    $('.get-all-result').append("Error retrieving authors.");
                }
            });
        });

        // GET author count
        $('#getCountForm').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'GET',
                url: "http://localhost:8080/authors/count",
                success: function(response) {
                    $('.get-count-result').empty(); // Clear previous result
                    $('.get-count-result').append("Retrieved authors count: " + JSON.stringify(response));
                },
                error: function() {
                    $('.get-count-result').empty();
                    $('.get-count-result').append("Error retrieving authors count.");
                }
            });
        });

        // POST author
        $('#postForm').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                type: 'POST',
                url: "http://localhost:8080/authors",
                data: formData,
                success: function(response) {
                    $('.post-result').empty(); // Clear previous result
                    $('.post-result').append("New author ID: " + response);
                },
                error: function() {
                    $('.post-result').empty();
                    $('.post-result').append("Error creating author.");
                }
            });
        });

        // PUT author
        $('#putForm').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            var id = $('#putForm input[name=id]').val();
            var name = $('#putForm input[name=name]').val();
            $.ajax({
                type: 'PUT',
                url: "http://localhost:8080/authors/" + id + "?name=" + name,
                success: function(response) {
                    $('.put-result').empty(); // Clear previous result
                    $('.put-result').append("Updated author with ID: " + response);
                },
                error: function() {
                    $('.put-result').empty();
                    $('.put-result').append("Error updating author.");
                }
            });
        });

        // DELETE author
        $('#deleteForm').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            var id = $('#deleteForm input[name=id]').val();
            $.ajax({
                type: 'DELETE',
                url: "http://localhost:8080/authors/" + id,
                success: function(response) {
                    $('.delete-result').empty(); // Clear previous result
                    $('.delete-result').append("Removed author with ID: " + response);
                },
                error: function() {
                    $('.delete-result').empty();
                    $('.delete-result').append("Error removing author.");
                }
            });
        });
    });
