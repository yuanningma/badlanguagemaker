// Global reference to the canvas element.
let canvas;

// Global reference to the canvas' context.
let ctx;

// Global reference to the image that would be shown to the user.
let image = new Image;

let panning = false;

$(document).ready(() => {

    //Setting up the canvas.
    canvas = $('#image')[0];
    canvas.width = 2000;
    canvas.height = 2000;

    ctx = canvas.getContext('2d');
    trackTransforms(ctx);

    draw();

   let lastX = canvas.width / 2;
   let lastY = canvas.height / 2;

    let scrolled = false;
    let mousedown;

    canvas.addEventListener('mousedown',function(evt){
        lastX = evt.pageX - canvas.offsetLeft;
        lastY = evt.pageY - canvas.offsetTop;
        mousedown = ctx.transformedPoint(lastX,lastY);
        scrolled = false;
    });

    canvas.addEventListener('mousemove',function(evt){
        lastX = evt.pageX - canvas.offsetLeft;
        lastY = evt.pageY - canvas.offsetTop;
        scrolled = true;
        if (mousedown != null){
            panning = true;
            let pt = ctx.transformedPoint(lastX,lastY);
            ctx.translate(pt.x-mousedown.x,pt.y-mousedown.y);
            draw();
        }
    });

    canvas.addEventListener('mouseup',function(evt){
        if(panning) {
            panning = false;
            mousedown = null;
        }
        if (!scrolled) zoom(evt.shiftKey ? -1 : 1 );
    });

    let scaleFactor = 1.1;

    let zoom = function(clicks){
        let pt = ctx.transformedPoint(lastX,lastY);
        ctx.translate(pt.x,pt.y);
        let factor = Math.pow(scaleFactor,clicks);
        ctx.scale(factor,factor);
        ctx.translate(-pt.x,-pt.y);
        draw();
    }

    let handleScroll = function(evt){
        let delta = evt.wheelDelta ? evt.wheelDelta/40 : evt.detail ? -evt.detail : 0;
        if (delta) zoom(delta);
        return evt.preventDefault() && false;
    };

    canvas.addEventListener('DOMMouseScroll',handleScroll,false);
    canvas.addEventListener('mousewheel',handleScroll,false);
});

function draw(){

    // Clear the entire canvas
    let p1 = ctx.transformedPoint(0,0);
    let p2 = ctx.transformedPoint(canvas.width,canvas.height);
    ctx.clearRect(p1.x,p1.y,p2.x,p2.y);

    ctx.drawImage(image,0,0);

}

image.src = "https://img.purch.com/w/660/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA1MC8zOTAvb3JpZ2luYWwvY2hlc3QteHJheS0xMTEwMjYtMDIuanBn";

// Adds ctx.getTransform() - returns an SVGMatrix
// Adds ctx.transformedPoint(x,y) - returns an SVGPoint
function trackTransforms(ctx){
    let svg = document.createElementNS("http://www.w3.org/2000/svg",'svg');
    let xform = svg.createSVGMatrix();
    ctx.getTransform = function(){ return xform; };

    let savedTransforms = [];
    let save = ctx.save;
    ctx.save = function(){
        savedTransforms.push(xform.translate(0,0));
        return save.call(ctx);
    };

    let restore = ctx.restore;
    ctx.restore = function(){
        xform = savedTransforms.pop();
        return restore.call(ctx);
    };

   let scale = ctx.scale;
    ctx.scale = function(sx,sy){
        xform = xform.scaleNonUniform(sx,sy);
        return scale.call(ctx,sx,sy);
    };

    let rotate = ctx.rotate;
    ctx.rotate = function(radians){
        xform = xform.rotate(radians*180/Math.PI);
        return rotate.call(ctx,radians);
    };

    let translate = ctx.translate;
    ctx.translate = function(dx,dy){
        xform = xform.translate(dx,dy);
        return translate.call(ctx,dx,dy);
    };

    let transform = ctx.transform;
    ctx.transform = function(a,b,c,d,e,f){
        var m2 = svg.createSVGMatrix();
        m2.a=a; m2.b=b; m2.c=c; m2.d=d; m2.e=e; m2.f=f;
        xform = xform.multiply(m2);
        return transform.call(ctx,a,b,c,d,e,f);
    };

    let setTransform = ctx.setTransform;
    ctx.setTransform = function(a,b,c,d,e,f){
        xform.a = a;
        xform.b = b;
        xform.c = c;
        xform.d = d;
        xform.e = e;
        xform.f = f;
        return setTransform.call(ctx,a,b,c,d,e,f);
    };

    let pt  = svg.createSVGPoint();
    ctx.transformedPoint = function(x,y){
        pt.x=x; pt.y=y;
        return pt.matrixTransform(xform.inverse());
    }
}
