jQuery(document).ready(function () {
    if (jQuery('.show-viewed-recently').length) {
        jQuery('.show-viewed-recently').each(function() {
            var wrapper = jQuery(this);

            if (wrapper.length) {
                jQuery.ajax({
                    type : "post",
                    dataType : "json",
                    url : qhAjax.ajaxurl,
                    data : { 
                        action: "get_recently_products", 
                        columns: wrapper.data('columns'), 
                        rows: wrapper.data('rows') 
                    },
                    success: function(res) {
                        var options = wrapper.data("flickity-options");
                        wrapper.flickity('destroy');
                        wrapper.html(res.content);
                        wrapper.flickity(options);
                        //console.log(res);
                    }
                })
            }
        });
    }
})