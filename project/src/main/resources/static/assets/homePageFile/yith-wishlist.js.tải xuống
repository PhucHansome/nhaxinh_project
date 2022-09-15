jQuery(document).ready(function () {
    jQuery('body').on('added_to_wishlist removed_from_wishlist', function() {
        jQuery.ajax({
            data: {
              action: 'qh_update_wishlist_view',
            },
            url: yith_wcwl_l10n.ajax_url,
        }).done(function (data) {
            jQuery('#overlay-right-sidebar .sidebar-menu').html(data);
            if (jQuery('.page-template-page-my-account [data-flickity-options]').length) {
                jQuery('.page-template-page-my-account [data-flickity-options]').each(function() {
                    var o = jQuery(this).data("flickity-options");
                    jQuery(this).flickity(o);
                })
            }
        })
    })


    jQuery( ".button-view-order-detail" ).on( "click", function() {
        jQuery('#order-detail-modal').toggleClass('active');
    });

    if (jQuery( "#btn-view-stock-modal" ).length) {
        jQuery("#btn-view-stock-modal" ).on( "click", function(e) {
            e.preventDefault();
            jQuery('.popup_stock_wraper').addClass('active');
            if (gtag && typeof gtag === 'function') { //Track GA
                gtag('event', 'view_product_stock', {
                    'event_category': 'Product',
                    'event_label': jQuery('h1.product-title').text(),
                });
            }
        });
        jQuery("button#close").on( "click", function() {
            jQuery('.popup_stock_wraper').removeClass('active');
        });
    }


    if (jQuery('.btn-status').length) {
        jQuery('.btn-status').on('click', function() {
            jQuery('.btn-status').removeClass('active');
            jQuery(this).addClass('active');

            var status = jQuery(this).data('status');
            if (status == 'all') {
                jQuery('.order-items-wrapper .order-item').fadeIn();
            } else {
                jQuery('.order-items-wrapper .order-item[data-order-status="' + status + '"]').fadeIn();
                jQuery('.order-items-wrapper .order-item[data-order-status!="' + status + '"]').fadeOut();
            }
        })
    }

    if (jQuery('[data-flickity-el]').length) {
        var flickityEl = jQuery('[data-flickity-el]').data('flickity-el');
        if (jQuery(flickityEl).length) {
            jQuery(flickityEl).on( 'change.flickity', function( event, index ) {
                //console.log(flickityEl, index, jQuery('.product-thumbnails .col:nth-child(' + (index + 1) + ')').length);
                if (index == 0) {
                    jQuery('.vertical-thumbnails').animate({scrollTop: 0},'slow');
                } else {
                    jQuery('.vertical-thumbnails').animate({scrollTop: jQuery('.product-thumbnails .col:nth-child(' + (index + 1) + ')').offset().top - 50},'slow');
                }
                
            });

            jQuery('[data-flickity-el]').on('click', function() {
                jQuery(flickityEl).flickity(jQuery(this).data('action'));
            })
        }
    }
})