$ ?= require 'jquery' # For Node.js compatibility

$(document).ready ->

    $.get '/', (data) ->
        $('body').append "Successfully got the page."