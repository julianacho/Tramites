//  Inspired by Jonathan Moreira

//  http://dribbble.com/shots/1216346-Guided-tour-tooltip

// Twitter @YoannHELIN

$(document).ready(function () {
  var nbP = $('.containerT p').length;
  var w = parseInt($('.containerT p').css("width"));
  var max = (nbP - 1) * w;
  $("ul li[data-num='1']").addClass('active');
  
  
  $('body').on('click','.next', function(){
    var margL = parseInt($('.slider-turnT').css('margin-left'));
    var modulo = margL%w;
    if (-margL < max && modulo == 0) {
      margL -= w;
      $('.slider-turnT').animate({
        'margin-left':margL
      },300);
      $('ul li.active').addClass('true').removeClass('active');
      var x = -margL/w +1;
      $('ul li[data-num="'+x+'"]').addClass('active');
      
    }
    else  {}
  });

  $('body').on('click','.back', function(){
    var margL = parseInt($('.slider-turnT').css('margin-left'));
    var modulo = margL%w;
    console.log('a'+margL+"-"+modulo+" Masx: "+max);
    if (margL != 0 && margL <= max && modulo == 0) {
      margL += w;

      console.log('d'+margL+" Mod: "+modulo+" Masx: "+max);

      $('.slider-turnT').animate({
        'margin-left':margL
      },300);
      $('ul li.active').addClass('true').removeClass('active');
      var x = -margL/w +1;
      $('ul li[data-num="'+x+'"]').addClass('active');
      
    }
    else  {}
  });
  
  $('body').on('click','.closeT',function(){
    $('.containerT').animate({
      'opacity':0
    },600);
    $('.containerT').animate({
      'top':-1200
    }, {
      duration: 2300,
      queue: false
    });
  });
  
  $('body').on('click','.openT',function() {
    
    $('.containerT').animate({
      'opacity':1
    },400);
    $('.containerT').animate({
      'top':'1%' //manejo respecto al oton
    }, {
      duration: 800,
      queue: false
    });
  });
});